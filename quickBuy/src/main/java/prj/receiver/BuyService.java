package prj.receiver;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import prj.model.BuyRecord;

import java.util.concurrent.TimeUnit;

/**
 * @className: BuyService
 * @Description: 调用redis和lua脚本实现限流和秒杀
 * @version: jdk11
 * @author: asher
 * @date: 2022/5/26 10:42
 */
@Service
public class BuyService {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    AmqpTemplate amqpTemplate;

    public boolean canVisit(String item, int limitTime, int limitNum) {
        long curTime = System.currentTimeMillis();

        redisTemplate.opsForZSet().add(item, curTime, curTime);

    //解释一下为啥是stop的下标是curTime - limitTime*1000：这样减了20秒之后的结果就是当前时间20秒之前那个时间点
    //20秒之前的时间点记作 x， 那么x到curTime的这20秒内的请求就不会被删除。就能实现“移除当前时间点到其前20秒之外的请求”
        redisTemplate.opsForZSet().removeRangeByScore(item, 0, curTime - limitTime*1000);
        Long count = redisTemplate.opsForZSet().zCard(item);
        redisTemplate.expire(item, limitTime, TimeUnit.SECONDS);
        return limitNum >= count;
    }

    public String buy(String item, String person) {
        String luaScript = "local item = KEYS[1] \n" +
                            "local person = ARGV[1] \n" +
                            "local left = tonumber(redis.call('get', item)) \n" +
                            "if (left >= 1)  \n" +
                            "then redis.call('decrby', item, 1) \n" +
                            " redis.call('rpush', 'personList', person) \n" +
                            " return 1 \n" +
                            "else \n" +
                            "return 0\n" +
                            "end\n" +
                            "\n"
                ;
        String key = item;
        String args = person;
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<String>();
        redisScript.setScriptText(luaScript);
        Object luaResult = redisTemplate.execute((RedisConnection connection) -> connection.eval(
                redisScript.getScriptAsString().getBytes(),
                ReturnType.INTEGER,
                1,
                key.getBytes(),
                args.getBytes()
        ));

        if (!luaResult.toString().equals("0")) {
            BuyRecord record = new BuyRecord();
            record.setItem(item);
            record.setPerson(person);
            amqpTemplate.convertAndSend("myExchange", "buyRecordQueue", record);
        }
        return luaResult.toString();
    }

}
