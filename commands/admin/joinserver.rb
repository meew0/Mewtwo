# Command to join a server
# Use like "%admin/joinserver irc.xyz.net 6667 MewtwosNickOnTheServer"

require 'java'

name = "bwt-#{Java::Meew0Mewtwo::MewtowMain.bwtCounter}"
bwt = Java::Meew0MewtwoThread::BotWrapperThread.new(Java::Meew0Mewtwo::MewtwoMain.configuration, ARGV[4], ARGV[2], ARGV[3].to_i, name)
thread = java.lang.Thread.new(bwt, name)
Java::Meew0Mewtwo::MewtowMain.bwtCounter += 1
thread.start
