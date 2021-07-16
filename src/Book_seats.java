
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hrithik
 */
public class Book_seats {
    public static int[] bookedseats = new int[100];
    public static int length;
    public static void selectedBus(int busid , String date)
    {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3250";
            String mydatabase = "BTRS";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "Bh@280801";
            Connection connection = DriverManager.getConnection(url, username, password);
            
            String command = "select seat_no from bus natural join ticket natural join booking where booking_date= ? and bus_id = ?";
            PreparedStatement st = connection.prepareStatement(command);
            
            st.setString(1,date);
            st.setInt(2,busid);
            ResultSet result = st.executeQuery();
            
            length =0;
            while(result.next())
            {
                bookedseats[length++] = result.getInt("seat_no");
            }
            System.out.println("Length is"+length);
            
        }
        catch(Exception e) {
            e.printStackTrace();

        }
    }
}
