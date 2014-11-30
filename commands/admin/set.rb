# Command to set something in a config file
# Use like "%admin/set path/to/cfgfile.group.key value"

require 'parseconfig'

path = ARGV[2].split('.')
value = ARGV[3..-1] * ' '

config = ParseConfig.new("#{path[0]}.cfg")

if path.length == 2
  config.params[path[1]] = value
  puts "Option successfully set"
elsif path.length == 3
  config[path[1]][path[2]] = value
  puts "Option successfully set"
else
  puts "Not a valid path"
end

file = File.open("#{path[0]}.cfg", 'w')
config.write(file)
file.close
