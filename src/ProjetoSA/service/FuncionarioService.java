package ProjetoSA.service;


import java.sql.SQLException;
import java.util.List;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.repository.FuncionarioDAO;

public class FuncionarioService {
    private FuncionarioDAO repository = new FuncionarioDAO();


    public void salvar(FuncionarioModel funcionario) throws SQLException{
        if(funcionario.getNome_funcionario().trim().isEmpty()){
            throw new RuntimeException("ERRO: Nenhum dado do funcionário pode ser vazio!");
        }

        if(funcionario.getEmail_funcionario().trim().isEmpty()){
            throw new RuntimeException("ERRO: Nenhum dado do funcionário pode ser vazio!");
        }

        if(funcionario.getSenha_funcionario().trim().isEmpty()){
            throw new RuntimeException("ERRO: Nenhum dado do funcionário pode ser vazio!");
        }

        if(!repository.create(funcionario)){
            throw new RuntimeException("ERRO: Algo deu errado!");
        }
    }


     public List<FuncionarioModel> listar() throws SQLException{
        List<FuncionarioModel> lista = repository.read();

        if(lista.isEmpty()){
            throw new RuntimeException("Nenhum funcionário salvo no banco de dados!");

        }
        return lista;
     }

     public FuncionarioModel buscarID(int id_funcionario) throws SQLException{
        FuncionarioModel funcionario = repository.readId(id_funcionario);

        if(id_funcionario < 0){
            throw new RuntimeException("ERRO: funcionário com ID negativo");
        }
        if(funcionario == null){
            throw new RuntimeException("ERRO: funcionário com esse ID não encontrado!");
        }
        return funcionario;
     }

     public FuncionarioModel buscarEmail(String email) throws SQLException{
        FuncionarioModel funcionario = repository.readEmail(email);

        if(email.trim().isEmpty()){
            throw new RuntimeException("ERRO: email não pode ser vazio!");
        }
        if(funcionario == null){
            throw new RuntimeException("ERRO: funcionário com esse email não encontrado!");
        }

        return funcionario;
     }


    // Atualizar:
    /*
    - Fazer verificação do atributo {id} com 'if(* < 0)'
    - Fazer verificação dos atributos {nome_funcionario, email_funcionario, senha_funcionario} com 'if(*.trim().isEmpty())'
    - Fazer verificação se encontrou o objeto com 'if(* == null)'
    - Fazer verificação do retorno booleano do 'update()' do repository
    */

    // Desativar/Ativar:
    /*
    - Criação do método: 'public boolean desativarOuAtivar(int id, boolean estado)'
    - Fazer verificação do atributo {id} com 'if(* < 0)'
    - Fazer verificação se encontrou o objeto com 'TipoMovimentacaoModel objeto = repository.readId(id)' e 'if(* == null)'
    - Fazer verificação com 'if(objeto.isAtivo() == estado){ throw new RuntimeException("ERRO: o objeto com esse ID já está " + (estado ? "ativo" : "desativo") + "!"); }'
    - Fazer verificação do retorno booleano do 'desativarOuAtivar()' do repository
    */

    // Verificar email:
    /*
    - Criação do método: 'public boolean verificarEmail(String email)'
    - Fazer verificação do atributo {email} com 'if(*.trim().isEmpty())'
    - Retornar 'return repository.verificarEmail(email);'
    */

    // Verificar senha:
    /*
    - Criação do método: 'public boolean verificarSenha(String senha, String email)'
    - Fazer verificação dos atributos {senha, email} com 'if(*.trim().isEmpty())'
    - Fazer verificação se encontrou o objeto com 'TipoMovimentacaoModel objeto = repository.readEmail(email)' e 'if(* == null)'
    - Retornar 'return repository.verificarSenha(senha, email);'
    */
}