package ProjetoSA.service;

import ProjetoSA.model.ProdutoModel;
import ProjetoSA.repository.ProdutoDAO;
import java.sql.SQLException;
import java.util.List;

public class ProdutoService {
    private ProdutoDAO repository = new ProdutoDAO();

    // Salvar
    public ProdutoModel salvar(ProdutoModel produto) throws SQLException{
        // Verifica 'nome' do produto:
        if(produto.getNome_produto().trim().isEmpty()){
            throw new RuntimeException("ERRO: Nenhum dado do produto pode ser vazio!");
        }
        // Verifica 'quantidade':
        if(produto.getQtd_produto() < 0){
            throw new RuntimeException("ERRO: quantidade do produto não pode ser negativa!");
        }
        // Verifica 'quantidade mínima':
        if(produto.getQtd_minima() < 0){
            throw new RuntimeException("ERRO: quantidade mínima do produto não pode ser negativa!");
        }
        // Manda requisição para o repository:
        return repository.create(produto);
    }

    // Listar
    public List<ProdutoModel> listar() throws SQLException{
        List<ProdutoModel> lista = repository.read();
        // Verifica se encontrou algum dado:
        if(lista.isEmpty()){
            throw new RuntimeException("Nenhum produto salvo no banco de dados!");
        }
        // Retorna a lista:
        return lista;
    }

    // Buscar id
    public ProdutoModel buscarId(int id_produto) throws SQLException{
        // Verifica integridade do ID:
        if(id_produto < 0){
            throw new RuntimeException("ERRO: ID informado inválido!");
        }
        // Verifica se encontrou algum dado:
        ProdutoModel produto = repository.readId(id_produto);
        if(produto == null){
            throw new RuntimeException("ERRO: produto com esse ID não encontrado!");
        }
        // Retorna o produto:
        return produto;
    }

     // Atualizar
    public ProdutoModel atualizar(ProdutoModel modifiedProduto) throws SQLException{
        //  Verifica 'nome' do produto:
        if(modifiedProduto.getNome_produto().trim().isEmpty()){
            throw new RuntimeException("ERRO: nenhum dado do produto pode ser vazio!");
        }
        // Verifica 'quantidade':
        if(modifiedProduto.getQtd_produto() < 0){
            throw new RuntimeException("ERRO: quantidade do produto não pode ser negativa!");
        }
        // Verifica 'quantidade mínima':
        if(modifiedProduto.getQtd_minima() < 0){
            throw new RuntimeException("ERRO: quantidade mínima do produto não pode ser negativa!");
        }
        // Verifica se encontrou algum dado:
        if(repository.readId(modifiedProduto.getId_produto()) == null){
            throw new RuntimeException("ERRO: produto com esse ID não encontrado!");
        }
        // Manda a rquisição para o repository:
        return repository.update(modifiedProduto);
    }

    // Desativar ou Ativar
    public boolean desativarOuAtivar(int id, boolean estado) throws SQLException{
        // Verifica integridade do ID:
        if(id < 0){
            throw new RuntimeException("ERRO: produto com ID negativo!");
        }
        // Verifica se encontrou algum dado:
        ProdutoModel produto = repository.readId(id);
        if(produto == null){
            throw new RuntimeException("ERRO: produto com esse ID não encontrado!");
        }
        // Verifica se o dado já não possui o estado desejado:
        if(produto.isAtivo() == estado){
            throw new RuntimeException("ERRO: o objeto com esse ID já está " + (estado ? "ativo" : "desativo") + "!");
        }
        // Manda a requisição para o repository:
        return repository.desativarOuAtivar(id, estado);
    }
}