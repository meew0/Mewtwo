require 'nokogiri'
require 'open-uri'
require 'parseconfig'

config = ParseConfig.new('commands.cfg')
use_rff = config['randomfact']['use_rff']

page = Nokogiri::HTML(open(use_rff ? 'http://randomfunfacts.com'
                                   : 'http://randomfactgenerator.net'))

fact = 'An error has occurred while trying to get a random fact!'

if(use_rff)
  fact = page.css('strong i').text
else
  fact = page.css('#f')[1].css('#z')[1].text
end

user = ARGV[2] ? "#{ARGV[2]}, " : ''

puts "#{user}#{fact}"
