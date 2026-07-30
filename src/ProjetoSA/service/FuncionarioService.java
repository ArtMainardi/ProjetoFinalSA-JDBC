package ProjetoSA.service;


import java.sql.SQLException;
import java.util.List;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.repository.FuncionarioDAO;

public class FuncionarioService {
    private FuncionarioDAO repository = new FuncionarioDAO();

    // Salvar:
    public FuncionarioModel salvar(FuncionarioModel funcionario) throws SQLException{
        // Verifica integridade de dados:
        if(funcionario.getNome_funcionario().trim().isEmpty() || funcionario.getEmail_funcionario().trim().isEmpty()
                || funcionario.getSenha_funcionario().trim().isEmpty()){
            throw new RuntimeException("ERRO: Nenhum dado do funcionário pode ser vazio!");
        }
        // Manda a requisição para o repository:
        return repository.create(funcionario);
    }

    // Listar:
    public List<FuncionarioModel> listar() throws SQLException{
        // Cria a lista e envia a requisição para o repository:
        List<FuncionarioModel> lista = repository.read();
        // Verifica se encontrou algum dado:
        if(lista.isEmpty()){
            throw new RuntimeException("Nenhum funcionário salvo no banco de dados!");
        }
        // Retorna a lista:
        return lista;
    }

    // Buscar (ID):
    public FuncionarioModel buscarID(int id_funcionario) throws SQLException{
        // Verifica integridade do ID:
        if(id_funcionario < 0){
            throw new RuntimeException("ERRO: funcionário com ID negativo");
        }
        // Manda a requisição para o repository e verifica se encontrou algum dado:
        FuncionarioModel funcionario = repository.readId(id_funcionario);
        if(funcionario == null){
            throw new RuntimeException("ERRO: funcionário com esse ID não encontrado!");
        }
        // Retorna o dado:
        return funcionario;
    }

    // Buscar (email):
    public FuncionarioModel buscarEmail(String email) throws SQLException{
        // Verifica integridade do email informado:
        if(email.trim().isEmpty()){
            throw new RuntimeException("ERRO: email não pode ser vazio!");
        }
        // Manda a requisição para o repository e verifica se encontrou algum dado:
        FuncionarioModel funcionario = repository.readEmail(email);
        if(funcionario == null){
            throw new RuntimeException("ERRO: funcionário com esse email não encontrado!");
        }
        // Retorna o dado:
        return funcionario;
    }

    // Atualizar
    public FuncionarioModel atualizar(FuncionarioModel modifiedFuncionario) throws SQLException{
        // Verifica integridade de dados:
        if(modifiedFuncionario.getNome_funcionario().trim().isEmpty() || modifiedFuncionario.getEmail_funcionario().trim().isEmpty()
                || modifiedFuncionario.getSenha_funcionario().trim().isEmpty()){
            throw new RuntimeException("ERRO: Nenhum dado do funcionário pode ser vazio!");
        }
        // Verifica se encontrou algum dado com esse ID:
        buscarID(modifiedFuncionario.getId_funcionario());
        // Manda a requisição para o repository:
        return repository.update(modifiedFuncionario);
    }

    // Desativar ou Ativar (soft delete):
    public boolean desativarOuAtivar(int id, boolean estado) throws SQLException{
        // Verifica integridade do ID:
        if(id < 0){
            throw new RuntimeException("ERRO: funcionário com ID negativo!");
        }
        // Verifica se encontrou algum dado:
        FuncionarioModel funcionario = repository.readId(id);
        if(funcionario == null){
            throw new RuntimeException("ERRO: funcionário com esse ID não encontrado!");
        }
        // Verifica se o dado encontrado já não possui o estado informado:
        if(funcionario.isAtivo() == estado){
            throw new RuntimeException("ERRO: o objeto com esse ID já está " + (estado ? "ativo" : "desativo") + "!");
        }
        // Manda a requisição para o repository:
        return repository.desativarOuAtivar(id, estado);
    }

    // Verificar email:
    public boolean verificarEmail(String email) throws SQLException{
        // Verifica integridade do email:
        if(email.trim().isEmpty()){
            throw new RuntimeException("ERRO: email não pode ser vazio");
        }
        // Manda a requisição para o repository:
        return repository.verificarEmail(email);
    }
     
    // Verificar senha:
    public boolean verificarSenha(String senha, String email) throws SQLException{
        // Verifica integridade dos dados:
        if(!verificarEmail(email)){
            throw new RuntimeException("ERRO: nenhum funcionário encontrado com esse email!");
        }
        if(senha.trim().isEmpty()){
            throw new RuntimeException("ERRO: senha não pode ser vazia!");
        }
        // Manda a requisição para o repository:
        return repository.verificarSenha(senha, email);
    }
}