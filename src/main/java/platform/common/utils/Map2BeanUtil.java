package platform.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

/**
 * @author likk
 * map集合数据转为实体类
 */
public class Map2BeanUtil {
	
//	public static void main(String[] args) throws Exception {
//		
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("recode_id", "10");
//		map.put("user_name", "likang");
//		map.put("sex", "男");
//		map.put("like_game_id", "100");
//		map.put("_name", "1_name");
//		map.put("birth", "2017-02-03 10:10:10");
//		PersonInfo bean = convert2Bean(PersonInfo.class,map);
//		System.out.println(bean.getBirth());
//		
//	}
	
	public static <T> T convert2Bean(Class<T> clazz,Map<String, String> map) throws Exception{
		
			T t = clazz.newInstance();
			//遍历map集合 将map集合中的所有数据绑定到bean中
			for(Entry<String, String> entry : map.entrySet()){
				if(entry == null){
					continue;
				}
				String key = entry.getKey();
				String value = entry.getValue();
				if(StringUtils.isBlank(value)){
					continue;
				}
				String methodName = methodSetName(key); //得到setXxx
				Class<?> typeClass = getParamType(delUnderline(key),clazz);
				if(typeClass == null){
					continue;
				}
				String typeName = typeClass.getSimpleName();
				Method method = clazz.getDeclaredMethod(methodName, typeClass); //通过实体类的set方法的方法名调用setXx方法
				if("String".equals(typeName)){
					method.invoke(t, value);
				}
				if("Long".equals(typeName) || "long".equals(typeName)){
					method.invoke(t, Long.valueOf(value));
				}
				if("Integer".equals(typeName) || "int".equals(typeName)){
					method.invoke(t, Integer.valueOf(value));
				}
				if("Double".equals(typeName) || "double".equals(typeName)){
					method.invoke(t, Double.valueOf(value));
				}
				if("Float".equals(typeName) || "float".equals(typeName)){
					method.invoke(t, Float.valueOf(value));
				}
				if("Date".equals(typeName) || "date".equals(typeName)){
					method.invoke(t, DateTimeUtil.local2UtcTime(parseDate(value))); //转utc时间
				}
				if("BigDecimal".equals(typeName) || "bigDecimal".equals(typeName)){
					method.invoke(t, new BigDecimal(value));
				}
				if("Byte".equals(typeName) || "byte".equals(typeName)){
					method.invoke(t, new Byte(value));
				}
			}
			return t;
	}

	//获取bean属性的数据类型
	public static Class<?> getParamType(String fieldName,Class<?> clazz) throws Exception{
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			if(fieldName.equals(field.getName())){
				return field.getType();
			}
		}
		return null;
	}
	
	public static Date parseDate(String dateStr) throws Exception{
		if(dateStr == null || "".equals(dateStr)){
			return null;
		}
		String fmt = "yyyy-MM-dd";
		if(dateStr.indexOf(":") > 0){
			fmt = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException("时间格式转换异常");
		}
	}
	
	
	//实体类get方法方法名
	public static String methodGetName(String key){
		String methodBody = firt2UpperCase(key);
		StringBuffer buf = new StringBuffer(methodBody.length()+3);
		buf.append("get").append(methodBody);
		return buf.toString();
	}
	
	//实体类set方法方法名
	public static String methodSetName(String key){
		String methodBody = firt2UpperCase(key);
		StringBuffer buf = new StringBuffer(methodBody.length()+3);
		buf.append("set").append(methodBody);
		return buf.toString();
	}

    //请求参数名首字母大写成大驼峰
    public static String firt2UpperCase(String key) {
        String noUnderlineStr = delUnderline(key);
        StringBuffer buf = new StringBuffer(noUnderlineStr.length());
        buf.append(noUnderlineStr.substring(0, 1).toUpperCase());
        buf.append(noUnderlineStr.substring(1));
        return buf.toString();
    }
    
	//请求参数名去除下划线实现小驼峰得到对应的属性名
	public static String delUnderline(String key){
		StringBuffer buf = new StringBuffer(key.length());
		if(key.indexOf("_") < 0){
			return key;
		}
		for(int i = 0 ;i<key.length();i++){
			char ch  = key.charAt(i);
			if(ch == '_' && i > 0){
				buf.append(key.substring(i+1, i+2).toUpperCase());
				i++;
			}else{
				buf.append(key.substring(i, i+1));
			}
		}
		return buf.toString();
	}
	
	
	
}
