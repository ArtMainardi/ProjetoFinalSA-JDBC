package ProjetoSA.service;


import java.sql.SQLException;
import java.util.List;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.repository.FuncionarioDAO;

public class FuncionarioService {
    private FuncionarioDAO repository = new FuncionarioDAO();

    // Salvar
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

    // Listar
     public List<FuncionarioModel> listar() throws SQLException{
        List<FuncionarioModel> lista = repository.read();

        if(lista.isEmpty()){
            throw new RuntimeException("Nenhum funcionário salvo no banco de dados!");

        }
        return lista;
     }

     // Buscar ID
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

     // Buscar Email
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

     // Atualizar
     public void atualizar(FuncionarioModel modifiedFuncionario) throws SQLException{
        
        if(modifiedFuncionario.getNome_funcionario().trim().isEmpty()){
            throw new RuntimeException("ERRO: nenhum dado do funcionário pode ser vazio");
        }

        if(modifiedFuncionario.getEmail_funcionario().trim().isEmpty()){
            throw new RuntimeException("ERRO: nenhum dado do funcionário pode ser vazio");
        }
        if(modifiedFuncionario.getSenha_funcionario().trim().isEmpty()){
            throw new RuntimeException("ERRO: nenhum dado do funcionário pode ser vazio");
        }

        FuncionarioModel funcionario = repository.readId(modifiedFuncionario.getId_funcionario());
        if(funcionario == null){
            throw new RuntimeException("ERRO: funcionário com esse ID não encontrado!");
        }

        if(!repository.update(modifiedFuncionario)){
            throw new RuntimeException("ERRO: algo deu errado!");
        }
     }


     // Desativar ou Ativar
     public boolean desativarOuAtivar(int id, boolean estado) throws SQLException{
        if(id < 0){
            throw new RuntimeException("ERRO: funcionário com ID negativo!");
        }
        FuncionarioModel funcionario = repository.readId(id);
            if(funcionario == null){
                throw new RuntimeException("ERRO: funcionário com esse ID não encontrado!");
            }
            if(funcionario.isAtivo() == estado){
                throw new RuntimeException("ERRO: o objeto com esse ID já está " + (estado ? "ativo" : "desativo") + "!");
            }
            if(!repository.desativarOuAtivar(id, estado)){
                throw new RuntimeException("ERRO: algo deu errado!");
            }
             return estado;
     }



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