package ProjetoSA.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import ProjetoSA.Main;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.model.MovimentacaoModel;
import ProjetoSA.service.MovimentacaoService;

public class MovimentacaoMain {
    static Style sty;
    static Scanner sc;
    static MovimentacaoService service = new MovimentacaoService();

    public static void main(Style style, Scanner scanner, FuncionarioModel usuarioAtual) {
        // Definindo variáveis:
        sty = style;
        sc = scanner;

        Main.clear();
        // Menu de opções:
        int option = 1;
        do{
            try{
                sty.titulo("Movimentações");
                System.out.println("Digite uma opção: \n"
                                + "1- Visualizar Histórico Geral \n"
                                + "2- Registrar Movimentação \n"
                                + "3- Detalhes de uma Movimentação \n"
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
                        detalhesMovimentacao(usuarioAtual);
                        break;
                    case 0:
                        break;
                    default:
                        throw new Exception("ERRO: opção digitada inválida!");
                }
            } catch(Exception e){
                sty.quadro(e.getMessage());
            }
            Main.clear();
        } while(option != 0);
    }

    // Procedimento para printar todo o histórico de movimentações:
    public static void mostrarHistorico(){
        try{
            Main.clear();
            sty.titulo("Histórico de Movimentações");
        sty.quadro("ID   |     Produto     |   Quantidade   |   Funcionário");
            // Faz a requisição:
            List<MovimentacaoModel> movimentacoes = service.listar();
            // Lista as movimentações na tela:
            for(MovimentacaoModel m : movimentacoes){
                sty.quadro(m.mostrarDados());
            }
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }
    }

    // Procedimento para cadastrar uma nova movimentação:
    public static void cadastrar(){
        Main.clear();
        try{
            sty.titulo("Cadastrar Movimentação");
            // Definindo dados:
            System.out.println("Tipo de movimentação: \n"
                            + "1- Entrada \n"
                            + "2- Saída"
            );
            int tipo = sc.nextInt();
            System.out.println(); // Espaçamento
            System.out.print("ID do produto: ");
            int idProduto = sc.nextInt();
            System.out.print("Quantidade do produto: ");
            int quantidade = sc.nextInt();
            System.out.print("ID do Funcionário: ");
            int idFuncionario = sc.nextInt();
            System.out.println("Data da movimentação (DD/MM/AAAA, ou ENTER para data atual): ");
            sc.nextLine();
            String data = sc.nextLine().trim();

            // Fazendo requisição:
            service.salvar(new MovimentacaoModel(quantidade), data, idProduto, idFuncionario, tipo);
            sty.quadro("Movimentação registrada com sucesso!!");
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }
    }

    // Procedimento para buscar uma movimentação:
    public static void detalhesMovimentacao(FuncionarioModel usuarioAtual){
        try{
            // Chama método auxiliar para buscar a movimentação:
            MovimentacaoModel alvo = buscarMovimentação();
            
            int option = -1;
            do{
                // Exibe detalhes da movimentação na tela:
                Main.clear();
                sty.titulo("Detalhes da Movimentacao");
                System.out.println("ID: " + alvo.getId_movimentacao() + "\n\n"
                                + "Produto: \n"
                                + "  -> ID: " + alvo.getProduto().getId_produto() + "\n"
                                + "  -> Nome: " + alvo.getProduto().getNome_produto() + "\n"
                                + "Quantidade: " + alvo.getQtd_movimentacao() + "\n\n"
                                + "Tipo de Movimentação: " + alvo.getTipo().getTipo() + "\n"
                                + "Funcionário: \n"
                                + "  -> ID: " + alvo.getFuncionario().getId_funcionario() + "\n"
                                + "  -> Nome: " + alvo.getFuncionario().getNome_funcionario() + "\n"
                                + "Data da Movimentação: " + alvo.getData_movimentacao() + "\n"
                );

                // Verifica tipo de usuário:
                if(usuarioAtual.isAdmin()){
                    // Mostra menu de opções: 
                    System.out.println("\n\nDigite uma opção: \n"
                                    + "1- Editar Movimentação \n"
                                    + "2- Excluir Movimentação \n"
                                    + "0- Voltar"
                    );
                    option = Integer.parseInt(sc.nextLine().trim());
                    // Submenu das movimentações:
                    switch (option) {
                        case 1:
                            modificar(alvo);
                            option = 0;
                            break;
                        default:
                            break;
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
        }
    }
    // Método auxiliar para buscar uma movimentação de acordo com um ID informado:
    public static MovimentacaoModel buscarMovimentação() throws SQLException{
        System.out.print("\n> Digite o ID da movimentação: ");
        int idAlvo = Integer.parseInt(sc.nextLine().trim());
        return service.buscarId(idAlvo);
    }

    // Procedimento para editar um dado:
    public static void modificar(MovimentacaoModel alvo){
        try{
            Main.clear();
            sty.titulo("Modificar Movimentação");
            // Definindo dados:
            System.out.println("Tipo de movimentação: \n"
                            + "1- Entrada \n"
                            + "2- Saída"
            );
            System.out.print(alvo.getTipo().getTipo() + " -> ");
            int idTipo = sc.nextInt();
            System.out.print("ID do produto: " + alvo.getProduto().getId_produto() + " -> ");
            int idProduto = sc.nextInt();
            System.out.print("Quantidade do produto: " + alvo.getQtd_movimentacao() + " -> ");
            int quantidade = sc.nextInt();
            System.out.print("ID do funcionário: " + alvo.getFuncionario().getId_funcionario() + " -> ");
            int idFuncionario = sc.nextInt();
            System.out.print("Data da movimentação (DD/MM/AAAA, ou ENTER para data atual): " + alvo.getData_movimentacao() + " -> ");
            sc.nextLine();
            String data = sc.nextLine().trim();

            MovimentacaoModel m = new MovimentacaoModel(quantidade);
            m.setId_movimentacao(alvo.getId_movimentacao());

            service.atualizar(m, data, idProduto, idFuncionario, idTipo);
            sty.quadro("Movimentação modificada com sucesso!");
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }
        Main.continuar();
    }
}
