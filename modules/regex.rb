File.open("logs/#{ARGV[1]}", 'r') do |f|
  f.each_line do |l|
    user = l[/[^>]*/]
    message = l[user.length+1..-1]

    c = ARGV[2..-1] * ' '
    first = c[2..-1][/[^\/]+/]
    second = c[first.length+3..-1][/[^\/]+/]

    if(message =~ Regexp.new(first))
      result = message.gsub(first, second)
    end

    puts "<#{user}> #{result}"
  end
end
