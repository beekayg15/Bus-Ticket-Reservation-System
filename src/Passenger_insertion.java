/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hrithik
 */
import java.sql.*;
public class Passenger_insertion {
    
    public static boolean validate_username(String s) {
        int l = s.length();
        if(l == 0 || l > 30) {
            return false;
        }
        for (int i = 0; i < l; i++) {
            if(s.charAt(i) != ' ' && !Character.isAlphabetic(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
     public static boolean validate_age(String s) {
        try {
            int age = Integer.parseInt(s);
            if(age <= 0) {
                return false;
            }
            return true;
        }
        catch(Exception e) {
            return true;
        }
    }
     
    public static boolean validate_gender(String G) {
        if(G.toUpperCase().equals("M")) {
            return true;
        }
        else if(G.toUpperCase().equals("F")) {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static void insertintoBooking(String date,int nop,int bus_id)
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

            String command1 = "select max(booking_id) from booking";
            PreparedStatement st = connection.prepareStatement(command1);
            ResultSet result = st.executeQuery();
            

            int b_id = 2021000;
            if(result.next()) {
                b_id = result.getInt("max(booking_id)");
            }            
            if(b_id==0)
            {
                b_id = 2021000;
                
            }
            b_id += 1;

            
            String insert1 = "insert into booking values(?,?,?,?,?)";
            st = connection.prepareStatement(insert1);
            st.setInt(1,b_id);
            st.setString(2,date);
            st.setInt(3,nop);
            st.setInt(4,Login.current_login);
            st.setInt(5,bus_id);
            int rows = st.executeUpdate();
            System.out.println(rows + " row(s) affected");
            
            command1 = "select available from runs_on where bus_id = ? and days = ?";
            st = connection.prepareStatement(command1);
            st.setInt(1,bus_id);
            st.setString(2,date);
            result = st.executeQuery();
            int c = 0;
            if(result.next()) {
                c = result.getInt("available");
            }
            c -= nop;
            
            String insert4 = "Update runs_on set available = ? where bus_id = ? and days = ?";
            st = connection.prepareStatement(insert4);
            st.setInt(1,c);
            st.setInt(2,bus_id);
            st.setString(3,date);
            rows = st.executeUpdate();
            System.out.println(rows + " row(s) affected");

        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
    }
    public static void insertintoDB(String user ,int age ,String gender,int seatno,double fare)
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

            String command1 = "select max(booking_id) from booking";
            PreparedStatement st = connection.prepareStatement(command1);
            ResultSet result = st.executeQuery();

            int b_id = 0;
            if(result.next()) {
                b_id = result.getInt("max(booking_id)");
            }
            
            command1 = "select max(ticket_id) from ticket";
            st = connection.prepareStatement(command1);
            result = st.executeQuery();
            int t_id = 6793180;
            if(result.next()) {
                t_id = result.getInt("max(ticket_id)");
            }
            if(t_id==0)
            {
                t_id = 6793180;
            }
            t_id += 1;
            
            command1 = "select max(p_id) from passenger";
            st = connection.prepareStatement(command1);
            result = st.executeQuery();
            int p_id = 0;
            if(result.next()) {
                p_id = result.getInt("max(p_id)");
            }
            p_id += 1;
            
            String insert2 = "insert into ticket values(?,?,?,?)";
            st = connection.prepareStatement(insert2);
            st.setInt(1,b_id);
            st.setInt(2,t_id);
            st.setInt(3,seatno);
            st.setDouble(4,fare);
            int rows = st.executeUpdate();
            System.out.println(rows + " row(s) affected");
            
            String insert3 = "insert into passenger values(?,?,?,?,?)";
            st = connection.prepareStatement(insert3);
            st.setInt(1,t_id);
            st.setInt(2,p_id);
            st.setString(3,user);
            st.setInt(4,age);
            st.setString(5,gender.toUpperCase());
            rows = st.executeUpdate();
            System.out.println(rows + " row(s) affected");
            
            

            System.out.println("Done");
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
        
    }
    
    public static void ticket_details()
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
            
            String command1 = "select max(booking_id) from booking";
            PreparedStatement st = connection.prepareStatement(command1);
            ResultSet result = st.executeQuery();
            int b_id = 0;
            if(result.next()) {
                b_id = result.getInt("max(booking_id)");
            }
            
            command1 = "select * from booking natural join bus natural join customer natural join ticket where booking_id = ?";
            st = connection.prepareStatement(command1);
            st.setInt(1, b_id);
            result = st.executeQuery();
            String busn="" ,cn="",date="",src="",dest="",start_time="",type="";
            int ac,sl;
            String seats ="";
            double fare=0;
            while(result.next()) {
                busn = result.getString("bname");
                cn = result.getString("cname");
                date = result.getString("booking_date");
                src = result.getString("src");
                dest = result.getString("dest");
                start_time = result.getString("dep_time");
                ac = result.getInt("AC_type");
                sl = result.getInt("seat_type");
                seats = seats + " " +Integer.toString(result.getInt("seat_no"));
                fare = fare +result.getDouble("fare");
                if(ac == 1 && sl == 1)
                {
                    type = "AC Sleeper";
                }
                else if(ac == 1 && sl == 0)
                {
                    type = "AC Seater";
                }
                else if(ac == 0 && sl == 1)
                {
                    type = "Non AC Sleeper";
                }
                else if(ac == 0 && sl == 0)
                {
                    type = "Non AC Seater";
                }
                
            }
           new Ticket_confirmation_page().setVisible(true);
           Ticket_confirmation_page.jLabel3.setText(Integer.toString(b_id));
           Ticket_confirmation_page.jLabel4.setText(date);
           Ticket_confirmation_page.jLabel5.setText(start_time);
           Ticket_confirmation_page.jLabel6.setText(src+" -- "+dest);
           Ticket_confirmation_page.jLabel7.setText(busn);
           Ticket_confirmation_page.jLabel8.setText(cn);
           Ticket_confirmation_page.jLabel9.setText(seats);
           Ticket_confirmation_page.jLabel10.setText(Double.toString(fare));
           Ticket_confirmation_page.jLabel11.setText(type);

           
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
        
    }
    
}
