package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtilidades {
    
    private static final String DRIVERMYSQL = "com.mysql.jdbc.Driver";

    public static Connection createMySQLConnection(String dbName) 
            throws SQLException, ClassNotFoundException, 
            InstantiationException, IllegalAccessException  {
        String url = "jdbc:mysql://localhost:3306/";
        Class.forName(DRIVERMYSQL).newInstance() ;
        /*
        ATENCION!!!...poner como segundo y tercer argumento del método el nombre de usuario y la clave.....
        */
        return (Connection)DriverManager.getConnection(url +  dbName,"root","") ;
    }
    
    
}
