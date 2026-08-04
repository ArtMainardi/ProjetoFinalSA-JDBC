package ProjetoSA.util;

import java.util.Scanner;

import ProjetoSA.Main;
import ProjetoSA.repository.RelatorioDAO;

public class RelatorioMain {
    static Style sty;
    static Scanner sc;

    public static void main(Style style, Scanner scanner){
        // Definindo variáveis:
        sty = style;
        sc = scanner;

        Main.clear();
        // Menu de opções:
        int option = 1;
        do{
            try{
                sty.titulo("Relatórios");
                System.out.println("Digite uma opção: \n"
                                + "1- Produtos com Estoque Baixo (Alerta) \n"
                                + "2- Resumo Geral de Produtos no Estoque \n"
                                + "3- Ranking de Produtos Mais Movimentados \n"
                                + "4- Total por Tipo de Movimentação \n"
                                + "0- Voltar");
                option = Integer.parseInt(sc.nextLine().trim());

                switch (option) {
                    case 1:
                        RelatorioDAO.relatorioEstoqueBaixo();
                        Main.continuar();
                        break;
                    case 2:
                        RelatorioDAO.relatorioResumoEstoque();
                        Main.continuar();
                        break;
                    case 3:
                        RelatorioDAO.relatorioProdutosMaisMovimentados();
                        Main.continuar();
                        break;
                    case 4:
                        RelatorioDAO.relatorioTotaisPorTipoMovimentacao();
                        Main.continuar();
                        break;
                    case 0:
                        break;
                    default:
                        throw new Exception("ERRO: opção digitada inválida!");
                }
            } catch(Exception e){
                sty.quadro(e.getMessage());
                Main.continuar();
            }
            Main.clear();
        } while(option != 0);
    }
}