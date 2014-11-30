# ([a-zA-Z0-9\-_]+[,:][ ]?)?s/([^/]*)/([^/]*)(/(g)?)?
# message

str = ARGV[2..-1] * ' '

/((?<name>[a-zA-Z0-9\-_]+)[,:][ ]?)?s\/(?<first>[^\/]*)\/(?<second>[^\/]*)(\/(?<g>g)?)?/ =~ str

found = false

ctx.pctx.log.messages.to_a.each do |m|
  next if name && m.nick != name
  firstRegex = Regexp.new(first)
  msg = m.message
  if m.message =~ firstRegex
    next if msg =~ /([a-zA-Z0-9\-_]+[,:][ ]?)?s\/([^\/]*)\/([^\/]*)(\/(g)?)?/
    repl = msg.gsub(firstRegex, second) if g
    repl = msg.sub(firstRegex, second) unless g
    puts "#{name || ARGV[0]}: <#{m.nick}> #{repl}"
    found = true
    break
  end
end

puts "Couldn't find a message that matches #{first}" unless found
