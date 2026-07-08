package ProjetoSA.repository;
import ProjetoSA.connection.Conexao;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.model.MovimentacaoModel;
import ProjetoSA.model.ProdutoModel;
import ProjetoSA.model.TipoMovimentacaoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoDAO {
    private Conexao conexao = new Conexao();

    // 1. CREATE 
    public void salvar(MovimentacaoModel m) throws SQLException {
        String sql = "INSERT INTO Movimentacao (qtd_movimentacao, data_movimentacao, id_funcionario, id_produto, id_tipo) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql);) {
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

        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id_movimentacao");
                int qtd = rs.getInt("qtd_movimentacao");
                LocalDate data = rs.getObject("data_movimentacao", LocalDate.class);

                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                FuncionarioModel funcionario = funcionarioDAO.readId(rs.getInt("id_funcionario"));

                ProdutoDAO produtoDAO = new ProdutoDAO();
                ProdutoModel produto = produtoDAO.readId(rs.getInt("id_produto"));

                TipoMovimentacaoDAO tipoMovimentacaoDAO = new TipoMovimentacaoDAO();
                TipoMovimentacaoModel tipo = tipoMovimentacaoDAO.readId(rs.getInt("id_tipo"));

                MovimentacaoModel m = new MovimentacaoModel(id, qtd, data, funcionario, produto, tipo);
                movimentacoes.add(m);
            }
        }
        return movimentacoes;
    }

    // 3. READ 
    public MovimentacaoModel buscarPorId(int idBusca) throws SQLException {
        String sql = "SELECT id_movimentacao, qtd_movimentacao, data_movimentacao, id_funcionario, id_produto, id_tipo FROM Movimentacao WHERE id_movimentacao = ?";
        
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, idBusca);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_movimentacao");
                    int qtd = rs.getInt("qtd_movimentacao");
                    LocalDate data = rs.getObject("data_movimentacao", LocalDate.class);
                    
                    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                    FuncionarioModel funcionario = funcionarioDAO.readId(rs.getInt("id_funcionario"));

                    ProdutoDAO produtoDAO = new ProdutoDAO();
                    ProdutoModel produto = produtoDAO.readId(rs.getInt("id_produto"));

                    TipoMovimentacaoDAO tipoMovimentacaoDAO = new TipoMovimentacaoDAO();
                    TipoMovimentacaoModel tipo = tipoMovimentacaoDAO.readId(rs.getInt("id_tipo"));
                    
                    return new MovimentacaoModel(id, qtd, data, funcionario, produto, tipo);
                }
            }
        }
        return null; 
    }

    // 4. UPDATE (Atualizar)
    public void atualizar(MovimentacaoModel m) throws SQLException {
        String sql = "UPDATE Movimentacao SET qtd_movimentacao = ?, data_movimentacao = ?, id_funcionario = ?, id_produto = ?, id_tipo = ? WHERE id_movimentacao = ?";
        
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql);) {
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
        
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
        }
    }
}