package model;
import java.io.Serializable;
import java.time.LocalDateTime;
import model.*;



public class OrderedDish implements Serializable {
    private int odid;
    private Dish d;
    private LocalDateTime orderTime;
    private int quantity;
    private int price;
    private String note;

    // Constructor mặc định
    public OrderedDish() {
    }

    // Constructor đầy đủ tham số
    public OrderedDish(int odid, Dish d, LocalDateTime orderTime, int quantity, int price, String note) {
        this.odid = odid;
        this.d = d;
        this.orderTime = orderTime;
        this.quantity = quantity;
        this.price = price;
        this.note = note;
    }

    // Getters và Setters
    public int getOdid() {
        return odid;
    }

    public void setOdid(int odid) {
        this.odid = odid;
    }

    public Dish getD() {
        return d;
    }

    public void setD(Dish d) {
        this.d = d;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
