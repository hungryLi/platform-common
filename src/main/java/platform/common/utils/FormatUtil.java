package platform.common.utils;

public class FormatUtil {
	/***
	 * 格式化手机号码，进行打*号
	 * @return
	 */
	public static String formatPhoneUnSign(String phone){
		int length;
		if(phone!=null && (length=phone.length())>6){
			int index=length/2;
			String tmpPhone=phone.substring(0, index-2)+"****"+phone.substring(index+2, length);
			return tmpPhone;
		}else{
			return phone;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(formatPhoneUnSign("13537576329"));
	}
}
