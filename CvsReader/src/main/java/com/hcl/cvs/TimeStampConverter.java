package com.hcl.cvs;

import java.io.*;
import java.sql.*;
 
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
 
public class TimeStampConverter {
 
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/timestamp_db";
        String username = "root";
        String password = "root";
 
        String csvFilePath = "TimeStamp.csv";
 
        int batchSize = 20;
 
        Connection connection = null;
 
        ICsvBeanReader beanReader = null;
        CellProcessor[] processors = new CellProcessor[] {
                new NotNull(), 
                new NotNull(), 
                new ParseTimestamp(),
                new ParseDouble(), 
                new Optional()
        };
 
        try {
            long start = System.currentTimeMillis();
 
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
 
            String sql = "INSERT INTO timestamp (object_name, call_attemps, resulttime, granularityPeriod, cellId) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
 
            beanReader = new CsvBeanReader(new FileReader(csvFilePath),
                    CsvPreference.STANDARD_PREFERENCE);
 
            beanReader.getHeader(true); 
 
            String[] header = {"objectName", "cellId", "resultTime", "callAttemps", "granularityPeriod"};
            TimeStamp bean = null;
 
            int count = 0;
 
            while ((bean = beanReader.read(TimeStamp.class, header, processors)) != null) {
                String objectName = bean.getObjectName();
                double callAttemps = bean.getCallAttemps();
                Timestamp resultTime = bean.getResultTime();
                double cellId = bean.getCellId();
                int granularityPeriod = bean.getGranularityPeriod();
 
                statement.setString(1, objectName);
                statement.setLong(2, (long) callAttemps);
 
                statement.setTimestamp(3, resultTime);
 
                statement.setDouble(4, cellId);
 
                statement.setLong(5, granularityPeriod);
 
                statement.addBatch();
 
                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }
 
            beanReader.close();
 
            statement.executeBatch();
 
            connection.commit();
            connection.close();
 
            long end = System.currentTimeMillis();
            System.out.println("Execution Time: " + (end - start));
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
 
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
 
    }
}
