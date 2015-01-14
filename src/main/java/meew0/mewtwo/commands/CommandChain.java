package meew0.mewtwo.commands;

import meew0.mewtwo.context.MewtwoContext;

import java.util.HashMap;

/**
 * Here's a haiku for you:
 *
 * The hardest class written
 * was this one. Use with respect
 * and don't abuse it
 */
public class CommandChain implements ICommandChain {
    private String[] chainArgs;
    private String chain;

    private static final HashMap<String, String> variables = new HashMap<>();

    public CommandChain(String chain) {
        this.chain = chain;
    }

    /**
     * Trims spaces and newlines from the beginning and end of a string
     * @param result the string to trim
     * @return the string, trimmed
     */
    String specialTrim(String result) {
        while(result.startsWith(" ") || result.startsWith("\n")) result = result.substring(1, result.length());
        while(result.endsWith(" ") || result.endsWith("\n")) result = result.substring(0, result.length() - 1);

        return result;
    }

    /**
     * Execute this command chain without channel arguments.
     * @param ctx the currently active context
     * @return the result of the chain
     */
    String executeBareChain(MewtwoContext ctx) {
        // The index of the start of a subchain
        int bracketStart = -1;

        // The current parse depth
        int bracketLevel = 0;

        // The flat chain to parse after parsing quotes and subchains
        String resultChain = "";

        // Whether or not quoting is currently active
        boolean quoted = false;

        // Private use character to quote >s in quoted strings
        // This is a really hacky workaround.
        char privateUseLT = (char) 0xe001;

        // Parse quotes and subchains
        for (int i = 0; i < chain.length(); i++) {
            char c = chain.charAt(i);

            if(c == '\'') {
                quoted = !quoted;
                continue;
            }

            if(c == '>' && quoted) {
                // Use the previously defined private use character instead of an actual >, so it's not parsed later
                resultChain += privateUseLT;
                continue;
            }

            if(c == '[' && !quoted) {
                // Beginning of subchain
                if(bracketLevel == 0) bracketStart = i;
                bracketLevel++;
            }

            if(bracketLevel <= 0) {
                // Add character to result chain verbatim if not in a subchain
                resultChain += c;
            }

            if(c == ']' && !quoted) {
                // End of subchain
                bracketLevel--;
                if(bracketLevel == 0) {
                    // nested command chain detected

                    // Find the sub chain
                    String nestedChain = chain.substring(bracketStart + 1, i);
                    CommandChain subchain = new CommandChain(nestedChain);

                    // Execute the sub chain and add the result to the result chain
                    String r = subchain.execute(ctx);
                    resultChain += r;
                }
            }
        }


        // Print an error message if somebody forgot a bracket or has an extra one
        if(bracketLevel != 0) return "Your bracket level is " + bracketLevel + "! This means you " +
                ((bracketLevel > 0) ? "forgot " + bracketLevel + " closing bracket" + ((bracketLevel > 1) ? "s" : "")
                        : "have " + (-bracketLevel) + " extra closing bracket" + ((bracketLevel > 1) ? "s" : "")); // ternary operator gore

        // bracket parsing done, actually execute chain now


        // Copy over result chain to chain
        chain = resultChain;

        // Point where chain arguments end
        int colonIndex = chain.indexOf(':');

        if(colonIndex < 0) {
            // If there's no colon, assume no arguments
            chainArgs = new String[]{};
        }
        else {
            // Otherwise parse arguments
            chainArgs = chain.substring(0, colonIndex).split(",");
            chain = chain.substring(colonIndex + 1, chain.length());
        }


        // Result of previous command, as replacement for %p
        String previousResult = "";

        String chainToSplit = chain;
        if(chain.charAt(0) == '>') chainToSplit = chain.substring(1, chain.length()); // don't break if a command happens to be called ">"
        boolean first = true;

        for(String s1 : chainToSplit.split(">")) {
            // If we're at the first character and it happens to be a >, execute it instead of parsing it
            if(first && chain.charAt(0) == '>') s1 = ">" + s1;
            first = false;

            // Remove spaces
            String s = specialTrim(s1);

            // Replace the private use > with an actual >
            s = s.replace(privateUseLT, '>');

            // Find end of command name and start of arguments
            int firstSpace = s.indexOf(" ");

            // Then parse name and arguments
            String cmdName = (firstSpace > -1) ? s.substring(0, firstSpace) : s;
            String args = (firstSpace > -1) ? s.substring(firstSpace + 1, s.length()) : "";

            if(!args.contains("%p")) args += " %p";

            // Create a command from name and args
            Command cmd = new Command(cmdName, args.replace("%p", previousResult), ctx);

            // Prevent disabled commands from being executed
            if(cmd.isDisabled()) return "The command '" + cmdName + "' is disabled!";

            // Prevent out-of-scope commands from being executed
            if(cmd.isOutsideScope()) return "The command '" + cmdName + "' is outside of the allowed scope!";

            try {
                cmd.getWrapper();

            } catch (Throwable t) {
                return "The command '" + cmdName + "' does not exist!";
            }

            previousResult = specialTrim(cmd.execute(ctx));

        }

        return previousResult;
    }

    /**
     * Convert a color name to an IRC color code
     * @param color the color name to convert
     * @return an IRC color code, from 00 to 15
     */
    private String strToColor(String color) {
        if(color.equalsIgnoreCase("white")) color = "00";
        if(color.equalsIgnoreCase("black")) color = "01";
        if(color.equalsIgnoreCase("blue")) color = "02"; if(color.equalsIgnoreCase("navy")) color = "02";
        if(color.equalsIgnoreCase("green")) color = "03";
        if(color.equalsIgnoreCase("red")) color = "04";
        if(color.equalsIgnoreCase("brown")) color = "05"; if(color.equalsIgnoreCase("maroon")) color = "05";
        if(color.equalsIgnoreCase("purple")) color = "06"; if(color.equalsIgnoreCase("violet")) color = "06";
        if(color.equalsIgnoreCase("orange")) color = "07";
        if(color.equalsIgnoreCase("yellow")) color = "08";
        if(color.equalsIgnoreCase("lightgreen")) color = "09"; if(color.equalsIgnoreCase("lime")) color = "09";
        if(color.equalsIgnoreCase("teal")) color = "10"; if(color.equalsIgnoreCase("turquoise")) color = "10";
        if(color.equalsIgnoreCase("cyan")) color = "11"; if(color.equalsIgnoreCase("aqua")) color = "11";
        if(color.equalsIgnoreCase("lightblue")) color = "12"; if(color.equalsIgnoreCase("royal")) color = "12";
        if(color.equalsIgnoreCase("pink")) color = "13"; if(color.equalsIgnoreCase("fuchsia")) color = "13";
        if(color.equalsIgnoreCase("grey")) color = "14"; if(color.equalsIgnoreCase("gray")) color = "14";
        if(color.equalsIgnoreCase("silver")) color = "15"; if(color.equalsIgnoreCase("lightgrey")) color = "15";
        if(color.equalsIgnoreCase("lightgray")) color = "15";

        return color;
    }

    /**
     * Get the argument of a chain argument
     * @param arg the chain argument
     * @return its argument
     */
    private String getArgumentArgument(String arg) {
        return arg.substring(arg.indexOf(" ") + 1, arg.length());
    }

    /**
     * Get the chain without its arguments
     * @param chain chain with arguments
     * @return chain without arguments
     */
    private String chainWithoutArgs(String chain) {
        int colonIndex = chain.indexOf(':');

        if(colonIndex < 0) return chain;
        else return chain.substring(colonIndex + 1, chain.length());
    }

    /**
     * Execute a chain with arguments
     * @param ctx currently active context
     * @return the chain's result
     */
    public String execute(MewtwoContext ctx) {

        if(chain.length() > 1000) {
            return "The command chain length limit has been reached!";
        }

        ctx.append("chain:" + chain);

        String oldChain = chain;

        String result = executeBareChain(ctx);
        String newResult = "";

        for(int i = 0; i < result.length(); i++) {
            char c = result.charAt(i);
            if(c == '%') {
                if(i + 1 == result.length()) {
                    newResult += c;
                    break;
                }
                if(result.charAt(i + 1) == '$') {
                    String varName = result.substring(i + 2).split("[^a-zA-Z0-9]")[0];
                    newResult += variables.get(varName);
                    i = i + 1 + varName.length();
                } else if(result.charAt(i + 1) == '!') {
                    String varName = result.substring(i + 2).split("[^a-zA-Z0-9]")[0];
                    newResult += new CommandChain(":" + variables.get(varName)).execute(ctx);
                    i = i + 1 + varName.length();
                } else {
                    newResult += c;
                }
            } else {
                newResult += c;
            }
        }

        result = newResult;

        String delimiter = " ";
        String countVar = "", prevVar = "";

        if(chainArgs == null) chainArgs = new String[]{};

        for(String arg : chainArgs) {
            arg = specialTrim(arg);
            if(arg.startsWith("delim")) {
                if(arg.length() == 5) {
                    delimiter = "";
                } else {
                    delimiter = getArgumentArgument(arg);
                    delimiter = delimiter.replace("%space", " ");
                    delimiter = delimiter.replace("%bracket_open", "[");
                    delimiter = delimiter.replace("%bracket_close", "]");
                    delimiter = delimiter.replace("%colon", ":");
                    delimiter = delimiter.replace("%comma", ",");
                    delimiter = delimiter.replace("%newline", "\n");
                    if (delimiter.equals("%nothing")) delimiter = "";
                }
            }
            else if(arg.startsWith("count ")) {
                countVar = getArgumentArgument(arg);
            }
            else if(arg.startsWith("prev ")) {
                prevVar = getArgumentArgument(arg);
            }
            else if(arg.startsWith("chain ")) { // save as command chain
                String chainToExecute = chainWithoutArgs(oldChain);
                variables.put(getArgumentArgument(arg), chainToExecute);
            }
            else if(arg.startsWith("repeat ")) {
                String amountStr = getArgumentArgument(arg);
                int amount;
                try {
                    amount = Integer.parseInt(amountStr);
                } catch (NumberFormatException t) {
                    return "'" + amountStr + "' is not an integer!";
                }

                if(amount > 100) {
                    return "You can't repeat something more than 100 times!";
                }

                newResult = "";
                for(int i = 0; i < amount; i++) {
                    newResult += (result + ((i == amount-1) ? "" : delimiter));
                }
                result = newResult;
            }
            else if(arg.startsWith("repeat_chain ")) {
                String amountStr = getArgumentArgument(arg);

                String chainToExecute = chainWithoutArgs(oldChain);

                int amount;
                try {
                    amount = Integer.parseInt(amountStr);
                } catch (NumberFormatException t) {
                    return "'" + amountStr + "' is not an integer!";
                }

                if(amount > 20) {
                    return "You can't repeat a chain more than 20 times!";
                }

                newResult = "";
                String prevResult = "";
                for(int i = 0; i < amount; i++) {
                    variables.put(countVar, "" + i);
                    variables.put(prevVar, prevResult);
                    prevResult = (new CommandChain(":" + chainToExecute).execute(ctx) + ((i == amount-1) ? "" : delimiter));
                    newResult += prevResult;
                }
                result = newResult;
            }
            else if(arg.startsWith("override ")) {
                result = getArgumentArgument(arg);
            }
            else if(arg.startsWith("bold")) {
                result = "" + ((char) 2) + result + ((char) 2);
            }
            else if(arg.startsWith("italic")) {
                result = "" + ((char) 29) + result + ((char) 29);
            }
            else if(arg.startsWith("underline")) {
                result = "" + ((char) 31) + result + ((char) 31);
            }
            else if(arg.startsWith("color ") || arg.startsWith("spoiler")) {
                String colorStr;
                if(arg.startsWith("spoiler")) {
                    colorStr = "00,00";
                    result = result.replace("" + (char) 31, "");
                    result = result.replace("" + (char) 2, "");
                    result = result.replace("" + (char) 29, "");
                    result = result.replace("" + (char) 3, "");
                    result = result.replace("" + (char) 15, "");
                } else {
                    String[] color = getArgumentArgument(arg).split(" ");
                    String fgColor, bgColor = "";
                    if (color.length == 2) {
                        fgColor = strToColor(color[0]);
                        bgColor = strToColor(color[1]);
                    } else if (color.length == 1) {
                        fgColor = strToColor(color[0]);
                    } else {
                        return "'color' chain arg takes one or two arguments!";
                    }

                    colorStr = fgColor;
                    if(!bgColor.isEmpty()) colorStr += ("," + bgColor);
                }

                result = "" + ((char) 3) + colorStr + result + ((char) 3);

            }
            else if(arg.startsWith("plain")) {

                result = result.replace("" + (char) 31, "");
                result = result.replace("" + (char) 2, "");
                result = result.replace("" + (char) 29, "");
                result = result.replace("" + (char) 3, "");
            }
            else if(arg.startsWith("discard")) { // discard result
                result = "";
            }
            else if(arg.startsWith("as ")) { // save as variable
                if(arg.length() == 5) {
                    variables.put("", result);
                } else {
                    variables.put(getArgumentArgument(arg), result);
                }
            }
        }



        return specialTrim(result);

    }
}
