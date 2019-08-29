package com.socket.demo.po;


public class Dbconstant {

    private static String DriverClassName = "com.mysql.jdbc.Driver";//数据库DRIVER例如"com.mysql.jdbc.Driver"

    private static String Url = "jdbc:mysql://localhost:3306/practicee?characterEncoding=UTF8";//数据库名例如"jdbc:mysql://localhost:3306/practicee?characterEncoding=UTF8"

    private static String Username = "root";//数据库用户名

    private static String Password = "1234567";//数据库密码

    private static String UrlBean = "com.socket.demo.po.";//举例com.system.po.

    private static boolean dbrecord = false;

    public static boolean isDbrecord() {
        return dbrecord;
    }

    public static void setDbrecord(boolean dbrecord) {
        Dbconstant.dbrecord = dbrecord;
    }

    public static String getDriverClassName() {
        return DriverClassName;
    }

    public static void setDriverClassName(String driverClassName) {
        DriverClassName = driverClassName;
    }

    public static String getUrl() {
        return Url;
    }

    public static void setUrl(String url) {
        Url = url;
    }

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    public static String getUrlBean() {
        return UrlBean;
    }

    public static void setUrlBean(String urlBean) {
        UrlBean = urlBean;
    }
}
