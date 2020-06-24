package chatmessage;
import org.apache.commons.lang3.StringEscapeUtils;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

import com.google.gson.Gson;

public class ChatMessageListener extends ListenerAdapter  {
	
	private String targetChannelUsername;
	
	public ChatMessageListener(String targetChannelUsername) {
		this.targetChannelUsername = targetChannelUsername;
	}
	
	@Override
	public void onGenericMessage(GenericMessageEvent event) throws Exception {
		super.onGenericMessage(event);
		String user = event.getUser().getIdent().toLowerCase(); 
		if(user.length() >= 3 && event.getMessage().charAt(0) != '!') {
			String botChecker = user.substring(user.length() - 3);
			if(!user.equals("streamelements") && !user.equals("streamlabs") && !botChecker.equals("bot"))
				MessageQueue.getInstance().put(StringEscapeUtils.unescapeJava(parseToJSONString(event)));
		}
			
		
	}
	
	private String parseToJSONString(GenericMessageEvent event) {
		 Gson gson = new Gson();
		 String json = gson.toJson(new UserMessage(
				 targetChannelUsername,
				 event.getUser().getIdent().toString(),
				 event.getUser().getNick().toString(),
				 event.getUser().getUserId().toString(),
				 event.getMessage(),
				 event.getTimestamp()));
		  
		 return json;
	}
}

class UserMessage{
	public String targetChannelUsername;
	public String ident;
	public String nickname;
	public String userId;
	public String message;
	public Long timestamp;
	
	public UserMessage(String targetChannelUsername, String ident, String nick, String userId, String message, Long timestamp) {
		this.targetChannelUsername = targetChannelUsername;
		this.ident = ident;
		this.nickname = nick;
		this.userId = userId;
		this.message = message;
		this.timestamp = timestamp;
	}	
}
