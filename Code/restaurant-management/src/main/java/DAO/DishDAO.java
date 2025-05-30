package DAO;

import static DAO.DAO.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Client;

import model.Dish;
import model.Order;
import model.OrderedDish;
import model.Receipt;
import model.Statistic;
import model.Table;

public class DishDAO extends DAO{
	
	public DishDAO() {
		super();
	}
	public boolean addDish(Dish dish){
            String sql = "INSERT INTO tblDish (name, type, price, des) VALUES (?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dish.getName());
			ps.setString(2, dish.getType());
                        ps.setInt(3, dish.getPrice());
                        ps.setString(4, dish.getDes());
			ps.executeUpdate();
                        return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
            return false;
        }
        public boolean editDish(Dish dish){
            String sql = "UPDATE tblDish " +
                        "SET name = ?, type = ?, price = ?, des = ? " +
                        "WHERE did = ?;";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dish.getName());
			ps.setString(2, dish.getType());
                        ps.setInt(3, dish.getPrice());
                        ps.setString(4, dish.getDes());
                        ps.setInt(5, dish.getId());
			ps.executeUpdate();
                        return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
            return false;
        }
       public boolean deleteDish(List<Dish> dishes) {
            if (dishes == null || dishes.isEmpty()) return false;

            
            StringBuilder sb = new StringBuilder("DELETE FROM tblDish WHERE did IN (");
            for (int i = 0; i < dishes.size(); i++) {
                sb.append("?");
                if (i < dishes.size() - 1) sb.append(",");
            }
            sb.append(")");

            try {
                PreparedStatement ps = con.prepareStatement(sb.toString());
                for (int i = 0; i < dishes.size(); i++) {
                    ps.setInt(i + 1, dishes.get(i).getId());
                }
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

	public List<Dish> getDishList(){
            String sql = "SELECT * FROM tblDish";
            List<Dish> dishes = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
			while (rs.next()){
                            Dish dish = new Dish();
                            dish.setId(rs.getInt("DID"));
                            dish.setName(rs.getString("name"));
                            dish.setType(rs.getString("type"));
                            dish.setPrice(rs.getInt("price"));
                            dish.setDes(rs.getString("des"));
                            dishes.add(dish);
                            
                        }
                        
			
		}catch(Exception e) {
			e.printStackTrace();
		}
            return dishes;
        }
        public List<Statistic> getDishStat(LocalDate startDate, LocalDate endDate){
            List<Statistic> statList = new ArrayList<>();
            String sql ="SELECT " +
                        "    d.DID," +
                        "    d.name," +
                        "    d.type," +
                        "    d.price AS unitPrice," +
                        "    d.des," +
                        "    ISNULL(SUM(od.quantity), 0) AS orderCount," +
                        "    ISNULL(SUM(od.quantity * od.price), 0) AS revenue " +
                        "FROM tblDish d " +
                        "LEFT JOIN tblOrderedDish od ON d.DID = od.tblDishDID "  +
                        "WHERE od.OrderTime BETWEEN ? AND ? " +
                        "GROUP BY d.DID, d.name, d.type, d.price, d.des";
            
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setDate(1,java.sql.Date.valueOf(startDate));
                ps.setDate(2, java.sql.Date.valueOf(endDate));
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()){
                    Dish d = new Dish();
                    d.setId(rs.getInt("DID"));
                    d.setName(rs.getString("name"));
                    d.setType(rs.getString("type"));
                    d.setPrice(rs.getInt("unitPrice"));
                    d.setDes(rs.getString("des"));
                    int orderCount = rs.getInt("orderCount");
                    int revenue = rs.getInt("revenue");
                    Statistic dishStat = new Statistic(d,startDate,endDate,orderCount,revenue);
                    statList.add(dishStat);
                    
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(DishDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return statList;
        }
        public List<Receipt> getOrderLog(LocalDate startDate, LocalDate endDate,int did){
            List<Receipt> log = new ArrayList<>();
            String sql = "SELECT " +
                        "	r.tblOrderOID, " +
                        "	c.name, " +
                        "	c.CID, " +
                        "	t.position, " +
                        "    od.ODID, " +
                        "    od.OrderTime, " +
                        "    od.quantity, " +
                        "    od.price, " +
                        "    od.note  " +
                        "FROM tblOrderedDish od " +
                        "JOIN tblDish d ON od.tblDishDID = d.DID " +
                        "JOIN tblOrder o ON od.tblOrderOID = o.OID " +
                        "JOIN tblTable t ON o.tblTableTID = t.TID " +
                        "JOIN tblReceipt r ON  o.OID = r.tblOrderOID " +
                        "JOIN tblClient c on r.tblClientCID = c.CID " +
                        "WHERE d.DID = ? AND od.OrderTime BETWEEN ? AND ? " +
                        "ORDER BY od.OrderTime ASC;";
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1,did);
                ps.setString(2,startDate.toString());
                ps.setString(3,endDate.toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    Receipt receipt = new Receipt();
                    Order order = new Order();
                    List<OrderedDish> od = new ArrayList<>();
                    Client c = new Client();
                    Table t = new Table();
                    t.setPosition(rs.getString("position"));
                    OrderedDish odd = new OrderedDish();
                    order.setOid(rs.getInt("tblOrderOID"));
                    order.setT(t);
                    c.setName(rs.getString("name"));
                    c.setCid(rs.getInt("CID"));
                    odd.setOrderTime(rs.getTimestamp("OrderTime").toLocalDateTime());
                    odd.setOdid(rs.getInt("ODID"));
                    odd.setQuantity(rs.getInt("quantity"));
                    odd.setPrice(rs.getInt("price"));
                    odd.setNote(rs.getString("Note"));
                    od.add(odd);
                    order.setOd(od);
                    receipt.setO(order);
                    receipt.setC(c);
                    log.add(receipt);
                    
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(DishDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return log;
            
        }
}
