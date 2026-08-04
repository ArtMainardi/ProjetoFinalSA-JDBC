package ProjetoSA.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;
import ProjetoSA.Main;
import ProjetoSA.connection.Conexao;
import ProjetoSA.model.FuncionarioModel;

public class FuncionarioDAO {
    Conexao conexao = new Conexao();

    // CREATE:
    public FuncionarioModel create(FuncionarioModel f) throws SQLException{
        String sql = "INSERT INTO Funcionario(nome_funcionario, email_funcionario, senha_funcionario, is_admin, ativo) VALUES (?,?,?,?,?)";
        // Criptografa a senha:
        String senhaCriptografada = BCrypt.hashpw(f.getSenha_funcionario(), BCrypt.gensalt());

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            // Define os dados da query:
            stmt.setString(1, f.getNome_funcionario());
            stmt.setString(2, f.getEmail_funcionario());
            stmt.setString(3, senhaCriptografada);
            stmt.setBoolean(4, f.isAdmin());
            stmt.setBoolean(5, f.isAtivo());
            // Executa a query:
            stmt.executeUpdate();

            // Verifica o ID gerado:
            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    f.setId_funcionario(rs.getInt(1));

                    // Log:
                    LogDAO.registrar(Main.getIdUsuarioAtual(), "CADASTROU_FUNCIONARIO", 
                        "Cadastrou o funcionário: " + f.getNome_funcionario() + " (ID: " + f.getId_funcionario() + ").");
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
        String sql = "SELECT * FROM Funcionario WHERE ativo = true";
        
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
        // Criptografa a senha:
        String senhaCriptografada = BCrypt.hashpw(f.getSenha_funcionario(), BCrypt.gensalt());   
        // Salva estado anterior:
        FuncionarioModel anterior = readId(f.getId_funcionario());

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados da query:
            stmt.setString(1, f.getNome_funcionario());
            stmt.setString(2, f.getEmail_funcionario());
            stmt.setString(3, senhaCriptografada);
            stmt.setBoolean(4, f.isAdmin());
            stmt.setBoolean(5, f.isAtivo());
            stmt.setInt(6, f.getId_funcionario());

            // Executa e guarda a quantidade de linhas afetadas
            int linhasAfetadas = stmt.executeUpdate();
            // Verifica se atualizou:
            if (linhasAfetadas == 0) {
                return null;
            }

            // Log:
            String log = "";
            if(!anterior.getNome_funcionario().equals(f.getNome_funcionario())){
                log += "Nome: " + anterior.getNome_funcionario() + " -> " + f.getNome_funcionario() + "  |  ";
            }
            if(!anterior.getEmail_funcionario().equals(f.getEmail_funcionario())){
                log += "Email: " + anterior.getEmail_funcionario() + " -> " + f.getEmail_funcionario() + "  |  ";
            }
            if(!anterior.getSenha_funcionario().equals(f.getSenha_funcionario())){
                log += "Senha Alterada  |  ";
            }
            if(anterior.isAdmin() != f.isAdmin()){
                log += "Administrador: " + (!anterior.isAdmin() ? "NÃO -> SIM" : "SIM -> NÃO") + "   |  ";
            }
            if(anterior.isAtivo() != f.isAtivo()){
                log += "Status: " + (!anterior.isAtivo() ? "DESATIVO -> ATIVO" : "ATIVO -> DESATIVO") + "   |  ";
            }
            LogDAO.registrar(Main.getIdUsuarioAtual(), "ATUALIZOU_FUNCIONARIO", 
                "Atualizou o funcionário: " + f.getNome_funcionario() + " (ID: " + f.getId_funcionario() + "). Mudanças feitas: " + (log.trim().isEmpty() ? "nenhuma" : log));
            
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
                // Log:
                LogDAO.registrar(Main.getIdUsuarioAtual(), ((estado ? "ATIVOU" : "DESATIVOU") + "_FUNCIONARIO"), 
                    (estado ? "Ativou" : "Desativou") + " o funcionário com ID: " + id + ".");
                return true;
            }
            else {
                return false;
            }     
        }
    }

    public boolean verificarEmail(String email)throws SQLException{
        String sql = "SELECT email_funcionario FROM Funcionario WHERE email_funcionario = ? AND ativo = true";

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
        String sql = "SELECT senha_funcionario, nome_funcionario, id_funcionario FROM Funcionario WHERE email_funcionario = ? AND ativo = true";

        // Faz a conexão e prepara a query:
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados da query:
            stmt.setString(1, email);

            // Executa a query:
            try (ResultSet resultado = stmt.executeQuery()){
                resultado.next();
                String senhaBanco = resultado.getString("senha_funcionario");

                // Verifica se a senha do BD é a mesma que a digitada pelo usuário:
                if(BCrypt.checkpw(senha, senhaBanco)){
                    // Log:
                    LogDAO.registrar(Main.getIdUsuarioAtual(), "REALIZOU_LOGIN", 
                        "Usuário " + resultado.getString("nome_funcionario") + " (ID: " + resultado.getInt("id_funcionario") + ") realizou login.");
                    return true;
                } else{
                    // Log:
                    LogDAO.registrar(Main.getIdUsuarioAtual(), "FALHA_LOGIN", 
                        "Alguém falhou ao tentar fazer login no usuário: " + 
                        resultado.getString("nome_funcionario") + " (ID: " + resultado.getInt("id_funcionario") + ").");
                    return false;
                }
            }
        }
    }
}