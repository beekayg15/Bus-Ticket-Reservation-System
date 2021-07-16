
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
public class Profile {
    public String Login;
    public String MotDePasse;
    private boolean Logged = false;
    public static String email ;
    public static String user_name ;
    public static int age;
    public static char gender;
    public static String city ;
    public static String phone ;
    public static String pass;
    public static void ProfileinDB(int id) {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3250";
            String mydatabase = "BTRS";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "Bh@280801";
            Connection connection = DriverManager.getConnection(url, username, password);

            String command1 = "select * from customer where customer_id = ?";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setInt(1,id);
            ResultSet result = st.executeQuery();

            boolean flag = false;

            if(result.next()) {
                user_name = result.getString("cname");
                email = result.getString("email");
                age = result.getInt("age");
                gender = result.getString("gender").charAt(0);
                city = result.getString("city");
                phone = result.getString("phone");
                pass = result.getString("pass");
                System.out.print("\n ID : " + id);
                System.out.print("\n Name : " + user_name);
                System.out.print("\n Age : " + age);
                System.out.print("\n Gender : " + gender);
                System.out.print("\n City : " + city);
                System.out.print("\n Email : " + email);
                System.out.print("\n Phone Number : " + phone);
                System.out.print("\n Password : " + password);
                flag = true;
            }

            if(!flag) {
                System.out.println("Data Not Found");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
    }
    
}
