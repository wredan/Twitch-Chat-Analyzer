package chatmessage;

import java.io.IOException;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.exception.IrcException;

public class BotClient implements Runnable {
	
	private String channelUsername;
	private String oauth;
	private String channel;
	
	public BotClient(String channelUsername, String oauth, String channel) {
		this.channelUsername = channelUsername;
		this.oauth = oauth;
		this.channel = channel;
	}
	
	public void run() {
		Configuration config = new Configuration.Builder()
				.setAutoNickChange(false) //Twitch doesn't support multiple users
				.setOnJoinWhoEnabled(false) //Twitch doesn't support WHO command
				.setCapEnabled(true)
				.addCapHandler(new EnableCapHandler("twitch.tv/membership")) //Twitch by default doesn't send JOIN, PART, and NAMES unless you request it, see https://dev.twitch.tv/docs/irc/guide/#twitch-irc-capabilities
				.addServer("irc.twitch.tv")
				.setName(channelUsername) //Your twitch.tv username
				.setServerPassword(oauth) //Your oauth password from http://twitchapps.com/tmi
				.addAutoJoinChannel(channel) //Some twitch channel		
				.addListener(new ChatMessageListener())
				.buildConfiguration();
				
		PircBotX clientBot = new PircBotX(config);
		try {
			clientBot.startBot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IrcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
