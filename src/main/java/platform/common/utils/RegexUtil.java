package platform.common.utils;

import org.junit.Test;

public class RegexUtil {
	
	public static boolean isStrongPassword(String value){
		String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";
		return value.matches(regex);
	}
	
	public static boolean isValidEmail(String email){
		String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		return email.matches(regex);
	}
	
	@Test
	public void test(){
		String value = "Aadfdeddd2@a.com";
		System.out.println(isValidEmail(value));
	}
}
