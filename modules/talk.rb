# :[^ ]+ PRIVMSG [^ ]+ :.*[Mm][Ee][Ww][Tt][Ww][Oo].*

# This is an example for a module.
# In the first line, the regex for which this module should activate
# is defined (in this case, it matches everything containing "Mewtwo", case-independently)
# In the second line, the triggers are defined. Possible triggers:
#  message
#  pm
#  join
#  nickchange
#  action
#  disabled
# The "disabled" trigger should stand on its own (otherwise it wouldn't make much sense)

msg = ARGV[2..-1].join(' ').downcase  # Get the full message
user = ARGV[0]  # Get the user

# Regexes for greetings, farewells and asking who Mewtwo is
greeting_regex = /o?hai|[\\\/]?[oO0][\\\/]?|(evenin|mornin|afternoon)[g']?|[eh]e(yo?|llo)|hi|sup|greeting[sz]?/i
farewell_regex = /(good)?b(ye|ai)|cya/i
identity_regex = /who (is|are)( you)?/i

# Now check if one of the regexes is found in the message.
if (msg =~ greeting_regex) == 0
  responses = ["What's up?", "How are you today?", "How goes?", "Nice to see you!"]
  puts "Hello #{user}! #{responses.sample}"
end

if (msg =~ farewell_regex) == 0
  responses = ["Hope to see you again!", "See you soon!", "See ya!"]
  puts "Bye #{user} :( #{responses.sample}"
end

if (msg =~ identity_regex) == 0
  puts "Hey there #{user}! I'm a bot programmed by meew0 to do various tasks!"
end
