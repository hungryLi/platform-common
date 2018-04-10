package platform.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

public class ExportExcel {
	/***
	 * 每一个sheet存放数据大小
	 */
	public static int sheetSize = 5000;
	private HSSFWorkbook workbook;

	private String title;
	private String params;
	private String[] headers;
	private List dataset;
	private String outPutStr;
	private OutputStream out;
	private String pattern;
	private static int zoom = 256;
	private ExcelDataformater dataFormater;
	private ExcelFieldFilter excelFieldFilter;
	private String relativePath="";

	/***
	 * 
	 * @param title
	 * @param params
	 * @param headers
	 * @param dataset
	 * @param dataFormater
	 * @param excelFieldFilter
	 * @throws FileNotFoundException
	 */
	public ExportExcel(String title, String params, String[] headers,
			List dataset,String fileParentPath, ExcelDataformater dataFormater,
			ExcelFieldFilter excelFieldFilter) throws FileNotFoundException{
		RandomGUID guid=new RandomGUID();
		String guidStr=guid.toString().substring(0, 5);
		for (int i=0;i<5;i++) {
			relativePath=relativePath+File.separator+guidStr.charAt(i);
		}
		String fileAbsolutePath=fileParentPath+relativePath;
		relativePath=relativePath+File.separator+guidStr+".xls";
		File file=new File(fileAbsolutePath);
		if(!file.exists()){
			file.mkdirs();
		}
		fileAbsolutePath=fileAbsolutePath+File.separator+guidStr+".xls";
		this.params = params;
		this.title = title;
		this.headers = headers;
		this.dataset = dataset;
		this.outPutStr=fileAbsolutePath;
		this.out = new FileOutputStream(outPutStr);
		this.dataFormater = dataFormater;
		this.excelFieldFilter = excelFieldFilter;
	}

	 /**
	  * 
	  * @param pageNo 当前页码
	  * @param pageSize 页数
	  * @param list  所有集合
	  * @return
	  * @throws Exception
	  */
	public static List<Object> splitList(int pageNo, int pageSize, List<Object> list){
		List<Object> result = new ArrayList<Object>();
		if (list != null && list.size() > 0) {
			int allCount = list.size();
			int pageCount = (allCount + pageSize - 1) / pageSize;
			if (pageNo >= pageCount) {
				pageNo = pageCount;
			}
			int start = (pageNo - 1) * pageSize;
			int end = pageNo * pageSize;
			if (end >= allCount) {
				end = allCount;
			}
			for (int i = start; i < end; i++) {
				result.add(list.get(i));
			}
		}
		return (result != null && result.size() > 0) ? result : null;
	}
	
	public String exportExcel() {
		// 声明一个工作薄
		workbook = new HSSFWorkbook();
		int dataLength = dataset.size();
		if (dataLength > 0) {
			int pageSize = dataLength / sheetSize + 1;
			for (int i = 0; i < pageSize; i++) {
				List pageDataSet=splitList(i+1,sheetSize,dataset);
				buildSheet(title + i,pageDataSet);
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(workbook!=null){
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return relativePath;
	}

	private void buildSheet(String sheetName,List dataset) {
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetName);
		int dataIndex = 0;
		if (title != null) {
			dataIndex = dataIndex + 1;
			HSSFRow titleRow = sheet.createRow(0);// 创建标题行
			buildTitleRow(titleRow, sheet);// 构建标题行
		}
		if (params != null) {
			dataIndex = dataIndex + 1;
			HSSFRow paramsRow = sheet.createRow(1);// 创建参数行
			buildParamsRow(paramsRow, sheet);// 构建参数行
		}
		HSSFRow headsRow = sheet.createRow(dataIndex);// 创建heads行
		dataIndex = dataIndex + 1;
		buildDataBody(sheet, dataIndex,dataset);// 构建表体
		buildHeads(headsRow, sheet);// 构建表格头行
	}

	private void buildDataBody(HSSFSheet sheet, int dataIndex, Collection dataset) {
		// 遍历集合数据，产生数据行
		Iterator it = dataset.iterator();
		// 创建数据的index
		int index = dataIndex;
		while (it.hasNext()) {
			HSSFRow row = sheet.createRow(index);
			Object t = it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			int not_data_index = 0;
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				if (excelFieldFilter != null) {
					boolean isField = excelFieldFilter.filter(fieldName);
					if (!isField) {
						not_data_index++;
						continue;
					}
				}
				int realIndex = i - not_data_index;
				HSSFCell cell = row.createCell(realIndex);
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);

				Class tCls = t.getClass();
				Method getMethod = null;
				Object value = null;
				try {
					getMethod = tCls.getMethod(getMethodName, new Class[] {});
					value = getMethod.invoke(t, new Object[] {});
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				if (dataFormater != null) {
					value = dataFormater.format(fieldName, value); // 进行数据过滤
				}
				if (value != null) {
					String textValue = null;
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof BigDecimal) {
						BigDecimal bd = (BigDecimal) value;
						textValue = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
								.toString();
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					if (textValue != null) {
						if (value instanceof BigDecimal){
							double doubleValue=((BigDecimal)value).doubleValue();
							cell.setCellValue(doubleValue);
						}else{
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);
							cell.setCellValue(richString);
						}
						sheet.setColumnWidth(realIndex,
								textValue.getBytes().length * zoom);
					}
				}
			}
			index++;
		}
	}

	private HSSFRow buildHeads(HSSFRow headsRow, HSSFSheet sheet) {
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = headsRow.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
			int width = sheet.getColumnWidth(i);
			int newWidth = headers[i].getBytes().length * zoom;
			if (newWidth > width) {
				sheet.setColumnWidth(i, newWidth);
			}
		}
		return headsRow;
	}

	private HSSFCell buildTitleRow(HSSFRow titileRow,
			HSSFSheet sheet) {
		HSSFRow rowT = titileRow;
		HSSFCell cellT = rowT.createCell((short) 0);
		int cellNum = headers.length;
		Region region = new Region(rowT.getRowNum(), (short) 0,
				rowT.getRowNum(), (short) cellNum);// 合并从第rowFrom行columnFrom列
		sheet.addMergedRegion(region);// 到rowTo行columnTo的区域
		rowT.setHeight((short) 400);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeight((short) 250);
		cellStyle.setFont(font);
		cellT.setCellStyle(cellStyle);
		cellT.setCellValue(title == null ? "" : title);
		return cellT;
	}

	private HSSFCell buildParamsRow(HSSFRow titileRow,
			HSSFSheet sheet) {
		HSSFRow rowT = titileRow;
		HSSFCell cellT = rowT.createCell((short) 0);
		int cellNum = headers.length;
		Region region = new Region(rowT.getRowNum(), (short) 0,
				rowT.getRowNum(), (short) cellNum);// 合并从第rowFrom行columnFrom列
		sheet.addMergedRegion(region);// 到rowTo行columnTo的区域
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		HSSFFont font = workbook.createFont();
		cellStyle.setFont(font);
		cellT.setCellStyle(cellStyle);
		cellT.setCellValue(params == null ? "" : params);
		return cellT;
	}
}
