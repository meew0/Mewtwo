require 'open-uri'
require 'json'

term = ARGV[2..ARGV.length] * '+'

doc = open("http://api.urbandictionary.com/v0/define?term=#{term}")
data = JSON.parse(doc.read)

u000f = [15].pack('U*')

if data['list'].length == 0
  puts "No results found"
else
  obj = data['list'][0]
  print "#{ARGV[0]}, #{obj['word']}: \u0002#{obj['definition']}#{u000f} - " \
        "#{obj['example']} | \u00039+#{obj['thumbs_up']}#{u000f} " \
        "\u00034-#{obj['thumbs_down']}\n"
end
