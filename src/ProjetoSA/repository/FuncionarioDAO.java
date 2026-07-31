package ProjetoSA.repository;
import ProjetoSA.connection.Conexao;
import ProjetoSA.model.FuncionarioModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FuncionarioDAO {
    Conexao conexao = new Conexao();

    // CREATE:
    public FuncionarioModel create(FuncionarioModel f) throws SQLException{
        String sql = "INSERT INTO Funcionario(nome_funcionario, email_funcionario, senha_funcionario, is_admin, ativo) VALUES (?,?,?,?,?)";

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            // Define os dados da query:
            stmt.setString(1, f.getNome_funcionario());
            stmt.setString(2, f.getEmail_funcionario());
            stmt.setString(3, f.getSenha_funcionario());
            stmt.setBoolean(4, f.isAdmin());
            stmt.setBoolean(5, f.isAtivo());
            // Executa a query:
            stmt.executeUpdate();

            // Verifica o ID gerado:
            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    f.setId_funcionario(rs.getInt(1));
                    return f;
                } else{
                    throw new SQLException("Falha ao inserir funcionário, nenhum ID foi gerado.");
                }
            }
        }
    }

    // READ:
    public ArrayList<FuncionarioModel> read() throws SQLException{
        // Cria a lista vazia:
        ArrayList<FuncionarioModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM Funcionario";
        
        // Faz a conexão, prepara e executa a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            // Adiciona objetos na lista:
            while(rs.next()){
                // Define os dados:
                int id_funcionario = rs.getInt("id_funcionario");
                String nome_funcionario = rs.getString("nome_funcionario");
                String email_funcionario = rs.getString("email_funcionario");
                String senha_funcionario = rs.getString("senha_funcionario");
                boolean is_admin = rs.getBoolean("is_admin");
                boolean ativo = rs.getBoolean("ativo");

                // Adiciona o objeto criado na lista:
                lista.add(new FuncionarioModel(id_funcionario, nome_funcionario, email_funcionario, senha_funcionario, is_admin, ativo));
            }
        }
        // Retorna a lista:
        return lista;
    }

    // READ (ID):
    public FuncionarioModel readId(int id) throws SQLException{
        String sql = "SELECT * FROM Funcionario WHERE id_funcionario = ?";

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados:
            stmt.setInt(1,id);

            // Executa a query:
            try(ResultSet resultado = stmt.executeQuery();){
                if(resultado.next()){ // Verifica se encontrou um dado
                    // Salva dados no objeto:
                    int id_funcionario = resultado.getInt("id_funcionario");
                    String nome_funcionario = resultado.getString("nome_funcionario");
                    String email_funcionario = resultado.getString("email_funcionario");
                    String senha_funcionario = resultado.getString("senha_funcionario");
                    boolean is_admin = resultado.getBoolean("is_admin");
                    boolean ativo = resultado.getBoolean("ativo");

                    // Retorna objeto:
                    FuncionarioModel funcionarioBusca = new FuncionarioModel(id_funcionario, nome_funcionario, email_funcionario, senha_funcionario, is_admin, ativo);
                    return funcionarioBusca;
                } else{
                    return null;
                }
            }
        }
    }

    // READ (EMAIL):
    public FuncionarioModel readEmail(String email) throws SQLException{
        String sql = "SELECT * FROM Funcionario WHERE email_funcionario = ?";

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados da query:
            stmt.setString(1, email);

            // Executa a query:
            try(ResultSet resultado = stmt.executeQuery();){
                if(resultado.next()){ // Verifica se encontrou um dado
                    // Salva dados no objeto:
                    int id_funcionario = resultado.getInt("id_funcionario");
                    String nome_funcionario = resultado.getString("nome_funcionario");
                    String email_funcionario = resultado.getString("email_funcionario");
                    String senha_funcionario = resultado.getString("senha_funcionario");
                    boolean is_admin = resultado.getBoolean("is_admin");
                    boolean ativo = resultado.getBoolean("ativo");

                    // Retorna objeto:
                    FuncionarioModel funcionarioBusca = new FuncionarioModel(id_funcionario, nome_funcionario, email_funcionario, senha_funcionario, is_admin, ativo);
                    return funcionarioBusca;
                } else{
                    return null;
                }
            }
        }
    }

    // UPDATE:
    public FuncionarioModel update(FuncionarioModel f) throws SQLException{
        String sql = "UPDATE Funcionario SET nome_funcionario = ?, email_funcionario = ?, senha_funcionario = ?, is_admin = ?, ativo = ? WHERE id_funcionario = ?";    

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados da query:
            stmt.setString(1, f.getNome_funcionario());
            stmt.setString(2, f.getEmail_funcionario());
            stmt.setString(3, f.getSenha_funcionario());
            stmt.setBoolean(4, f.isAdmin());
            stmt.setBoolean(5, f.isAtivo());

            // Executa e guarda a quantidade de linhas afetadas
            int linhasAfetadas = stmt.executeUpdate();
            
            // Verifica se atualizou:
            if (linhasAfetadas == 0) {
                return null;
            }
            return f;
        }
    }

    // DESATIVAR/ATIVAR ("DELETE"):
    public boolean desativarOuAtivar(int id, boolean estado) throws SQLException{
        String sql = "UPDATE Funcionario SET ativo = ? WHERE id_funcionario = ?";

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados da query:
            stmt.setBoolean(1, estado);
            stmt.setInt(2, id);

            // Verifica se o dado foi modificado: 
            int linhasAfetadas = stmt.executeUpdate(); // Pega a quantidade de linhas afetadas pela query
            if(linhasAfetadas > 0){
                return true;
            }
            else {
                return false;
            }     
        }
    }

    public boolean verificarEmail(String email)throws SQLException{
        String sql = "SELECT email_funcionario FROM Funcionario WHERE email_funcionario = ?";

        // Faz a conexão e prepara a query:
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados da query:
            stmt.setString(1, email);

            // Verifica se existe um usuário com esse email:
            try (ResultSet resultado = stmt.executeQuery()){
                return resultado.next();
            }
        }
    }
    
    public boolean verificarSenha(String senha, String email)throws SQLException{
        // Procura senha do funcionário pelo email dele:
        String sql = "SELECT senha_funcionario FROM Funcionario WHERE email_funcionario = ?";

        // Faz a conexão e prepara a query:
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados da query:
            stmt.setString(1, email);

            // Executa a query:
            try (ResultSet resultado = stmt.executeQuery()){
                resultado.next();
                String senhaBanco = resultado.getString("senha_funcionario");

                // Verifica se a senha do BD é a mesma que a digitada pelo usuário:
                if(senhaBanco.equals(senha)){
                    return true;
                } else{
                    return false;
                }
            }
        }
    }
}