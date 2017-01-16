package logic;
import com.sendgrid.*;
import java.io.IOException;

public class Message {
	
  public static void main(String[] args) throws IOException {
    Email from = new Email("SmartParking@emailservice.com");
    String subject = "Hello World from the SendGrid Java Library!";
    Email to = new Email(""); // put your email in here
    Content content = new Content("text/plain", "Hello, Email!");
    Mail mail = new Mail(from, subject, to, content);
    SendGrid sg = new SendGrid("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");
    Request request = new Request();
    try {
      request.method = Method.POST;
      request.endpoint = "mail/send";
      request.body = mail.build();
      Response response = sg.api(request);
      System.out.println(response.statusCode);
      System.out.println(response.body);
      System.out.println(response.headers);
    } catch (IOException ex) {
      throw ex;
    }
  }
  
}