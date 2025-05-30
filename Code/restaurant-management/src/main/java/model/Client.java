package model;
import java.io.Serializable;

public class Client implements Serializable {
    private int cid;
    private String name;
    private String tel;
    private String address;
    private String email;

    // Constructor không tham số
    public Client() {
    }

    // Constructor đầy đủ tham số
    public Client(int cid, String name, String tel, String address, String email) {
        this.cid = cid;
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.email = email;
    }

    // Getter và Setter
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
