import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        login();
    }

    // Procedimento para tela de login (BETA):
    public static void login(){
        String emailCadastrado = "jdbc@gmail.com";
        String senhaCadastrada = "123";

        System.out.println("Digite seu email: ");
        String email = sc.nextLine();
        System.out.println("Digite sua senha: ");
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