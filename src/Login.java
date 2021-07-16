/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Hrithik
 */
public class Login {
    public static int current_login = -1;
    public String Login;
    public String MotDePasse;
    private boolean Logged = false;

    public static boolean SignIninDB(String email, String pass) {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3250";
            String mydatabase = "BTRS";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "Bh@280801";
            Connection connection = DriverManager.getConnection(url, username, password);

            String command1 = "select customer_id,pass from customer where email = ?";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setString(1,email);
            ResultSet result = st.executeQuery();

            boolean flag = false;

            String p = "";

            if(result.next()) {
                flag = true;
                p = result.getString("pass");
                current_login = result.getInt("customer_id");
            }

            if(!flag) {
                JOptionPane.showMessageDialog(null,"Account Not Found" );
                return false;
            }

            if(!p.equals(pass)) {
                JOptionPane.showMessageDialog(null,"Incorrect Password!!" );
                return false;
            }

            new homepage().setVisible(true);
            return true;
        }
        catch(Exception e) {
            System.out.println(e);
            System.out.println("Failed");
        }
        return true;

    }
}
