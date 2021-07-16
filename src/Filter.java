
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
public class Filter {
    

    public static boolean FilterinDB(String source, String destination, String date, int iac, int inac, int isl, int ist, String min, String max,String sortb) {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3250";
            String mydatabase = "BTRS";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "Bh@280801";
            Connection connection = DriverManager.getConnection(url, username, password);

            String command1 = "select * from bus natural join runs_on where src = ? and dest = ? and DATE_FORMAT(days, \"%d/%m/%Y\") = ? and (AC_fare > ? or non_AC_fare > ?) and (st_fare > ? or sl_fare > ?) and dep_time >= ? and dep_time < ? "+sortb;
            PreparedStatement st = connection.prepareStatement(command1);
            st.setString(2, destination);
            st.setString(1, source);
            st.setString(3, date);
            st.setDouble(4, iac);
            st.setDouble(5, inac);
            st.setDouble(6, ist);
            st.setDouble(7, isl);
            st.setString(8, min);
            st.setString(9, max);
            ResultSet result = st.executeQuery();

            Selection.length = 0;

            while (result.next()) {
                Selection.length += 1;
                Selection.numbers[Selection.length - 1] = result.getInt("bus_id");
                Selection.dep_times[Selection.length - 1] = result.getString("dep_time");
                Selection.dest_times[Selection.length - 1] = result.getString("dest_time");
                Selection.total_seats[Selection.length - 1] = result.getInt("total_seats");
                Selection.available[Selection.length - 1] = result.getInt("available");
                Selection.ac[Selection.length - 1] = result.getInt("AC_type");
                Selection.sl[Selection.length - 1] = result.getInt("seat_type");
                Selection.names[Selection.length - 1] = result.getString("bname");
                if (Selection.ac[Selection.length - 1] == 1) {
                    Selection.fare[Selection.length - 1] = result.getDouble("base_fare") + result.getDouble("AC_fare");
                } else {
                    Selection.fare[Selection.length - 1] = result.getDouble("base_fare") + result.getDouble("non_AC_fare");
                }
                if (Selection.sl[Selection.length - 1] == 1) {
                    Selection.fare[Selection.length - 1] += result.getDouble("sl_fare");
                } else {
                    Selection.fare[Selection.length - 1] += result.getDouble("st_fare");
                }
            }

            if (Selection.length > 0) {
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
    
}
