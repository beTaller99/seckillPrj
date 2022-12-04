package prj.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import prj.model.BuyRecord;
import prj.repo.BuyRecordRepo;
import prj.util.JsonUtil;

/**
 * @className: QuickBuyService
 * @Description: TODO
 * @version: jdk11
 * @author: asher
 * @date: 2022/6/4 14:38
 */
@Component
/*@RabbitListener(queues = "buyRecordQueue")*/

public class QuickBuyService {

    @Autowired
    private BuyRecordRepo buyRecordRepo;

    @KafkaListener(topics = "purchaseRecord")
    public void saveBuyRecord (ConsumerRecord<String, Object> record) {
        buyRecordRepo.save(JsonUtil.toObject(record.value().toString(), BuyRecord.class));
    }
}
