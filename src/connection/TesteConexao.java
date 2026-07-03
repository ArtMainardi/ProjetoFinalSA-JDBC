package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class TesteConexao {
    public boolean testar() {
        try {
            Connection conn = DriverManager.getConnection("",
                    "",
                    ""
            );
            System.out.println("Conexão estabelecida com sucesso!");
            return true;
        } catch (Exception erro){
            System.out.println("ERRO: " + erro.getMessage());
            return false;
        }
    }
}

