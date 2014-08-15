package meew0.mewtwo;

import java.util.LinkedList;

public class ChatLog {

    public class Message {
        public String nick = "", message = "";
    }

	public static int limit = 200;
	
	public LinkedList<Message> messages;

	
	public ChatLog() {
		messages = new LinkedList<Message>();
	}
	
	public void add(String s, String nick) {
        Message m = new Message();

        m.message = s;
        m.nick = nick;

		messages.addFirst(m);
		
		if(messages.size() > limit) messages.removeLast();
	}

    public Message getLatestFromUser(String nick) {
        for(Message m : messages) {
            if(m.nick.equals(nick)) {
                if(!m.message.startsWith(MewtwoListener.prefix)) {
                    return m;
                }
            }
        }
        return null;
    }

    public Message getLatestThatMatches(String regex) {
        for(Message m : messages) {
            if(m.message.matches(".*" + regex + ".*")) {
                if(!m.message.startsWith(MewtwoListener.prefix)) {
                    return m;
                }
            }
        }
        return null;
    }
}
