package platform.common.utils.sendmail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.FileCopyUtils;

public class SendHtmlMailThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(SendMailThread.class);

	private Email email;

	private JavaMailSender mailSender;

	public SendHtmlMailThread(JavaMailSender mailSender, Email email) {
		this.mailSender = mailSender;
		this.email = email;
	}
	


	@SuppressWarnings( "deprecation" )
  public void run() {
		// 添加到本地队列发送出去
		for (int i = 1; i <= 3; i++) {
			try {
				javax.mail.internet.MimeMessage msg = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
					
				
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
				
				Resource fs = new ClassPathResource("/mail/template/email_flat_red.html");
				EncodedResource encRes = new EncodedResource(fs,"UTF-8");
				String html = FileCopyUtils.copyToString(encRes.getReader());
				html = html.replace("${mail_title}", email.getSubject());
				html = html.replace("${mail_content}", email.getContent());
				
  			helper.setText(html, true);
//  			Resource fsImageLogo = new ClassPathResource("/mail/template/image/logo_red.png");
//  			helper.addInline("logo", fsImageLogo);
				
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
