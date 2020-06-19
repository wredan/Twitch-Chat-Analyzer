package kafkaconnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.pircbotx.PircBotX;

import chatmessage.BotClient;

public class ChatMessageSourceConnector extends SourceConnector{

	private PircBotX clientBot;
	private String kafkaTopic;
	
	public String version() {
		return "1";
	}
	
	@Override
	public void start(Map<String, String> props) {
		kafkaTopic = props.get("topic");
		new Thread(new BotClient(props.get("channel.username"), props.get("oauth"), props.get("channel")), "twitch_bot").start();		
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
