package model;
import java.io.Serializable;
import java.util.List;
import model.*;


public class Order implements Serializable {
    private int oid;
    private Client c;
    private Table t;
    private Boolean isPaid = false;
    private List<OrderedDish> od;

    // Constructor không tham số
    public Order() {
    }

    // Constructor đầy đủ tham số
    public Order(int oid, Client c, Table t, List<OrderedDish> od) {
        this.oid = oid;
        this.c = c;
        this.t = t;
        this.od = od;
    }

    // Getter và Setter
    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public Client getC() {
        return c;
    }

    public void setC(Client c) {
        this.c = c;
    }

    public Table getT() {
        return t;
    }

    public void setT(Table t) {
        this.t = t;
    }

    public List<OrderedDish> getOd() {
        return od;
    }

    public void setOd(List<OrderedDish> od) {
        this.od = od;
    }
    public Boolean getPaid(){
        return isPaid;
    }
    public void setPaid(Boolean paid){
        isPaid = paid;
    }
    public String orderString(){
        String ans="";
        for (OrderedDish odd : this.od){
            ans+=odd.getD().getName();
            ans+=" X "+String.valueOf(odd.getQuantity())+"\n";
        }
        return ans;
    }
}

