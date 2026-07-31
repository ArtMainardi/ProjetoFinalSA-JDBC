package ProjetoSA.util;

import ProjetoSA.Main;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.service.FuncionarioService;
import java.util.List;
import java.util.Scanner;

public class FuncionarioMain {
    static Style sty;
    static Scanner sc;
    static FuncionarioService service = new FuncionarioService();

    public static void main(Style style, Scanner scanner){
        //definição de variáveis
        sty = style;
        sc = scanner;

        Main.clear();
        //menu de opções
        int opcao = 0;
        do { 
            try {
                sty.titulo("Produtos");
                System.out.println("Digite uma opção: \n"
                                + "1 - Cadastrar Cliente \n"
                                + "2 - Listar Clientes \n"
                                + "3 - Atualizar Cliente \n"
                                + "4 - Deletar Cliente \n"
                                + "5 - Sair");
                opcao = Integer.parseInt(sc.nextLine().trim());
                switch (opcao) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        break;
                    default:
                        throw new Exception("ERRO: opção digitada inválida!");
                }
            } catch (Exception e) {
                sty.quadro(e.getMessage());
            }
            Main.continuar();
            Main.clear();
        } while (opcao != 5);
    }
    //metodo que vai cadastrar funcionarios
    public static void salvar(){
        try {
            Main.clear();
            sty.titulo("Cadastrar Funcionario");

            System.out.print("Nome: ");
            String nome = sc.nextLine().trim();
            System.out.print("Email: ");
            String email = sc.nextLine().trim();
            System.out.print("Senha: ");
            String senha = sc.nextLine().trim();
            System.out.print("É administrador? (1 - Sim / 2 - Não): ");
            int opcaoAdmin = Integer.parseInt(sc.nextLine().trim());
            boolean isAdmin = (opcaoAdmin == 1);
            
            // O funcionário já começa ativo (true).
            FuncionarioModel novoFuncionario = new FuncionarioModel(nome, email, senha, isAdmin, true);
            service.salvar(novoFuncionario);
            
            sty.quadro("Funcionário cadastrado com sucesso!");
        } catch (Exception e) {
            sty.quadro("Erro ao cadastrar: " + e.getMessage());
        }
    }
    //método que lista os funcionarios
    public static void listarFuncionarios(){
        try {
            Main.clear();
            sty.titulo("Lista de Funcionários");

            List<FuncionarioModel> funcionarios = service.listar();

            for(FuncionarioModel f : funcionarios){
                // Operador ternário para facilitar a leitura no terminal
                String admin = f.isAdmin() ? "Sim" : "Não";
                String status = f.isAtivo() ? "Ativo" : "Desativado";
                
                sty.quadro("ID: " + f.getId_funcionario() + 
                           " | Nome: " + f.getNome_funcionario() + 
                           " | Email: " + f.getEmail_funcionario() + 
                           " | Admin: " + admin +
                           " | Status: " + status);
            }
        } catch (Exception e) {
            sty.quadro("ERRO ao listar: " + e.getMessage());
        }
    }
    public static void atualizar(){
        try {
            Main.clear();
            sty.titulo("Atualizar Funcionário");

            System.out.print("Digite o ID do funcionário que deseja atualizar: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            
            // Busca o funcionário para confirmar que ele existe (o service vai dar throw se não achar)
            FuncionarioModel funcAtual = service.buscarID(id);
            System.out.println("Editando o funcionário: " + funcAtual.getNome_funcionario());
            
            System.out.print("Digite o novo nome: ");
            String nome = sc.nextLine().trim();
            System.out.print("Digite o novo email: ");
            String email = sc.nextLine().trim();
            System.out.print("Digite a nova senha: ");
            String senha = sc.nextLine().trim();
            
            System.out.print("É administrador? (1 - Sim / 2 - Não): ");
            int opcaoAdmin = Integer.parseInt(sc.nextLine().trim());
            boolean isAdmin = (opcaoAdmin == 1);
            
            System.out.print("O funcionário está ativo? (1 - Sim / 2 - Não): ");
            int opcaoAtivo = Integer.parseInt(sc.nextLine().trim());
            boolean isAtivo = (opcaoAtivo == 1);

            FuncionarioModel funcionarioModificado = new FuncionarioModel(id, nome, email, senha, isAdmin, isAtivo);
            service.atualizar(funcionarioModificado);

            sty.quadro("Funcionário atualizado com sucesso!");
        } catch (Exception e) {
            sty.quadro("ERRO ao atualizar: " + e.getMessage());
        }
    }
}
