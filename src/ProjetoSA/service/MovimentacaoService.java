package ProjetoSA.service;

import ProjetoSA.model.MovimentacaoModel;
import ProjetoSA.repository.MovimentacaoDAO;
import java.sql.SQLException;
import java.util.List;

public class MovimentacaoService {
    private MovimentacaoDAO repository = new MovimentacaoDAO();

    // Salvar:
    public MovimentacaoModel salvar(MovimentacaoModel m) throws SQLException{
        // Verifica a quantidade de movimentações:
        if(m.getQtd_movimentacao() <= 0){
            throw new RuntimeException("Erro: A quantidade de movimentações deve ser maior que 0");
        }
        // Verifica a data:
        if(m.getData_movimentacao() == null){
            throw new RuntimeException("Erro: A data de movimentação é obrigatória");
        }
        // Verifica o funcionário:
        if(m.getFuncionario() == null || m.getFuncionario().getId_funcionario() <= 0){
            throw new RuntimeException("Erro: Um funcionário válido deve ser associado a movimentação");
        }
        // Verifica o produto:
        if(m.getProduto() == null || m.getProduto().getId_produto() <= 0){
            throw new RuntimeException("Erro: Um produto válido deve ser associado a movimentação");
        }
        // Verifica o tipo de movimentação:
        if(m.getTipo() == null || m.getTipo().getId_tipo() <= 0){
            throw new RuntimeException("Erro: O tipo de movimentação deve ser informado");
        }
        // Manda a requisição para o repository:
        return repository.create(m);
    }

    // Listar:
    public List<MovimentacaoModel> listar() throws SQLException{
        // Cria lista e manda requisição para o repository:
        List<MovimentacaoModel> lista;
        lista = repository.read();
        // Verifica se encontrou algum dado:
        if(lista == null || lista.isEmpty()){
            throw new RuntimeException("Erro: Nenhuma movimentação salva!");
        }
        // Retorna a lista:
        return lista;
    }

    // Buscar (ID):
    public MovimentacaoModel buscarId(int id) throws SQLException{
        // Valida o ID informado:
        if(id < 0){
            throw new RuntimeException("Erro: ID informado inválido");
        }
        // Manda a requisição para o repository:
        MovimentacaoModel m = repository.readId(id);
        // Verifica se encontrou algum dado:
        if(m == null){
            throw new RuntimeException("Erro: Movimentação com o ID" + id + " não foi encontrada");
        }
        // Retorna o dado:
        return m;
    }

    // Atualizar:
    public MovimentacaoModel atualizar(MovimentacaoModel m) throws SQLException{
        if(m.getQtd_movimentacao() <= 0){
            throw new RuntimeException("Erro: A quantidade para atualização deve ser maior que zero");
        }
        if(m.getData_movimentacao() == null){
            throw new RuntimeException("Erro: A data não pode ser nula");
        }
        if(repository.readId(m.getId_movimentacao())== null){
            throw new RuntimeException("Erro: Não é possível atualizar uma movimentação inexistente");
        }
        return repository.update(m);
    }

    // Ativar/Desativar (soft delete):
    public boolean desativarOuAtivar(int id, boolean estado) throws SQLException{
        // Valida o ID informado:
        if(id < 0 || repository.readId(id) == null){
            throw new RuntimeException("Erro: ID informado inválido");
        }
        // Manda a requisição para o repository:
        return repository.desativarOuAtivar(id, estado);
    }
}