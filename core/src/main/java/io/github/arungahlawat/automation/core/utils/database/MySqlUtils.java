package io.github.arungahlawat.automation.core.utils.database;

import io.github.arungahlawat.automation.core.utils.Log;
import lombok.SneakyThrows;
import lombok.Synchronized;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlUtils {
    private static final Map<String, Connection> connectionPool = new HashMap<>();

    public Connection connection;
    private String selectExpression;
    private String updateExpression;
    private String conditionExpression;
    private String insertValuesExpression;
    private String queryType;

    public MySqlUtils() {
    }

    public MySqlUtils(String host, int port, String schema, String userName, String password) {
        this.connection = connect(host, port, schema, userName, password);
    }

    @SneakyThrows
    @Synchronized
    public static Connection connect(String host, int port, String schema, String userName, String password) {
        Connection connection;
        try {
            if (connectionPool.containsKey(schema) && !connectionPool.get(schema).isClosed())
                return connectionPool.get(schema);
            if (StringUtils.isBlank(host) || port <= 0 || StringUtils.isBlank(schema)) {
                Log.error("Invalid connection params");
                return null;
            }
            String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + schema + "?autoReconnect=true";
            connection = DriverManager.getConnection(connectionString, userName, password);
            if (connectionPool.containsKey(schema))
                connectionPool.replace(schema, connection);
            else
                connectionPool.put(schema, connection);
        } catch (SQLException sqlException) {
            Log.error("Error connecting to database", sqlException);
            throw sqlException;
        }
        return connection;
    }

    public static Connection reConnect(String host, int port, String schema, String userName, String password) {
        disconnect(connectionPool.get(schema));
        return connect(host, port, schema, userName, password);
    }

    public static void disconnect(Connection connection) {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException sqlException) {
            Log.error("Error disconnecting connection", sqlException);
        }
    }

    public static void disconnect(String schema) {
        if (!connectionPool.containsKey(schema))
            return;
        try (Connection currentConnection = connectionPool.remove(schema)) {
            if (currentConnection.isClosed())
                return;
            disconnect(currentConnection);
        } catch (SQLException sqlException) {
            Log.error("Error disconnecting connection", sqlException);
        }
    }

    public static void disconnectAll(List<String> schemas) {
        schemas.forEach(MySqlUtils::disconnect);
    }

    public static void disconnectAll() {
        connectionPool.forEach((schame, connection) -> disconnect(connection));
    }

    public static ResultSet query(Connection connection, String sql) {
        try {
            return connection.createStatement().executeQuery(sql);
        } catch (SQLException sqlException) {
            Log.error("Error querying database", sqlException);
        }
        return null;
    }

    @Synchronized
    public static long insert(Connection connection, String sql) {
        try {
            return connection.createStatement().executeUpdate(sql);
        } catch (SQLException sqlException) {
            Log.error("Error executing dml", sqlException);
        }
        return -1;
    }

    public static long update(Connection connection, String sql) {
        return insert(connection, sql);
    }

    public static long delete(Connection connection, String sql) {
        return insert(connection, sql);
    }

    public MySqlUtils select(String tableName, String... selectParams) {
        return select(tableName, StringUtils.join(selectParams, ", "));
    }

    public MySqlUtils select(String tableName, String selectExpression) {
        this.selectExpression = "select " + selectExpression.replace("select ", "") + " from " + tableName;
        this.queryType = "select";
        return this;
    }

    public MySqlUtils where(String key, Object value) {
        return where(key, "=", value);
    }

    public MySqlUtils where(String key, String operator, Object value) {
        return where(new ConditionsParam(key, operator, value).getExpression());
    }

    public MySqlUtils where(String conditionExpression) {
        this.conditionExpression = " where " + conditionExpression.replace("where ", "");
        return this;
    }

    public MySqlUtils and(String key, Object value) {
        return and(key, "=", value);
    }

    public MySqlUtils and(String key, String operator, Object value) {
        return and(new ConditionsParam(key, operator, value).getExpression());
    }

    public MySqlUtils and(String conditionExpression) {
        this.conditionExpression += " and " + conditionExpression.replace("and ", "");
        return this;
    }

    public MySqlUtils or(String key, Object value) {
        return or(key, "=", value);
    }

    public MySqlUtils or(String key, String operator, Object value) {
        return or(new ConditionsParam(key, operator, value).getExpression());
    }

    public MySqlUtils or(String conditionExpression) {
        this.conditionExpression += " or " + conditionExpression.replace("or ", "");
        return this;
    }

    public MySqlUtils limit(int limit) {
        this.conditionExpression += " limit " + limit;
        return this;
    }

    public MySqlUtils insertInto(String tableName, String... columns) {
        this.insertValuesExpression = "insert into " + tableName + "(" + StringUtils.join(columns, ", ") + ")";
        this.queryType = "insert";
        return this;
    }

    public MySqlUtils insertInto(String tableName) {
        this.insertValuesExpression = "insert into " + tableName;
        this.queryType = "insert";
        return this;
    }

    public MySqlUtils values(String... insertValues) {
        return values("'" + StringUtils.join(insertValues, "', '") + "'");
    }

    public MySqlUtils values(String insertValuesExpression) {
        this.insertValuesExpression += " values(" + insertValuesExpression.replace("values(", "") + ")";
        return this;
    }

    public MySqlUtils update(String tablename) {
        this.updateExpression = "update " + tablename + " SET ";
        this.queryType = "update";
        return this;
    }

    public MySqlUtils set(String updateExpression) {
        this.updateExpression += StringUtils.endsWithIgnoreCase(this.updateExpression, "SET ") ? updateExpression : ", " + updateExpression;
        return this;
    }

    public MySqlUtils set(String key, Object value) {
        return set(new ConditionsParam(key, "=", value).getExpression());
    }

    public MySqlUtils deleteFrom(String tableName) {
        this.updateExpression = "delete from " + tableName;
        this.queryType = "delete";
        return this;
    }

    public Object execute() {
        String query;
        switch (this.queryType) {
            case "select":
                query = this.selectExpression + this.conditionExpression;
                return query(this.connection, query);
            case "insert":
                query = this.insertValuesExpression;
                return insert(this.connection, query);
            case "update":
            case "delete":
                query = this.updateExpression + this.conditionExpression;
                return update(this.connection, query);
        }
        return null;
    }

    static class ConditionsParam {
        String key;
        String operator;
        Object value;

        ConditionsParam(String key, String operator, Object value) {
            this.key = key;
            this.operator = operator;
            this.value = value;
        }

        public String getExpression() {
            String expression;
            if ("in".equals(operator)) {
                expression = key + " in (" + value + ")";
            } else {
                if (value == null)
                    expression = key + " " + this.operator + " null";
                else
                    expression = key + " " + this.operator + " '" + value + "'";
            }
            return expression;
        }
    }
}
