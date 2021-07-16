
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hrithik
 */
public class Selection {
    public String Login;
    public String MotDePasse;
    private boolean Logged = false;
    public static int[] numbers = new int[1000];
    public static String[] dep_times = new String[1000];
    public static String[] dest_times = new String[1000];
    public static int[] total_seats = new int[1000];
    public static int[] available = new int[1000];
    public static double[] fare = new double[1000];
    public static int[] sl = new int[1000];
    public static int[] ac = new int[1000];
    public static int length = 0;
    public static String[] names = new String[1000];
    
    public static boolean SelectioninDB(String source, String destination, String date) {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3250";
            String mydatabase = "BTRS";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "Bh@280801";
            Connection connection = DriverManager.getConnection(url, username, password);

            String command1 = "select * from bus natural join runs_on where src = ? and dest = ? and DATE_FORMAT(days, \"%d/%m/%Y\") = ?";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setString(2,destination);
            st.setString(1,source);
            st.setString(3,date);
            ResultSet result = st.executeQuery();

            length = 0;

            while(result.next()) {
                length += 1;
                numbers[length - 1] = result.getInt("bus_id");
                dep_times[length - 1] = result.getString("dep_time");
                dest_times[length - 1] = result.getString("dest_time");
                total_seats[length - 1] = result.getInt("total_seats");
                available[length - 1] = result.getInt("available");
                ac[length - 1] = result.getInt("AC_type");
                sl[length - 1] = result.getInt("seat_type");
                names[length - 1] = result.getString("bname");
                if(ac[length - 1] == 1) {
                    fare[length - 1] = result.getDouble("base_fare") + result.getDouble("AC_fare");
                }
                else {
                    fare[length - 1] = result.getDouble("base_fare") + result.getDouble("non_AC_fare");
                }
                if(sl[length - 1] == 1) {
                    fare[length - 1] += result.getDouble("sl_fare");
                }
                else {
                    fare[length - 1] += result.getDouble("st_fare");
                }
            }

            if(length > 0) {
                return true;
            }
            else {
                return false;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }

    public static boolean validate_city(String s) {
        int l = s.length();
        if (l != 0 && l <= 20) {
            for(int i = 0; i < l; ++i) {
                if (s.charAt(i) != ' ' && !Character.isAlphabetic(s.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean validate_date(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();

        java.util.Date current = cal.getTime();
        java.util.Date userdate = sdf.parse(date);
        cal.add(Calendar.DATE, 7);
        java.util.Date last_date = cal.getTime();

        if (userdate.after(current) && userdate.before(last_date)) {
            return true;
        }
        return false;
    }
    
}
