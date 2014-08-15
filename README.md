Mewtwo
======

An experimental IRC bot, mainly to learn how it works.

# Compiling

Mewtwo's core is written in Java and requires the following libraries:

* Commons BeanUtils
* Commons Codec
* Commons Collections
* Commons Configuration
* Commons Digester
* Commons JEXL
* Commons JXPath
* Commons Lang3
* Commons Lang
* Commons Logging
* Commons VFS2
* Guava 17
* PircBotX 2.0.1
* Slf4J API
* Slf4J Simple

(Some of the libraries may not actually be required, but this works for me)

The commands are written in Ruby and require the following gems:

* parseconfig
* nokogiri

# Usage

By default, Mewtwo will connect to `irc.esper.net`. You can change this in its
config (mewtwo.cfg). This would be a config for the default settings:

```
nick = Mewtwo
server = irc.esper.net
login = Mewtwo
password =
prefix = %
```

Note that the `prefix` field must always be set, because some commands rely on
it being set and don't have a default value.

As soon as Mewtwo has joined the server, you can make it join a channel by PMing
it with either:

```
%join #channel
```
or
```
%joinall #channel1 #channel2 #channel3
```

Note that you need admin rights to make Mewtwo join a channel. You can give
admin rights to yourself by adding `yournick = true` to the `admins.cfg` config.
