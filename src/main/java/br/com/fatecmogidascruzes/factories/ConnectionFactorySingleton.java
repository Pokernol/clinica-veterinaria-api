package br.com.fatecmogidascruzes.factories;

import java.sql.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.h2.tools.Server;

public class ConnectionFactorySingleton {
    private static ConnectionFactorySingleton instancia;
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost:9092/~/clinica_vet";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static Server tcpServer;
    private static Server webServer;

    private ConnectionFactorySingleton() {
    }

    public static synchronized ConnectionFactorySingleton getInstancia() {
        if (instancia == null) {
            instancia = new ConnectionFactorySingleton();
            instancia.initializeDatabaseServerAndSchema();
        }
        return instancia;
    }

    private void initializeDatabaseServerAndSchema() {
        try {
            Class.forName("org.h2.Driver");

            if (tcpServer == null || !tcpServer.isRunning(false)) {
                tcpServer = Server.createTcpServer(
                        "-tcp", "-tcpAllowOthers", "-tcpPort", "9092",
                        "-ifNotExists",
                        "-baseDir", System.getProperty("user.home")
                ).start();
                System.out.println("Servidor H2 TCP iniciado em: " + tcpServer.getURL());
            }

            if (webServer == null || !webServer.isRunning(false)) {
                webServer = Server.createWebServer(
                        "-web", "-webAllowOthers", "-webPort", "8082"
                ).start();
                System.out.println("Console Web H2 iniciado em: " + webServer.getURL());
            }

            try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
                if (!tableExists(conn, "Donos")) {
                    runSchemaScript(conn);
                    System.out.println("Schema 'schema.sql' executado com sucesso.");
                } else {
                    System.out.println("Tabela 'Donos' já existe. Schema não será executado novamente.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao inicializar o schema do banco de dados: " + e.getMessage());
                throw e;
            }

        } catch (SQLException e) {
            System.err.println("Erro crítico ao iniciar os servidores H2: " + e.getMessage());
            throw new RuntimeException("Falha crítica ao iniciar os servidores H2.", e);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC H2 não encontrado: " + e.getMessage());
            throw new RuntimeException("Driver JDBC H2 não encontrado.", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    private boolean tableExists(Connection conn, String tableName) throws SQLException {
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName.toUpperCase(), null)) {
            return rs.next();
        }
    }

    private void runSchemaScript(Connection conn) throws SQLException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("schema.sql");
        if (inputStream == null) {
            throw new SQLException("schema.sql não encontrado no classpath.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             Statement stmt = conn.createStatement()) {

            StringBuilder fullSqlScript = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("--")) {
                    continue; 
                }

                fullSqlScript.append(trimmedLine).append(" ");
            }

            String[] statements = fullSqlScript.toString().split(";");
            for (String statement : statements) {
                String sqlToExecute = statement.trim();
                if (!sqlToExecute.isEmpty()) {
                    stmt.execute(sqlToExecute);
                }
            }
        } catch (Exception e) {
            throw new SQLException("Erro ao executar script schema.sql: " + e.getMessage(), e);
        }
    }

    public static void stopServer() {
        if (tcpServer != null && tcpServer.isRunning(true)) {
            tcpServer.stop();
            System.out.println("Servidor H2 TCP parado.");
        }
        if (webServer != null && webServer.isRunning(true)) {
            webServer.stop();
            System.out.println("Console Web H2 parado.");
        }
        
        try {
            String dbPath = System.getProperty("user.home") + "/clinica_vet.mv.db";
            java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(dbPath));
            System.out.println("Arquivo do banco de dados removido para reset.");
        } catch (Exception e) {
            System.err.println("Erro ao tentar deletar o banco: " + e.getMessage());
        }
    }

}