package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	public static Connection con;
	
	public DAO(){
		if(con == null){
			try {
                            String url = "jdbc:sqlserver://localhost:1433; databaseName=RESTAURANT;user=sa;password=12345678;"
                + "encrypt=true;trustServerCertificate=true";
				
				con = DriverManager.getConnection(url);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
