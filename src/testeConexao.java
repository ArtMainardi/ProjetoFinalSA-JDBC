import java.sql.Connection;
import java.sql.DriverManager;

public class testeConexao {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("",
                    "avnadmin",
                    ""
            );
            System.out.println("Conexão estabelecida com sucesso !");
        } catch (Exception erro){
            System.out.println("ERRO: " + erro.getMessage());
        }
    }
}

