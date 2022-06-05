package prj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prj.receiver.BuyService;

/**
 * @className: Controller
 * @Description: TODO
 * @version: jdk11
 * @author: asher
 * @date: 2022/5/26 10:45
 */
@RestController
public class Controller {
    @Autowired
    BuyService buyService;

    @RequestMapping("quickBuy/{item}/{person}")
    public String qucikBuy(@PathVariable String item, @PathVariable String person) {
        if (buyService.canVisit(item, 20, 100)) {
            String result = buyService.buy(item, person);
            /*if (!result.equals("0")) {
                return person + "success";
            } else {
                return ;
            }*/
            return !result.equals("0") ? person + "success" : person + "fail";
        } else {
            return person + "fail";
        }
    }
}
