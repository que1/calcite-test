package com.calcite.test.csv;

import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

import java.util.Map;

public class CsvSchema extends AbstractSchema {

    private String dataFile;
    private Map<String, Table> tableMap;

    public CsvSchema(String dataFile) {
        this.dataFile = dataFile;
    }

    @Override
    public Map<String, Table> getTableMap() {
        //
        return tableMap;
    }


}
