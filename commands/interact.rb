puts "According to ARGV, you're currently in channel ##{ARGV[1]}."
puts "According to ctx, you're currently in channel #{ctx.channel.name}."
puts "According to ARGV, you're #{ARGV[0]}."
puts "According to ctx, you're #{ctx.user.nick}."