package platform.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
 
/**
 * 对excel进行操作工具类
 **/
@SuppressWarnings("rawtypes")
public class ExcelHandle {
 
    private Map<String,HashMap[]> tempFileMap  = new HashMap<String,HashMap[]>();
    private Map<String,Map<String,Cell>> cellMap = new HashMap<String,Map<String,Cell>>();
    private Map<String,FileInputStream> tempStream = new HashMap<String, FileInputStream>();
    private Map<String,HSSFWorkbook> tempWorkbook = new HashMap<String, HSSFWorkbook>();
    private Map<String,HSSFWorkbook> dataWorkbook = new HashMap<String, HSSFWorkbook>();
    
    private int pageSize=100000;
    
    /***
     * 单个值的key
     */
    private List<String> dataCell;
    
    /***
     * 单个值的key value
     */
    private Map<String,Object> dataMap;
    
    /***
     * 列表头的key
     */
    private List<String> dataListCell;
    /***
     * 列表对应的value
     */
    private List<Map<String,Object>> dataListMap;
    
    /***
     * 导出文件后的路径
     */
    private String outFilePath;
    
    /***
     * 模板文件后的路径
     */
    private String tempFilePath;

	public void setDataCell(List<String> dataCell) {
		this.dataCell = dataCell;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setDataListCell(List<String> dataListCell) {
		this.dataListCell = dataListCell;
	}

	public void setDataListMap(List<Map<String, Object>> dataListMap) {
		this.dataListMap = dataListMap;
	}

	public ExcelHandle(String tempFilePath,String outFilePath) {
    	this.outFilePath=outFilePath;
    	this.tempFilePath=tempFilePath;
	}

	/**
     * 单无格类
     * @author xiliang.xiao
     *
     */
    class Cell{
        private int column;//列
        private int line;//行
        private CellStyle cellStyle;
 
        public int getColumn() {
            return column;
        }
        public void setColumn(int column) {
            this.column = column;
        }
        public int getLine() {
            return line;
        }
        public void setLine(int line) {
            this.line = line;
        }
        public CellStyle getCellStyle() {
            return cellStyle;
        }
        public void setCellStyle(CellStyle cellStyle) {
            this.cellStyle = cellStyle;
        }
    }
     
    /**
     * 向Excel中输入相同title的多条数据
     * @param tempFilePath excel模板文件路径
     * @param cellList 需要填充的数据（模板<!%后的字符串）
     * @param dataList 填充的数据
     * @throws IOException 
     */
    public void writeListData(String tempFilePath,List<String> cellList,List<Map<String,Object>> dataList) throws IOException{
    	//按模板为写入板
        HSSFWorkbook temWorkbook = getTempWorkbook(tempFilePath);
        //数据填充的sheet
        HSSFSheet wsheet = temWorkbook.getSheetAt(0);
        int pageCount = (dataList.size() + pageSize - 1) / pageSize;
        for (int i = 0; i < pageCount; i++) {
        	List<Map<String, Object>> pageData= splitList(i+1, pageSize, dataList);
            HSSFSheet datasheet=temWorkbook.cloneSheet(0);
            HashMap temp = getTemp(tempFilePath,0);
            //获取数据填充开始行
            int startCell = Integer.parseInt((String)temp.get("STARTCELL"));
            
            //移除模板开始行数据即<!%
            datasheet.removeRow(datasheet.getRow(startCell));
			if (pageData != null && pageData.size() > 0) {
				for (Map<String, Object> map : pageData) {
					for (String cell : cellList) {
						// 获取对应单元格数据
						Cell c = getCell(cell, temp, temWorkbook, tempFilePath);
						if(c!=null){
							// 写入数据
							ExcelUtil.setValue(datasheet, startCell, c.getColumn(),
									map.get(cell), c.getCellStyle());
						}
					}
					startCell++;
				}
			}
		}
        temWorkbook.removeSheetAt(0);
    }
    
    /**
     * 向Excel中输入相同title的多条数据
     * @param tempFilePath excel模板文件路径
     * @param cellList 需要填充的数据（模板<!%后的字符串）
     * @param dataList 填充的数据
     * @param sheet 填充的excel sheet,从0开始
     * @throws IOException 
     */
    public void writeListData(String tempFilePath,List<String> cellList,List<Map<String,Object>> dataList,int sheet) throws IOException{
    	//按模板为写入板
        HSSFWorkbook temWorkbook = getTempWorkbook(tempFilePath);
        //数据填充的sheet
        HSSFSheet wsheet = temWorkbook.getSheetAt(sheet);
        HashMap temp = getTemp(tempFilePath,sheet);
        //获取数据填充开始行
        int startCell = Integer.parseInt((String)temp.get("STARTCELL"));
        
        //移除模板开始行数据即<!%
        HSSFRow row=wsheet.getRow(startCell);
        if(row!=null){
        	 wsheet.removeRow(row);
        }
        if (dataList != null && dataList.size() > 0) {
			for (Map<String, Object> map : dataList) {
				for (String cell : cellList) {
					// 获取对应单元格数据
					Cell c = getCell(cell, temp, temWorkbook, tempFilePath);
					if(c!=null){
						// 写入数据
						ExcelUtil.setValue(wsheet, startCell, c.getColumn(),
								map.get(cell), c.getCellStyle());
					}
				}
				startCell++;
			}
		}
       /* 
        int pageCount = (dataList.size() + pageSize - 1) / pageSize;
        for (int i = 0; i < pageCount; i++) {
        	List<Map<String, Object>> pageData= splitList(i+1, pageSize, dataList);
            HSSFSheet datasheet=temWorkbook.cloneSheet(0);
            HashMap temp = getTemp(tempFilePath,0);
            //获取数据填充开始行
            int startCell = Integer.parseInt((String)temp.get("STARTCELL"));
            
            //移除模板开始行数据即<!%
            datasheet.removeRow(datasheet.getRow(startCell));
			if (pageData != null && pageData.size() > 0) {
				for (Map<String, Object> map : pageData) {
					for (String cell : cellList) {
						// 获取对应单元格数据
						Cell c = getCell(cell, temp, temWorkbook, tempFilePath);
						if(c!=null){
							// 写入数据
							ExcelUtil.setValue(datasheet, startCell, c.getColumn(),
									map.get(cell), c.getCellStyle());
						}
					}
					startCell++;
				}
			}
		}
        temWorkbook.removeSheetAt(0);*/
    }
 
    /**
     * 按模板向Excel中相应地方填充数据
     * @param tempFilePath excel模板文件路径
     * @param cellList 需要填充的数据（模板<%后的字符串）
     * @param dataMap 填充的数据
     * @param sheet 填充的excel sheet,从0开始
     * @throws IOException 
     */
    public void writeData(String tempFilePath,List<String> cellList,Map<String,Object> dataMap,int sheet) throws IOException{
        //获取模板填充格式位置等数据
        HashMap tem = getTemp(tempFilePath,sheet);
        //按模板为写入板
        HSSFWorkbook wbModule = getTempWorkbook(tempFilePath);
        //数据填充的sheet
        HSSFSheet wsheet = wbModule.getSheetAt(sheet);
        if(dataMap!=null&&dataMap.size()>0){
            for(String cell:cellList){
                //获取对应单元格数据
                Cell c = getCell(cell,tem,wbModule,tempFilePath);
                if(c!=null){
                    ExcelUtil.setValue(wsheet, c.getLine(), c.getColumn(), dataMap.get(cell), c.getCellStyle());
                }
            }
        }
    }
     
    /**
     * Excel文件读值
     * @param tempFilePath
     * @param cell
     * @param sheet
     * @return
     * @throws IOException 
     */
    public Object getValue(String tempFilePath,String cell,int sheet,File excelFile) throws IOException{
        //获取模板填充格式位置等数据
        HashMap tem = getTemp(tempFilePath,sheet);
        //模板工作区
        HSSFWorkbook temWorkbook = getTempWorkbook(tempFilePath);
        //数据工作区
        HSSFWorkbook dataWorkbook = getDataWorkbook(tempFilePath, excelFile);
        //获取对应单元格数据
        Cell c = getCell(cell,tem,temWorkbook,tempFilePath);
        if(c==null){
        	return null;
        }
        //数据sheet
        HSSFSheet dataSheet = dataWorkbook.getSheetAt(sheet);
        return ExcelUtil.getCellValue(dataSheet, c.getLine(), c.getColumn());
    }
     
    /**
     * 读值列表值
     * @param tempFilePath
     * @param cell
     * @param sheet
     * @return
     * @throws IOException 
     */
    public List<Map<String,Object>> getListValue(String tempFilePath,List<String> cellList,int sheet,File excelFile) throws IOException{
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        //获取模板填充格式位置等数据
        HashMap tem = getTemp(tempFilePath,sheet);
        //获取数据填充开始行
        int startCell = Integer.parseInt((String)tem.get("STARTCELL"));
        //将Excel文件转换为工作区间
        HSSFWorkbook dataWorkbook = getDataWorkbook(tempFilePath,excelFile) ;
        //数据sheet
        HSSFSheet dataSheet = dataWorkbook.getSheetAt(sheet);
        //文件最后一行
        int lastLine = dataSheet.getLastRowNum();
         
        for(int i=startCell;i<=lastLine;i++){
            dataList.add(getListLineValue(i, tempFilePath, cellList, sheet, excelFile));
        }
        return dataList;
    }
     
    /**
     * 读值一行列表值
     * @param tempFilePath
     * @param cell
     * @param sheet
     * @return
     * @throws IOException 
     */
    public Map<String,Object> getListLineValue(int line,String tempFilePath,List<String> cellList,int sheet,File excelFile) throws IOException{
        Map<String,Object> lineMap = new HashMap<String, Object>();
        //获取模板填充格式位置等数据
        HashMap tem = getTemp(tempFilePath,sheet);
        //按模板为写入板
        HSSFWorkbook temWorkbook = getTempWorkbook(tempFilePath);
        //将Excel文件转换为工作区间
        HSSFWorkbook dataWorkbook = getDataWorkbook(tempFilePath,excelFile) ;
        //数据sheet
        HSSFSheet dataSheet = dataWorkbook.getSheetAt(sheet);
        for(String cell:cellList){
            //获取对应单元格数据
            Cell c = getCell(cell,tem,temWorkbook,tempFilePath);
            if(c==null){
            	continue;
            }
            lineMap.put(cell, ExcelUtil.getCellValue(dataSheet, line, c.getColumn()));
        }
        return lineMap;
    }
     
     
 
    /**
     * 获得模板输入流
     * @param tempFilePath 
     * @return
     * @throws FileNotFoundException 
     */
    private FileInputStream getFileInputStream(String tempFilePath) throws FileNotFoundException {
        if(!tempStream.containsKey(tempFilePath)){
            tempStream.put(tempFilePath, new FileInputStream(tempFilePath));
        }
         
        return tempStream.get(tempFilePath);
    }
 
    /**
     * 获得输入工作区
     * @param tempFilePath
     * @return
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    private HSSFWorkbook getTempWorkbook(String tempFilePath) throws FileNotFoundException, IOException {
        if(!tempWorkbook.containsKey(tempFilePath)){
            tempWorkbook.put(tempFilePath, new HSSFWorkbook(getFileInputStream(tempFilePath)));
        }
        return tempWorkbook.get(tempFilePath);
    }
     
    /**
     * 获取对应单元格样式等数据数据
     * @param cell
     * @param tem
     * @param wbModule 
     * @param tempFilePath
     * @return
     */
    private Cell getCell(String cell, HashMap tem, HSSFWorkbook wbModule, String tempFilePath) {
        if(!cellMap.get(tempFilePath).containsKey(cell)){
            Cell c = new Cell();
             
            int[] pos = ExcelUtil.getPos(tem, cell);
            if(pos.length>1){
                c.setLine(pos[1]);
            }
            if(pos.length==0){ //如果为空
            	return null;
            }
            c.setColumn(pos[0]);
            c.setCellStyle((ExcelUtil.getStyle(tem, cell, wbModule)));
            cellMap.get(tempFilePath).put(cell, c);
        }
        return cellMap.get(tempFilePath).get(cell);
    }
 
    /**
     * 获取模板数据
     * @param tempFilePath 模板文件路径
     * @param sheet 
     * @return
     * @throws IOException
     */
    private HashMap getTemp(String tempFilePath, int sheet) throws IOException {
        if(!tempFileMap.containsKey(tempFilePath)){
            tempFileMap.put(tempFilePath, ExcelUtil.getTemplateFile(tempFilePath));
            cellMap.put(tempFilePath, new HashMap<String,Cell>());
        }
        return tempFileMap.get(tempFilePath)[sheet];
    }
     
    /**
     * 资源关闭
     * @param tempFilePath 模板文件路径
     * @param os 输出流
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public void writeAndClose(String tempFilePath,OutputStream os) throws FileNotFoundException, IOException{
        if(getTempWorkbook(tempFilePath)!=null){
            getTempWorkbook(tempFilePath).write(os);
            tempWorkbook.remove(tempFilePath);
        }
        if(getFileInputStream(tempFilePath)!=null){
            getFileInputStream(tempFilePath).close();
            tempStream.remove(tempFilePath);
        }
    }
     
    /**
     * 获得读取数据工作间
     * @param tempFilePath
     * @param excelFile
     * @return
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    private HSSFWorkbook getDataWorkbook(String tempFilePath, File excelFile) throws FileNotFoundException, IOException {
        if(!dataWorkbook.containsKey(tempFilePath)){
            dataWorkbook.put(tempFilePath, new HSSFWorkbook(new FileInputStream(excelFile)));
        }
        return dataWorkbook.get(tempFilePath);
    }
     
    /**
     * 读取数据后关闭
     * @param tempFilePath
     */
    public void readClose(String tempFilePath){
        dataWorkbook.remove(tempFilePath);
    }
    
	public File export() {
		try {
			if(this.dataCell!=null&&this.dataCell.size()>0){
				writeData(this.tempFilePath, this.dataCell, this.dataMap, 0);
			}
			if(this.dataListCell!=null&&this.dataListMap.size()>0){
				writeListData(this.tempFilePath, this.dataListCell,
						this.dataListMap);
			}
			File outFile=new File(this.outFilePath);
			OutputStream os = new FileOutputStream(outFile);
			// 写到输出流并关闭资源
			writeAndClose(tempFilePath, os);
			os.flush();
			os.close();
			return outFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public File export(int sheet) {
		try {
			if(this.dataCell!=null&&this.dataCell.size()>0){
				writeData(this.tempFilePath, this.dataCell, this.dataMap, sheet);
			}
			if(this.dataListCell!=null&&this.dataListMap.size()>0){
				writeListData(this.tempFilePath, this.dataListCell,
						this.dataListMap,sheet);
			}
			File outFile=new File(this.outFilePath);
			OutputStream os = new FileOutputStream(outFile);
			// 写到输出流并关闭资源
			writeAndClose(tempFilePath, os);
			os.flush();
			os.close();
			return outFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
    
	 /**
	  * 
	  * @param pageNo 当前页码
	  * @param pageSize 页数
	  * @param list  所有集合
	  * @return
	  * @throws Exception
	  */
	public static List<Map<String,Object>> splitList(int pageNo, int pageSize, List<Map<String,Object>> list){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
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
	
	public static Map<String, Object> transBean2Map(Object obj) {  
		  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
  
    }  
	
	static class User {
		private String names;
		private int ages;
		private String sexs;
		private String deses;
		private int fsdf;
		
		public int getFsdf() {
			return fsdf;
		}
		public void setFsdf(int fsdf) {
			this.fsdf = fsdf;
		}
		public String getNames() {
			return names;
		}
		public void setNames(String names) {
			this.names = names;
		}
		public int getAges() {
			return ages;
		}
		public void setAges(int ages) {
			this.ages = ages;
		}
		public String getSexs() {
			return sexs;
		}
		public void setSexs(String sexs) {
			this.sexs = sexs;
		}
		public String getDeses() {
			return deses;
		}
		public void setDeses(String deses) {
			this.deses = deses;
		}
	}
     
    public static void main(String args[]) throws IOException{
        String tempFilePath = "D:\\work_space\\windows\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\fin_platform\\WEB-INF\\classes\\exportTemplet\\yejiquerendan.xls";
        List<String> dataListCell = new ArrayList<String>();
        dataListCell.add("no");
        dataListCell.add("floorNo");
        dataListCell.add("houseNo");
        dataListCell.add("contractArea");
        dataListCell.add("contractPrice");
        dataListCell.add("customerName");
        dataListCell.add("customerContact");
        dataListCell.add("subscribeTime");
        dataListCell.add("shouleMemberMoney");
        dataListCell.add("shouleDeveloperMoney");
        dataListCell.add("shouleGroupMoney");
        dataListCell.add("subscribeRefundTime");
        dataListCell.add("achievementDes");
        List<Map<String,Object>> dataList = new  ArrayList<Map<String,Object>>();
        for (int i = 0; i < 100; i++) {
        	Map<String,Object> map=new HashMap<String,Object>();
        	map.put("no", i);
        	map.put("floorNo", i);
        	map.put("houseNo", i);
        	map.put("contractArea", i);
        	map.put("contractPrice", i);
        	map.put("customerName", i);
        	map.put("customerContact", i);
        	map.put("subscribeTime", i);
        	map.put("shouleMemberMoney", i);
        	map.put("shouleDeveloperMoney", i);
        	map.put("shouleGroupMoney", i);
        	map.put("subscribeRefundTime", i);
        	map.put("achievementDes", i);
            dataList.add(map);
		}
       
        List<String> dataCell = new ArrayList<String>();
        dataCell.add("title");
        dataCell.add("companyName");
        dataCell.add("achievementDess");
        Map<String,Object> dataMap = new  HashMap<String, Object>();
        dataMap.put("title", "eweweewew");
        dataMap.put("companyName", "fsdfsdfsfd");
        dataMap.put("achievementDess", "女ewewe");
        ExcelHandle handle = new  ExcelHandle(tempFilePath,"D:\\fsdjf.xls");
        
        handle.setDataCell(dataCell);
        handle.setDataMap(dataMap);
        
        handle.setDataListCell(dataListCell);
        handle.setDataListMap(dataList);
        handle.export();
    }
     
}
