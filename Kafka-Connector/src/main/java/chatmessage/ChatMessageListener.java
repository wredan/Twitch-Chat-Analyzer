package chatmessage;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class ChatMessageListener extends ListenerAdapter  {
	
	@Override
	public void onGenericMessage(GenericMessageEvent event) throws Exception {
		// TODO Auto-generated method stub
		super.onGenericMessage(event);
		
		MessageQueue.getInstance().add(event.getUser().getNick() + ": " + event.getMessage());
	}
}
