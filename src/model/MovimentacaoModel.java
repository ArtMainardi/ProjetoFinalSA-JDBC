package model;

import java.time.LocalDate;

public class MovimentacaoModel {
    private int idMovimentacao;
    private int qtdMovimentacao;
    private java.time.LocalDate dataMovimentacao;
    private FuncionarioModel funcionario;
    private ProdutoModel produto;
    private TipoMovimentacaoModel tipo;

    public MovimentacaoModel(){
    }

    public MovimentacaoModel(int idMovimentacao, int qtdMovimentacao, LocalDate dataMovimentacao, FuncionarioModel funcionario, ProdutoModel produto, TipoMovimentacaoModel tipo) {
        this.idMovimentacao = idMovimentacao;
        this.qtdMovimentacao = qtdMovimentacao;
        this.dataMovimentacao = dataMovimentacao;
        this.funcionario = funcionario;
        this.produto = produto;
        this.tipo = tipo;
    }

    public int getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(int idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public int getQtdMovimentacao() {
        return qtdMovimentacao;
    }

    public void setQtdMovimentacao(int qtdMovimentacao) {
        this.qtdMovimentacao = qtdMovimentacao;
    }

    public LocalDate getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDate dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
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
