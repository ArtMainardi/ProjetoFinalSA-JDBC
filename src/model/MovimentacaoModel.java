package model;

import java.time.LocalDate;

public class MovimentacaoModel {
    private int id_movimentacao;
    private int qtd_movimentacao;
    private java.time.LocalDate data_movimentacao;
    private FuncionarioModel funcionario;
    private ProdutoModel produto;
    private TipoMovimentacaoModel tipo;

    public MovimentacaoModel(int id_movimentacao, int qtd_movimentacao, LocalDate data_movimentacao, FuncionarioModel funcionario, ProdutoModel produto, TipoMovimentacaoModel tipo) {
        this.id_movimentacao = id_movimentacao;
        this.qtd_movimentacao = qtd_movimentacao;
        this.data_movimentacao = data_movimentacao;
        this.funcionario = funcionario;
        this.produto = produto;
        this.tipo = tipo;
    }
    public MovimentacaoModel(int qtd_movimentacao, LocalDate data_movimentacao, FuncionarioModel funcionario, ProdutoModel produto, TipoMovimentacaoModel tipo) {
        this.qtd_movimentacao = qtd_movimentacao;
        this.data_movimentacao = data_movimentacao;
        this.funcionario = funcionario;
        this.produto = produto;
        this.tipo = tipo;
    }


    public int getId_movimentacao() {
        return id_movimentacao;
    }

    public void setId_movimentacao(int id_movimentacao) {
        this.id_movimentacao = id_movimentacao;
    }

    public int getQtd_movimentacao() {
        return qtd_movimentacao;
    }

    public void setQtd_movimentacao(int qtd_movimentacao) {
        this.qtd_movimentacao = qtd_movimentacao;
    }

    public LocalDate getData_movimentacao() {
        return data_movimentacao;
    }

    public void setData_movimentacao(LocalDate data_movimentacao) {
        this.data_movimentacao = data_movimentacao;
    }

    public FuncionarioModel getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioModel funcionario) {
        this.funcionario = funcionario;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }

    public TipoMovimentacaoModel getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacaoModel tipo) {
        this.tipo = tipo;
    }
}
