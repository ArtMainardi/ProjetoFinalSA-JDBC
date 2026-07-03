import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Style sty = new Style();

    public static void main(String[] args){
        login();
    }

    // Procedimento para tela de login (BETA):
    public static void login(){
        String emailCadastrado = "jdbc@gmail.com";
        String senhaCadastrada = "123";

        // Laço de repetição para o login:
        boolean verify = false;
        while(!verify){
            sty.titulo("TELA DE LOGIN");
            
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
        }
    }
}