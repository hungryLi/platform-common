package platform.common.utils;


import java.util.Date;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/***
 * Hbase 数据工具
 * 
 * @date : 2015年10月31日 上午10:24:33
 * @version : 1.0
 */
public class HbaseDataUtil {
  private final static Logger logger = LoggerFactory.getLogger(HbaseDataUtil.class);

  public static void addPut( Put p, byte[] family, byte[] qualifier, Object value ) {
    if(value != null) {
      if(value instanceof String) {
        if(!StringUtil.isEmpty((String) value)) {
          p.addColumn(family, qualifier, Bytes.toBytes((String) value));
        }
      }
      else if(value instanceof Long) {
        p.addColumn(family, qualifier, Bytes.toBytes((Long) value));
      }
      else if(value instanceof Double) {
        p.addColumn(family, qualifier, Bytes.toBytes((Double) value));
      }
      else if(value instanceof Integer) {
        p.addColumn(family, qualifier, Bytes.toBytes((Integer) value));
      }
      else if(value instanceof Date) {
        p.addColumn(family, qualifier, Bytes.toBytes(( (Date) value ).getTime()));
      }
      else {
        // 如果没有，说明是新的数据类型，需要新加，先写error日志
        logger.error("---------------------数据类型" + value.getClass() + ",找不到，不添加到habase put里面---------------------");
      }
    }
  }

  public static Object getHbaseValue( Class targetClass, Result result, byte[] family, byte[] qualifier ) {
    byte[] bytes = result.getValue(family, qualifier);
    if(targetClass.getName().equals(String.class.getName())) {
      return bytes == null ? null : Bytes.toString(bytes);
    }
    else if(targetClass.getName().equals(Long.class.getName())) {
      return bytes == null ? null : Bytes.toLong(bytes);
    }
    else if(targetClass.getName().equals(Double.class.getName())) {
      return bytes == null ? null : Bytes.toDouble(bytes);
    }
    else if(targetClass.getName().equals(Integer.class.getName())) {
      return bytes == null ? null : Bytes.toInt(bytes);
    }
    else if(targetClass.getName().equals(Date.class.getName())) {
      return bytes == null ? null : new Date(Bytes.toLong(bytes));
    }
    else {
      return bytes;
    }

    /*
     * else if(value instanceof Long) { p.addColumn(family, qualifier,
     * Bytes.toBytes((Long) value)); } else if(value instanceof Double) {
     * p.addColumn(family, qualifier, Bytes.toBytes((Double) value)); } else
     * if(value instanceof Integer) { p.addColumn(family, qualifier,
     * Bytes.toBytes((Integer) value)); } else if(value instanceof Date) {
     * p.addColumn(family, qualifier, Bytes.toBytes(( (Date) value
     * ).getTime())); } else { // 如果没有，说明是新的数据类型，需要新加，先写error日志
     * logger.error("---------------------数据类型" + value.getClass() +
     * ",找不到，不添加到habase put里面---------------------"); }
     */
  }
  public static void main( String[] args ) {
    Class<String> targetClass = String.class;
    System.out.println(targetClass.getName());
    Class<Long> d = Long.class;
    System.out.println(d.getName());
    Class<Double> ddd = Double.class;
    System.out.println(ddd.getName());
    Class<Integer> sss = Integer.class;
    System.out.println(sss.getName());
    Class<Date> fdf = Date.class;
    System.out.println(fdf.getName());
  }
}
