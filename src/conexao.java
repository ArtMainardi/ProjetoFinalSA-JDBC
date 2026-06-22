import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class conexao {
    public static final String url = "";
    public static final String user = "avnadmin";
    public static final String password = "";

    public static Connection conectar() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

}
