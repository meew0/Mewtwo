# ([a-zA-Z0-9\-_]+[,:][ ]?)?s/([^/]*)/([^/]*)(/(g)?)?
# message

# Module that allows users to replace stuff from earlier messages using sed regex syntax.
# Also allows users to specify whose messages to replace using "meew0, s///" syntax

# Get arguments
str = ARGV[2..-1] * ' '

# Get named groups from arguments
/((?<name>[a-zA-Z0-9\-_]+)[,:][ ]?)?s\/(?<first>[^\/]*)\/(?<second>[^\/]*)(\/(?<g>g)?)?/ =~ str

found = false

# For each message, do...
ctx.pctx.log.messages.to_a.each do |m|
  # Skip this one if a name has been specified but the name is wrong
  next if name && m.nick != name

  # Create a regex object from the first regex in the syntax
  firstRegex = Regexp.new(first)
  msg = m.message

  # If the message matches the regex...
  if msg =~ firstRegex
    # Skip this one if the message is a regex itself (don't want to replace previous regexes)
    next if msg =~ /([a-zA-Z0-9\-_]+[,:][ ]?)?s\/([^\/]*)\/([^\/]*)(\/(g)?)?/

    # Allow usage of the global flag, do actual replacement
    repl = msg.gsub(firstRegex, second) if g
    repl = msg.sub(firstRegex, second) unless g

    # Post the replacement to the channel and break out of the loop
    puts "#{name || ARGV[0]}: <#{m.nick}> #{repl}"
    found = true
    break
  end
end

# If not found, send message to user
puts "Couldn't find a message that matches #{first}" unless found
