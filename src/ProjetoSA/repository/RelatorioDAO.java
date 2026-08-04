package ProjetoSA.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ProjetoSA.Main;
import ProjetoSA.connection.Conexao;
import ProjetoSA.util.Style;

public class RelatorioDAO {
    // Método auxiliar para formatar campo de texto com tamanho fixo
    private static String formatarCampo(String texto, int tamanhoDesejado) {
        if (texto == null) {
            texto = "";
        }
        String extra = "";
        for (int cont = texto.length(); cont < tamanhoDesejado; cont++) {
            extra += " ";
        }
        return texto + extra;
    }

    // 1. Relatório: Estoque Baixo
    public static void relatorioEstoqueBaixo() throws SQLException {
        Conexao conexao = new Conexao();
        Style sty = new Style();
        String sql = "SELECT * FROM vw_estoque_baixo";

        try (Connection conn = conexao.conectar(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            
            Main.clear();
            sty.titulo("Relatório: Estoque Baixo");
            sty.quadro("ID   |   Produto             |   Qtd Atual   |   Qtd Mínima");

            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                String id = formatarCampo(String.valueOf(rs.getInt("id_produto")), 4);
                String produto = formatarCampo(rs.getString("nome_produto"), 20);
                String qtdAtual = formatarCampo(String.valueOf(rs.getInt("qtd_produto")), 11);
                String qtdMinima = String.valueOf(rs.getInt("qtd_minima"));

                String linha = id + "  |  " + produto + "  |  " + qtdAtual + "  |  " + qtdMinima;
                sty.quadro(linha);
            }

            if (!encontrou) {
                sty.quadro("Nenhum produto com estoque crítico no momento.");
            }
        }
    }

    // 2. Relatório: Resumo Geral do Estoque (Produtos Ativos)
    public static void relatorioResumoEstoque() throws SQLException {
        Conexao conexao = new Conexao();
        Style sty = new Style();
        String sql = "SELECT id_produto, nome_produto, qtd_produto, qtd_minima FROM Produto WHERE ativo = TRUE";

        try (Connection conn = conexao.conectar(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            
            Main.clear();
            sty.titulo("Relatório: Resumo de Produtos no Estoque");
            sty.quadro("ID   |   Produto             |   Qtd Atual   |   Qtd Mínima");

            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                String id = formatarCampo(String.valueOf(rs.getInt("id_produto")), 4);
                String produto = formatarCampo(rs.getString("nome_produto"), 20);
                String qtdAtual = formatarCampo(String.valueOf(rs.getInt("qtd_produto")), 11);
                String qtdMinima = String.valueOf(rs.getInt("qtd_minima"));

                String linha = id + "  |  " + produto + "  |  " + qtdAtual + "  |  " + qtdMinima;
                sty.quadro(linha);
            }

            if (!encontrou) {
                sty.quadro("Nenhum produto ativo cadastrado.");
            }
        }
    }

    // 3. Relatório: Produtos Mais Movimentados
    public static void relatorioProdutosMaisMovimentados() throws SQLException {
        Conexao conexao = new Conexao();
        Style sty = new Style();
        String sql = "SELECT * FROM vw_produtos_mais_movimentados ORDER BY total_movimentado DESC";

        try (Connection conn = conexao.conectar(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            
            Main.clear();
            sty.titulo("Relatório: Produtos Mais Movimentados");
            sty.quadro("Produto             |   Total Movimentado");

            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                String produto = formatarCampo(rs.getString("nome_produto"), 20);
                String total = String.valueOf(rs.getInt("total_movimentado"));

                String linha = produto + "  |  " + total;
                sty.quadro(linha);
            }

            if (!encontrou) {
                sty.quadro("Nenhuma movimentação para gerar o ranking.");
            }
        }
    }

    // 4. Relatório: Total Movimentado por Tipo de Movimentação
    public static void relatorioTotaisPorTipoMovimentacao() throws SQLException {
        Conexao conexao = new Conexao();
        Style sty = new Style();
        String sql = "SELECT tm.tipo, SUM(m.qtd_movimentacao) AS total_qtd " +
                     "FROM Movimentacao m " +
                     "JOIN TipoMovimentacao tm ON m.id_tipo = tm.id_tipo " +
                     "WHERE m.ativo = TRUE " +
                     "GROUP BY tm.tipo";

        try (Connection conn = conexao.conectar(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            
            Main.clear();
            sty.titulo("Relatório: Total por Tipo de Movimentação");
            sty.quadro("Tipo de Movimentação   |   Quantidade Total");

            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                String tipo = formatarCampo(rs.getString("tipo"), 22);
                String total = String.valueOf(rs.getInt("total_qtd"));

                String linha = tipo + "  |  " + total;
                sty.quadro(linha);
            }

            if (!encontrou) {
                sty.quadro("Nenhuma movimentação ativa registrada no momento.");
            }
        }
    }
}