package model;

public class ProdutoModel {
    private int id_produto;
    private String nome_produto;
    private String descricao_produto;
    private int qtd_produto;
    private int qtd_minima;
    private boolean ativo;
    
    // Construtores:
    public ProdutoModel(int id_produto, String nome_produto, String descricao_produto, int qtd_produto, int qtd_minima,
            boolean ativo) {
        this.id_produto = id_produto;
        this.nome_produto = nome_produto;
        this.descricao_produto = descricao_produto;
        this.qtd_produto = qtd_produto;
        this.qtd_minima = qtd_minima;
        this.ativo = ativo;
    }
    public ProdutoModel(String nome_produto, String descricao_produto, int qtd_produto, int qtd_minima, boolean ativo) {
        this.nome_produto = nome_produto;
        this.descricao_produto = descricao_produto;
        this.qtd_produto = qtd_produto;
        this.qtd_minima = qtd_minima;
        this.ativo = ativo;
    }

    // Métodos:
    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getDescricao_produto() {
        return descricao_produto;
    }

    public void setDescricao_produto(String descricao_produto) {
        this.descricao_produto = descricao_produto;
    }

    public int getQtd_produto() {
        return qtd_produto;
    }

    public void setQtd_produto(int qtd_produto) {
        this.qtd_produto = qtd_produto;
    }

    public int getQtd_minima() {
        return qtd_minima;
    }

    public void setQtd_minima(int qtd_minima) {
        this.qtd_minima = qtd_minima;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}