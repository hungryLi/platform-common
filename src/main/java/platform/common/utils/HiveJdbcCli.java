package platform.common.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import platform.common.base.kafka.exception.PersistenceHbaseException;


/***
 * hive 操作hbase
 * 
 * @author : muck
 * @date : 2015年11月2日 上午10:11:00
 * @version : 1.0
 */
public class HiveJdbcCli {

  private static String       driverName = "org.apache.hive.jdbc.HiveDriver";
  private static String       url        = "jdbc:hive2://123.59.83.213:10000/default";
  private static String       user       = "hive";
  private static String       password   = "hive";
  private static final Logger log        = Logger.getLogger(HiveJdbcCli.class);
  private static HiveJdbcCli  one;

  private HiveJdbcCli() {}

  public static synchronized HiveJdbcCli getInstance() {
    if(one == null) {
      one = new HiveJdbcCli();
    }
    return one;
  }

  public Connection getConn() {
    Connection conn = null;
    try {
      Class.forName(driverName);
      conn = DriverManager.getConnection(url, user, password);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw new PersistenceHbaseException("获取hive jdbc 连接异常：", e);
    }
    return conn;
  }

  public static void releaseConnection( Connection conn ) {
    if(conn != null) {
      try {
        conn.close();
      }
      catch(SQLException e) {
        e.printStackTrace();
        throw new PersistenceHbaseException("关闭 hive jdbc 连接异常：", e);
      }
      finally {
        conn = null;
      }
    }
  }

  public static void releaseStateMent( Statement statement ) {
    if(statement != null) {
      try {
        statement.close();
      }
      catch(SQLException e) {
        e.printStackTrace();
        throw new PersistenceHbaseException("关闭 hive statement异常：", e);
      }
      finally {
        statement = null;
      }
    }
  }

  public boolean createTable( Connection con, String createTableSql, String tableName ) {
    Statement statement = null;
    try {
      statement = con.createStatement();
      return statement.execute(createTableSql);
    }
    catch(Exception e) {
      throw new PersistenceHbaseException("执行建表语句异常，createTableSql:" + createTableSql, e);
    }
    finally {
      releaseStateMent(statement);
      releaseConnection(con);
    }
  }

}
