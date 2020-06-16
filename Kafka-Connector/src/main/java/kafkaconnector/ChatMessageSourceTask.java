package kafkaconnector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import chatmessage.MessageQueue;

public class ChatMessageSourceTask extends SourceTask{

	private final String OFFSET_KEY = "twitch";
	private final String OFFSET_VALUE = "position";
	private String kafkaTopic;
	private MessageQueue queue;
	private Long count;
	
	public String version() {
		return "1";
	}
	
	@Override
	public void start(Map<String, String> props) {
		kafkaTopic = props.get("topic");
		queue = MessageQueue.getInstance();
		count = 0L;		
	}
	
	@Override
	public List<SourceRecord> poll() throws InterruptedException {
		List<SourceRecord> records = new ArrayList<SourceRecord>();
		while (!queue.isEmpty()) {
			String data = queue.take();
			SourceRecord record = new SourceRecord(offsetKey(OFFSET_KEY), offsetValue(count++), kafkaTopic,
					Schema.STRING_SCHEMA, data);
			records.add(record);
		}
		return records;
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	private Map<String, String> offsetKey(String offKey) {
		return Collections.singletonMap(OFFSET_KEY, offKey);
	}

	private Map<String, Long> offsetValue(Long pos) {
		return Collections.singletonMap(OFFSET_VALUE, pos);
	}

	
}
