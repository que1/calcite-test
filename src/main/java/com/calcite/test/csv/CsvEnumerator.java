package com.calcite.test.csv;

import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.util.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class CsvEnumerator<E> implements Enumerator<E> {

    private static final Logger logger = LoggerFactory.getLogger(CsvEnumerator.class);

    private E current;

    private BufferedReader bufferedReader;

    public CsvEnumerator(Source source) {
        try {
            this.bufferedReader = new BufferedReader(source.reader());
            this.bufferedReader.readLine(); // csvwe文件第一行是列标题
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public E current() {
        return this.current;
    }

    @Override
    public boolean moveNext() {
        try {
            String line = this.bufferedReader.readLine();
            if (line == null) {
                return false;
            }
            this.current = (E) line.split(",");
        } catch (IOException e) {
            logger.error("read csv file error", e);
            return false;
        }
        return true;
    }

    @Override
    public void reset() {
        logger.error("no support");
    }

    @Override
    public void close() {

    }

}
