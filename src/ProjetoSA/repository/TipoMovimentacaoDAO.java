package ProjetoSA.repository;
import ProjetoSA.connection.Conexao;
import ProjetoSA.model.TipoMovimentacaoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoMovimentacaoDAO {
    Conexao conexao = new Conexao();

    // CREATE:
    public TipoMovimentacaoModel create(TipoMovimentacaoModel t) throws SQLException{
        String sql = "INSERT INTO tipo_movimentacao(tipo) VALUES (?)";

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Definne os dados da query:
            stmt.setString(1, t.getTipo());
            stmt.executeUpdate();
            
            // Verifica se o ID foi gerado:
            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                   t.setId_tipo(rs.getInt(1));
                   return t;
                } else{
                    throw new SQLException("Falha ao inserir funcionário, nenhum ID foi gerado.");
                }
            }
        }
    }

    // READ:
    public List<TipoMovimentacaoModel> read() throws SQLException{
        List<TipoMovimentacaoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
       
        // Faz a conexão, prepara e executa a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery();){
            // Verifica se encontrou algum dado:
            while(rs.next()) {
                // Adiciona dado na lista:
                lista.add(new TipoMovimentacaoModel(rs.getInt("id_tipo"), rs.getString("tipo")));
            }
        }
        // Retorna a lista:
        return lista;
    }

    // READ (ID):
    public TipoMovimentacaoModel readId(int id) throws SQLException{
        String sql = "SELECT * FROM produtos where id_tipo = ?";
       
        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql);){
            // Define os dados da query:
            stmt.setInt(1, id);
            
            // Executa a query:
            try(ResultSet rs = stmt.executeQuery()){
                // Verifica se encontrou um dado:
                if(rs.next()){
                    int id_tipo = rs.getInt("id_tipo");
                    String tipo = rs.getString("tipo");
                    // Retorna o dado:
                    return new TipoMovimentacaoModel(id_tipo, tipo);
                }
            }
        }
        return null;
    }

    // UPDATE:
    public TipoMovimentacaoModel update(TipoMovimentacaoModel t) throws SQLException {
        String sql = "UPDATE tipo_movimentacao SET tipo = ? WHERE id = ?";

        // Faz a conexão e prepara a query:
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Define os dados da query:
            stmt.setInt(1, t.getId_tipo());
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                return null;
            }
        }
        return t;
    }

    // DELETE:
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM tipo_movimentacao WHERE id = ?";

        // Faz a conexão e prepara a query:
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Define os dados da query:
            stmt.setInt(1, id);

            // Executa e verifica a query:
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                return true;
            } else {
                return false;
            }
        }
    }
}