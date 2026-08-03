package ProjetoSA.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ProjetoSA.connection.Conexao;
import ProjetoSA.model.LogModel;

public class LogDAO{
    Conexao conexao = new Conexao();

    // CREATE:
    public LogModel create(LogModel l) throws SQLException{
        String sql = "INSERT INTO LogSistem (data_hora, id_funcionario, acao, detalhes) values (?, ?, ?, ?)";

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
}