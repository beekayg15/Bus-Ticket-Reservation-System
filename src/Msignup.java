
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
public class Msignup {
    public String Login;
    public String MotDePasse;
    private boolean Logged = false;

    public static void SignUpinDB(String cname, int age, String gender, String city, String email, String phone, String pass) {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3250";
            String mydatabase = "BTRS";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "Bh@280801";
            Connection connection = DriverManager.getConnection(url, username, password);

            String command1 = "select max(customer_id) from customer";
            PreparedStatement st = connection.prepareStatement(command1);
            ResultSet result = st.executeQuery();

            int c_id = 0;
            if(result.next()) {
                c_id = result.getInt("max(customer_id)");
            }

            c_id += 1;

            String command2 = "insert into customer values(?,?,?,?,?,?,?,?)";
            st = connection.prepareStatement(command2);
            st.setInt(1,c_id);
            st.setString(2,cname);
            st.setInt(3,age);
            st.setString(4,gender);
            st.setString(5,city);
            st.setString(6,email);
            st.setString(8,phone);
            st.setString(7,pass);
            int rows = st.executeUpdate();
            System.out.println(rows + " row(s) affected");

            System.out.println("Done");
        }
        catch(Exception e) {
            System.out.println(e);
            System.out.println("Failed");
        }
    }

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

    public static int validate_age(String s) {
        try {
            int age = Integer.parseInt(s);
            if(age < 0) {
                return 0;
            }
            return age;
        }
        catch(Exception e) {
            return 0;
        }
    }

    public static boolean validate_gender(int a, int b) {
        if(a != 0 && a != 1) {
            return false;
        }
        if(b != 0 && b != 1) {
            return false;
        }
        if(a == b) {
            return false;
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
        if(s.length()>20 || s.length()<8) {
            return false;
        }
        char[] pass = s.toCharArray();
        for(char x:pass){
            if(!Character.isDigit(x) && !Character.isLetter(x) && x != '@') {
                return false;
            }
        }
        return true;
    }

    public static boolean validate_email(String s){
        if(s.length()>35 || s.length()<5) {
            return false;
        }
        char[] pass = s.toCharArray();
        boolean at_found = false;
        for(char x:pass){
            if(!Character.isDigit(x) && !Character.isLetter(x) && x != '@' && x != '.') {
                return false;
            }
            if(at_found && x=='@') {
                return false;//More than one '@'
            }
            if(x=='@') {
                at_found=true;
            }
        }
        return at_found;
    }

    public static boolean email_is_exists(String s) {
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3250";
            String mydatabase = "BTRS";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "Bh@280801";
            Connection connection = DriverManager.getConnection(url, username, password);

            String command1 = "select * from customer where email = ?";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setString(1,s.toLowerCase());
            ResultSet result = st.executeQuery();

            boolean flag = true;

            while(result.next()) {
                flag = false;
            }

            return flag;
        }
        catch(Exception e) {
            System.out.println("Connectivity Error");
        }
        return false;
    }

    public static boolean confirm_password(String s1,String s2){
        return s1.equals(s2);
    }
}
