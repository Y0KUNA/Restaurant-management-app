package DAO;

import static DAO.DAO.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.*;

public class TableDAO extends DAO{
	
	public TableDAO() {
		super();
	}
	public List<Table> getTableList(){
            List<Table> tableList = new ArrayList<>();

            String sql = "SELECT * FROM tblTable";

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Table table = new Table();
                    table.setTid(rs.getInt("TID"));
                    table.setPosition(rs.getString("position"));
                    table.setSeatNumber(rs.getInt("seatNumber"));
                    tableList.add(table);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return tableList;

            
        }
        public List<Order> getOrderList(){
            List<Order> orderList = new ArrayList<>();
            List<Table> tableList = getTableList();
            for (Table table : tableList){
                Order order = new Order();
                List<OrderedDish> odl = new ArrayList<>();
                order.setT(table);
                String sql = "SELECT * FROM TBLORDEREDDISH OD " +
                            "JOIN TBLDISH D ON D.DID = OD.TBLDISHDID " +
                            "JOIN TBLORDER O ON O.OID = OD.tblOrderOID " +
                            "JOIN TBLTABLE T ON O.tblTableTid = T.TID " +
                            "WHERE TID = ? AND o.isPaid = '0' ;";

                try{ 
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setInt(1, table.getTid());
                    ResultSet rs = ps.executeQuery(); 
                    
                    while (rs.next()) {
                        OrderedDish od = new OrderedDish();
                        od.setOdid(rs.getInt("ODID"));
                        od.setQuantity(rs.getInt("quantity"));
                        Dish d = new Dish();
                        d.setName(rs.getString("name"));
                        d.setType(rs.getString("type"));
                        d.setPrice(rs.getInt("price"));
                        d.setDes(rs.getString("des"));
                        od.setD(d);
                        od.setNote(rs.getString("note"));
                        od.setOrderTime(rs.getTimestamp("OrderTime").toLocalDateTime());
                        odl.add(od);
                    }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    order.setOd(odl);
                    orderList.add(order);
                }
            return orderList;
        }
        public boolean uploadOrder(Order order) {
        String sqlAddOrder = "INSERT INTO tblOrder (tblTableTID, isPaid) VALUES (?,?)"; // Sử dụng tên cột rõ ràng nếu cần
        String sqlAddOrderedDish = "INSERT INTO tblOrderedDish (OrderTime, price, quantity, note, tblDishDID, tblOrderOID) VALUES (?,?,?,?,?,?);";
        try {
            // Chèn đơn hàng mới và lấy generated key (OID)
            PreparedStatement psOrder = con.prepareStatement(sqlAddOrder, java.sql.Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, order.getT().getTid());
            psOrder.setInt(2, 0);
            System.out.println("Table ID: " + order.getT().getTid());
            psOrder.executeUpdate();

            ResultSet rsKeys = psOrder.getGeneratedKeys();
            if (rsKeys.next()) {
                int generatedOid = rsKeys.getInt(1);
                order.setOid(generatedOid);
                System.out.println("Generated Order ID: " + generatedOid);
            } else {
                System.out.println("Không lấy được generated keys.");
                return false;
            }

            // Thêm danh sách các OrderedDish
            PreparedStatement psDish = con.prepareStatement(sqlAddOrderedDish);
            for (OrderedDish od : order.getOd()) {
                psDish.setTimestamp(1, Timestamp.valueOf(od.getOrderTime()));
                psDish.setInt(2, od.getD().getPrice());
                psDish.setInt(3, od.getQuantity());
                psDish.setString(4, od.getNote());
                psDish.setInt(5, od.getD().getId());
                psDish.setInt(6, order.getOid());
                System.out.printf("%s %d %d %s %d %d\n",
                        Timestamp.valueOf(od.getOrderTime()).toString(),
                        od.getD().getPrice(),
                        od.getQuantity(),
                        od.getNote(),
                        od.getD().getId(),
                        order.getOid());
                psDish.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    return false;
}

}

