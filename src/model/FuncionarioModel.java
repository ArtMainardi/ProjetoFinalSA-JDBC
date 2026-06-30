public class FuncionarioModel {
    private int id_funcionario;
    private String nome_funcionario;
    private String email_funcionario;
    private String senha_funcionario;
    private boolean is_admin;
    private boolean ativo;


    // construtores
    // com id
    public FuncionarioModel(int id_funcionario, String nome_funcionario, String email_funcionario, String senha_funcionario,  boolean is_admin, boolean ativo) {
        this.id_funcionario = id_funcionario;
        this.nome_funcionario = nome_funcionario;
        this.email_funcionario = email_funcionario;
        this.senha_funcionario = senha_funcionario;
        this.is_admin = is_admin;
        this.ativo = ativo;
    }

   // sem id
    public FuncionarioModel(String nome_funcionario, String email_funcionario, String senha_funcionario, boolean is_admin, boolean ativo) {
        this.nome_funcionario = nome_funcionario;
        this.email_funcionario = email_funcionario;
        this.senha_funcionario = senha_funcionario;
        this.is_admin = is_admin;
        this.ativo = ativo;
    }
    
    // getters e setters
    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public String getNome_funcionario() {
        return nome_funcionario;
    }

    public void setNome_funcionario(String nome_funcionario) {
        this.nome_funcionario = nome_funcionario;
    }
    
    public String getEmail_funcionario() {
        return email_funcionario;
    }

    public void setEmail_funcionario(String email_funcionario) {
        this.email_funcionario = email_funcionario;
    }

    public String getSenha_funcionario() {
        return senha_funcionario;
    }

    public void setSenha_funcionario(String senha_funcionario) {
        this.senha_funcionario = senha_funcionario;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}