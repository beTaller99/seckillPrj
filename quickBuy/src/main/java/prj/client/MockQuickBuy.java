package prj.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @className: QucikBuyThread
 * @Description: TODO
 * @version: jdk11
 * @author: asher
 * @date: 2022/6/4 14:53
 */
class QucikBuyThread extends Thread{
    public void run () {
        RestTemplate restTemplate = new RestTemplate();
        String user = Thread.currentThread().getName();

        //restTemplate.getForEntity()方法模拟秒杀请求
        ResponseEntity<String> entity = restTemplate.getForEntity(
                "http://localhost:80/quickBuy/Computer/" + user,
                String.class
        );
        System.out.println(entity.getBody());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class MockQuickBuy {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new QucikBuyThread().start();
        }
    }
}
