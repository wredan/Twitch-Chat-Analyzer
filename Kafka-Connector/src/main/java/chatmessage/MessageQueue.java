package chatmessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
	
	private BlockingQueue<String> queue;
	private static MessageQueue instance;
	
	private MessageQueue() {
		queue = new LinkedBlockingQueue<String>();
	}
	
	public static MessageQueue getInstance() {
		if(instance == null)
			instance = new MessageQueue();
		return instance;
	}
	
	public void add(String s) {
		queue.add(s);
	}
	
	public String take() throws InterruptedException {
		return queue.take();
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
}
