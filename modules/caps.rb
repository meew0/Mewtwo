# [^%]{1}.*
# disabled

i = 0.0
message = ARGV[2..-1].join(' ')

message.each_char do |c|
  if c != c.downcase
    i += 1
  end
end

if (i / message.length) > 0.75 && !['XD'].include?(message)
  puts "#{ARGV[0]}, please don't talk in caps!"
end
