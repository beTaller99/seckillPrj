package prj.model;



import java.io.Serializable;

/**
 * @className: BuyRecord
 * @Description: TODO
 * @version: jdk11
 * @author: asher
 * @date: 2022/5/27 18:40
 */

public class BuyRecord implements Serializable {
    private String item;
    private String person;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
