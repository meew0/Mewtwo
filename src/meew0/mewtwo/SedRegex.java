package meew0.mewtwo;

import java.util.regex.Pattern;

public class SedRegex {
	private boolean valid = false;
	private boolean doSa = false;

	private String firstRegex, secondRegex;

	public String getFirstRegex() {
		return firstRegex;
	}

	public void setFirstRegex(String firstRegex) {
		this.firstRegex = firstRegex;
	}

	public boolean isValid() {
		return valid;
	}

	public boolean matches(String s) {
		return Pattern.compile((doSa) ? firstRegex.replace("%%%", "[A-Za-z]") : firstRegex).matcher(s).find();
	}
	
	public String replace(String s) {
		if(doSa) {
			for(int i = 0; i < 26; i++) {
				System.out.println(i);
				System.out.println(String.valueOf((char) (i + 97)));
				s = s.replaceAll(firstRegex.replace("%%%", "[" + String.valueOf((char) (i + 65)) + String.valueOf((char) (i + 97) + "]")), secondRegex.replace("%%%", String.valueOf((char) (i + 97))));
			}
			return s;
		}
		return s.replaceAll(firstRegex, secondRegex);
	}

	public static SedRegex getSedRegex(String s) {
		SedRegex r = new SedRegex();
		boolean sa = false;
		if (s.startsWith("s/") || (sa = s.startsWith("sa/"))) {
			System.out.println("true");

			int slashes = 0;
			int[] slashLocs = new int[3];
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (i > 0 && (s.charAt(i - 1) != '\\') && c == '/') {
					if (slashes < 3) {
						slashLocs[slashes] = i;
					}
					slashes++;
				}
			}
			
			
			System.out.println(slashes);
			if(slashes == 3) {
				r.valid = true;
				r.firstRegex = s.substring(slashLocs[0] + 1, slashLocs[1]);
				r.secondRegex = s.substring(slashLocs[1] + 1, slashLocs[2]);
			}
		}
		
		r.doSa = sa;

		return r;
	}
}
