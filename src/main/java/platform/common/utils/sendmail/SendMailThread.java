package platform.common.utils.sendmail;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 
 * @author yubing
 * @since 2014-11-24
 * 
 *          Email email = new Email(to, cc, from, subject, content);
           
            SendMailThread runnable = new SendMailThread(mailSender, email);
            
            SendMailPool.getInstance().addPool(runnable);

 */
@Deprecated
public class SendMailThread implements Runnable {

	public static final String TEXT_EMAIL_LUOKUAN = "房多多财务综合管理平台\nhttps://finance.fangdd.com";
	private static final Logger logger = LoggerFactory.getLogger(SendMailThread.class);

	private Email email;

	private JavaMailSender mailSender;

	public SendMailThread(JavaMailSender mailSender, Email email) {
		this.mailSender = mailSender;
		this.email = email;
	}

	public void run() {
		// 添加到本地队列发送出去
		for (int i = 1; i <= 3; i++) {
			try {
				javax.mail.internet.MimeMessage msg = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(msg, true, "GB2312");
				helper.setTo(email.getTo());
				if (email.getCc() != null) {
					helper.setCc(email.getCc());
				}
				if (email.getFrom() == null) {
					helper.setFrom(Constants.DEFAULT_SENDMAIL_FROM_VAL);
				} else {
					helper.setFrom(email.getFrom());
				}
				helper.setSubject(email.getSubject());
				helper.setText(email.getContent());
				mailSender.send(msg);
				logger.info("发送邮件,主题为:" + email.getSubject() + ",邮件内容为:" + email.getContent() + "");
				break;
			} catch (Exception ex) {
				logger.error("发送邮件异常,主题为:" + email.getSubject() + ",邮件内容为:" + email.getContent() + ",出现异常异常信息为:\n" + ex.getMessage() + "\n开始" + "第" + i + "次重试\n");
				try {
					Thread.sleep(1000 * 3);
				} catch (InterruptedException e) {
					logger.error("发送邮件线程停顿时出现异常");
				}
			}
		}

	}

}
