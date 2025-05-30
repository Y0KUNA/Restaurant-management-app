package DAO;

import static DAO.DAO.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Client;

import model.Dish;
import model.Order;
import model.OrderedDish;
import model.Receipt;
import model.Table;

public class ReceiptDAO extends DAO{
	
	public ReceiptDAO() {
		super();
	}
        public Receipt getReceiptDetail(Receipt receipt){
            String sql = "SELECT * FROM tblOrderedDish od " +
                        "JOIN tblDish d on d.DID = od.tblDishDID " +
                        "JOIN tblOrder o on o.OID = od.tblOrderOID " +
                        "JOIN tblTable t on t.TID = o.tblTableTID " +
                        "WHERE t.position = ? and o.isPaid ='0';"; 
            try{
                PreparedStatement id = con.prepareStatement(sql);
                id.setString(1,receipt.getO().getT().getPosition());
                ResultSet ex = id.executeQuery();
                List<OrderedDish> od = new ArrayList<>();
                while (ex.next()){
                    OrderedDish odd = new OrderedDish();
                    odd.setOdid(ex.getInt("ODID"));
                    receipt.getO().setOid(ex.getInt("tblOrderOID"));
                    odd.setPrice(ex.getInt("price"));
                    odd.setQuantity(ex.getInt("quantity"));
                    Dish d = new Dish();
                    d.setId(ex.getInt("DID"));
                    d.setName(ex.getString("name"));
                    d.setType(ex.getString("type"));
                    d.setDes(ex.getString("des"));
                    odd.setD(d);
                    odd.setOrderTime(ex.getTimestamp("OrderTime").toLocalDateTime());
                    od.add(odd);
                    
                    
                }
                receipt.getO().setOd(od);
                
            }catch(Exception e){
                e.printStackTrace();
            }
            return receipt;
        }
        public Boolean saveReceipt(Receipt receipt){
            String sqlAddClient ="IF NOT EXISTS (SELECT 1 FROM tblClient WHERE tel = ?) " +
                                    "BEGIN INSERT INTO tblClient (name, tel, email,address) VALUES (?,?,?,?) END";
            String sqlGetClientID = "SELECT * FROM tblClient WHERE name = ? AND tel = ?";
            String sqlUpdateOrder = "UPDATE tblOrder SET isPaid = 1 WHERE OID = ?;";
            String sqlAddReceipt = "INSERT INTO tblReceipt (tblUserUID, tblClientCID, tblOrderOID, discount)  VALUES (?,?,?,?);";
            try {
                PreparedStatement AddClient = con.prepareStatement(sqlAddClient);
                AddClient.setString(1,receipt.getC().getTel());
                AddClient.setString(2,receipt.getC().getName());
                AddClient.setString(3,receipt.getC().getTel());
                AddClient.setString(4,receipt.getC().getEmail());
                AddClient.setString(5,receipt.getC().getAddress());
                AddClient.executeUpdate();
                PreparedStatement GetClientID = con.prepareStatement(sqlGetClientID);
                GetClientID.setString(1,receipt.getC().getName());
                GetClientID.setString(2,receipt.getC().getTel());
                ResultSet cid = GetClientID.executeQuery();
                if(cid.next()){
                    receipt.getC().setCid(cid.getInt("CID"));
                }
                PreparedStatement UpdateOrder = con.prepareStatement(sqlUpdateOrder);
                UpdateOrder.setInt(1, receipt.getO().getOid());
                UpdateOrder.executeUpdate();
                PreparedStatement AddReceipt = con.prepareStatement(sqlAddReceipt);
                AddReceipt.setInt(1,receipt.getU().getId());
                AddReceipt.setInt(2,receipt.getC().getCid());
                AddReceipt.setInt(3,receipt.getO().getOid());
                AddReceipt.setInt(4,receipt.getDiscount());
                AddReceipt.executeUpdate();
                return true;
                
                
                
            } catch (SQLException ex) {
                Logger.getLogger(ReceiptDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            
        }
        public Receipt getReceiptLog(Receipt receipt){
            String sql = "SELECT od.ODID,od.OrderTime,od.price as oprice, od.quantity, od.note,od.tblDishDID, od.tblOrderOID,d.name,d.type,od.price,d.des,o.OID,o.tblTableTID,o.isPaid,r.RID,r.discount,r.tblUserUID,r.tblClientCID,c.CID,c.name as cname,c.tel,c.email,c.address,t.TID,t.position,t.seatNumber FROM tblOrderedDish od   " +
                            "JOIN tblDish d on d.DID = od.tblDishDID " +
                            "JOIN tblOrder o on o.OID = od.tblOrderOID " +
                            "JOIN tblReceipt r ON od.tblOrderOID = r.tblOrderOID " +
                            "JOIN tblClient c ON r.tblClientCID = c.CID " +
                            "JOIN tblTable t on t.TID = o.tblTableTID " +
                            "WHERE o.OID = ?"; 
            try{
                PreparedStatement id = con.prepareStatement(sql);
                id.setInt(1,receipt.getO().getOid());
                ResultSet ex = id.executeQuery();
                Order o = new Order();
                List<OrderedDish> od = new ArrayList<>();
                while (ex.next()){
                    OrderedDish odd = new OrderedDish();
                    odd.setOdid(ex.getInt("ODID"));
                    
                    
                    o.setOid(ex.getInt("tblOrderOID"));
                    odd.setPrice(ex.getInt("price"));
                    odd.setQuantity(ex.getInt("quantity"));
                    Dish d = new Dish();
                    d.setId(ex.getInt("tblDishDID"));
                    d.setName(ex.getString("name"));
                    d.setType(ex.getString("type"));
                    d.setDes(ex.getString("des"));
                    odd.setD(d);
                    odd.setOrderTime(ex.getTimestamp("OrderTime").toLocalDateTime());
                    od.add(odd);
                    Table t = new Table();
                    t.setPosition(ex.getString("position"));
                    o.setT(t);
                    Client c = new Client();
                    c.setName(ex.getString("cname"));
                    c.setTel(ex.getString("tel"));
                    c.setAddress(ex.getString("address"));
                    c.setEmail(ex.getString("Email"));
                    receipt.setC(c);
                    
                    
                }
                o.setOd(od);
                receipt.setO(o);
                
            }catch(Exception e){
                e.printStackTrace();
            }
            return receipt;
        }
}
