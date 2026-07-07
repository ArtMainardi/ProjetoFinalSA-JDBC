package ProjetoSA.service;
import ProjetoSA.connection.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import ProjetoSA.model.FuncionarioModel;

public class FuncionarioDAO {
    Conexao conexao = new Conexao();

    // CREATE:
    public boolean create(FuncionarioModel f) throws SQLException{
        String sql = "INSERT INTO Funcionario(nome_funcionario, email_funcionario, senha_funcionario, is_admin, ativo) VALUES (?,?,?,?,?)";

        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, f.getNome_funcionario());
            stmt.setString(2, f.getEmail_funcionario());
            stmt.setString(3, f.getSenha_funcionario());
            stmt.setBoolean(4, f.isAdmin());
            stmt.setBoolean(5, f.isAtivo());

            // Verifica se o dado foi criado:
            int linhas = stmt.executeUpdate(); // Pega a quantidade de linhas afetadas pela query
            if(linhas > 0){
                return true;
            } else{
                return false;
            }
        }
    }

    // READ:
    public ArrayList<FuncionarioModel> read() throws SQLException{
        ArrayList<FuncionarioModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM Funcionario";

        try(Connection conn = conexao.conectar(); Statement stmt = conn.createStatement();) {
            ResultSet resultado = stmt.executeQuery(sql);

            // Adiciona objetos na lista:
            while(resultado.next()){
                int id_funcionario = resultado.getInt("id_funcionario");
                String nome_funcionario = resultado.getString("nome_funcionario");
                String email_funcionario = resultado.getString("email_funcionario");
                String senha_funcionario = resultado.getString("senha_funcionario");
                boolean is_admin = resultado.getBoolean("is_admin");
                boolean ativo = resultado.getBoolean("ativo");

                lista.add(new FuncionarioModel(id_funcionario, nome_funcionario, email_funcionario, senha_funcionario, is_admin, ativo));
            }
            return lista;
        }
    }

    // READ (ID):
    public FuncionarioModel readId(int id) throws SQLException{
        String sql = "SELECT * FROM Funcionario WHERE id_funcionario = ?";

        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,id);

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

        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, email);

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
    public boolean update(FuncionarioModel funcionarioModificado) throws SQLException{
        String sql = "UPDATE Funcionario SET nome_funcionario = ?, email_funcionario = ?, senha_funcionario = ?, is_admin = ?, ativo = ? WHERE id_funcionario = ?";    

        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, funcionarioModificado.getNome_funcionario());
            stmt.setString(2, funcionarioModificado.getEmail_funcionario());
            stmt.setString(3, funcionarioModificado.getSenha_funcionario());
            stmt.setBoolean(4, funcionarioModificado.isAdmin());
            stmt.setBoolean(5, funcionarioModificado.isAtivo());

            // Verifica se o dado foi atualizado:
            int linhasAfetadas = stmt.executeUpdate(); // Pega a quantidade de linhas afetadas pela query
            if(linhasAfetadas > 0){
                return true;
            }else {
                return false;
            }
        }
    }

    // DESATIVAR/ATIVAR ("DELETE"):
    public boolean desativarOuAtivar(int id, boolean estado) throws SQLException{
        String sql = "UPDATE Funcionario SET ativo = ? WHERE id_funcionario = ?";

        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
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
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
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

        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, email);
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