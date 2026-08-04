package ProjetoSA.repository;
import ProjetoSA.Main;
import ProjetoSA.connection.Conexao;
import ProjetoSA.model.ProdutoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ProdutoDAO {
    Conexao conexao = new Conexao();

    // CREATE:
    public ProdutoModel create(ProdutoModel p) throws SQLException{
        String sql = "INSERT INTO Produto(nome_produto, descricao_produto, qtd_produto, qtd_minima, ativo) VALUES (?,?,?,?,?)";

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            // Define os dados da query:
            stmt.setString(1, p.getNome_produto());
            stmt.setString(2, p.getDescricao_produto());
            stmt.setInt(3, p.getQtd_produto());
            stmt.setInt(4, p.getQtd_minima());
            stmt.setBoolean(5, p.isAtivo());
            // Executa a query:
            stmt.executeUpdate();

            // Verifica o ID gerado:
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    p.setId_produto(rs.getInt(1));

                    //Log: 
                    LogDAO.registrar(Main.getIdUsuarioAtual(), "CADASTROU_PRODUTO", 
                    "Cadastrou o produto " + p.getNome_produto() + " (ID: " + p.getId_produto() + ").");

                     // Retorna o produto com o ID gerado:
                    return p;

                } else{
                    throw new SQLException("Falha ao inserir produto, nenhum ID foi gerado.");
                }
            }
        }
    }

    // READ:
    public ArrayList<ProdutoModel> read() throws SQLException{
        ArrayList<ProdutoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM Produto WHERE ativo = true";

        // Faz a conexão e executa a query:
        try(Connection conn = conexao.conectar(); Statement stmt = conn.createStatement(); ResultSet resultado = stmt.executeQuery(sql);){
            // Adiciona todos os dados na lista:
            while(resultado.next()){
                int id_produto = resultado.getInt("id_produto");
                String nome_produto = resultado.getString("nome_produto");
                String descricao_produto = resultado.getString("descricao_produto");
                int qtd_produto = resultado.getInt("qtd_produto");
                int qtd_minima = resultado.getInt("qtd_minima");
                boolean ativo = resultado.getBoolean("ativo");

                // Cria o objeto com os dados e insere ele na lista:
                lista.add(new ProdutoModel(id_produto,nome_produto, descricao_produto, qtd_produto, qtd_minima, ativo));
            }
            return lista;
        }
    }

    // READ (ID):
    public ProdutoModel readId(int id) throws SQLException{
        String sql = "SELECT * FROM Produto WHERE id_produto = ?";

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados da query:
            stmt.setInt(1, id);

            // Executa a query:
            try(ResultSet resultado = stmt.executeQuery();){
                // Verifica se encontrou algum dado:
                if(resultado.next()){
                    int id_produto = resultado.getInt("id_produto");
                    String nome_produto = resultado.getString("nome_produto");
                    String descricao_produto = resultado.getString("descricao_produto");
                    int qtd_produto = resultado.getInt("qtd_produto");
                    int qtd_minima = resultado.getInt("qtd_minima");
                    boolean ativo = resultado.getBoolean("ativo");
                    ProdutoModel produtoBuscar = new ProdutoModel(id_produto, nome_produto, descricao_produto, qtd_produto, qtd_minima, ativo);

                    // Retorna o objeto:
                    return produtoBuscar;
                }
            }
        }
        return null;
    }

    // READ (DESATIVADOS):
    public ArrayList<ProdutoModel> readDesativados() throws SQLException{
        ArrayList<ProdutoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM Produto WHERE ativo = false";

        // Faz a conexão e executa a query:
        try(Connection conn = conexao.conectar(); Statement stmt = conn.createStatement(); ResultSet resultado = stmt.executeQuery(sql);){
            // Adiciona todos os dados na lista:
            while(resultado.next()){
                int id_produto = resultado.getInt("id_produto");
                String nome_produto = resultado.getString("nome_produto");
                String descricao_produto = resultado.getString("descricao_produto");
                int qtd_produto = resultado.getInt("qtd_produto");
                int qtd_minima = resultado.getInt("qtd_minima");
                boolean ativo = resultado.getBoolean("ativo");

                // Cria o objeto com os dados e insere ele na lista:
                lista.add(new ProdutoModel(id_produto,nome_produto, descricao_produto, qtd_produto, qtd_minima, ativo));
            }
            return lista;
        }
    }

    // UPDATE:
    public ProdutoModel update(ProdutoModel produtoModificado) throws SQLException{
        String sql = "UPDATE Produto SET nome_produto = ?, descricao_produto = ?, qtd_produto = ?, qtd_minima = ?, ativo = ? WHERE id_produto = ?";

        // Salva estado anterior:
        ProdutoModel anterior = readId(produtoModificado.getId_produto());

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados da query:
            stmt.setString(1, produtoModificado.getNome_produto());
            stmt.setString(2, produtoModificado.getDescricao_produto());
            stmt.setInt(3, produtoModificado.getQtd_produto());
            stmt.setInt(4, produtoModificado.getQtd_minima());
            stmt.setBoolean(5, produtoModificado.isAtivo());
            stmt.setInt(6, produtoModificado.getId_produto());
            int linhasAfetadas = stmt.executeUpdate();

            // Verifica se atualizou:
            if(linhasAfetadas == 0){
                return null;
            }

            // Log: 
            String log = "";
            if(!anterior.getNome_produto().equals(produtoModificado.getNome_produto())){
                log += "Nome : " + anterior.getNome_produto() + " -> " + produtoModificado.getNome_produto() + " | ";
             }
            if(!anterior.getDescricao_produto().equals(produtoModificado.getDescricao_produto())){
                log += "Descrição: " + anterior.getDescricao_produto() + " -> " + produtoModificado.getDescricao_produto() + " | ";
             }
            if(anterior.getQtd_produto() != produtoModificado.getQtd_produto()){
                log += "Quantidade: " + anterior.getQtd_produto() + " -> " + produtoModificado.getQtd_produto() + " | ";
            }
            if(anterior.getQtd_minima() != produtoModificado.getQtd_minima()){
                log += "Quantidade mínima: " + anterior.getQtd_minima() + " -> " + produtoModificado.getQtd_minima() + " | ";
             }
            if(anterior.isAtivo() != produtoModificado.isAtivo()){
                log += "Status: " + (!anterior.isAtivo() ? "DESATIVO -> ATIVO" : "ATIVO -> DESATIVO") + " | ";
            }
            LogDAO.registrar(Main.getIdUsuarioAtual(), "ATUALIZOU_PRODUTO",
                 "Atualizou o produto " + produtoModificado.getNome_produto() + "(ID: " + produtoModificado.getId_produto() + "). Mudanças feitas: " + (log.trim().isEmpty() ? "Nenhuma" : log));

         return produtoModificado;

        }
    }

    // ATIVAR/DESATIVAR (SOFT DELETE):
    public boolean desativarOuAtivar(int id, boolean estado) throws SQLException {
        String sql = "UPDATE Produto SET ativo = ? WHERE id_produto = ?";
        
        // Faz a conexão e prepara a query:
        try (Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            // Define os dados da query:
            stmt.setBoolean(1, estado);
            stmt.setInt(2, id);

            // Executa a query e verifica se atualizou o dado:
            int linhas = stmt.executeUpdate();
            if(linhas > 0){  
                 // Log:
                LogDAO.registrar(Main.getIdUsuarioAtual(), ((estado ? "ATIVOU" : "DESATIVOU ") + "_PRODUTO"), 
                (estado ? "Ativou" : "Desativou") + " o produto com ID: " + id + ".");
              return true;

            } else{
                return false;
              
            }
        }
    }
}
