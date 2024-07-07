# This script is run when the 'reminder' timer is triggered.
# See commands/reminder.rb for an example of how to setup a timer.
IRC_BOLD = 2.chr
message = ctx.get 'message'
puts "#{ctx.user.nick}, I am hereby reminding you#{message.nil? ? "." : " of #{IRC_BOLD}#{message}#{IRC_BOLD}."}"
