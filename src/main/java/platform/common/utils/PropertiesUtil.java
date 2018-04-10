package platform.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
/**
 * 动态读取配置文件  application.properties文件默认放在classes目录下。
 * <p>Description: properties文件操作工具</p>
 */
public class PropertiesUtil {
	private String paramsPath = "params.properties";
	private String counterPath = "counter.properties";

	/**
	 * application.properties文件默认放在classes目录下。
	 * <p>Description: 根据key读取value</p>
	 * @param key
	 * @return
	 */
	public static String readValue(String key) {
		Properties props = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path+"application.properties");
			//不能用以下的方式，否则必须要重启服务器才能读取到最新的数据，问题就出在这
//			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(isParamProper?paramsPath:counterPath);
//			props.load(in);
			props.load(fis);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null!=fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	
	/**
	 * <p>Description: 写入properties信息</p>
	 * @throws IOException 
	 */
	public static void writeProperties(boolean isParamProper,String parameterName,
			String parameterValue) {
		Properties prop = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
		
		FileInputStream fis = null;
		OutputStream fos = null;
		try {
//			fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(isParamProper?paramsPath:counterPath);
			// 从输入流中读取属性列表（键和元素对）
//			fis = new FileInputStream(path+(isParamProper?paramsPath:counterPath));
			fis = new FileInputStream(path+"application.properties");
			prop.load(fis);
			fis.close();//一定要在修改值之前关闭fis
			
			fos = new FileOutputStream(path+"application.properties");
			prop.setProperty(parameterName, parameterValue);
			
			prop.store(fos, null);//第二个参数为注释
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null!=fos) {
				try {
					fos.close();
					if(null!=fis) fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
