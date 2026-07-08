package ProjetoSA.service;

import ProjetoSA.model.MovimentacaoModel;
import ProjetoSA.repository.MovimentacaoDAO;
import java.util.List;

public class MovimentacaoService {
    private MovimentacaoDAO repository = new MovimentacaoDAO();


    public void salvar(MovimentacaoModel m){
        if(m.getQtd_movimentacao() <= 0){
            throw new RuntimeException("Erro: A quantidade de movimentações deve ser maior que 0");
        }
        if(m.getData_movimentacao() == null){
            throw new RuntimeException("Erro: A data de movimentação é obrigatória");
        }
        if(m.getFuncionario() == null || m.getFuncionario().getId_funcionario() <= 0){
            throw new RuntimeException("Erro: Um funcionário válido deve ser associado a movimentação");
        }
        if(m.getProduto() == null || m.getProduto().getId_produto() <= 0){
            throw new RuntimeException("Erro: Um produto válido deve ser associado a movimentação");
        }
        if(m.getTipo() == null || m.getTipo().getId_tipo() <= 0){
            throw new RuntimeException("Erro: O tipo de movimentação deve ser informado");
        }
        try {
            repository.salvar(m);
        } catch (Exception e) {
            throw new RuntimeException("Erro: Não foi possível salvar a movimentação");
        }
    }
    public List<MovimentacaoModel> listar(){
        List<MovimentacaoModel> lista;

        try {
            lista = repository.listarTodos();
        } catch (Exception e) {
            throw new RuntimeException("Erro: Falha ao buscar movimentações no banco de dados");
        }
        if(lista == null || lista.isEmpty()){
            throw new RuntimeException("Erro: Nenhuma movimentação foi encontrada!");
        }
        return lista;
    }
    public MovimentacaoModel buscarPorId(int id){
        if(id < 0){
            throw new RuntimeException("Erro: O ID informado não pode ser negativo");
        }
        MovimentacaoModel m;
        try {
            m = repository.buscarPorId(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro: Falha ao buscar a movimentação por ID");
        }
        if(m == null){
            throw new RuntimeException("Erro: Movimentação com o ID" + id + " não foi encontrada");
        }
        return m;
    }
    public void atualizar(MovimentacaoModel m){
        if(){
            
        }
    }
}