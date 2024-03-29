package prj.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @className: KafkaConsumer
 * @Description: 此类作为kafka的消费Demo类，与本项目主要逻辑无关
 * @version: jdk11
 * @author: asher
 * @date: 2022/12/4 13:40
 */
@Component
public class KafkaConsumer {
    // 消费监听
    @KafkaListener(topics = {"topic1"})
    public void listen1(ConsumerRecord<String, String> record) {
        System.out.println(record.value());
    }
}