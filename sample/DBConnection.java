package sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    public static Connection pmartConnection()
    {
        Connection con =null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url ="jdbc:sqlserver://ADMINISTRATOR\\SQLEXPRESS:1433;database=DACN;username=sa;password=123";
            con = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE,null,e);

        }

        return con;
    }
}
