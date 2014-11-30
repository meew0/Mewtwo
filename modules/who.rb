# [Ww]ho (is|are)[ ]?(you)?[,]? Mewtwo[\?]?
# message,pm

require 'parseconfig'

config = ParseConfig.new('mewtwo.cfg')
prefix = config['prefix']

puts "I'm Mewtwo, an IRC bot programmed by meew0."
puts "Try '#{prefix}help' for help"
