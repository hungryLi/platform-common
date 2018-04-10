package platform.common.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ConfigureUtil {
	
	public static String PROPERTY_FILE_NAME = "/application.properties";
	
	public static String SYSTEM_PROFILE_PROD = "prod";
	public static String SYSTEM_PROFILE_TEST = "test";
	public static String SYSTEM_PROFILE_DEV  = "dev";
	

	public static String getParameter(String key){
		String value = null;
		Resource resource = new ClassPathResource(PROPERTY_FILE_NAME);
		Properties props;
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
			value = props.getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public static boolean isSystemProfileDev(){
		String profile = ConfigureUtil.getParameter("server.enviroment.profile");
		return (profile != null && profile.equalsIgnoreCase(SYSTEM_PROFILE_DEV));
	}
	
	public static boolean isSystemProfileTest(){
		String profile = ConfigureUtil.getParameter("server.enviroment.profile");
		return (profile != null && profile.equalsIgnoreCase(SYSTEM_PROFILE_TEST));
	}
	
	public static boolean isSystemProfileProd(){
		String profile = ConfigureUtil.getParameter("server.enviroment.profile");
		return (profile != null && profile.equalsIgnoreCase(SYSTEM_PROFILE_PROD));
	}
	
}
