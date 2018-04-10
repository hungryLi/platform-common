package platform.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 基于fastjson实现的JsonObjectUtils工具类
 * 
 * @author
 * @since 2014年7月3日
 */
public class JSONUtil {

	private static final Logger log = LoggerFactory.getLogger(JSONUtil.class);

	static {
		// 配置生成json的规则
		int features = 0;
		features |= SerializerFeature.QuoteFieldNames.getMask();
		features |= SerializerFeature.SkipTransientField.getMask();
		features |= SerializerFeature.WriteEnumUsingToString.getMask();
		features |= SerializerFeature.WriteDateUseDateFormat.getMask();
		features |= SerializerFeature.WriteNullListAsEmpty.getMask();
		features |= SerializerFeature.WriteNullStringAsEmpty.getMask();
		features |= SerializerFeature.WriteNullNumberAsZero.getMask();
		features |= SerializerFeature.WriteNullBooleanAsFalse.getMask();
		JSON.DEFAULT_GENERATE_FEATURE = features;

		// 配置解析json的规则
		features = 0;
		features |= Feature.AutoCloseSource.getMask();
		features |= Feature.InternFieldNames.getMask();
		features |= Feature.UseBigDecimal.getMask();
		features |= Feature.AllowUnQuotedFieldNames.getMask();
		features |= Feature.AllowSingleQuotes.getMask();
		features |= Feature.AllowArbitraryCommas.getMask();
		features |= Feature.IgnoreNotMatch.getMask();
		JSON.DEFAULT_PARSER_FEATURE = features;

		// 配置日期时间生成/解析格式
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	}

	/**
	 * 将传入的对象生成JSON字符串，如果转换失败，或者传入对象空，返回{}
	 */
	public static String toJSONString(Object obj) {
		try {
			return JSON.toJSONString(obj);
		} catch (Exception e) {
			log.error("createJson failed, type={}", null == obj ? null : obj.getClass(), e);
		}
		return "{}";
	}

	/**
	 * 将JSON字符串转化为指定的对象，如果转换失败，返回null
	 */
	public static <T> T parseObject(String jsonString, Class<T> requiredClass) {
		try {
			return JSON.parseObject(jsonString, requiredClass);
		} catch (Exception e) {
			log.error("parseJson Failed, requiredClass={}, jsonString={}", requiredClass, jsonString, e);
		}
		return null;
	}

	/**
	 * 将JSON字符串转化为指定的对象，如果转换失败，返回null
	 */
	public static <T> T parseObject(String jsonString, TypeReference<T> requiredType) {
		try {
			return JSON.parseObject(jsonString, requiredType);
		} catch (Exception e) {
			log.error("parseJson Failed, requiredClass={}, jsonString={}", requiredType, jsonString, e);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static List parseObjectList(String jsonString, Class requiredClass) {
		List list = new ArrayList();
		try {
			return JSON.parseArray(jsonString, requiredClass);
		} catch (Exception e) {
			log.error("parseJson Failed, requiredClass={}, jsonString={}", requiredClass, jsonString, e);
		}
		return null;
	}

}
