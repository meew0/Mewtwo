# Command to join channel
# Use like "%admin/join #channel1 #channel2"

ARGV[2..-1].each do |c|
	ctx.bot.send_irc.join_channel(c)
end
