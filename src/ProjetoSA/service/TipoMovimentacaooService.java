package ProjetoSA.service;

import ProjetoSA.model.TipoMovimentacaoModel;
import ProjetoSA.repository.TipoMovimentacaoDAO;
import java.sql.SQLException;
import java.util.List;

public class TipoMovimentacaooService {
    // Cria objeto DAO para podermos usar seus métodos:
    private TipoMovimentacaoDAO repository = new TipoMovimentacaoDAO();

    // Salvar:
    public TipoMovimentacaoModel salvar(TipoMovimentacaoModel objeto) throws SQLException{
        // Verifica o atributo 'tipo':
        if(objeto.getTipo().trim().isEmpty()){
            throw new RuntimeException("ERRO: nenhum dado do objeto pode ser vazio!");
        }
        // Manda a requisição para o repository:
        return repository.create(objeto);
    }

    // Listar:
    public List<TipoMovimentacaoModel> listar() throws SQLException{
        List<TipoMovimentacaoModel> lista = repository.read();

        // Verifica se encontrou algum dado:
        if(lista.isEmpty()){
            throw new RuntimeException("Nenhum tipo de movimentação salvo no banco de dados!");
        }
        return lista;
    }

    // Buscar:
    public TipoMovimentacaoModel buscar(int id) throws SQLException{
        TipoMovimentacaoModel objeto = repository.readId(id);

        // Verifica se encontrou o objeto:
        if(objeto == null){
            throw new RuntimeException("ERRO: objeto com esse ID não encontrado!");
        }
        return objeto;
    }

    // Atualizar:
    public TipoMovimentacaoModel atualizar(TipoMovimentacaoModel modifiedObjeto) throws SQLException{
        // Verifica o atributo 'tipo':
        if(modifiedObjeto.getTipo().trim().isEmpty()){
            throw new RuntimeException("ERRO: nenhum dado do objeto pode ser vazio!");
        }

        // Verifica se encontrou o objeto:
        TipoMovimentacaoModel objeto = repository.readId(modifiedObjeto.getId_tipo());
        if(objeto == null){
            throw new RuntimeException("ERRO: objeto com esse ID não encontrado!");
        }
        
        // Manda a requisição para o repository:
        return repository.update(modifiedObjeto);
    }

    // Deletar:
    public void deletar(int id) throws SQLException{
        // Verifica se encontrou o objeto:
        TipoMovimentacaoModel objeto = repository.readId(id);
        if(objeto == null){
            throw new RuntimeException("ERRO: objeto com esse ID não encontrado!");
        }
        // Manda a requisição para o repository:
        repository.delete(id);
    }
}
