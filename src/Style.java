public class Style {
    // Método que cria um quadro com um texto no centro:
    public void quadro(String frase){
        // Tamanho da frase: 
        int tmh = frase.length();

        System.out.println("┌" + repetir("─", (tmh + 10)) + "┐");
        System.out.println("│" + repetir(" ", 5) + frase + repetir(" ", 5) + "│");
        System.out.println("└" + repetir("─", (tmh + 10)) + "┘");
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
