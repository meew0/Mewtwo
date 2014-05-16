package meew0.mewtwo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class UserActionList {
	private File file;
	public ArrayList<UserAction> users;

	public UserActionList(File file) {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				MewtwoMain.mewtwoLogger.error("WARNING: Could not create user action file "
						+ file.getName() + "!");
			}
		}
		this.file = file;
		users = new ArrayList<UserAction>();
		reread();
	}
	
	public void add(String hostMask, String c) {
		users.add(new UserAction(hostMask, c));
		rewrite();
	}
	
	public void add(User u, Channel c) {
		add(u.getHostmask(), c.getName());
	}
	
	public void remove(String hostMask, String c) {
		UserAction[] array = users.toArray(new UserAction[]{}); // FUCK CMES
		for(UserAction a : array) {
			if(a.hostMask.equals(hostMask) && a.channel.equals(c)) {
				users.remove(a);
			}
		}
		rewrite();
	}
	
	public void remove(User u, Channel c) {
		remove(u.getHostmask(), c.getName());
	}

	public void reread() {
		try {
			users = new ArrayList<UserAction>();
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			
			while ((line = br.readLine()) != null) {
				String[] l2 = line.split(" ");
				UserAction a = new UserAction(l2[0], l2[1]);
				users.add(a);
			}
			
			br.close();
		} catch (Exception e) {
            MewtwoMain.mewtwoLogger.error("WARNING: Could not read user action file "
					+ file.getName() + "!");
		}
	}
	
	public void rewrite() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(UserAction line : users) {
				bw.write(line.hostMask + " " + line.channel);
				bw.newLine();
				bw.flush();
			}
			
			bw.close();
		} catch (Exception e) {
            MewtwoMain.mewtwoLogger.error("WARNING: Could not read user action file "
					+ file.getName() + "!");
		}
	}
}
