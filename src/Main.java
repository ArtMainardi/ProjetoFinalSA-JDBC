import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Style sty = new Style();

    public static void main(String[] args){
        login();
    }

    // Procedimento para tela de login (BETA):
    public static void login(){
        sty.titulo("TELA DE LOGIN");
        String emailCadastrado = "jdbc@gmail.com";
        String senhaCadastrada = "123";

        System.out.print("Digite seu email: ");
        String email = sc.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = sc.nextLine();

        if(email.equals(emailCadastrado)){
            if(senha.equals(senhaCadastrada)){
                System.out.println("Login efetuado com sucesso!");
            }else{
                System.out.println("ERRO: senha incorreta!");
            }
        } else{
            System.out.println("ERRO: email inválido!");
        }
    }
}