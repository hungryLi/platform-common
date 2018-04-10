package platform.common.utils.sendmail;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.mail.javamail.JavaMailSender;


/**
 * 
 * @author Administrator
 * @since 2014年11月25日
 */
@Deprecated
public class SendMailPool {
	private ExecutorService service = null;
	private SendMailPool() {
		service = Executors.newCachedThreadPool();
	}
	/**
	 * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例 没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
	 */
	private static class PutMailInQueuePoolHolder {
		/**
		 * 静态初始化器，由JVM来保证线程安全
		 */
		private static SendMailPool instance = new SendMailPool();
	}
	public static SendMailPool getInstance() {
		return PutMailInQueuePoolHolder.instance;
	}
	/**
	 * 向线程池添加任务
	 * 
	 * @param task
	 */
	public void addPool(Runnable task) {
		service.execute(task);
	}
	
	public void sendMailtoLixiaomin(JavaMailSender mailSender, String subject, String content){
		String[] to = new String[1];
		to[0] = "lixiaomin@fangdd.com";
		String[] cc = null;
		String from = "finance.admin@fangdd.com";
		Email email = new Email(to, cc, from, subject, content);      
    SendMailThread runnable = new SendMailThread(mailSender, email);      
    SendMailPool.getInstance().addPool(runnable);
	}
	
	public void sendMailtoRetivePerson(JavaMailSender mailSender, String[] to,String subject, String content){
		String[] cc = null;
		String from = "finance.admin@fangdd.com";
		Email email = new Email(to, cc, from, subject, content);      
		SendMailThread runnable = new SendMailThread(mailSender, email);      
		SendMailPool.getInstance().addPool(runnable);
	}

}

