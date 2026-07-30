package ProjetoSA.util;

import ProjetoSA.service.ProdutoService;
import java.util.Scanner;

public class ProdutoMain {
    static Style sty;
    static Scanner sc;
    static ProdutoService service = new ProdutoService();

    public static void main(Style style, Scanner scanner){
        sty = style;
        sc = scanner;
    }
}
