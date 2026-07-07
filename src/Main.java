import connection.Conexao;
import java.sql.SQLException;
import java.util.Scanner;
import model.FuncionarioModel;
import util.MovimentacaoMain;
import util.Style;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Style sty = new Style();
    static FuncionarioModel usuarioAtual = new FuncionarioModel();

    public static void main(String[] args) throws SQLException{
        testarConexao();
        clear();
        login();

        // Menu de opções:
        int option;
        do{
            sty.titulo("Gerenciador de Movimentação de Almoxarifado");
            System.out.println("Digite uma opção: \n"
                            + "1- Movimentações \n"
                            + "2- Produtos \n"
                            + (usuarioAtual.isAdmin() ? "3- Funcionários \n" : "")
                            + "0- Sair");
            option = sc.nextInt();
            try{
                switch (option) {
                    case 1:
                        MovimentacaoMain.main(sty, sc);
                        break;
                    case 2:
                        break;
                    case 3:
                        if(!usuarioAtual.isAdmin()){
                            throw new Exception("ERRO: opção digitada inválida!");
                        } else{
                        }
                        break;
                    case 0:
                        break;
                    default:
                        throw new Exception("ERRO: opção digitada inválida!");
                }
            } catch(Exception e){
                System.out.println(e.getMessage() + "\n");
                continuar();
            }
        } while(option != 0);

        sty.quadro("Finalizando programa ...");
        System.out.println("Programa finalizado!");
    }

    // Procedimento para testar conexão com o BD:
    public static void testarConexao(){
        // Verifica conexão com o banco de dados:
        sty.titulo("Testando conexão");
        Conexao teste = new Conexao();
        if(!teste.testar()){
            System.out.println(); // Espaçamento
            continuar();
            return;
        }
        System.out.println(); // Espaçamento
        continuar();
    }

    // Procedimento para tela de login (BETA):
    public static void login() throws SQLException{
        FuncionarioDAO service = new FuncionarioDAO();
        boolean verify = false;

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
    }

    // Procedimento para limpar a tela:
    public static void clear(){
        for(int cont = 0; cont < 20; cont++){
            System.out.println(" ");
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for(int cont = 0; cont < 2; cont++){
            System.out.println(" ");
        }
    }

    // Procedimento que pede confirmação para o usuário para então continuar com o programa:
    public static void continuar(){
        System.out.print("Pressione ENTER para continuar");
        sc.nextLine();
    }
}