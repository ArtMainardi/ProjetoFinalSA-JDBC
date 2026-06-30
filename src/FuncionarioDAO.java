import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FuncionarioDAO {
       Conexao conexao = new Conexao();

        public ArrayList <FuncionarioModel> listar() throws SQLException{
        ArrayList<FuncionarioModel> lista = new ArrayList<>();

        String sql = "SELECT * FROM Funcionario";

        Connection conn = conexao.conectar();
        Statement stmt = conn.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);

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

     public void inserir (FuncionarioModel f) throws SQLException{
         String sql = "INSERT INTO Funcionario(nome_funcionario, email_funcionario, senha_funcionario, is_admin, ativo) VALUES (?,?,?,?,?)";

         try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setString(1, f.getNome_funcionario());
                stmt.setString(2, f.getEmail_funcionario());
                stmt.setString(3, f.getSenha_funcionario());
                stmt.setBoolean(4, f.isIs_admin());
                stmt.setBoolean(5, f.isAtivo());

                stmt.executeUpdate();
                System.out.println("Produto inserido com sucesso!");

            }
        }
}
