
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hrithik
 */
public class view_booking {
    public static void displaybooking(int c_id)
    {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3306";
            String mydatabase = "BTRS";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "barath15";
            Connection connection = DriverManager.getConnection(url, username, password);
            
            String command1 = "select * from booking natural join bus natural join customer where customer_id = ?";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setInt(1,c_id);
            ResultSet result = st.executeQuery();
            String busn="",date="",src="",dest="";
            int b_id=0;
            int nop=0;
            
            DefaultTableModel model = (DefaultTableModel) Booking_management.jTable1.getModel();
            int rows=model.getRowCount();
            if(rows>0)
            {
                for(int i=0;i<rows;i++)
                {
                    model.removeRow(0);
                }
            }
            while(result.next()) {
                busn = result.getString("bname");
                date = result.getString("booking_date");
                src = result.getString("src");
                dest = result.getString("dest");
                b_id = result.getInt("booking_id");
                nop = result.getInt("no_passengers");
                
                model.addRow(new Object[] {b_id,busn,src,dest,date,nop});
            } 
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
    }
    
    public static void view_tickets(int b_id)
    {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3306";
            String mydatabase = "BTRS";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "barath15";
            Connection connection = DriverManager.getConnection(url, username, password);
            
            String command1 = "select * from booking natural join ticket natural join passenger where booking_id = ?";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setInt(1,b_id);
            ResultSet result = st.executeQuery();
            String pname = "",g="";
            int t_id=0,age=0,seat_no;
            

            DefaultTableModel model = (DefaultTableModel) Booking_management.jTable2.getModel();
            int rows=model.getRowCount();
            if(rows>0)
            {
                for(int i=0;i<rows;i++)
                {
                    model.removeRow(0);
                }
            }
            while(result.next()) {
                pname = result.getString("p_name");
                g = result.getString("gender");
                t_id = result.getInt("ticket_id");
                seat_no = result.getInt("seat_no");
                age = result.getInt("age");
                
                model.addRow(new Object[] {t_id,pname,age,g,seat_no});
            } 
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
        
    }
    
    public static void cancel_ticket(int t_id,int booking_id)
    {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3306";
            String mydatabase = "BTRS";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "barath15";
            Connection connection = DriverManager.getConnection(url, username, password);
            
            String command1 = "delete from ticket where ticket_id=?";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setInt(1,t_id);
            int r = st.executeUpdate();
            
            String date="";
            int nop=0;
            command1 = "select bus_id ,booking_date ,no_passengers from booking where booking_id = ? ";
            st = connection.prepareStatement(command1);
            st.setInt(1,booking_id);
            ResultSet result = st.executeQuery();
            int bus_id = 0;
            if(result.next()) {
                bus_id = result.getInt("bus_id");
                date = result.getString("booking_date");
                nop = result.getInt("no_passengers");
            }
            nop--;
            
            command1 = "select available from runs_on where bus_id = ? and days = ?";
            st = connection.prepareStatement(command1);
            st.setInt(1,bus_id);
            st.setString(2,date);
            result = st.executeQuery();
            int c = 0;
            if(result.next()) {
                c = result.getInt("available");
            }
            c ++;
            
            String insert4 = "Update runs_on set available = ? where bus_id = ? and days = ?";
            st = connection.prepareStatement(insert4);
            st.setInt(1,c);
            st.setInt(2,bus_id);
            st.setString(3,date);
            int rows = st.executeUpdate();
            System.out.println(rows + " row(s) affected");
            
            insert4 = "Update booking set no_passengers = ? where booking_id = ? ";
            st = connection.prepareStatement(insert4);
            st.setInt(1,nop);
            st.setInt(2,booking_id);
            rows = st.executeUpdate();
            System.out.println(rows + " row(s) affected");
            
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
        
    }
    
    public static void cancel_booking(int b_id)
    {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3306";
            String mydatabase = "BTRS";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "barath15";
            Connection connection = DriverManager.getConnection(url, username, password);
            
            String date="";
            int nop=0;
            String command1 = "select bus_id ,booking_date ,no_passengers from booking where booking_id = ? ";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setInt(1,b_id);
            ResultSet result = st.executeQuery();
            int bus_id = 0;
            if(result.next()) {
                bus_id = result.getInt("bus_id");
                date = result.getString("booking_date");
                nop = result.getInt("no_passengers");
            }
            
            command1 = "select available from runs_on where bus_id = ? and days = ?";
            st = connection.prepareStatement(command1);
            st.setInt(1,bus_id);
            st.setString(2,date);
            result = st.executeQuery();
            int c = 0;
            if(result.next()) {
                c = result.getInt("available");
            }
            c =c+nop;
            
            String insert4 = "Update runs_on set available = ? where bus_id = ? and days = ?";
            st = connection.prepareStatement(insert4);
            st.setInt(1,c);
            st.setInt(2,bus_id);
            st.setString(3,date);
            int rows = st.executeUpdate();
            System.out.println(rows + " row(s) affected");
            
            
            command1 = "delete from booking where booking_id=?";
            st = connection.prepareStatement(command1);
            st.setInt(1,b_id);
            int r = st.executeUpdate();            
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
        
    }
    
    
    
}
