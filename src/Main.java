import connection.Conexao;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Style sty = new Style();

    public static void main(String[] args){
        testarConexao();
        clear();
        login();
    }

    // Procedimento para testar conexão com o BD:
    public static void testarConexao(){
        // Verifica conexão com o banco de dados:
        sty.titulo("Testando conexão");
        Conexao teste = new Conexao();
        if(!teste.testar()){
            System.out.println(); // Espaçamento
            System.out.print("Pressione ENTER para continuar");
            sc.nextLine();
            return;
        }
        System.out.println(); // Espaçamento
        System.out.print("Pressione ENTER para continuar");
        sc.nextLine();
    }

    // Procedimento para tela de login (BETA):
    public static void login(){
        String emailCadastrado = "jdbc@gmail.com";
        String senhaCadastrada = "123";

        // Laço de repetição para o login:
        boolean verify = false;
        while(!verify){
            sty.titulo("Tela de Login");

            // Recebe dados do usuário:
            System.out.print("Digite seu email: ");
            String email = sc.nextLine();
            System.out.print("Digite sua senha: ");
            String senha = sc.nextLine();

            // Verifica login:
            if(email.equals(emailCadastrado)){
                if(senha.equals(senhaCadastrada)){
                    sty.quadro("Login efetuado com sucesso!");
                    verify = true;
                }else{
                    sty.quadro("ERRO: senha incorreta!");
                }
            } else{
                sty.quadro("ERRO: email inválido!");
            }
            System.out.print("Pressione ENTER para continuar");
            sc.nextLine();
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
}