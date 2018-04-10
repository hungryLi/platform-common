package platform.common.utils.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

public class SMSUtil {
	 
	 private static final Logger logger = Logger.getLogger(SMSUtil.class);
	 
	 // Find your Account Sid and Token at twilio.com/user/account
	 public static final String ACCOUNT_SID = "ACa1d258435585d4ecb3f8b09e99ad1981";
	 public static final String AUTH_TOKEN = "78329fb3411233d8da6df7d3a94fc9ff";
	 
	 /**
	  * 发送短信
	  * @param phone 手机号
	  * @param content 内容
	  * @return 0:成功  1:参数有误 2:发送异常
	  */
	 public static int SendMessage(String phone, String content) {
		 if(StringUtils.isBlank(phone) || StringUtils.isBlank(content)){
			 return 1;
		 }
		 try {
			 TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
			 // Build the parameters
			 List<NameValuePair> params = new ArrayList<NameValuePair>();
			 params.add(new BasicNameValuePair("To", phone));
			 params.add(new BasicNameValuePair("From", "830-521-2032"));
			 params.add(new BasicNameValuePair("Body", content));
			 //params.add(new BasicNameValuePair("MediaUrl", "https://climacons.herokuapp.com/clear.png"));//媒体访问地址
			 MessageFactory messageFactory = client.getAccount().getMessageFactory();
			 Message message = messageFactory.create(params);
			 
			 logger.info("==> SMS SEND SUCCESS, SID: "+ message.getSid());
			 return 0;
		} catch (TwilioRestException e) {
			logger.error("--> 发送短信异常", e);
			return 2;
		}
	}
	 
	public static void main(String[] args) {
		 SendMessage("+8615013830926", "验证码123456。 请尽快输入完成后续操作。");
	}
	 
}