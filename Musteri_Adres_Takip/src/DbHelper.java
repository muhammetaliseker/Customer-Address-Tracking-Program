import java.sql.*;

public class DbHelper {
    static String username = "root";
    static String password = "12345";
    static String dbUrl = "jdbc:mysql://localhost:3306/k_giris";


    public Connection getConnection() throws SQLException {
        return(Connection) DriverManager.getConnection(dbUrl,username,password);
    }
    public void ShowError(SQLException exception){
        System.out.println("Error: "+exception.getMessage());
        System.out.println("ErrorCode: "+exception.getErrorCode());
    }





    }
