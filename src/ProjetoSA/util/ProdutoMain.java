package ProjetoSA.util;

import ProjetoSA.Main;
import ProjetoSA.model.ProdutoModel;
import ProjetoSA.service.ProdutoService;
import java.util.List;
import java.util.Scanner;

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
                    case 2:
                        salvar();
                        break;
                    case 3:
                        listarProdutos();
                        break;
                    case 4:
                        atualizar();
                        break;
                    case 5:
                        deletar();
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
    //método que vai cadastrar os produtos
    public static void salvar(){
        try {
            Main.clear();
            sty.titulo("Cadastrar Produto");
            
            System.out.println("Nome do produto: ");
            String nome = sc.nextLine().trim();
            System.out.println("Descrição do produto: ");
            String descricao = sc.nextLine().trim();
            System.out.println("Quantidade Atual: ");
            int qtdAtual = Integer.parseInt(sc.nextLine().trim());
            System.out.println("Quantidade Mínima: ");
            int qtdMinima = Integer.parseInt(sc.nextLine().trim());
            //o id será passado como 0, pois o banco vai gerar automaticamente.
            
            ProdutoModel novoProduto = new ProdutoModel(nome, descricao, qtdAtual, qtdMinima, true);
            service.salvar(novoProduto);
            
            sty.quadro("Produto Cadastrado com Sucesso!");
        } catch (Exception e) {
            sty.quadro("Erro ao cadastrar: " + e.getMessage());
        }
    }
    //metodo para listar produtos
    public static void listarProdutos(){
        try {
            Main.clear();
            sty.titulo("Lista de Produtos");

            List<ProdutoModel> produtos = service.listar();

            for(ProdutoModel p : produtos){
                //condicional pra listar somente os ativos:
                if(p.isAtivo()){
                    sty.quadro("ID: " + p.getId_produto() + " | Nome: " + p.getNome_produto() + " | Qtd: " + p.getQtd_produto());
                }
            }
        } catch (Exception e) {
            sty.quadro("ERRO ao listar: " + e.getMessage());
        }
    }
    
}
