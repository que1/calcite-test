package com.calcite.test.csv;

import com.google.common.collect.Lists;
import org.apache.calcite.DataContext;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.util.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvTable extends AbstractTable implements ScannableTable {

    private static final Logger logger = LoggerFactory.getLogger(CsvTable.class);

    private Source source;

    public CsvTable(Source source) {
        this.source = source;
    }

    /**
     * 需要改成懒汉模式，不需要每次都计算
     *
     * @param typeFactory
     * @return
     */
    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        List<String> names = Lists.newArrayList();
        List<RelDataType> types = Lists.newLinkedList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(source.file()));
            String line = reader.readLine(); // 读取csv的首行
            if (line == null || line.isEmpty()) {
                throw new Exception("csv file is empty");
            }
            // csv首行是a:bigint,b:varchar这种格式，包含名称和类型
            String[] columns = line.split(",");
            for (String column : columns) {
                String[] items = column.split(":");
                if (items.length != 2) {
                    throw new Exception("csv file format is error");
                }
                String columnName = items[0];
                String columnType = items[1];
                names.add(columnName);
                types.add(typeFactory.createSqlType(SqlTypeName.get(columnType)));
            }
        } catch (FileNotFoundException e) {
            logger.error("get row-type error, reader csv file failed, file not found", e);
        } catch (Exception e) {
            logger.error("get row-type error, reader csv file failed, read first line error", e);
        }
        RelDataType dataType = typeFactory.createStructType(types, names);
        return dataType;
    }


    @Override
    public Enumerable<Object[]> scan(DataContext root) {
        JavaTypeFactory javaTypeFactory = root.getTypeFactory();
        return null;
    }

}
