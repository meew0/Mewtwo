package meew0.mewtwo.commands;

import meew0.mewtwo.MewtwoListener;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandInsult implements ICommand {
	
	public static final String[] insults = {
		"Whoever's willing to fuck you is just too lazy to jerk off.",
		"You know, I'm really trying to see things your way, but I just can't stick my head that far up my ass.",
		"It must be difficult for you, exhausting your entire vocabulary in one sentence.",
		"Is your ass jealous of all the shit that comes out of your mouth?",
		"You look like you're going to spend your life having one epiphany after another, always thinking you've finally figured out what's holding you back, and how you can finally be productive and creative and turn your life around. But nothing will ever change. That cycle of mediocrity isn't due to some obstacle. It's who you are. The thing standing in the way of your dreams is that the person having them is you.",
		"You're here because your parents are pro-life.",
		"Are you and your dick having a competition to see who can disappoint me the most?",
		"You're the reason they only have McRibs a certain time of the year.",
		"Yo mamma is so ugly her portraits hang themselves",
		"Go fuck a landmine",
		"Go headbutt a bullet",
		"I'm not saying you're fat, but it looks like you were poured into your clothes and forgot to say \"when\"",
		"I hope the rest of your day is as pleasant as you are.",
		"I bet you weren't as good at blowing your uncle the first couple of times.",
		"It looks like your face was on fire and someone put it out with a wet brick.",
		"Did you fall from Heaven? Because it looks like you hit the ground face-first.",
		"I don't know what your problem is, but I'll bet its hard to pronounce.",
		"You know that fear that you have deep down in the back of your mind, wondering if everybody hates you, if they are all putting on a facade just to deal with you until you leave, if they hate being around you, and talk about you behind your back, well they do and its entirely because of the person you are.",
		"Your birth certificate is an apology from the abortion clinic.",
		"Hey laser lips, your mother was a snow blower!",
		"The only excitement that you get is when your finger breaks through the toilet paper when you wipe your ass.",
		"Your mother is so old she has a separate entrance for black dicks",
		"I'm not saying you'd suck a dick, but you would hold it in your mouth until the swelling went down.",
		"You're the personification of comic sans.",
		"Take a walk until your hat floats.",
		"I dont have the time or the crayons to explain stuff to you",
		"Are you in first grade or is your dick always that small? Oh you are in first grade.",
		"If you were to fall into a freezing river while walking home one night, that, I suppose, would be unfortunate; if someone were to pull you out, that would be a tragedy.",
		"You think you're hot shit but you're really just cold diarrhea.",
		"I hope you realize everyone's just putting up with you.",
		"Your face looks like someone tried to put out a forest fire with a screwdriver",
		"The smartest thing that's ever come from your mouth was my dick",
		"Stop crying, you look so fucking fat when you cry.",
		"This is why everyone talks behind your back",
		"Everyone who loves you is wrong.",
		"Is your ass jealous of the amount of shit that just came out of your mouth?",
		"Have you ever considered suing your mother for drinking while she was pregnant with you?",
		"I wish you were retarded, because then you'd at least have an excuse.",
		"You make sunny days less bright.",
		"Before I met you, I was pro life.",
		"I'd call you a cunt, but you lack both the warmth and the depth",
		"It looks like your face caught fire and someone tried to put it out with a bicycle chain.",
		"Letting you live was medical malpractice",
		"You have the intelligence of a stillborn.",
		"Your mother should have swallowed you",
		"If you want my comeback go look in your mother's mouth.",
		"A douche of your magnitude could cleanse the vagina of a whale",
		"Your IQ doesn't make a respectable earthquake",
		"You are a walking advertisement for the benefits of birth control.",
		"You're so ugly your birth certificate is an apology letter from the condom company.",
		"If you were any more inbred, you'd be a sandwich.",
		"If my dogs face was as ugly as yours, I'd shave his ass and teach him to walk backwards.",
		"Why don't you go outside and play a game of hide-and-go-fuck-yourself.",
		"Somewhere... out there in the world... there is a tree tirelessly producing oxygen for you... I believe you owe it an apology.",
		"Save your breath, you'll need to to blow up your girlfriend later",
		"You're about as useful as Anne Frank's drum kit.",
		"That's got to be the dumbest thing anyone's ever said in the history of fuck",
		"I'd like to see things from your point of view but I can't seem to get my head that far up my ass.",
		"With a face like yours, I'd be careful who and what I make fun of",
		"Alright, give me your phone number and Ill call you when I give a shit",
		"You're free marketing for condom companies",
		"Is your head up your ass for the warmth?",
		"No wonder everyone talks about you behind your back.",
		"It looks like your face was on fire, and someone tried to put it out with a fork.",
		"I didn't forgive you because you deserve it, I forgave you because I don't want to end up bitter and alone, like you.",
		"Does your vagina ever get jealous of you being a huge cunt",
		"What's the difference between 2 dicks and a joke? You can't take a joke.",
		"I hope you outlive your children"
	};

	@Override
	public String getCommandName() {
		return "insult";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		String insult = "";
		if(args.length > 0) insult += (args[0] + ", ");
		if(args.length > 1) insult += insults[Integer.parseInt(args[1])];
		else {
			insult += insults[MewtwoListener.rnd.nextInt(insults.length)];
		}
		event.getChannel().send().message(insult);
	}

	@Override
	public String getHelpEntry() {
		return "insult|i [person] [n]: Insults the whole channel (or the specified person) with a random insult or the one with the index n";
	}

	@Override
	public String getAlias() {
		return "i";
	}

}
