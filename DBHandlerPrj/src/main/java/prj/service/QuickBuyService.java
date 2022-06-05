package prj.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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
@RabbitListener(queues = "buyRecordQueue")
public class QuickBuyService {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private BuyRecordRepo buyRecordRepo;

    @RabbitHandler
    public void saveBuyRecord (BuyRecord record) {
        buyRecordRepo.save(record);
    }

}
