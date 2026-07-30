package ProjetoSA.util;
public class Style {
    // Procedimento que cria um quadro com um texto no centro:
    public void quadro(String frase){
        // Tamanho da frase: 
        int tmh = frase.length();

        System.out.println("┌" + repetir("─", (tmh + 10)) + "┐");
        System.out.println("│" + repetir(" ", 5) + frase + repetir(" ", 5) + "│");
        System.out.println("└" + repetir("─", (tmh + 10)) + "┘");
    }

    // Procedimento que cria um título:
    public void titulo(String frase){
        // Tamanho da frase: 
        int tmh = frase.length();

        System.out.println("┌" + repetir("─", (tmh + 16)) + "┐");
        System.out.println("│" + repetir("-", 8) + frase.toUpperCase() + repetir("-", 8) + "│");
        System.out.println("└" + repetir("─", (tmh + 16)) + "┘");
    }

    // Método que retorna String repetida:
    public String repetir(String valor, int repeticoes){
        String novoValor = "";
        for(int cont = 1; cont <= repeticoes; cont++){
            novoValor += valor;
        }
        return novoValor;
    }
}
