/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
/**
 *
 * @author Hrithik
 */
public class Update {
     public String Login;
    public String MotDePasse;
    private boolean Logged = false;

    public static void SignUpinDB(String city, String phone, String pass, int c_id) {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3250";
            String mydatabase = "BTRS";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "Bh@280801";
            Connection connection = DriverManager.getConnection(url, username, password);

            String command1 = "update customer set pass = ? , phone = ? , city = ? where customer_id = ?";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setInt(4,c_id);
            st.setString(3,city);
            st.setString(2,phone);
            st.setString(1,pass);
            int rows = st.executeUpdate();
            System.out.println(rows + " row(s) affected");

            System.out.println("Done");
        }
        catch(Exception e) {
            System.out.println(e);
            System.out.println("Failed");
        }
    }

    public static boolean validate_city(String s) {
        int l = s.length();
        if(l == 0 || l > 20) {
            return false;
        }
        for (int i = 0; i < l; i++) {
            if(s.charAt(i) != ' ' && !Character.isAlphabetic(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validate_phone(String s) {
        int l = s.length();
        if(l != 10) {
            return false;
        }
        for (int i = 0; i < l; i++) {
            if(!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validate_password(String s) {
        if (s.length() > 20 || s.length() < 8) {
            return false;
        }
        char[] pass = s.toCharArray();
        for (char x : pass) {
            if (!Character.isDigit(x) && !Character.isLetter(x) && x != '@') {
                return false;
            }
        }
        return true;
    }

    public static boolean confirm_password(String s1,String s2){
        return s1.equals(s2);
    }
}
