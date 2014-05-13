package meew0.mewtwo.commands;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import com.google.common.base.Joiner;
import com.google.gson.Gson;

public class CommandGoogle implements ICommand {
	@Override
	public String getCommandName() {
		return "google";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
		String search = Joiner.on(" ").join(args);
		String charset = "UTF-8";
		
		try {

			URL url = new URL(google + URLEncoder.encode(search, charset));
			Reader reader = new InputStreamReader(url.openStream(), charset);
			GoogleResults results = new Gson()
					.fromJson(reader, GoogleResults.class);
			
	
			if(results.getResponseData().getResults().size() == 0) {
				event.respond("No results found");
			}
			event.respond("Found " + results.getResponseData().getResults().get(0).getUrl() + " - " + results.getResponseData().getResults().get(0).getTitle().replace("<b>", "\u0002").replace("</b>", "\u000F"));
		} catch(Exception e) {
			RuntimeException e2 = new RuntimeException("An exception occurred while executing command", e);
			throw e2;
		}

	}

	@Override
	public String getHelpEntry() {
		return "google|g {term}: Performs a google search through Google's AJAX API and returns the first result";
	}

	@Override
	public String getAlias() {
		return "g";
	}
}

class GoogleResults {

    private ResponseData responseData;
    public ResponseData getResponseData() { return responseData; }
    public void setResponseData(ResponseData responseData) { this.responseData = responseData; }
    public String toString() { return "ResponseData[" + responseData + "]"; }

    static class ResponseData {
        private List<Result> results;
        public List<Result> getResults() { return results; }
        public void setResults(List<Result> results) { this.results = results; }
        public String toString() { return "Results[" + results + "]"; }
    }

    static class Result {
        private String url;
        private String title;
        public String getUrl() { return url; }
        public String getTitle() { return title; }
        public void setUrl(String url) { this.url = url; }
        public void setTitle(String title) { this.title = title; }
        public String toString() { return "Result[url:" + url +",title:" + title + "]"; }
    }

}