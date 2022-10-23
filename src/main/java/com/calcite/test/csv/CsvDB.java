package com.calcite.test.csv;

import org.apache.calcite.util.Sources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class CsvDB {

    private static final Logger logger = LoggerFactory.getLogger(CsvDB.class);

    private Connection conn;

    public CsvDB() {

    }

    public void execQuery() {
        try {
            this.createConn();
        } catch (SQLException e) {
            logger.error("create conn failed");
        }
    }

    private void createConn() throws SQLException {
        Properties info = new Properties();
        String filePath = Sources.of(CsvDB.class.getResource("/model.json")).file().getAbsolutePath();
        info.put("model", filePath);
        logger.info("model file path: " + filePath);
        this.conn = DriverManager.getConnection("jdbc:calcite:", info);
        logger.info("create conn successfully");
        this.conn.close();
    }

    public static void main(String[] args) {
        CsvDB csvDB = new CsvDB();
        csvDB.execQuery();
    }

}
