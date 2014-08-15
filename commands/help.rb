require 'parseconfig'

config = ParseConfig.new('help.cfg')

if ARGV[2]
  puts config[ARGV[2]]
else
  config.params.each do |k,v|
    print k + ' '
  end
  puts
end
