package model;
import View.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Receipt implements Serializable {
    private int RID;
    private int subTotal;
    private int discount;
    private int total;
    private LocalDateTime date;
    private Order o;
    private Client c;
    private User u;

    // Constructor đầy đủ
    public Receipt(int RID, int subTotal, int discount, int total, LocalDateTime date, Order o, Client c, User u) {
        this.RID = RID;
        this.subTotal = subTotal;
        this.discount = discount;
        this.total = total;
        this.date = date;
        this.o = o;
        this.c = c;
        this.u = u;
    }

    // Constructor mặc định
    public Receipt() {
    }

    // Getter và Setter
    public int getRID() {
        return RID;
    }

    public void setRID(int RID) {
        this.RID = RID;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Order getO() {
        return o;
    }

    public void setO(Order o) {
        this.o = o;
    }

    public Client getC() {
        return c;
    }

    public void setC(Client c) {
        this.c = c;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }
}

