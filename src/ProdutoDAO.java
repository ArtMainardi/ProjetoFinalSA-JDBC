import connection.Conexao;
import model.ProdutoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ProdutoDAO {
    Conexao conexao = new Conexao();

    public ArrayList <ProdutoModel> listar() throws SQLException{
        ArrayList<ProdutoModel> lista = new ArrayList<>();

        String sql = "SELECT * FROM Produto";

        Connection conn = conexao.conectar();
        Statement stmt = conn.createStatement();
        ResultSet resultado = stmt.executeQuery(sql);

        while(resultado.next()){
            int id_produto = resultado.getInt("id_produto");
            String nome_produto = resultado.getString("nome_produto");
            String descricao_produto = resultado.getString("descricao_produto");
            int qtd_produto = resultado.getInt("qtd_produto");
            int qtd_minima = resultado.getInt("qtd_minima");
            boolean ativo = resultado.getBoolean("ativo");

            lista.add(new ProdutoModel(id_produto,nome_produto, descricao_produto, qtd_produto, qtd_minima, ativo));
        }
        return lista;
    }

    public void inserir(ProdutoModel p) throws SQLException{
        String sql = "INSERT INTO Produto(nome_produto, descricao_produto, qtd_produto, qtd_minima, ativo) VALUES (?,?,?,?,?)";

        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, p.getNome_produto());
            stmt.setString(2, p.getDescricao_produto());
            stmt.setInt(3, p.getQtd_produto());
            stmt.setInt(4, p.getQtd_minima());
            stmt.setBoolean(5, p.isAtivo());

            stmt.executeUpdate();
            System.out.println("Produto inserido com sucesso!");
        }
    }

    public ProdutoModel buscar (int id) throws SQLException{
        String sql = "SELECT * FROM Produto WHERE id_produto = ?";

        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, id);

            try(ResultSet resultado = stmt.executeQuery();){
                if(resultado.next()){
                    int id_produto = resultado.getInt("id_produto");
                    String nome_produto = resultado.getString("nome_produto");
                    String descricao_produto = resultado.getString("descricao_produto");
                    int qtd_produto = resultado.getInt("qtd_produto");
                    int qtd_minima = resultado.getInt("qtd_minima");
                    boolean ativo = resultado.getBoolean("ativo");

                    ProdutoModel produtoBuscar = new ProdutoModel(id_produto, nome_produto, descricao_produto, qtd_produto, qtd_minima, ativo);
                    return produtoBuscar;
                }
            }
        }
                return null;
    }

    public boolean atualizar(ProdutoModel produtoModificado) throws SQLException{
        String sql = "UPDATE Produto SET nome_produto = ?, descricao_produto = ?, qtd_produto = ?, qtd_minima = ?, ativo = ? WHERE id_produto = ?";

        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, produtoModificado.getNome_produto());
            stmt.setString(2, produtoModificado.getDescricao_produto());
            stmt.setInt(3, produtoModificado.getQtd_produto());
            stmt.setInt(4, produtoModificado.getQtd_minima());
            stmt.setBoolean(5, produtoModificado.isAtivo());

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0){
                return true;
            }else {
                return false;
            }
        }
    }

    public boolean desativar(int id) throws SQLException{
        String sql = "UPDATE Produto SET ativo = false WHERE id_produto = ?";

        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, id);
            
            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0){
                return true;
            }else {
                return false;
            }
        }
    }

    public boolean ativar (int id) throws SQLException {
        String sql = "UPDATE Produto SET ativo = true WHERE id_produto = ?";

        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0){
                return true;
            }else {
                return false;
            }
        }
    }
}
