package meew0.mewtwo.commands;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;

import meew0.mewtwo.commands.UrbanResults.Definition;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import com.google.common.base.Joiner;
import com.google.gson.Gson;

public class CommandUrban implements ICommand {
	@Override
	public String getCommandName() {
		return "define";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		String google = "http://api.urbandictionary.com/v0/define?term=";
		String search = Joiner.on(" ").join(args);
		String charset = "UTF-8";
		
		try {

			URL url = new URL(google + URLEncoder.encode(search, charset));
			Reader reader = new InputStreamReader(url.openStream(), charset);
			UrbanResults results = new Gson()
					.fromJson(reader, UrbanResults.class);
			
	
			if(results.getList().size() == 0) {
				event.respond("No results found");
			}
			Definition d = results.getList().get(0);
			
			event.respond(d.getWord() + 
					": \u0002" + d.getDefinition() + 
					"\u000F - " + d.getExample() + 
					" || \u00039+" + d.getThumbs_up() + "\u000F \u00034-" + d.getThumbs_down());
		} catch(Exception e) {
			RuntimeException e2 = new RuntimeException("An exception occurred while executing command", e);
			throw e2;
		}

	}

	@Override
	public String getHelpEntry() {
		return "define|d {term}: Defines something.";
	}

	@Override
	public String getAlias() {
		return "d";
	}
}

class UrbanResults {

    private java.util.List<Definition> list;
    public java.util.List<Definition> getList() { return list; }
    public void setList(java.util.List<Definition> results) { this.list = results; }
    public String toString() { return "Definitions[" + list + "]"; }

    static class Definition {
        private String permalink;
        private String definition;
        private String example;
        private int thumbs_up;
        private int thumbs_down;
        private String word;
		public String getWord() {
			return word;
		}
		public void setWord(String word) {
			this.word = word;
		}
		public int getThumbs_up() {
			return thumbs_up;
		}
		public void setThumbs_up(int thumbs_up) {
			this.thumbs_up = thumbs_up;
		}
		public int getThumbs_down() {
			return thumbs_down;
		}
		public void setThumbs_down(int thumbs_down) {
			this.thumbs_down = thumbs_down;
		}
		public String getPermalink() {
			return permalink;
		}
		public void setPermalink(String permalink) {
			this.permalink = permalink;
		}
		public String getDefinition() {
			return definition;
		}
		public void setDefinition(String definition) {
			this.definition = definition;
		}
		public String getExample() {
			return example;
		}
		public void setExample(String example) {
			this.example = example;
		}
		
		@Override
		public String toString() {
			return "Definition[]";
		}
    }

}