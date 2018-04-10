package platform.common.utils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;





import com.alibaba.fastjson.JSONObject;

public class BeanUtil {
	/**
	 * 拷贝相同属性 从src 复制到 des
	 */
	public static void copyPropertys(Object src, Object des) {
		ArrayList<Field> srcFields = new ArrayList<Field>();
		HashMap<String, Field> desFields = new HashMap<String, Field>();
		for (Field field : src.getClass().getDeclaredFields()) {
			srcFields.add(field);
		}
		for (Field field : des.getClass().getDeclaredFields()) {
			desFields.put(field.getName(), field);
		}
		for (Field srcfield : srcFields) {
			try {
				if (desFields.containsKey(srcfield.getName())) {
					Field desField = desFields.get(srcfield.getName());
					if (srcfield.getName().equals("serialVersionUID")) {
						continue;
					}
					desField.setAccessible(true);
					srcfield.setAccessible(true);
					if (srcfield.getType() != desField.getType()) {
						if ((srcfield.getType() == java.util.Date.class || srcfield.getType() == java.sql.Date.class || srcfield.getType() == java.sql.Timestamp.class)
								&& desField.getType() == java.lang.String.class) {
							java.util.Date dates = (Date) srcfield.get(src);
							desField.set(des, formatDate(dates));
						} else {
							if (desField.getType() == java.util.Date.class || desField.getType() == java.sql.Date.class || desField.getType() == java.sql.Timestamp.class) {
								java.util.Date date = ToDate(String.valueOf(srcfield.get(src)));
								desField.set(des, date);
							} else if (desField.getType() == java.lang.Integer.class || desField.getType() == int.class) {
								Integer ints = java.lang.Integer.valueOf(String.valueOf(srcfield.get(src)));
								desField.set(des, ints);
							} else if (desField.getType() == java.lang.Long.class) {
								java.lang.Long longs = java.lang.Long.getLong(String.valueOf(srcfield.get(src)));
								desField.set(des, longs);
							} else {
								desField.set(des, String.valueOf(srcfield.get(src)));
							}
						}
					} else {
						desField.set(des, srcfield.get(src));
					}
					desField.setAccessible(false);
					srcfield.setAccessible(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void copyJSONPropertys(JSONObject src, Object des) {
		HashMap<String, Field> desFields = new HashMap<String, Field>();
		for (Field field : des.getClass().getDeclaredFields()) {
			desFields.put(field.getName(), field);
		}
		for (java.util.Map.Entry<String, Object> entry : src.entrySet()) {
			try {
				if (desFields.containsKey(entry.getKey())) {
					Field desField = desFields.get(entry.getKey());
					if (entry.getKey().equals("serialVersionUID")) {
						continue;
					}
					desField.setAccessible(true);
					if (desField.getType() == java.util.Date.class || desField.getType() == java.sql.Date.class || desField.getType() == java.sql.Timestamp.class) {
						java.util.Date date = ToDate(String.valueOf(entry.getValue()));
						desField.set(des, date);
					} else if (desField.getType() == java.lang.Integer.class || desField.getType() == int.class) {
						Integer ints = java.lang.Integer.valueOf(String.valueOf(entry.getValue()));
						desField.set(des, ints);
					} else if (desField.getType() == java.lang.Long.class || desField.getType() == long.class) {
						Long ints = java.lang.Long.valueOf(String.valueOf(entry.getValue()));
						desField.set(des, ints);
					} else if (desField.getType() == java.lang.Long.class) {
						java.lang.Long longs = java.lang.Long.getLong(String.valueOf(entry.getValue()));
						desField.set(des, longs);
					} else if (desField.getType() == java.lang.Double.class || desField.getType() == double.class) {
						Double doubles = java.lang.Double.valueOf(String.valueOf(entry.getValue()));
						desField.set(des, doubles);
					} else if (desField.getType() == java.math.BigDecimal.class) {
						java.math.BigDecimal bigDecimal = new java.math.BigDecimal(String.valueOf(entry.getValue()));
						desField.set(des, bigDecimal);
					} else if (desField.getType() == java.lang.Byte.class) {
						java.lang.Byte bytes = java.lang.Byte.valueOf(String.valueOf(entry.getValue()));
						desField.set(des, bytes);
					} else {
						desField.set(des, String.valueOf(entry.getValue()));
					}
					desField.setAccessible(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unused")
	public static Map<String, Object> copyClassToMap(Object src) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<Field> srcFields = new ArrayList<Field>();
		HashMap<String, Field> desFields = new HashMap<String, Field>();
		for (Field field : src.getClass().getDeclaredFields()) {
			srcFields.add(field);
		}
		for (Field srcfield : srcFields) {
			try {
				srcfield.setAccessible(true);
				map.put(srcfield.getName(), srcfield.get(src));
				srcfield.setAccessible(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static Map<String, Object> copyMapToClass(Map<String, Object> map, Object des) {
		HashMap<String, Field> desFields = new HashMap<String, Field>();
		for (Field field : des.getClass().getDeclaredFields()) {
			desFields.put(field.getName(), field);
		}
		for (String fieldName : map.keySet()) {
			try {
				if (desFields.containsKey(fieldName)) {
					Field desField = desFields.get(fieldName);
					desField.setAccessible(true);
					Object fieldValue = map.get(fieldName);

					if (desField.getType() == java.util.Date.class || desField.getType() == java.sql.Date.class || desField.getType() == java.sql.Timestamp.class) {
						java.util.Date date = ToDate(String.valueOf(fieldValue));
						desField.set(des, date);
					} else if (desField.getType() == java.lang.Integer.class || desField.getType() == int.class) {
						Integer ints = java.lang.Integer.valueOf(String.valueOf(fieldValue));
						desField.set(des, ints);
					} else if (desField.getType() == java.lang.Double.class) {
						Double ints = java.lang.Double.valueOf(String.valueOf(fieldValue));
						desField.set(des, ints);
					} else if (desField.getType() == java.lang.Float.class) {
						Float ints = java.lang.Float.valueOf(String.valueOf(fieldValue));
						desField.set(des, ints);
					} else if (desField.getType() == java.lang.Long.class) {
						java.lang.Long longs = java.lang.Long.getLong(String.valueOf(fieldValue));
						desField.set(des, longs);
					} else {
						desField.set(des, String.valueOf(fieldValue));
					}

					desField.setAccessible(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	
	
	@SuppressWarnings("rawtypes")
	public static void copyRequestToClass(HttpServletRequest request, Object des) {
		HashMap<String, Field> desFields = new HashMap<String, Field>();
		for (Field field : des.getClass().getDeclaredFields()) {
			desFields.put(field.getName(), field);
		}
		Map properties = request.getParameterMap();
		Iterator entries = properties.entrySet().iterator();
		while (entries.hasNext()) {
			try {
				Map.Entry entry = (Map.Entry) entries.next();
				String fieldName = (String) entry.getKey();
				if (desFields.containsKey(fieldName)) {
					Field desField = desFields.get(fieldName);
					desField.setAccessible(true);

					String[] valueArray = (String[])entry.getValue();
					String fieldValue = valueArray[0];

					if (desField.getType() == java.util.Date.class || desField.getType() == java.sql.Date.class || desField.getType() == java.sql.Timestamp.class) {
						java.util.Date date = ToDate(String.valueOf(fieldValue));
						desField.set(des, date);
					} else if (desField.getType() == java.lang.Integer.class || desField.getType() == int.class) {
						Integer ints = java.lang.Integer.valueOf(String.valueOf(fieldValue));
						desField.set(des, ints);
					} else if (desField.getType() == java.lang.Double.class) {
						Double ints = java.lang.Double.valueOf(String.valueOf(fieldValue));
						desField.set(des, ints);
					} else if (desField.getType() == java.lang.Float.class) {
						Float ints = java.lang.Float.valueOf(String.valueOf(fieldValue));
						desField.set(des, ints);
					} else if (desField.getType() == java.lang.Long.class) {
						java.lang.Long longs = java.lang.Long.getLong(String.valueOf(fieldValue));
						desField.set(des, longs);
					} else {
						desField.set(des, String.valueOf(fieldValue));
					}

					desField.setAccessible(false);
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}
	
	/**
	 * 把Date格式成yyyy-MM-dd HH:mm:ss格式的字符串
	 */
	public static String formatDate(Date date) {
		if (date == null) return "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	/**
	 * 把Date格式成yyyy-MM-dd HH:mm:ss格式的字符串
	 */
	public static Date ToDate(String date) throws ParseException {
		date = date.replace("T", " ");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(date);
	}
}
