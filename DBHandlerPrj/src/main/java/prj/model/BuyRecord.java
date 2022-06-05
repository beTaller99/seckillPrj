package prj.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @className: BuyRecord
 * @Description: TODO
 * @version: jdk11
 * @author: asher
 * @date: 2022/6/4 14:47
 */
@Entity
@Table(name = "buyrecord")
public class BuyRecord implements Serializable {



    @Column(name = "item")
    private String item;
    @Id
    @Column(name = "person")
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
