path = "commands/#{ARGV[2]}.rb"

if File.exists?(path)
  puts "You can't overwrite an existing command!"
else
  File.open(path, 'w') do |f|
    f.puts(ARGV[3..-1] * ' ')
  end
  puts "Command '#{ARGV[2]}' successfully added, try %#{ARGV[2]}"
end
