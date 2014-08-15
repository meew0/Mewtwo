require 'parseconfig'

config = ParseConfig.new('commands.cfg')
max_x = config['spam']['max_x']
max_y = config['spam']['max_y']

term = ARGV[2..ARGV.length] * ' '

x_spam = "#{term} " * rand(max_x.to_i)
y_spam = "#{x_spam} \n" * rand(max_y.to_i)

puts y_spam
