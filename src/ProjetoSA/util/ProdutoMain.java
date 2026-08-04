package ProjetoSA.util;

import java.util.List;
import java.util.Scanner;

import ProjetoSA.Main;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.model.ProdutoModel;
import ProjetoSA.service.ProdutoService;

public class ProdutoMain {
    static Style sty;
    static Scanner sc;
    static FuncionarioModel usuarioAtual;
    static ProdutoService service = new ProdutoService();

    public static void main(Style style, Scanner scanner, FuncionarioModel u){
        // Definindo variáveis:
        sty = style;
        sc = scanner;
        usuarioAtual = u;

        Main.clear();
        // Menu de opções:
        int option = 1;
        do{
            try{
                sty.titulo("Produtos");
                System.out.println("Digite uma opção: \n"
                                + "1- Listar todos os Produtos \n"
                                + "2- Cadastrar Produto \n"
                                + "3- Detalhes de um Produto \n"
                                + "4- Listar Produtos Desativados \n"
                                + "0- Voltar");
                option = Integer.parseInt(sc.nextLine().trim());

                switch (option) {
                    case 1:
                        mostrarHistorico();
                        Main.continuar();
                        break;
                    case 2:
                        cadastrar();
                        Main.continuar();
                        break;
                    case 3:
                        detalhesProduto();
                        break;
                    case 4:
                        mostrarDesativados();
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

    // Procedimento para printar todo o histórico de produtos:
    public static void mostrarHistorico(){
        try{
            Main.clear();
            sty.titulo("Lista de Produtos");
            sty.quadro("ID   |     Produto     |   Quantidade   |   Quantidade Mínima");

            // Faz a requisição:
            List<ProdutoModel> produtos = service.listar();
            // Lista os produtos na tela:
            for(ProdutoModel p : produtos){
                sty.quadro(p.mostrarDados());
            }
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }
    }

    // Procedimento para cadastrar um novo produto:
    public static void cadastrar(){
        Main.clear();

        try{
            sty.titulo("Cadastrar Produto");
            System.out.print("Nome do produto: ");
            String nome = sc.nextLine().trim();
            System.out.print("Descrição do produto: ");
            String descricao = sc.nextLine().trim();
            System.out.print("Quantidade atual: ");
            int quantidade = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Quantidade mínima: ");
            int quantidadeMinima = Integer.parseInt(sc.nextLine().trim());

            ProdutoModel produto = new ProdutoModel(nome, descricao, quantidade, quantidadeMinima, true);
            // Fazendo requisição:
            service.salvar(produto);
            sty.quadro("Produto cadastrado com sucesso!");
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }
    }

    // Procedimento para buscar um produto:
    public static void detalhesProduto(){
        try{
            // Chama método auxiliar para buscar o produto:
            ProdutoModel alvo = buscarProduto();

            int option = -1;

            do{
                // Exibe detalhes do produto na tela:
                Main.clear();
                sty.titulo("Detalhes do Produto");

                System.out.println("ID: " + alvo.getId_produto() + "\n\n"
                                + "Nome: " + alvo.getNome_produto() + "\n"
                                + "Descrição: " + alvo.getDescricao_produto() + "\n"
                                + "Quantidade Atual: " + alvo.getQtd_produto() + "\n"
                                + "Quantidade Mínima: " + alvo.getQtd_minima() + "\n"
                                + "Status: " + (alvo.isAtivo() ? "ATIVO" : "DESATIVO")
                );

                // Verifica tipo de usuário:
                if(usuarioAtual.isAdmin()){
                    // Mostra menu de opções: 
                    System.out.println("\n\nDigite uma opção: \n"
                                    + "1- Editar Produto \n"
                                    + "2- " + (alvo.isAtivo() ? "Desativar" : "Ativar") + " Produto \n"
                                    + "0- Voltar"
                    );
                    option = Integer.parseInt(sc.nextLine().trim());
                    // Submenu das movimentações:
                    switch (option) {
                        case 1:
                            modificar(alvo);
                            option = 0;
                            break;
                        case 2:
                            desativarOuAtivar(alvo);
                            option = 0;
                            break;
                        case 0:
                            break;
                        default:
                            throw new RuntimeException("ERRO: opção digitada inválida!");
                    }
                } else{
                    System.out.println(); // Espaçamento
                    Main.continuar();
                    option = 0;
                }

                Main.clear();
            } while(option != 0);

        } catch(Exception e){
            sty.quadro(e.getMessage());
            Main.continuar();
        }
    }

    // Método auxiliar para buscar um produto de acordo com um ID informado:
    public static ProdutoModel buscarProduto() throws Exception{
        System.out.print("\n> Digite o ID do produto: ");
        int idAlvo = Integer.parseInt(sc.nextLine().trim());

        return service.buscarId(idAlvo);
    }

    // Procedimento para editar um produto:
    public static void modificar(ProdutoModel alvo){
        try{
            Main.clear();
            sty.titulo("Modificar Produto");
            System.out.println("Observação: pressione ENTER nos dados em que você não deseja modificar: ");
            System.out.print("Nome do produto: " + alvo.getNome_produto() + " -> ");
            String nome = sc.nextLine().trim();
            if(nome.isEmpty()){
                nome = alvo.getNome_produto();
            }

            System.out.print("Descrição do produto: " + alvo.getDescricao_produto() + " -> ");
            String descricao = sc.nextLine().trim();
            if(descricao.isEmpty()){
                descricao = alvo.getDescricao_produto();
            }

            System.out.print("Quantidade atual: " + alvo.getQtd_produto() + " -> ");
            String qtdTexto = sc.nextLine().trim();
            int quantidade;
            if(qtdTexto.isEmpty()){
                quantidade = alvo.getQtd_produto();
            } else{
                quantidade = Integer.parseInt(qtdTexto);
            }

            System.out.print("Quantidade mínima: " + alvo.getQtd_minima() + " -> ");
            String qtdMinTexto = sc.nextLine().trim();
            int quantidadeMinima;
            if(qtdMinTexto.isEmpty()){
                quantidadeMinima = alvo.getQtd_minima();
            } else{
                quantidadeMinima = Integer.parseInt(qtdMinTexto);
            }

            ProdutoModel produto = new ProdutoModel( nome, descricao, quantidade, quantidadeMinima, alvo.isAtivo());
            produto.setId_produto(alvo.getId_produto());
            service.atualizar(produto);
            sty.quadro("Produto modificado com sucesso!");
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }
        Main.continuar();
    }

    // Procedimento para desativar ou ativar um produto:
    public static void desativarOuAtivar(ProdutoModel alvo) throws Exception{
        System.out.println("\nDeseja mesmo "
                        + (alvo.isAtivo() ? "desativar" : "ativar")
                        + " esse produto?: \n"
                        + "1- Sim \n"
                        + "2- Não"
        );
        int option = Integer.parseInt(sc.nextLine().trim());

        switch (option) {
            case 1:
                // Define qual estado mudar:
                boolean estado;

                if(alvo.isAtivo()){
                    estado = false;
                } else{
                    estado = true;
                }

                // Cria requisição:
                service.desativarOuAtivar(
                    alvo.getId_produto(),
                    estado
                );

                sty.quadro("Produto "
                        + (estado ? "ativado" : "desativado")
                        + " com sucesso!");
                break;

            case 2:
                break;

            default:
                throw new RuntimeException("ERRO: opção digitada inválida!");
        }
        Main.continuar();
        Main.clear();
    }

    // Procedimento para printar os produtos desativados:
    public static void mostrarDesativados(){
        try{
            Main.clear();
            sty.titulo("Produtos Desativados");
            sty.quadro("ID   |     Produto     |   Quantidade   |   Quantidade Mínima");

            // Faz a requisição:
            List<ProdutoModel> produtos = service.listarDesativados();
            // Lista os produtos na tela:
            for(ProdutoModel p : produtos){
                sty.quadro(p.mostrarDados());
            }
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }
    }
}