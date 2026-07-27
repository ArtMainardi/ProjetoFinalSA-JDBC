package ProjetoSA.repository;
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
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
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
                } else{
                    throw new SQLException("Falha ao inserir produto, nenhum ID foi gerado.");
                }
            }
        }
        // Retorna o produto com o ID gerado:
        return p;
    }

    // READ:
    public ArrayList<ProdutoModel> read() throws SQLException{
        ArrayList<ProdutoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM Produto";

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

    // UPDATE:
    public ProdutoModel update(ProdutoModel produtoModificado) throws SQLException{
        String sql = "UPDATE Produto SET nome_produto = ?, descricao_produto = ?, qtd_produto = ?, qtd_minima = ?, ativo = ? WHERE id_produto = ?";

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados da query:
            stmt.setString(1, produtoModificado.getNome_produto());
            stmt.setString(2, produtoModificado.getDescricao_produto());
            stmt.setInt(3, produtoModificado.getQtd_produto());
            stmt.setInt(4, produtoModificado.getQtd_minima());
            stmt.setBoolean(5, produtoModificado.isAtivo());
            int linhasAfetadas = stmt.executeUpdate();

            // Verifica se atualizou:
            if(linhasAfetadas == 0){
                return null;
            }
        }
        return produtoModificado;
    }

    // DELETE (DESATIVAR):
    public boolean desativar(int id) throws SQLException{
        String sql = "UPDATE Produto SET ativo = false WHERE id_produto = ?";

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados da query:
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            // Verifica se atualizou:
            if(linhasAfetadas == 0){
                return false;
            }
        }
        return true;
    }

    // DELETE (ATIVAR):
    public boolean ativar (int id) throws SQLException {
        String sql = "UPDATE Produto SET ativo = true WHERE id_produto = ?";

        // Faz a conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            // Define os dados da query:
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            // Verifica se atualizou:
            if(linhasAfetadas == 0){
                return false;
            }
        }
        return true;
    }
}
