package ProjetoSA.util;

import ProjetoSA.Main;
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
                        break;
                    case 6:
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
        } while (opcao != 6);
    }
}
