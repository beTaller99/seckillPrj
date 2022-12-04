package prj.receiver;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import prj.model.PurchaseRecord;
import prj.util.JsonUtil;

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
    KafkaTemplate<String, String> kafkaTemplate;

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
            PurchaseRecord record = new PurchaseRecord();
            record.setItem(item);
            record.setPerson(person);
            kafkaTemplate.send("purchaseRecord", JsonUtil.toJson(record));
        }
        return luaResult.toString();
    }

}
