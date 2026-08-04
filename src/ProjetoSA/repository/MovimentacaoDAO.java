package ProjetoSA.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ProjetoSA.Main;
import ProjetoSA.connection.Conexao;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.model.MovimentacaoModel;
import ProjetoSA.model.ProdutoModel;
import ProjetoSA.model.TipoMovimentacaoModel;

public class MovimentacaoDAO {
    private Conexao conexao = new Conexao();

    // CREATE:
    public MovimentacaoModel create(MovimentacaoModel m) throws SQLException {
        String sqlUpdateEstoque;
        if(m.getTipo().getTipo().equals("Entrada")){
            sqlUpdateEstoque = "UPDATE Produto SET qtd_produto = qtd_produto + ? WHERE id_produto = ?";
        } else{
            sqlUpdateEstoque = "UPDATE Produto SET qtd_produto = qtd_produto - ? WHERE id_produto = ? AND qtd_produto >= ?";
        }
        String sql = "INSERT INTO Movimentacao (qtd_movimentacao, data_movimentacao, id_funcionario, id_produto, id_tipo) VALUES (?, ?, ?, ?, ?)";
        
        // Faz a conexão:
        Connection conn = null;
        try{
            // Inicia a transição:
            conn = conexao.conectar();
            conn.setAutoCommit(false);

            // Prepara a query para a atualização do estoque
            try(PreparedStatement pStmt = conn.prepareStatement(sqlUpdateEstoque)){
                // Define os dados da query:
                pStmt.setInt(1, m.getQtd_movimentacao());
                pStmt.setInt(2, m.getProduto().getId_produto());
                if(m.getTipo().getTipo().equals("Saída")){
                    pStmt.setInt(3, m.getQtd_movimentacao());
                }
                // Executa e verifica:
                int linhas = pStmt.executeUpdate();
                if(linhas == 0){
                    conn.rollback();
                    throw new RuntimeException("ERRO: falha ao atualizar o estoque do produto!");
                }

                // Prepara query para o registro da movimentação:
                try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
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
                            conn.commit();
                            return m;
                        } else{
                            conn.rollback();
                            throw new SQLException("Falha ao inserir movimentação, nenhum ID foi gerado.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
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
    
    //READ (DESATIVADOS):
    public List<MovimentacaoModel> readDesativados() throws SQLException {
        String sql = "SELECT id_movimentacao, qtd_movimentacao, data_movimentacao, id_funcionario, id_produto, id_tipo, ativo FROM Movimentacao WHERE ativo = false";
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
        
        // Query para restaurar o estoque do produto:
        String sqlRestaurarProduto = "";
        // Query para atualizar o estoque do produto:
        String sqlUpdateProduto = "";
        boolean modifyProduto = false;
        if(anterior.getQtd_movimentacao() != m.getQtd_movimentacao() || !anterior.getTipo().getTipo().equals(m.getTipo().getTipo())){
            modifyProduto = true;
            if(anterior.getTipo().getTipo().equals("Entrada")){
                sqlRestaurarProduto = "UPDATE Produto SET qtd_produto = qtd_produto - ? WHERE id_produto = ? AND qtd_produto >= ?";
            } else{
                sqlRestaurarProduto = "UPDATE Produto SET qtd_produto = qtd_produto + ? WHERE id_produto = ?";
            }
            if(m.getTipo().getTipo().equals("Entrada")){
                sqlUpdateProduto = "UPDATE Produto SET qtd_produto = qtd_produto + ? WHERE id_produto = ?";
            } else{
                sqlUpdateProduto = "UPDATE Produto SET qtd_produto = qtd_produto - ? WHERE id_produto = ? AND qtd_produto >= ?";
            }
        }
        
        // Faz a conexão:
        Connection conn = null;
        try{
            // Inicia a transição:
            conn = conexao.conectar();
            conn.setAutoCommit(false);

            if(modifyProduto){
                // Prepara a query para restauração do Produto:
                try(PreparedStatement rStmt = conn.prepareStatement(sqlRestaurarProduto)){
                    // Define dados da query:
                    rStmt.setInt(1, anterior.getQtd_movimentacao());
                    rStmt.setInt(2, anterior.getProduto().getId_produto());
                    if(anterior.getTipo().getTipo().equals("Entrada")){
                        rStmt.setInt(3, anterior.getQtd_movimentacao());
                    }

                    // Executa e verifica:
                    int linhas = rStmt.executeUpdate();
                    if(linhas == 0){
                        conn.rollback();
                        throw new RuntimeException("ERRO: falha ao restaurar o estoque do produto!");
                    }
                }
                // Prepara a query para atualizar o produto:
                try(PreparedStatement pStmt = conn.prepareStatement(sqlUpdateProduto)){
                    // Define dados da query:
                    pStmt.setInt(1, m.getQtd_movimentacao());
                    pStmt.setInt(2, m.getProduto().getId_produto());
                    if(m.getTipo().getTipo().equals("Saída")){
                        pStmt.setInt(3, m.getQtd_movimentacao());
                    }

                    // Executa e verifica:
                    int linhas = pStmt.executeUpdate();
                    if(linhas == 0){
                        conn.rollback();
                        throw new RuntimeException("ERRO: falha ao atualizar o estoque do produto!");
                    }
                }
            }

            // Prepara a query para atualizar a movimentação:
            try (PreparedStatement stmt = conn.prepareStatement(sql);) {
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

                conn.commit();
                return m;
            }
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // ATIVAR/DESATIVAR (SOFT DELETE):
    public boolean desativarOuAtivar(int id, boolean estado) throws SQLException {
        String sql = "UPDATE Movimentacao SET ativo = ? WHERE id_movimentacao = ?";
        // Salva estado anterior:
        MovimentacaoModel anterior = readId(id);
        // Query para restaurar o estoque do produto:
        String sqlRestaurarProduto;
        boolean treeParameters = false;
        if(anterior.getTipo().getTipo().equals("Entrada")){
            if(anterior.isAtivo()){
                sqlRestaurarProduto = "UPDATE Produto SET qtd_produto = qtd_produto - ? WHERE id_produto = ? AND qtd_produto >= ?";
                treeParameters = true;
            } else{
                sqlRestaurarProduto = "UPDATE Produto SET qtd_produto = qtd_produto + ? WHERE id_produto = ?";
            }
        } else{
            if(!anterior.isAtivo()){
                sqlRestaurarProduto = "UPDATE Produto SET qtd_produto = qtd_produto - ? WHERE id_produto = ? AND qtd_produto >= ?";
                treeParameters = true;
            } else{
                sqlRestaurarProduto = "UPDATE Produto SET qtd_produto = qtd_produto + ? WHERE id_produto = ?";
            }
        }
        
        Connection conn = null;
        try{
            conn = conexao.conectar();
            conn.setAutoCommit(false);

            // Prepara a query para restauração do Produto:
            try(PreparedStatement rStmt = conn.prepareStatement(sqlRestaurarProduto)){
                // Define dados da query:
                rStmt.setInt(1, anterior.getQtd_movimentacao());
                rStmt.setInt(2, anterior.getProduto().getId_produto());
                if(treeParameters){
                    rStmt.setInt(3, anterior.getQtd_movimentacao());
                }

                // Executa e verifica:
                int linhas = rStmt.executeUpdate();
                if(linhas == 0){
                    conn.rollback();
                    throw new RuntimeException("ERRO: falha ao restaurar o estoque do produto!");
                }
            }

            // Prepara a query para atualizar a movimentação:
            try (PreparedStatement stmt = conn.prepareStatement(sql);) {
                // Define os dados da query:
                stmt.setBoolean(1, estado);
                stmt.setInt(2, id);

                // Executa a query e verifica se atualizou o dado::
                int linhas = stmt.executeUpdate();
                if(linhas > 0){
                    // Log:
                    LogDAO.registrar(Main.getIdUsuarioAtual(), ((estado ? "ATIVOU" : "DESATIVOU") + "_MOVIMENTACAO"),
                        (estado ? "Ativou" : "Desativou") + " a movimentação com ID: " + id + ".");
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}