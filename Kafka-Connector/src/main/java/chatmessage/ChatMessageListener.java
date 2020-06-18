package chatmessage;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

import com.google.gson.Gson;

public class ChatMessageListener extends ListenerAdapter  {
	
	@Override
	public void onGenericMessage(GenericMessageEvent event) throws Exception {
		super.onGenericMessage(event);
		
		MessageQueue.getInstance().put(parseToJSONString(event));
	}
	
	private String parseToJSONString(GenericMessageEvent event) {
		 Gson gson = new Gson();
		 String json = gson.toJson(new UserMessage(
				 event.getUser().getIdent().toString(),
				 event.getUser().getNick().toString(),
				 event.getUser().getUserId().toString(),
				 event.getMessage().toString(),
				 event.getTimestamp()));
		  
		 return json;
	}
}

class UserMessage{
	public String ident;
	public String nick;
	public String userId;
	public String message;
	public Long timestamp;
	
	public UserMessage(String ident, String nick, String userId, String message, Long timestamp) {
		this.ident = ident;
		this.nick = nick;
		this.userId = userId;
		this.message = message;
		this.timestamp = timestamp;
	}	
}
