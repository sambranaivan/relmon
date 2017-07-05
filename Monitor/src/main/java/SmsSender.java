
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class SmsSender implements iObserver{
	// Find your Account Sid and Token at twilio.com/user/account
	  public static final String ACCOUNT_SID = "AC9a12d6d1b98743ad8b0539ff387d4fef";
	  public static final String AUTH_TOKEN = "a50ad29c928220b2f5ac636194c08255";
	@Override
	public void update() {
		
		 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		    Message message = Message.creator(new PhoneNumber("+18569246781"),
		        new PhoneNumber("+3794688483"), 
		        "This is the ship that made the Kessel Run in fourteen parsecs?").create();

		    System.out.println(message.getSid());
		
		// TODO Auto-generated method stub
		System.out.println("!!Sms Sender");
		System.out.println("!!SMS ENVIADO");
	}
}
