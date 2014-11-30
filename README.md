Mewtwo
======

IRC bot using Java and JRuby

# Dependencies

Mewtwo downloads all its dependencies itself during the compiling process. For compiling, however, you need the latest Gradle (http://gradle.org/downloads) and a JVM.

# Compiling

Easily compile Mewtwo using Gradle:

```
$ git clone https://github.com/meew0/Mewtwo.git
$ cd Mewtwo
$ gradle fatJar
```

This will create a file called "`Mewtwo-all-<version>.jar`" in build/libs/. You can either run that file directly (make sure to stay in the same directory as before, as Mewtwo assumes that the current working directory is its root directory!) or use Gradle again:

```
$ gradle run
```

# Configuration

Before running it, it's highly recommended to configure Mewtwo properly. Most of the configuration is done using the file `mewtwo.cfg`, which should be self-explanatory.

Afterwards, you should make yourself an admin by adding `yourNick = true` to the admins.cfg file. (Make sure that you're registered using NickServ, otherwise people can do evil stuff when you're not online!)

# Usage

Mewtwo responds to commands over PM and messages. Make it join a channel using the following command:

```%admin/join #channelName```

Make it leave again by executing this command (in the channel you want it to leave):

```%admin/leave ```

If you get an error message saying that Mewtwo can't let you do this, then you forgot to make yourself an admin (see Configuration).

Some example commands have been included, like `%hello` which just says "Hello world!" to the channel.

# Writing commands

Mewtwo's commands are written in Ruby, so knowing Ruby is required for this.

## Sending something to the channel

First, let's take a look at the example command file "`commands/hello.rb`":

```
# Example command that prints "Hello world!"

puts 'Hello world!'
```

As you can see, writing a command that just prints something is really easy, just print it to the standard output of the command. In fact, this is valid for all commands - to write something to the current channel, write it to the command's stdout.

## Getting information and interacting with more things

Let's take a look at the other example command, `commands/interact.rb`:

```
# Example command that shows interaction with more things than just stdout

# Print some information to stdout
puts "According to ARGV, you're currently in channel ##{ARGV[1]}."
puts "According to ctx, you're currently in channel #{ctx.channel.name}."
puts "According to ARGV, you're #{ARGV[0]}."
puts "According to ctx, you're #{ctx.user.nick}."

# Add something to the context's benchmark
ctx.benchmark 'interact.test'

# Write something to the chat log
ctx.pctx.log.add('This is a test message added to the log by %interact!', '%interact')
```

This one is a little more complicated. We'll go through it step by step:

* Firstly, the current channel is retrieved, once using `ARGV[1]` and once using the context.
* Secondly, the user nick is retrieved, once using `ARGV[0]` and once using the context again.
* Then, the command uses Mewtwo's internal benchmark functionality to benchmark something.
* Finally, it writes a new message to the chat log.

This shows some more basic principles:

* `ARGV[0]` contains the user nick, `ARGV[1]` contains the current channel
* More information is saved in `ctx`

In addition to the user nick and the channel, `ARGV` also contains the arguments for the command. To get just the command's arguments as a Ruby array, use `ARGV[2..-1]`. For an example for this, take a look at `commands/admin/join.rb` or `commands/admin/joinserver.rb`.

## The context

In addition to the things saved in `ARGV`, a variable called `ctx` is also passed to the script. It contains some more information about the user and channel and also contains a variable called `pctx`, which contains even more information. Take a look at [MewtwoContext.java](https://github.com/meew0/Mewtwo/blob/master/src/main/java/meew0/mewtwo/context/MewtwoContext.java) and [PermanentContext.java](https://github.com/meew0/Mewtwo/blob/master/src/main/java/meew0/mewtwo/context/PermanentContext.java) to see what exactly is saved.

## Using gems

You can of course use gems in your script, but the method for installing them is a little more complicated than just a simple `gem install gem1 gem2 ...`.

First, open the file called "`MewtwoGemfile`" in the root directory. It already contains some gems that you can use. If you want to use another one not in here, append its name to the list, save the file and run `gradle buildGems` in the root directory. Now you should be able to use your gem!

## Debugging

Mewtwo doesn't have a fancy debugger for commands, but if there's an error in the ruby script, it will be written to Mewtwo's standard error. You can also directly write to there using `STDERR.puts` in the script.

# Writing modules

TODO

# Useful documentation

* [Calling Java from JRuby](https://github.com/jruby/jruby/wiki/CallingJavaFromJRuby) - you can use other Java classes, not just the contexts, in your commands.
* [Ruby 1.9.3 documentation](http://ruby-doc.org/core-1.9.3/) - as JRuby is only compatible with Ruby 1.9.3, you can't use some newer stuff.

# Contributing

You are absolutely free to open an issue or a pull request if there's something wrong. Try not to break too many things in your pull requests though.
