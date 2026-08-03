package ProjetoSA.model;

import java.time.LocalDateTime;

public class LogModel {
    private int id_log;
    private LocalDateTime data_hora;
    private FuncionarioModel funcionario;
    private String acao;
    private String detalhes;

    // Construtor vazio:
    public LogModel(){}
    // Construtor para cadastro:
    public LogModel(String acao, String detalhes){
        this.acao = acao;
        this.detalhes = detalhes;
    }
    // Construtor completo:
    public LogModel(int id_log, LocalDateTime data_hora, FuncionarioModel funcionario, String acao, String detalhes) {
        this.id_log = id_log;
        this.data_hora = data_hora;
        this.funcionario = funcionario;
        this.acao = acao;
        this.detalhes = detalhes;
    }

    // Getters e Setters:
    public int getId_log() {
        return id_log;
    }
    public void setId_log(int id_log) {
        this.id_log = id_log;
    }
    public LocalDateTime getData_hora() {
        return data_hora;
    }
    public void setData_hora(LocalDateTime data_hora) {
        this.data_hora = data_hora;
    }
    public FuncionarioModel getFuncionario() {
        return funcionario;
    }
    public void setFuncionario(FuncionarioModel funcionario) {
        this.funcionario = funcionario;
    }
    public String getAcao() {
        return acao;
    }
    public void setAcao(String acao) {
        this.acao = acao;
    }
    public String getDetalhes() {
        return detalhes;
    }
    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
}
