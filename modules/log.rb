# [^%]{1}.*
# none

File.open("logs/#{ARGV[1]}", 'a') do |f|
  f.puts "#{ARGV[0]}>#{ARGV[2..-1].join(' ')}"
end
