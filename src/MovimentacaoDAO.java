import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MovimentacaoDAO {
    private Connection conexao;

    public MovimentacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void salvar(MovimentacaoModel m) throws SQLException {
        String sql = "INSERT INTO movimentacao (qtd_movimentacao, data_movimentacao, id_funcionario, id_produto, id_tipo) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, m.getQtdMovimentacao());
            stmt.setObject(2, m.getDataMovimentacao());
            stmt.setInt(3, m.getIdFuncionario());     
            stmt.setInt(4, m.getIdProduto());          
            stmt.setInt(5, m.getIdTipo());              
            
            stmt.executeUpdate();
        }
    }
}