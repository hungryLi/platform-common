package platform.common.utils;

/***
 * 用于对属性进行过滤
 */
public interface ExcelFieldFilter {
	/***
	 * 如果bean里面的属性在excel里面出现，返回true，如果不需要出现返回false
	 */
	public boolean filter(String fieldName);
}