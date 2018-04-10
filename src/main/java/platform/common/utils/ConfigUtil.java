package platform.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;


/***
 * 加载配置文件
 * 
 * @author : muck
 * @date : 2015年10月21日 下午8:00:56
 * @version : 1.0
 */
public class ConfigUtil {

  private static Config       config      = null;

  private static String       config_name = "application.properties";

  private final static Logger logger      = LoggerFactory.getLogger(ConfigUtil.class);

  static {
    config = load(config_name);
  }

  private static Config load( String resource ) {
    try {
      config = ConfigFactory.load(resource);
    }
    catch(Exception e) {
      logger.error("加载配置文件" + config_name + "出错,系统退出");
      System.exit(1);
    }
    if(config == null) {
      logger.error("加载配置文件" + config_name + "出错,系统退出");
      System.exit(1);
    }
    return config;
  }
  public static String getString( String key ) {
    return config.getString(key);
  }

  public static String getString( String key, String defaultValue ) {
    if(config.hasPath(key)) return config.getString(key);
    return defaultValue;
  }

  public static int getInt( String key ) {
    return config.getInt(key);
  }

  public static int getInt( String key, int defaultValue ) {
    if(config.hasPath(key)) return config.getInt(key);
    return defaultValue;
  }

}
