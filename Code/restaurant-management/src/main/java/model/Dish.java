package model;
import java.io.Serializable;

public class Dish implements Serializable {
    private int id;
    private String name;
    private String type;
    private int price;
    private String des;


    public Dish() {
    }


    public Dish(int id, String name, String type, int price, String des) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.des = des;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
