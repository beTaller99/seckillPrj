package prj.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: com.asher.KafkaProducer
 * @Description: 此类作为kafka的生产Demo类，与本项目主要逻辑无关
 * @version: jdk11
 * @author: asher
 * @date: 2022/12/4 13:40
 */
@RestController
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // 发送消息
    @GetMapping("/test/{message}")
    public void sendMessage1(@PathVariable("message") String normalMessage) {
        kafkaTemplate.send("topic1", normalMessage);
    }
}