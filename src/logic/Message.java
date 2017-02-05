package logic;

import com.sendgrid.*;
import java.io.IOException;

public class Message {

	private static final String EMAIL = "SmartParking@emailservice.com";

	private static final String API_KEY = "";

	public static void sendEmail(final String userEmail, final String subject, final String message) throws IOException {
		final Email from = new Email(EMAIL), to = new Email(userEmail);
		final Content content = new Content("text/plain", message);
		final Mail mail = new Mail(from, subject, to, content);
		final Request request = new Request();
		request.method = Method.POST;
		request.endpoint = "mail/send";
		request.body = mail.build();
		new SendGrid(API_KEY).api(request);
	}
}