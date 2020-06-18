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
	
	public void put(String message) {
		try {
			queue.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String take() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
}
