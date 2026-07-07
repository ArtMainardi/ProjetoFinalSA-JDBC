package ProjetoSA.service;

import ProjetoSA.model.TipoMovimentacaoModel;
import ProjetoSA.repository.TipoMovimentacaoDAO;
import java.sql.SQLException;
import java.util.List;

public class TipoMovimentaçãoService {
    // Cria objeto DAO para podermos usar seus métodos:
    private TipoMovimentacaoDAO repository = new TipoMovimentacaoDAO();

    // Salvar:
    public String salvar(TipoMovimentacaoModel objeto){
        String message;
        try {
            // Verifica o atributo 'tipo':
            if(objeto.getTipo().trim().isEmpty()){
                throw new Exception("ERRO: nenhum dado do objeto pode ser vazio!");
            }
            // Verifica o retorno booleano do 'crate()':
            if(!repository.create(objeto)){
                throw new Exception("ERRO: algo deu de errado");
            }
            // Define a mensagem de retorno:
            message = "Tipo de movimentação salvo com sucesso!";
        } catch (Exception e) {
            message = e.getMessage();
        }
        // Retorna a mensagem:
        return message;
    }

    // Listar:
    public List<TipoMovimentacaoModel> listar() throws SQLException{
        return repository.read();
    }
}