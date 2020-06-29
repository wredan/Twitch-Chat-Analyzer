package chatmessage;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

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
		JSONObject json = new JSONObject();
		json.put("targetChannelUsername", targetChannelUsername)
				.put("nickname", event.getUser().getNick())
				.put("message", new String(event.getMessage().replace("\"", "'").replace("\\", "").getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8))
				.put("timestamp", event.getTimestamp());
		  
		 return json.toString();
	}	
}
