package ProjetoSA.util;

import ProjetoSA.Main;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.service.FuncionarioService;
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
}
