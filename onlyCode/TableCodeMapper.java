package com.onlyCode;

public interface TableCodeMapper {
    String getTableCode(String tableName, String columnName, String prefix) ;
    // select concat_ws('','${regex}',MAX(t.sch+0)) from (select replace(${columnName},'${regex}','') sch
    // from ${tableName} where instr(${columnName},'${regex}') > 0) t
    // select concat_ws('','${regex}',MAX(t.sch+0)) from (select replace(${columnName},'${regex}','') sch from ${tableName} where instr(${columnName},'${regex}') > 0) t
    // prefix == regex
}
