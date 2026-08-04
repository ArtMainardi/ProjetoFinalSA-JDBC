package ProjetoSA;
import java.util.Scanner;

import ProjetoSA.connection.Conexao;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.repository.FuncionarioDAO;
import ProjetoSA.util.FuncionarioMain;
import ProjetoSA.util.MovimentacaoMain;
import ProjetoSA.util.ProdutoMain;
import ProjetoSA.util.RelatorioMain;
import ProjetoSA.util.Style;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Style sty = new Style();
    static FuncionarioModel usuarioAtual = null;

    public static void main(String[] args){
        if(!testarConexao()){
            return;
        }
        clear();
        login();

        // Menu de opções:
        int option = 1;
        do{
            try{
                sty.titulo("Gerenciador de Movimentação de Almoxarifado");
                System.out.println("Digite uma opção: \n"
                                + "1- Movimentações \n"
                                + "2- Produtos \n"
                                + (usuarioAtual.isAdmin() ? "3- Funcionários \n" : "")
                                
                                + (usuarioAtual.isAdmin() ? "4- " : "3- ") + "Relatórios \n"
                                + "0- Sair");
                option = Integer.parseInt(sc.nextLine().trim());
                
                switch (option) {
                    case 1:
                        MovimentacaoMain.main(sty, sc, usuarioAtual);
                        break;
                    case 2:
                        ProdutoMain.main(sty, sc);
                        break;
                    case 3:
                        if(!usuarioAtual.isAdmin()){
                            throw new Exception("ERRO: opção digitada inválida!");
                        } else{
                            FuncionarioMain.main(sty, sc);
                        }
                        break;
                    case 4:
                        RelatorioMain.main(sty, sc);
                        break;
                    case 0:
                        break;
                    default:
                        throw new Exception("ERRO: opção digitada inválida!");
                }
            } catch(Exception e){
                sty.quadro(e.getMessage());
                continuar();
                clear();
            }
        } while(option != 0);

        sty.quadro("Finalizando programa ...");
        System.out.println("Programa finalizado!");
    }

    // Procedimento para testar conexão com o BD:
    public static boolean testarConexao(){
        // Verifica conexão com o banco de dados:
        sty.titulo("Testando conexão");
        if(!Conexao.testar()){
            System.out.println(); // Espaçamento
            continuar();
            return false;
        }
        System.out.println(); // Espaçamento
        continuar();
        return true;
    }

    // Procedimento para tela de login:
    public static void login(){
        FuncionarioDAO service = new FuncionarioDAO();
        boolean verify = false;

        try {
            // Laço de repetição para o login:
            while(!verify){
                sty.titulo("Tela de Login");

                // Recebe dados do usuário:
                System.out.print("Digite seu email: ");
                String email = sc.nextLine();
                System.out.print("Digite sua senha: ");
                String senha = sc.nextLine();

                // Verifica login:
                if(service.verificarEmail(email)){
                    if(service.verificarSenha(senha, email)){
                        sty.quadro("Login efetuado com sucesso!");
                        verify = true;
                        usuarioAtual = service.readEmail(email);
                    }else{
                        sty.quadro("ERRO: senha incorreta!");
                    }
                } else{
                    sty.quadro("ERRO: email inválido!");
                }
                continuar();
                clear();
            }
        } catch (Exception e) {
            sty.quadro(e.getMessage());
        }
    }

    // Procedimento para limpar a tela:
    public static void clear(){
        for(int cont = 0; cont < 20; cont++){
            System.out.println(" ");
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for(int cont = 0; cont < 5; cont++){
            System.out.println(" ");
        }
    }

    // Procedimento que pede confirmação para o usuário para então continuar com o programa:
    public static void continuar(){
        System.out.print("Pressione ENTER para continuar");
        sc.nextLine();
    }

    public static Integer getIdUsuarioAtual(){
        if(usuarioAtual != null){
            return usuarioAtual.getId_funcionario();
        } else{
            return null;
        }
    }
}