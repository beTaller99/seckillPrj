package prj.limiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @className: TokenBucketLimiter
 * @Description: TODO
 * @version: jdk11
 * @author: asher
 * @date: 2022/12/3 19:28
 */
@Slf4j
@Repository
public class TokenBucketLimiter {
    // 上一次令牌发放时间
    public long lastTime = System.currentTimeMillis();
    // 桶的容量
    public int capacity = 10;
    // 令牌生成速度 个/s
    public int rate = 4;
    // 当前令牌数量
    public AtomicInteger tokens = new AtomicInteger(0);
    ;

    //返回值说明：
    // false 没有被限制到
    // true 被限流
    public synchronized boolean isLimited(long taskId, int applyCount) {
        long now = System.currentTimeMillis();
        //时间间隔,单位为 ms
        long gap = now - lastTime;

        //计算时间段内的令牌数
        int reverse_permits = (int) (gap * rate / 1000);
        int all_permits = tokens.get() + reverse_permits;
        // 当前令牌数
        tokens.set(Math.min(capacity, all_permits));
        log.info("tokens {} capacity {} gap {} ", tokens, capacity, gap);

        if (tokens.get() < applyCount) {
            // 若拿不到令牌,则拒绝
             log.info("被限流了.." + taskId + ", applyCount: " + applyCount);
            return true;
        } else {
            // 还有令牌，领取令牌
            tokens.getAndAdd(-applyCount);
            lastTime = now;

            log.info("剩余令牌.." + tokens);
            return false;
        }

    }
}
