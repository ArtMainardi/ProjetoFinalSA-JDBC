package ProjetoSA.util;

import java.util.Scanner;

public class MovimentacaoMain {
    public static void main(Style sty, Scanner sc) {
        // Menu de opções:
        int option;
        do{
            sty.titulo("Movimentações");
            System.out.println("Digite uma opção: \n"
                            + "1- Visualizar Histórico Geral \n"
                            + "2- Registrar Movimentação \n"
                            + "0- Voltar");
            option = sc.nextInt();
            try{
                switch (option) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 0:
                        break;
                    default:
                        throw new Exception("ERRO: opção digitada inválida!");
                }
            } catch(Exception e){
                System.out.println(e.getMessage() + "\n");
            }
        } while(option != 0);
    }
}
