package kafkaconnector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.exception.IrcException;

import chatmessage.ChatMessageListener;

public class ChatMessageSourceConnector extends SourceConnector{

	private PircBotX clientBot;
	private String kafkaTopic;
	
	public String version() {
		return "1";
	}
	
	@Override
	public void start(Map<String, String> props) {
		kafkaTopic = props.get("kafka.topic");
		Configuration config = new Configuration.Builder()
				.setAutoNickChange(false) //Twitch doesn't support multiple users
				.setOnJoinWhoEnabled(false) //Twitch doesn't support WHO command
				.setCapEnabled(true)
				.addCapHandler(new EnableCapHandler("twitch.tv/membership")) //Twitch by default doesn't send JOIN, PART, and NAMES unless you request it, see https://dev.twitch.tv/docs/irc/guide/#twitch-irc-capabilities
				.addServer("irc.twitch.tv")
				.setName(props.get("channel.username")) //Your twitch.tv username
				.setServerPassword(props.get("oauth")) //Your oauth password from http://twitchapps.com/tmi
				.addAutoJoinChannel(props.get("channel")) //Some twitch channel		
				.addListener(new ChatMessageListener())
				.buildConfiguration();
				
		clientBot = new PircBotX(config);
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
	@Override
	public Class<? extends Task> taskClass() {
		return ChatMessageSourceTask.class;
	}
	@Override
	public List<Map<String, String>> taskConfigs(int maxTasks) {
		List<Map<String, String>> configs = new ArrayList<Map<String, String>>();
		Map<String, String> config = new HashMap<String, String>();
		config.put("topic", kafkaTopic);
		configs.add(config);
		return configs;
	}
	@Override
	public void stop() {
		clientBot.stopBotReconnect();
	}
	
	@Override
	public ConfigDef config() {
		return new ConfigDef()
				.define("topic", Type.STRING, Importance.HIGH, "The Kafka topic");
	}

	
}
