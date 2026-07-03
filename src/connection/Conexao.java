package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {
    // Função para carregar as variáveis do 'application.properties':
    private static Properties carregarPropriedades(){
        Properties props = new Properties();

        try (FileInputStream fs = new FileInputStream("application.properties")) {
            props.load(fs);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo config.properties: " + e.getMessage());
        }
        return props;
    }
    
    public static Connection conectar() throws SQLException{
        Properties props = carregarPropriedades();
        
        // Pega os valores usando as chaves exatas que você definiu no arquivo
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        if (url == null || user == null || password == null) {
            throw new SQLException("Erro: Propriedades do banco de dados não foram encontradas no arquivo!");
        }

        return DriverManager.getConnection(url, user, password);
    }

    public boolean testar() {
        try {
            Properties props = carregarPropriedades();
            
            // Pega os valores usando as chaves exatas que você definiu no arquivo
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão estabelecida com sucesso!");
            return true;
        } catch (Exception erro){
            System.out.println("ERRO: " + erro.getMessage());
            return false;
        }
    }
}