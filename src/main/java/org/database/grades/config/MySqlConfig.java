package org.database.grades.config;

import org.hibernate.dialect.MySQL5Dialect;

public class MySqlConfig extends MySQL5Dialect {
    @Override
    public String getTableTypeString() {
        return "DEFAULT CHARSET=utf8";
    }
}