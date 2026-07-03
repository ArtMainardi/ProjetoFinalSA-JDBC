import connection.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.TipoMovimentacaoModel;

public class TipoMovimentacaoDAO {
    public void Create (TipoMovimentacaoModel t) throws SQLException{
       String sql = "INSERT INTO tipo_movimentacao(tipo) VALUES (?)";
       try(Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)){
          
          stmt.setString(1, t.getTipo());
          stmt.executeUpdate();
          System.out.println("Tipo de movimentação inserido com sucesso!");
       }
    }
    public List<TipoMovimentacaoModel> listar()throws SQLException{
       List<TipoMovimentacaoModel> lista = new ArrayList<>();
       
       String sql = "SELECT * FROM produtos";
       
       try(Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
          
          while(rs.next()) {
             lista.add(new TipoMovimentacaoModel(
                   rs.getInt("id_tipo"),
                   rs.getString("tipo")
                   ));
          }
       } 
       return lista;
    }
    public void atualizar(TipoMovimentacaoModel t) throws SQLException {
        String sql = "UPDATE tipo_movimentacao SET tipo = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getTipo());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Tipo de movimentação atualizado com sucesso!");
            } else {
                System.out.println("Nenhum tipo de atualização encontrado com esse ID.");
            }
        }
    }
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM tipo_movimentacao WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Tipo de movimentação excluído com sucesso!");
            } else {
                System.out.println("Nenhum tipo de movimentação encontrado com esse ID.");
            }
        }
    }
}