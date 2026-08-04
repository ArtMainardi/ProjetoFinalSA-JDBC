package ProjetoSA.repository;

import ProjetoSA.connection.Conexao;
import ProjetoSA.model.FuncionarioModel;
import ProjetoSA.model.LogModel;
import ProjetoSA.service.FuncionarioService;
import ProjetoSA.util.Style;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogDAO{
    Conexao conexao = new Conexao();

    // CREATE:
    public LogModel create(LogModel l) throws SQLException{
        String sql = "INSERT INTO LogSistema (data_hora, id_funcionario, acao, detalhes) values (?, ?, ?, ?)";

        // Faz conexão e prepara a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            // Define os dados da query:
            stmt.setObject(1, l.getData_hora());
            stmt.setInt(2, l.getFuncionario().getId_funcionario());
            stmt.setString(3, l.getAcao());
            stmt.setString(4, l.getDetalhes());
            // Executa a query:
            stmt.executeUpdate();
            
            // Verifica o ID gerado:
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    l.setId_log(rs.getInt(1));
                } else{
                    throw new SQLException("Falha ao inserir movimentação, nenhum ID foi gerado.");
                }
            }
            return l;
        }
    }

    // READ:
    public List<LogModel> read() throws SQLException{
        List<LogModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM LogSistema";

        // Faz a conexão e executa a query:
        try(Connection conn = conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){
            // Verifica se encontrou algum dado:
            while(rs.next()){
                int id = rs.getInt("id_log");
                LocalDateTime dataHora = rs.getObject("data_hora", LocalDateTime.class);
                int idFuncionario = rs.getInt("id_funcionario");
                // --
                FuncionarioService fService = new FuncionarioService();
                FuncionarioModel funcionario = fService.buscarID(idFuncionario);
                //--
                String acao = rs.getString("acao");
                String detalhes = rs.getString("detalhes");

                lista.add(new LogModel(id, dataHora, funcionario, acao, detalhes));
            }
        }
        return lista;
    }

    public static void registrar(Integer idFuncionario, String acao, String detalhes) {
        Conexao c = new Conexao();
        String sql = "INSERT INTO LogSistema (id_funcionario, acao, detalhes) VALUES (?, ?, ?)";

        // Faz conexão e prepara a query:
        try(Connection conn = c.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            // Define os dados:
            if (idFuncionario != null) {
                stmt.setInt(1, idFuncionario);
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
            }
            stmt.setString(2, acao);
            stmt.setString(3, detalhes);
            // Executa a query:
            stmt.executeUpdate();
        } catch (SQLException e) {
            Style sty = new Style();
            sty.quadro("Falha ao gravar log no banco: " + e.getMessage());
        }
    }
}