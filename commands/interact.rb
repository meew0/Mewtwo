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
