require 'parseconfig'

config = ParseConfig.new('memos.cfg')

case ARGV[2]
when 'add'
  memo = ARGV[4..-1] * ' '
  config.add(ARGV[3], { rand().to_s => "#{ARGV[0]}:#{memo}" })
  puts "Memo added"
when 'get'
  memos = config[ARGV[0]]

  puts "You have #{memos.length} memos"
  memos.each do |k, v|
    user = v[%r{[^:]*:}].slice(0..-2)
    msg = v[%r{:.*}].slice(1..-1) # yay regex
    puts " <#{user}> #{msg}"
  end
  puts "Use 'memo clear' to clear them"
when 'clear'
  puts "Cleared #{config[ARGV[0]].length} memos"
  config[ARGV[0]].clear
  config.params.delete(ARGV[0])
  config.groups.delete(ARGV[0])
else
  puts "No argument specified, try 'add', 'get' or 'clear'"
end

file = File.open('memos.cfg', 'w')
config.write(file)
file.close
