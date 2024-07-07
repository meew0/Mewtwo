data = ARGV[1..-1]
exit unless data.size >= 2

if data.size > 2
  message = data[2..-1].join(' ')
else
  message = nil
end

time = data[1]
match_data = /(\d+\.?\d*)(h|m|s)/.match(time)

unless match_data
  puts "Invalid time format."
  exit
end

amount = match_data[1].to_f
seconds = 0
IRC_BOLD = 2.chr

case match_data[2]
when "h"
  puts "I will remind you #{message.nil? ? "" : "of that "}in #{IRC_BOLD}#{amount}#{IRC_BOLD} hours."
  seconds = amount * 3600
when "m"
  puts "I will remind you #{message.nil? ? "" : "of that "}in #{IRC_BOLD}#{amount}#{IRC_BOLD} minutes."
  seconds = amount * 60
when "s"
  puts "I will remind you #{message.nil? ? "" : "of that "}in #{IRC_BOLD}#{amount}#{IRC_BOLD} seconds."
  seconds = amount
end

# `add_timer` returns a new object of the same type as `ctx`.
# It can be used to put data that will be present when the timer is executed.
# `add_timer` receives a Java `Instant`, which can be converted from a Ruby `Time`.
delegate = ctx.add_timer(:reminder, Time.now + seconds)
delegate.put("message", message) unless message.nil?
