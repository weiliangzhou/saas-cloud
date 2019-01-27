package com.wegoo.saasdao.config;

/**
 * ${保存一个线程安全的DatabaseType容器}
 * author 二师兄超级帅
 * create 2018-06-29 13:34
 **/
public class DatabaseContextHolder {

    //用于存放多线程环境下的成员变量
    private static final ThreadLocal<DatabaseType> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseType type) {
        CONTEXT_HOLDER.set(type);
    }

    public static DatabaseType getDatabaseType() {
        return CONTEXT_HOLDER.get();
    }
}
