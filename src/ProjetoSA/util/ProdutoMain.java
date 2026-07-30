package ProjetoSA.util;

import ProjetoSA.Main;
import ProjetoSA.model.MovimentacaoModel;
import ProjetoSA.service.ProdutoService;

import java.util.List;
import java.util.Scanner;

import ProjetoSA.Main;
import ProjetoSA.model.ProdutoModel;

public class ProdutoMain {
    static Style sty;
    static Scanner sc;
    static ProdutoService service = new ProdutoService();

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
                                + "1 - Visualizar Histórico Geral \n"
                                + "2 - Cadastrar Produto \n"
                                + "3 - Listar Produtos \n"
                                + "4 - Atualizar Produto \n"
                                + "5 - Deletar Produto \n"
                                + "6 - Sair");
                opcao = Integer.parseInt(sc.nextLine().trim());
                switch (opcao) {
                    case 1:
                        mostrarHistorico();
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
    //método para printar o histórico de produtos:
    public static void mostrarHistorico(){
        try{
            Main.clear();
            sty.titulo("Histórico de Produtos");
        sty.quadro("Nome_Produto   |     Descrição_Produto     |   Quantidade   |   Quantidade Mínima");
            // Faz a requisição:
            List<ProdutoModel> produtos = service.listar();
            // Lista as movimentações na tela:
            for(ProdutoModel p : produtos){
                sty.quadro(p.mostrarDados());
            }
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }
    }
}
