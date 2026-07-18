package ProjetoSA.service;


import java.sql.SQLException;
import java.util.List;
import ProjetoSA.model.ProdutoModel;
import ProjetoSA.repository.ProdutoDAO;

public class ProdutoService {

    private ProdutoDAO repository = new ProdutoDAO();

    // salvar
        public void salvar(ProdutoModel produto) throws SQLException{
            if(produto.getNome_produto().trim().isEmpty()){
                throw new RuntimeException("ERRO: Nenhum dado do produto pode ser vazio!");
            }

            if(produto.getQtd_produto() < 0){
                throw new RuntimeException("ERRO: quantidade do produto não pode ser negativa!");
            }
            if(produto.getQtd_minima() < 0){
                throw new RuntimeException("ERRO: quantidade mínima do produto não pode ser negativa!");
            }
            repository.create(produto);
        }

     // Listar
        public List<ProdutoModel> listar() throws SQLException{
            List<ProdutoModel> lista = repository.read();

            if(lista.isEmpty()){
                throw new RuntimeException("Nenhum produto salvo no banco de dados!");
            }
            return lista;
        }

    // Buscar id
        public ProdutoModel buscarID(int id_produto) throws SQLException{
            if(id_produto < 0){
                throw new RuntimeException("ERRO: produto com ID negativo");
            }

            ProdutoModel produto = repository.readId(id_produto);

            if(produto == null){
                throw new RuntimeException("ERRO: produto com esse ID não encontrado!");
            }
            return produto;
        }

     // Atualizar
        public void atualizar(ProdutoModel modifiedProduto) throws SQLException{
            if(modifiedProduto.getNome_produto().trim().isEmpty()){
                throw new RuntimeException("ERRO: nenhum dado do produto pode ser vazio!");
            }

            if(modifiedProduto.getQtd_produto() < 0){
                throw new RuntimeException("ERRO: quantidade do produto não pode ser negativa!");
            }
            if(modifiedProduto.getQtd_minima() < 0){
                throw new RuntimeException("ERRO: quantidade mínima do produto não pode ser negativa!");
            }

            ProdutoModel produto = repository.readId(modifiedProduto.getId_produto());

            if(produto == null){
                throw new RuntimeException("ERRO: produto com esse ID não encontrado!");
            }
            
            if(!repository.update(modifiedProduto)){
                throw new RuntimeException("ERRO: algo deu errado!");
            }

        }

    // Desativar ou Ativar
        public boolean desativarOuAtivar(int id, boolean estado) throws SQLException{
            if(id < 0){
                throw new RuntimeException("ERRO: produto com ID negativo!");
            }

            ProdutoModel produto = repository.readId(id);
            if(produto == null){
                throw new RuntimeException("ERRO: produto com esse ID não encontrado!");
            }

            if(produto.isAtivo() == estado){
                throw new RuntimeException("ERRO: o objeto com esse ID já está " + (estado ? "ativo" : "desativo") + "!");
            }
            boolean sucesso = estado ? repository.ativar(id) : repository.desativar(id);

            if(!sucesso){
                throw new RuntimeException("ERRO: algo deu errado!");
            }
            return estado;
        }
  

}