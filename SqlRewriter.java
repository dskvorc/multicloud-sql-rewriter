import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class SqlRewriter {

    public static Connection getDbConnection(String dbSettingsFile) {
        try {
            FileReader dbSettingsReader = new FileReader(dbSettingsFile);
            Properties dbSettings = new Properties();
            dbSettings.load(dbSettingsReader);

            Class.forName(dbSettings.getProperty("driver"));
            return DriverManager.getConnection(dbSettings.getProperty("url"), dbSettings);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    public static void runQuery(String query, Connection connection) {
        System.out.println();
        System.out.println("QUERY>>> " + query);
        try {
            Statement statement = connection.createStatement();
            boolean isResultSetAvailable = statement.execute(query);
            if (isResultSetAvailable) {
                ResultSet results = statement.getResultSet();
                ResultSetMetaData meta = results.getMetaData();
                int noOfColumns = meta.getColumnCount();
                while (results.next()) {
                    for (int i = 1; i <= noOfColumns; i++) {
                        String columnName = meta.getColumnName(i);
                        Object columnValue = results.getObject(columnName);
                        System.out.print(columnName + " = " + String.format("%-15s", String.valueOf(columnValue)) + "|");
                    }
                    System.out.println();
                }
                results.close();
            }
            else {
                System.out.print(statement.getUpdateCount() + " row(s) updated");
                System.out.println();
            }
            statement.close();
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    

    public static void main(String[] args) {
        try {
            Logger.getRootLogger().setLevel(Level.OFF);
            String dbSettingsFile = "app.properties";
            Connection connection = getDbConnection(dbSettingsFile);

            ArrayList<String> queries = new ArrayList<>();
            queries.add("SELECT * FROM sqlrewriter_orig.medinfo");
            //queries.add("SELECT firstname, lastname FROM sqlrewriter_orig.medinfo");
            //queries.add("SELECT age FROM sqlrewriter_orig.medinfo");
            //queries.add("SELECT illness FROM sqlrewriter_orig.medinfo");
            //queries.add("SELECT firstname, lastname, age, illness FROM sqlrewriter_orig.medinfo");
            //queries.add("SELECT firstname, lastname, age, illness FROM sqlrewriter_orig.medinfo WHERE lastname='Ivic'");
            //queries.add("SELECT firstname, lastname FROM sqlrewriter_orig.medinfo WHERE illness='Flu'");

            for (String query : queries) {
                runQuery(query, connection);
            }
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
