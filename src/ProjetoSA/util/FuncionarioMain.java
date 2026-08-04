package ProjetoSA.util;

import ProjetoSA.Main;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.service.FuncionarioService;
import java.util.List;
import java.util.Scanner;

public class FuncionarioMain {
    static Style sty;
    static Scanner sc;
    static FuncionarioService service = new FuncionarioService();

    public static void main(Style style, Scanner scanner){
        // Definindo variáveis:
        sty = style;
        sc = scanner;

        Main.clear();
        // Menu de opções:
        int option = 1;
        do{
            try{
                sty.titulo("Funcionários");
                System.out.println("Digite uma opção: \n"
                                + "1 - Listar todos os Funcionários \n"
                                + "2 - Cadastrar Funcionário \n"
                                + "3 - Detalhes de um Funcionário \n"
                                + "4 - Listar Funcionarios Desativados \n"
                                + "0 - Voltar");
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
                        detalhesFuncionario();
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

    // Procedimento para printar todos os funcionários:
    public static void mostrarHistorico(){
        try{
            Main.clear();
            sty.titulo("Histórico de Funcionários");
            sty.quadro("ID   |     Nome     |     Email     |   Tipo");

            // Faz a requisição:
            List<FuncionarioModel> funcionarios = service.listar();

            // Lista os funcionários na tela:
            for(FuncionarioModel f : funcionarios){
                sty.quadro(f.mostrarDados());
            }
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }
    }

    // Procedimento para cadastrar um novo funcionário:
    public static void cadastrar(){
        Main.clear();

        try{
            sty.titulo("Cadastrar Funcionário");
            System.out.print("Nome do funcionário: ");
            String nome = sc.nextLine().trim();
            System.out.print("Email do funcionário: ");
            String email = sc.nextLine().trim();
            System.out.print("Senha do funcionário: ");
            String senha = sc.nextLine().trim();

            System.out.println("Tipo de usuário: \n"
                            + "1- Administrador \n"
                            + "2- Funcionário"
            );
            int tipo = Integer.parseInt(sc.nextLine().trim());
            boolean admin;
            if(tipo == 1){
                admin = true;
            } else if(tipo == 2){
                admin = false;
            } else{
                throw new Exception("ERRO: opção digitada inválida!");
            }

            FuncionarioModel funcionario = new FuncionarioModel(nome, email, senha, admin, true);

            // Fazendo requisição:
            service.salvar(funcionario);
            sty.quadro("Funcionário cadastrado com sucesso!");
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }
    }

    // Procedimento para buscar um funcionário:
    public static void detalhesFuncionario(){
        try{
            // Chama método auxiliar para buscar o funcionário:
            FuncionarioModel alvo = buscarFuncionario();

            int option = -1;

            do{
                // Exibe detalhes do funcionário na tela:
                Main.clear();
                sty.titulo("Detalhes do Funcionário");

                System.out.println("ID: " + alvo.getId_funcionario() + "\n\n"
                                + "Nome: " + alvo.getNome_funcionario() + "\n"
                                + "Email: " + alvo.getEmail_funcionario() + "\n"
                                + "Administrador: " + (alvo.isAdmin() ? "SIM" : "NÃO") + "\n"
                                + "Status: " + (alvo.isAtivo() ? "ATIVO" : "DESATIVO")
                );

                System.out.println("\n\nDigite uma opção: \n"
                                + "1- Editar Funcionário \n"
                                + "2- " + (alvo.isAtivo() ? "Desativar" : "Ativar") + " Funcionário \n"
                                + "0- Voltar"
                );

                option = Integer.parseInt(sc.nextLine().trim());

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

                Main.clear();
            } while(option != 0);

        } catch(Exception e){
            sty.quadro(e.getMessage());
            Main.continuar();
        }
    }

    // Método auxiliar para buscar um funcionário de acordo com um ID informado:
    public static FuncionarioModel buscarFuncionario() throws Exception{
        System.out.print("\n> Digite o ID do funcionário: ");
        int idAlvo = Integer.parseInt(sc.nextLine().trim());

        return service.buscarID(idAlvo);
    }

    // Procedimento para editar um funcionário:
    public static void modificar(FuncionarioModel alvo){
        try{
            Main.clear();
            sty.titulo("Modificar Funcionário");
            System.out.println("Observação: pressione ENTER nos dados em que você não deseja modificar: ");
            System.out.print("Nome do funcionário: " + alvo.getNome_funcionario() + " -> ");
            String nome = sc.nextLine().trim();
            if(nome.isEmpty()){
                nome = alvo.getNome_funcionario();
            }

            System.out.print("Email do funcionário: " + alvo.getEmail_funcionario() + " -> ");
            String email = sc.nextLine().trim();
            if(email.isEmpty()){
                email = alvo.getEmail_funcionario();
            }

            System.out.print("Senha do funcionário -> ");
            String senha = sc.nextLine().trim();
            if(senha.isEmpty()){
                senha = alvo.getSenha_funcionario();
            }

            System.out.println("Tipo de usuário: \n"
                            + "1- Administrador \n"
                            + "2- Funcionário");
            System.out.print((alvo.isAdmin() ? "Administrador" : "Funcionário") + " -> ");
            String tipoTexto = sc.nextLine().trim();
            boolean admin;
            if(tipoTexto.isEmpty()){
                admin = alvo.isAdmin();
            } else{
                int tipo = Integer.parseInt(tipoTexto);
                if(tipo == 1){
                    admin = true;
                } else if(tipo == 2){
                    admin = false;
                } else{
                    throw new Exception("ERRO: opção digitada inválida!");
                }
            }

            FuncionarioModel funcionario = new FuncionarioModel(alvo.getId_funcionario(), nome, email, senha, admin, alvo.isAtivo());
            service.atualizar(funcionario);
            sty.quadro("Funcionário modificado com sucesso!");
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }

        Main.continuar();
    }

    // Procedimento para desativar ou ativar um funcionário:
    public static void desativarOuAtivar(FuncionarioModel alvo) throws Exception{
        System.out.println("\nDeseja mesmo "
                        + (alvo.isAtivo() ? "desativar" : "ativar")
                        + " esse funcionário?: \n"
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
                    alvo.getId_funcionario(),
                    estado
                );
                sty.quadro("Funcionário " + (estado ? "ativado" : "desativado") + " com sucesso!");
                break;
            case 2:
                break;
            default:
                throw new RuntimeException("ERRO: opção digitada inválida!");
        }

        Main.continuar();
        Main.clear();
    }
    public static void mostrarDesativados(){
        try{
            Main.clear();
            sty.titulo("Funcionarios Desativados");
            sty.quadro("ID   |     Nome     |   Email   |   Senha   |   Admin   |");

            // Faz a requisição:
            List<FuncionarioModel> funcionarios = service.listarDesativados();
            // Lista os produtos na tela:
            for(FuncionarioModel f : funcionarios){
                sty.quadro(f.mostrarDados());
            }
        } catch(Exception e){
            sty.quadro(e.getMessage());
        }
    }
}