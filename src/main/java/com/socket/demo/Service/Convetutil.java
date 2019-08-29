package com.socket.demo.Service;

import com.socket.demo.po.Dbconstant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Queue;


public class Convetutil {

    private static JdbcTemplate jdbcTemplate;
    private static int id;

    public static void connectdata() {
        if (!Dbconstant.isDbrecord()) {
            return;
        }
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //设置参数
        dataSource.setDriverClassName(Dbconstant.getDriverClassName());
        dataSource.setUrl(Dbconstant.getUrl());
        dataSource.setUsername(Dbconstant.getUsername());
        dataSource.setPassword(Dbconstant.getPassword());

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //获取BEAN对应表名
    public static String gettablenaml(Object bean) {

        String a = Dbconstant.getUrlBean();
//        System.out.println();
        return bean.getClass().getName().replace(a, "").toLowerCase();

    }

    //获取BEAN中属性及其属性值0是名1是值
    public static Queue<String>[] getdataproperty(Object bean) throws IllegalAccessException {

        Queue<String> queuekey = new LinkedList<String>();
        Queue<String> queuevalue = new LinkedList<String>();
        for (Field field : bean.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("dateformat")) {
                continue;
            } else if (field.getName().equals("id")) {
                id = Integer.parseInt(field.get(bean).toString());
                continue;
            }
            queuevalue.offer(field.get(bean).toString());
            String f = new String();
            f = field.getName();
            for (int i = 0; i < field.getName().length(); i++) {

                char c = field.getName().charAt(i);
                if (Character.isUpperCase(c)) {
                    String s = field.getName().substring(0, i);
                    String e = field.getName().substring(i + 1);

                    f = s + "_" + Character.toLowerCase(c) + e;
                }
            }
            queuekey.offer(f);
        }
        Queue<String>[] x = new Queue[2];
        x[0] = queuekey;
        x[1] = queuevalue;
        return x;


    }

    public static void add(Object bean) throws IllegalAccessException {
        if (!Dbconstant.isDbrecord()) {
            return;
        }
        String sql = "insert into " + Convetutil.gettablenaml(bean) + "(";
        Queue<String>[] x = new Queue[2];
        x = Convetutil.getdataproperty(bean);
        for (String a : x[0]) {
            sql = sql + a + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ")values(";
        for (String b : x[1]) {
            sql = sql + "'" + b + "'" + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql = sql + ")";
        jdbcTemplate.update(sql);

    }

    public static void del(Object bean) throws IllegalAccessException {
        Convetutil.getdataproperty(bean);
        String sql = "delete from " + Convetutil.gettablenaml(bean) + " where";
        sql += " id=" + id;
        jdbcTemplate.update(sql);
    }

    public static void update(Object bean) throws IllegalAccessException {
        int i = 0;
        String sql = "update " + Convetutil.gettablenaml(bean) + " set ";
        Queue<String>[] x = new Queue[2];
        x = Convetutil.getdataproperty(bean);
        for (String a : x[0]) {
            sql = sql + a + "=?,";
            ++i;
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += " where id=" + id;
        Object[] values = new Object[i];
        for (int xx = 0; xx < i; xx++) {
            values[xx] = x[1].poll();
        }
        jdbcTemplate.update(sql, values);
    }
//    public static Object select(Object bean){
//        String sql="select * from "+Convetutil.gettablenaml(bean)+" where id="+id;
//        Object object=jdbcTemplate.queryForObject(sql,new )
//    }
}
