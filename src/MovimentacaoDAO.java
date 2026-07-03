import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.MovimentacaoModel;
import model.FuncionarioModel;
import model.ProdutoModel;
import model.TipoMovimentacaoModel;

public class MovimentacaoDAO {
    private Connection conexao;

    public MovimentacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // 1. CREATE 
    public void salvar(MovimentacaoModel m) throws SQLException {
        String sql = "INSERT INTO Movimentacao (qtd_movimentacao, data_movimentacao, id_funcionario, id_produto, id_tipo) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, m.getQtd_movimentacao());
            stmt.setObject(2, m.getData_movimentacao()); // LocalDate funciona muito bem com setObject
            stmt.setInt(3, m.getFuncionario().getId_funcionario());
            stmt.setInt(4, m.getProduto().getId_produto());
            stmt.setInt(5, m.getTipo().getId_tipo());
            
            stmt.executeUpdate();
        }
    }

    // 2. READ 
    public List<MovimentacaoModel> listarTodos() throws SQLException {
        String sql = "SELECT id_movimentacao, qtd_movimentacao, data_movimentacao, id_funcionario, id_produto, id_tipo FROM Movimentacao";
        List<MovimentacaoModel> movimentacoes = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id_movimentacao");
                int qtd = rs.getInt("qtd_movimentacao");
                LocalDate data = rs.getObject("data_movimentacao", LocalDate.class);

                FuncionarioModel funcionario = new FuncionarioModel();
                funcionario.setId_funcionario(rs.getInt("id_funcionario"));

                ProdutoModel produto = new ProdutoModel();
                produto.setId_produto(rs.getInt("id_produto"));

                TipoMovimentacaoModel tipo = new TipoMovimentacaoModel();
                tipo.setId_tipo(rs.getInt("id_tipo"));

                MovimentacaoModel m = new MovimentacaoModel(id, qtd, data, funcionario, produto, tipo);
                movimentacoes.add(m);
            }
        }
        return movimentacoes;
    }

    // 3. READ 
    public MovimentacaoModel buscarPorId(int idBusca) throws SQLException {
        String sql = "SELECT id_movimentacao, qtd_movimentacao, data_movimentacao, id_funcionario, id_produto, id_tipo FROM Movimentacao WHERE id_movimentacao = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idBusca);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_movimentacao");
                    int qtd = rs.getInt("qtd_movimentacao");
                    LocalDate data = rs.getObject("data_movimentacao", LocalDate.class);
                    
                    FuncionarioModel funcionario = new FuncionarioModel();
                    funcionario.setId_funcionario(rs.getInt("id_funcionario"));

                    ProdutoModel produto = new ProdutoModel();
                    produto.setId_produto(rs.getInt("id_produto"));

                    TipoMovimentacaoModel tipo = new TipoMovimentacaoModel();
                    tipo.setId_tipo(rs.getInt("id_tipo"));
                    
                    return new MovimentacaoModel(id, qtd, data, funcionario, produto, tipo);
                }
            }
        }
        return null; 
    }

    // 4. UPDATE (Atualizar)
    public void atualizar(MovimentacaoModel m) throws SQLException {
        String sql = "UPDATE Movimentacao SET qtd_movimentacao = ?, data_movimentacao = ?, id_funcionario = ?, id_produto = ?, id_tipo = ? WHERE id_movimentacao = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, m.getQtd_movimentacao());
            stmt.setObject(2, m.getData_movimentacao());
            stmt.setInt(3, m.getFuncionario().getId_funcionario());
            stmt.setInt(4, m.getProduto().getId_produto());
            stmt.setInt(5, m.getTipo().getId_tipo());
            stmt.setInt(6, m.getId_movimentacao()); 
            
            stmt.executeUpdate();
        }
    }

    // 5. DELETE (Excluir)
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Movimentacao WHERE id_movimentacao = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
        }
    }
}