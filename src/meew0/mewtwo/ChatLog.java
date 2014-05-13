package meew0.mewtwo;

import java.util.LinkedList;

public class ChatLog {
	public static int limit = 50;
	
	public LinkedList<String> messages;

	
	public ChatLog() {
		messages = new LinkedList<String>();
	}
	
	public void add(String s) {
		messages.addFirst(s);
		
		if(messages.size() > limit) messages.removeLast();
	}
}
