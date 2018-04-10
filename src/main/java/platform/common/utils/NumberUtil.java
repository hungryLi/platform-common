package platform.common.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
	 
	public static void main(String[] args){
		System.out.println(isInt("-0"));
	}
	
	public static String format(Double value){
		DecimalFormat df = new DecimalFormat("###,###,###,##0.00"); 
		return df.format(value);
	}
	
	public static Number strToDouble(String value){
		DecimalFormat df = new DecimalFormat("###,###,###,##0.00"); 
		try {
			return df.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -99999999.0;
	}
	
	public static boolean isInt(String str){
		if(StringUtil.isEmpty(str)){
			return false;
		}
		
		String regEx="^-?\\d+$"; 
		Pattern p=Pattern.compile(regEx);
		Matcher m=p.matcher(str);
		return m.matches();
	}
	
	public static boolean isFloat(String str){
		String regEx="^\\d*.?\\d*$"; 
		Pattern p=Pattern.compile(regEx);
		Matcher m=p.matcher(str);
		return m.matches();
	}
	
	public static boolean chkMobile(String str){
		if(isInt(str)){
			if(str!=null && str.trim().length()==11 && "1".equals(str.substring(0,1))){
				return true;
			}else{
				return false;
			}
			
		}else{
			return false;
		}
	}
	
	public static ArrayList<Integer> getRandomArray(int maxnum){
		ArrayList<Integer> array=new ArrayList<Integer>();
		LinkedList<Integer> link=new LinkedList<Integer>();
		for(int i=0;i<maxnum;i++){
			link.add(i);
		}
		Random random=new Random();
		
		while(link.size()>1){
			int r=random.nextInt(link.size());
			array.add(link.get(r));
			link.remove(r);
		}
		array.add(link.get(0));
		return array;
	}
	
	public static ArrayList<Integer> getFirstRandom(int maxnum,int firstnum){
		ArrayList<Integer> array=new ArrayList<Integer>();
		ArrayList<Integer> fullArray=getRandomArray(maxnum);
		
		for(int i=0;i<firstnum;i++){
			array.add(fullArray.get(i));
		}
		
		return array;
	}

}
