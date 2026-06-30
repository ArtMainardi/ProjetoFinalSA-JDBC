package model;

class TipoMovimentacaoModel{
    private int id_tipo;
    private String tipo;

    // Construtor:
    public TipoMovimentacaoModel(int id_tipo, String tipo) {
        this.id_tipo = id_tipo;
        this.tipo = tipo;
    }

    // Getters e Setters:
    public int getId_tipo() {
        return id_tipo;
    }
    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }
    // --
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}