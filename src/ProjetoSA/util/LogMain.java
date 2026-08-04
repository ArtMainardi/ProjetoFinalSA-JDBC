package ProjetoSA.util;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import ProjetoSA.Main;
import ProjetoSA.model.LogModel;
import ProjetoSA.repository.LogDAO;

public class LogMain {
    static Style sty;
    static Scanner sc;
    static LogDAO repository = new LogDAO();

    public static void main(Style style, Scanner scanner){
        sty = style;
        sc = scanner;

        Main.clear();
        int option = 1;

        do {
            try {
                sty.titulo("LOGS DE AUDITORIA");
                System.out.println("Digite uma opção: \n"
                                + "1- Listar Todos os Logs \n"
                                + "2- Detalhes de um Log \n"
                                + "0- Voltar");
                option = Integer.parseInt(sc.nextLine().trim());

                switch (option) {
                    case 1:
                        Main.clear();
                        mostrarLogs();
                        Main.continuar();
                        break;
                    case 2:
                        detalhesLog();
                        Main.continuar();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("\n[ERRO]: Opção digitada inválida!");
                        Main.continuar();
                }
            } catch (Exception e) {
                System.out.println("\n[ERRO]: Entrada inválida!");
                Main.continuar();
            }
            Main.clear();
        } while (option != 0);
    }

    // Procedimento para listar todos os logs alinhados em colunas
    public static void mostrarLogs(){
        try {
            List<LogModel> logs = repository.read();

            if (logs.isEmpty()) {
                System.out.println("\n[INFO] Nenhum registro de log encontrado.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            // Cabecalho formatado
            System.out.println("ID | DATA/HORA          | FUNCIONÁRIO (ID)               | AÇÃO                   | DETALHES");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");

            // Exibição alinhada para cada registro de log
            for (LogModel log : logs) {
                String idStr = String.format("%-2d", log.getId_log());
                String dataStr = log.getData_hora() != null ? log.getData_hora().format(formatter) : "-------------------";
                
                String funcInfo = "N/A";
                if (log.getFuncionario() != null) {
                    funcInfo = log.getFuncionario().getNome_funcionario() + " (ID: " + log.getFuncionario().getId_funcionario() + ")";
                }
                String funcStr = String.format("%-30s", funcInfo);

                String acaoStr = String.format("%-22s", log.getAcao());
                String detalheStr = log.getDetalhes() != null ? log.getDetalhes() : "";

                System.out.println(idStr + " | " + dataStr + " | " + funcStr + " | " + acaoStr + " | " + detalheStr);
            }

            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Total de registros exibidos: " + logs.size());

        } catch (Exception e) {
            System.out.println("\n[ERRO AO CARREGAR LOGS]: " + e.getMessage());
        }
    }

    // Procedimento para buscar e exibir os detalhes de um Log específico
    public static void detalhesLog() {
        try {
            System.out.print("\n> Digite o ID do log que deseja visualizar: ");
            int idAlvo = Integer.parseInt(sc.nextLine().trim());
            LogModel log = repository.readId(idAlvo);

            if (log == null) {
                System.out.println("\n[AVISO] Nenhum log encontrado com o ID informado.");
                return;
            }

            Main.clear();
            System.out.println(log.mostrarDetalhes());

        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO]: O ID informado deve ser um número inteiro válido!");
        } catch (Exception e) {
            System.out.println("\n[ERRO AO BUSCAR LOG]: " + e.getMessage());
        }
    }
}