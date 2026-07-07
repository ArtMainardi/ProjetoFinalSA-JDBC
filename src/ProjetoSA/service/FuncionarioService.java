package ProjetoSA.service;

import ProjetoSA.repository.FuncionarioDAO;

public class FuncionarioService {
    private FuncionarioDAO repository = new FuncionarioDAO();

    // Salvar:
    /*
    - Fazer verificação dos atributos {nome_funcionario, email_funcionario, senha_funcionario} com 'if(*.trim().isEmpty())'
    - Fazer verificação do retorno booleano do 'create()' do repository
    */

    // Listar:
    /*
    - Fazer verificação se encontrou algum dado com 'if(*.isEmpty())'
    */

    // Buscar (ID):
    /*
    - Fazer verificação do atributo {id} com 'if(* < 0)'
    - Fazer verificação se encontrou o objeto com 'if(* == null)'
    */

    // Buscar (EMAIL):
    /*
    - Criação do método: 'public FuncionarioModel buscarEmail(String email)''
    - Fazer verificação do atributo {email} com 'if(*.trim().isEmpty())'
    - Fazer verificação se encontrou o objeto com 'if(* == null)'
    */

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