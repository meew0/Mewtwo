require 'open-uri'
require 'json'

term = ARGV[2..ARGV.length] * ' '
term.gsub!(' ', '+')

doc = open("http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=#{term}")
data = JSON.parse(doc.read)

if data['responseData']['results'].length == 0
  puts "No results found"
else
  obj = data['responseData']['results'][0]
  title = obj['title'].sub('<b>', '').sub('</b>', '')
  print "#{ARGV[0]}, #{obj['url']} - #{title}"
end
