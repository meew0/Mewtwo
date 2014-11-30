path = "commands/#{ARGV[2]}.rb"

if File.exists?(path)
  puts "You can't overwrite an existing command!"
else
  cmd = ARGV[3..-1] * ' '
  cmd.gsub!('%u{', 'user = ARGV[2] ? "#{ARGV[2]}, " : ""; puts "#{user}')
  cmd.gsub!('}u%', '"')
  File.open(path, 'w') do |f|
    f.puts(cmd)
  end
  puts "Command '#{ARGV[2]}' successfully added, try %#{ARGV[2]}"
end
