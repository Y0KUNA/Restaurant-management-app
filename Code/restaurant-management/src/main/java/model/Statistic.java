package model;
import java.io.Serializable;
import java.time.LocalDate;

public class Statistic extends Dish implements Serializable {
    private LocalDate startTime;
    private LocalDate endTime;
    private int orderCount;
    private int revenue;

    // Constructor không tham số
    public Statistic() {
        super();
    }

    // Constructor đầy đủ
    public Statistic(Dish dish, LocalDate startTime, LocalDate endTime, int orderCount, int revenue) {
        super(dish.getId(), dish.getName(), dish.getType(), dish.getPrice(), dish.getDes());
        this.startTime = startTime;
        this.endTime = endTime;
        this.orderCount = orderCount;
        this.revenue = revenue;
    }


    // Getter và Setter
    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}

    

