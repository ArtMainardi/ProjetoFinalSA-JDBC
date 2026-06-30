import java.time.LocalDate;

public class movimentacaoModel {
    private int idMovimentacao;
    private int qtdMovimentacao;
    private java.time.LocalDate dataMovimentacao;
    private Funcionario funcionario;
    private Produto produto;
    private TipoMovimentacao tipo;

    public movimentacaoModel(){
    }

    public movimentacaoModel(int idMovimentacao, int qtdMovimentacao, LocalDate dataMovimentacao, Funcionario funcionario, Produto produto, TipoMovimentacao tipo) {
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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }
}
