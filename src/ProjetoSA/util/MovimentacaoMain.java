package ProjetoSA.util;

import java.util.List;
import java.util.Scanner;

import ProjetoSA.Main;
import ProjetoSA.model.MovimentacaoModel;
import ProjetoSA.service.MovimentacaoService;

public class MovimentacaoMain {
    static Style sty;
    static Scanner sc;
    static MovimentacaoService service = new MovimentacaoService();

    public static void main(Style style, Scanner scanner) {
        // Definindo variáveis:
        sty = style;
        sc = scanner;

        Main.clear();
        // Menu de opções:
        int option;
        do{
            sty.titulo("Movimentações");
            System.out.println("Digite uma opção: \n"
                            + "1- Visualizar Histórico Geral \n"
                            + "2- Registrar Movimentação \n"
                            + "0- Voltar");
            option = Integer.parseInt(sc.nextLine().trim());
            try{
                switch (option) {
                    case 1:
                        mostrarHistorico();
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

    // Procedimento para printar todo o histórico de movimentações:
    public static void mostrarHistorico(){
        try{
            // Faz a requisição:
            List<MovimentacaoModel> movimentacoes = service.listar();
            // Lista as movimentações na tela:
            for(MovimentacaoModel m : movimentacoes){
                sty.quadro("ID: " + m.getId_movimentacao() + "  |  Produto: " + m.getProduto().getNome_produto() + "  |  Quantidade: " + m.getQtd_movimentacao());
            }
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }
        Main.continuar();
        Main.clear();
    }
}
