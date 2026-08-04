package ProjetoSA.repository;

import ProjetoSA.Main;
import ProjetoSA.connection.Conexao;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.model.MovimentacaoModel;
import ProjetoSA.model.ProdutoModel;
import ProjetoSA.model.TipoMovimentacaoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoDAO {
    private Conexao conexao = new Conexao();

    // CREATE:
    public MovimentacaoModel create(MovimentacaoModel m) throws SQLException {
        String sql = "INSERT INTO Movimentacao (qtd_movimentacao, data_movimentacao, id_funcionario, id_produto, id_tipo) VALUES (?, ?, ?, ?, ?)";
        
        // Faz a conexão e prepara a query:
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            // Define os valores da query:
            stmt.setInt(1, m.getQtd_movimentacao());
            stmt.setObject(2, m.getData_movimentacao()); // LocalDate funciona muito bem com setObject
            stmt.setInt(3, m.getFuncionario().getId_funcionario());
            stmt.setInt(4, m.getProduto().getId_produto());
            stmt.setInt(5, m.getTipo().getId_tipo());
            // Executa a query:
            stmt.executeUpdate();
            
            // Verifica o ID gerado:
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    m.setId_movimentacao(rs.getInt(1));

                    // Log:
                    LogDAO.registrar(Main.getIdUsuarioAtual(), "CADASTROU_MOVIMENTACAO", 
                        "Cadastrou a movimentação (ID: " + m.getId_movimentacao() + "). ");
                         return m;

                } else{
                    throw new SQLException("Falha ao inserir movimentação, nenhum ID foi gerado.");
                }
            }
        }
        
    }

    // READ:
    public List<MovimentacaoModel> read() throws SQLException {
        String sql = "SELECT id_movimentacao, qtd_movimentacao, data_movimentacao, id_funcionario, id_produto, id_tipo, ativo FROM Movimentacao WHERE ativo = true";
        // Cria a lista:
        List<MovimentacaoModel> movimentacoes = new ArrayList<>();

        // Faz conexão e executa a query:
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            // Adiciona todos os dados na lista:
            while (rs.next()) {
                // Define os dados do objeto:
                int id = rs.getInt("id_movimentacao");
                int qtd = rs.getInt("qtd_movimentacao");
                LocalDate data = rs.getObject("data_movimentacao", LocalDate.class);
                // Procura funcionário pelo ID:
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                FuncionarioModel funcionario = funcionarioDAO.readId(rs.getInt("id_funcionario"));
                // Procura produto pelo ID:
                ProdutoDAO produtoDAO = new ProdutoDAO();
                ProdutoModel produto = produtoDAO.readId(rs.getInt("id_produto"));
                // Procura tipo de moovimentação pelo ID:
                TipoMovimentacaoDAO tipoMovimentacaoDAO = new TipoMovimentacaoDAO();
                TipoMovimentacaoModel tipo = tipoMovimentacaoDAO.readId(rs.getInt("id_tipo"));
                // Procura status:
                boolean ativo = rs.getBoolean("ativo");

                // Cria o objeto com os dados:
                MovimentacaoModel m = new MovimentacaoModel(id, qtd, data, funcionario, produto, tipo, ativo);
                // Adiciona ele na lista:
                movimentacoes.add(m);
            }
        }
        // Retorna a lista:
        return movimentacoes;
    }

    // READ (ID): 
    public MovimentacaoModel readId(int idBusca) throws SQLException {
        String sql = "SELECT id_movimentacao, qtd_movimentacao, data_movimentacao, id_funcionario, id_produto, id_tipo, ativo FROM Movimentacao WHERE id_movimentacao = ?";
        
        // Faz a conexão e prepara a query:
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            // Adiciona dados na query:
            stmt.setInt(1, idBusca);
            
            // Executa a query:
            try (ResultSet rs = stmt.executeQuery()) {
                // Verifica se encontrou algum dado:
                if (rs.next()) {
                    // Define os dados do objeto:
                    int id = rs.getInt("id_movimentacao");
                    int qtd = rs.getInt("qtd_movimentacao");
                    LocalDate data = rs.getObject("data_movimentacao", LocalDate.class);
                    // Procura funcionário pelo ID:
                    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                    FuncionarioModel funcionario = funcionarioDAO.readId(rs.getInt("id_funcionario"));
                    // Procura produto pelo ID:
                    ProdutoDAO produtoDAO = new ProdutoDAO();
                    ProdutoModel produto = produtoDAO.readId(rs.getInt("id_produto"));
                    // Procura tipo de moovimentação pelo ID:
                    TipoMovimentacaoDAO tipoMovimentacaoDAO = new TipoMovimentacaoDAO();
                    TipoMovimentacaoModel tipo = tipoMovimentacaoDAO.readId(rs.getInt("id_tipo"));
                    // Procura status:
                    boolean ativo = rs.getBoolean("ativo");

                    
                    // Retorna o objeto com os dados:
                    return new MovimentacaoModel(id, qtd, data, funcionario, produto, tipo, ativo);
                }
            }
        }
        return null; 
    }

    // UPDATE:
    public MovimentacaoModel update(MovimentacaoModel m) throws SQLException {
        String sql = "UPDATE Movimentacao SET qtd_movimentacao = ?, data_movimentacao = ?, id_funcionario = ?, id_produto = ?, id_tipo = ? WHERE id_movimentacao = ?";
            // Salva estado anterior:
            MovimentacaoModel anterior = readId(m.getId_movimentacao());
        
        // Faz a conexão e prepara a query:
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            // Define os dados da query:
            stmt.setInt(1, m.getQtd_movimentacao());
            stmt.setObject(2, m.getData_movimentacao());
            stmt.setInt(3, m.getFuncionario().getId_funcionario());
            stmt.setInt(4, m.getProduto().getId_produto());
            stmt.setInt(5, m.getTipo().getId_tipo());
            stmt.setInt(6, m.getId_movimentacao()); 
            
            // Executa e guarda a quantidade de linhas afetadas
            int linhasAfetadas = stmt.executeUpdate();
            
            // Verifica se atualizou:
            if (linhasAfetadas == 0) {
                return null;
            }

            // Log
            String log = "";
            if(anterior.getQtd_movimentacao() != m.getQtd_movimentacao()){
                log += "Quantidade: " + anterior.getQtd_movimentacao() + " -> " + m.getQtd_movimentacao() + " | ";
            }
            if(anterior.getData_movimentacao() != m.getData_movimentacao()){
                log += "Data da movimentação: " + anterior.getData_movimentacao() + " -> " + m.getData_movimentacao() + " | ";
            }
            if(anterior.isAtivo() != m.isAtivo()){
                log += "Status: " + (!anterior.isAtivo() ? "DESATIVO -> ATIVO" : "ATIVO -> DESATIVO") + " | ";
            } 
            LogDAO.registrar(Main.getIdUsuarioAtual(), "ATUALIZOU_MOVIMENTACAO",
                "Atualizou a movimentação (ID: " + m.getId_movimentacao() + "). Mudanças feitas: " + (log.trim().isEmpty() ? "Nenhuma" : log));

            return m;
        }
    }

    // ATIVAR/DESATIVAR (SOFT DELETE):
    public boolean desativarOuAtivar(int id, boolean estado) throws SQLException {
        String sql = "UPDATE Movimentacao SET ativo = ? WHERE id_movimentacao = ?";
        
        // Faz a conexão e prepara a query:
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            // Define os dados da query:
            stmt.setBoolean(1, estado);
            stmt.setInt(2, id);

            // Executa a query e verifica se atualizou o dado::
            int linhas = stmt.executeUpdate();
            if(linhas > 0){
                // Log:
                LogDAO.registrar(Main.getIdUsuarioAtual(), ((estado ? "ATIVOU" : "DESATIVOU") + "_MOVIMENTACAO"),
                    (estado ? "Ativou" : "Desativou") + " a movimentação com ID: " + id + ".");
                return true;
            } else {
                return false;
            }
        }
    }
}