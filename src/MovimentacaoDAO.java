import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.MovimentacaoModel;

public class MovimentacaoDAO {
    private Connection conexao;

    public MovimentacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void salvar(MovimentacaoModel m) throws SQLException {
        String sql = "INSERT INTO Movimentacao (qtd_movimentacao, data_movimentacao, id_funcionario, id_produto, id_tipo) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, m.getQtdMovimentacao());
            stmt.setObject(2, m.getDataMovimentacao());
            stmt.setInt(3, m.getFuncionario().getId_funcionario());     
            stmt.setInt(4, m.getProduto().getId_produto());          
            stmt.setInt(5, m.getTipo().getId_tipo());              
            
            stmt.executeUpdate();
        }
    }
}