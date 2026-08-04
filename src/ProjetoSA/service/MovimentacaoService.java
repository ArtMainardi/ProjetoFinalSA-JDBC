package ProjetoSA.service;

import ProjetoSA.model.MovimentacaoModel;
import ProjetoSA.repository.MovimentacaoDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MovimentacaoService {
    private MovimentacaoDAO repository = new MovimentacaoDAO();

    // Salvar:
    public MovimentacaoModel salvar(MovimentacaoModel m, String data, int idProduto, int idFuncionario, int idTipo) throws SQLException{
        // Verifica a quantidade de movimentações:
        if(m.getQtd_movimentacao() <= 0){
            throw new RuntimeException("Erro: A quantidade de movimentações deve ser maior que 0");
        }
        // Verifica a data:
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(data.isEmpty()){
            m.setData_movimentacao(LocalDate.now());
        } else{
            m.setData_movimentacao(LocalDate.parse(data, formato));
        }
        // Verifica o funcionário:
        FuncionarioService fService = new FuncionarioService();
        m.setFuncionario(fService.buscarID(idFuncionario));

        // Verifica o produto:
        ProdutoService pService = new ProdutoService();
        m.setProduto(pService.buscarId(idProduto));

        // Verifica o tipo de movimentação:
        TipoMovimentacaooService tService = new TipoMovimentacaooService();
        m.setTipo(tService.buscar(idTipo));
        // Verifica quantidade do produto para saída:
        if(m.getTipo().getTipo().equals("Saída")){
            if(m.getProduto().getQtd_produto() < m.getQtd_movimentacao()){
                throw new RuntimeException("Erro: Quantidade de estoque do produto insuficiente para a transferência! (" 
                    + m.getProduto().getQtd_produto() + " - " + m.getQtd_movimentacao() + ").");
            }
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
    public MovimentacaoModel atualizar(MovimentacaoModel m, String data, int idProduto, int idFuncionario, int idTipo) throws SQLException{
        // Verifica se encontrou algum dado com esse ID:
        buscarId(m.getId_movimentacao());

        // Verifica a quantidade de movimentações:
        if(m.getQtd_movimentacao() <= 0){
            throw new RuntimeException("Erro: A quantidade de movimentações deve ser maior que 0");
        }
        // Verifica a data:
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(data.isEmpty()){
            m.setData_movimentacao(LocalDate.now());
        } else{
            m.setData_movimentacao(LocalDate.parse(data, formato));
        }
        // Verifica o funcionário:
        FuncionarioService fService = new FuncionarioService();
        m.setFuncionario(fService.buscarID(idFuncionario));

        // Verifica o produto:
        ProdutoService pService = new ProdutoService();
        m.setProduto(pService.buscarId(idProduto));

        // Verifica o tipo de movimentação:
        TipoMovimentacaooService tService = new TipoMovimentacaooService();
        m.setTipo(tService.buscar(idTipo));
        
        // Manda a requisição para o repository:
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