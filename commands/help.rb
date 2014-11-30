require 'parseconfig'

config = ParseConfig.new('help.cfg')


if ARGV[2]
  puts config[ARGV[2]]
else
  print "#{ARGV[0]}, these are the commands I know: (do help [command] for help for a specific command) "
  config.params.each do |k,v|
    print k + ' '
  end
  puts
end
