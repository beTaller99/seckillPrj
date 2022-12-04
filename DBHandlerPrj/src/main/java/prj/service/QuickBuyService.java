package prj.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import prj.model.BuyRecord;
import prj.repo.BuyRecordRepo;

/**
 * @className: QuickBuyService
 * @Description: TODO
 * @version: jdk11
 * @author: asher
 * @date: 2022/6/4 14:38
 */
@Service
/*@RabbitListener(queues = "buyRecordQueue")*/
@KafkaListener(topics = "purchaseRecord")
public class QuickBuyService {
    /*@Autowired
    private AmqpTemplate amqpTemplate;*/


    @Autowired
    private BuyRecordRepo buyRecordRepo;

/*    @RabbitHandler
    public void saveBuyRecord (BuyRecord record) {
        buyRecordRepo.save(record);
    }*/

    @KafkaHandler
    public void saveBuyRecord (ConsumerRecord<String, Object> record) {
        buyRecordRepo.save((BuyRecord) record.value());
    }
}
