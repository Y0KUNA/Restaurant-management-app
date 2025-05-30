package model;
import java.io.Serializable;

public class Table implements Serializable {
    private int tid;
    private String position;
    private int seatNumber;

    // Constructor không tham số
    public Table() {
    }

    // Constructor đầy đủ tham số
    public Table(int tid, String position, int seatNumber) {
        this.tid = tid;
        this.position = position;
        this.seatNumber = seatNumber;
    }

    // Getter và Setter
    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
