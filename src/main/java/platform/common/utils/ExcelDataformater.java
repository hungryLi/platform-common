package platform.common.utils;

/***
 * 用于对数据进行格式
 */
public interface ExcelDataformater {
	/***
	 * 对每一个fieldName进行格式过滤
	 * 
	 * @param fieldName
	 *            bean 里面的属性名称
	 * @param value
	 *            bean 里面的属性对应的值
	 * @return 返回格式后的值
	 */
	public Object format(String fieldName, Object value);
}
