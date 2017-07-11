
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class SmsSender implements iObserver{
	// Find your Account Sid and Token at twilio.com/user/account
	  public static final String ACCOUNT_SID = "";
	  public static final String AUTH_TOKEN = "";
	@Override
	public void update() {
		
		 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		    Message message = Message.creator(new PhoneNumber("+"),
		        new PhoneNumber("+"), 
		        "This is the ship that made the Kessel Run in fourteen parsecs?").create();

		    System.out.println(message.getSid());
		
		// TODO Auto-generated method stub
		System.out.println("!!Sms Sender");
		System.out.println("!!SMS ENVIADO");
	}
}
