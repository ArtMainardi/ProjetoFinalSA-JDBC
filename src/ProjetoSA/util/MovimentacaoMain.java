package ProjetoSA.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        int option = 1;
        do{
            try{
                sty.titulo("Movimentações");
                System.out.println("Digite uma opção: \n"
                                + "1- Visualizar Histórico Geral \n"
                                + "2- Registrar Movimentação \n"
                                + "0- Voltar");
                option = Integer.parseInt(sc.nextLine().trim());
                switch (option) {
                    case 1:
                        mostrarHistorico();
                        break;
                    case 2:
                        cadastrar();
                        break;
                    case 0:
                        break;
                    default:
                        throw new Exception("ERRO: opção digitada inválida!");
                }
            } catch(Exception e){
                sty.quadro(e.getMessage());
            }
            Main.continuar();
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
}
