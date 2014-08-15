require 'nokogiri'
require 'open-uri'

page = Nokogiri::HTML(open('http://chainofgood.co.uk/passiton'))
s = page.css('.medium')
compliment = s[rand(s.length)].text

user = ARGV[2] ? "#{ARGV[2]}, " : ''

puts "#{user}#{compliment}"
