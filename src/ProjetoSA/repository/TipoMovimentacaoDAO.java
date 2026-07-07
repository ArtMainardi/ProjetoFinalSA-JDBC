package ProjetoSA.repository;
import ProjetoSA.connection.Conexao;
import ProjetoSA.model.TipoMovimentacaoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TipoMovimentacaoDAO {
    Conexao conexao = new Conexao();

    // CREATE:
    public boolean create(TipoMovimentacaoModel t) throws SQLException{
       String sql = "INSERT INTO tipo_movimentacao(tipo) VALUES (?)";

       try(Connection conn = conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, t.getTipo());
            int linhas = stmt.executeUpdate();
            
            if(linhas > 0){
                return true;
            } else{
                return false;
            }
        }
    }

    // READ:
    public List<TipoMovimentacaoModel> read()throws SQLException{
       List<TipoMovimentacaoModel> lista = new ArrayList<>();
       String sql = "SELECT * FROM produtos";
       
       try(Connection conn = conexao.conectar(); Statement stmt = conn.createStatement();){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                lista.add(new TipoMovimentacaoModel(
                    rs.getInt("id_tipo"),
                    rs.getString("tipo")
                ));
            }
       } 
       return lista;
    }

    // READ (ID):
    public TipoMovimentacaoModel readId(int id)throws SQLException{
       String sql = "SELECT * FROM produtos where id_tipo = ?";
       
       try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id_tipo = rs.getInt("id_tipo");
                String tipo = rs.getString("tipo");

                return new TipoMovimentacaoModel(id_tipo, tipo);
            }
       }
       return null;
    }

    // UPDATE:
    public boolean update(TipoMovimentacaoModel t) throws SQLException {
        String sql = "UPDATE tipo_movimentacao SET tipo = ? WHERE id = ?";

        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, t.getTipo());
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    // DELETE:
    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM tipo_movimentacao WHERE id = ?";

        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                return true;
            } else {
                return false;
            }
        }
    }
}