package com.onlyCode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生成唯一编码code
 */
public class CodeUtil {
    private static final String RULES = "[\\D*]";
    private static TableCodeMapper tableCodeMapper;
    private static Map<String,String> tableCodeMap = new ConcurrentHashMap<>();
    static {
        if(tableCodeMapper == null){
            tableCodeMapper = SpringUtils.getBean(TableCodeMapper.class);
        }
    }

    /**
     * 生成唯一code  eg: ftx1,ftx2
     * @param prefix 前缀
     * @param tableName 表名
     * @param columnName 列名3
     * @return 生成得code
     */
    public static String createNewCode(String prefix,String tableName,String columnName){
        // 判断参数是否合法
        if(tableName.isEmpty() || columnName.isEmpty()){
            return null;
        }
        if(prefix.isEmpty()){
            prefix = "";
        }
        String currCode = "";
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            String oldCode = tableCodeMap.get(tableName);
            if (oldCode.isEmpty()) {
                oldCode = tableCodeMapper.getTableCode(tableName,columnName,prefix);
            }

            if (oldCode != null && !prefix.equals(oldCode)) {
                oldCode = oldCode.replaceAll(CodeUtil.RULES,"");
                currCode = prefix + (Long.parseLong(oldCode) +1);
            } else {
                currCode = prefix + 1;
            }
            tableCodeMap.put(tableName,currCode);
        }catch (Exception e){

        } finally {
            lock.unlock();
        }
        return currCode;
    }
}
