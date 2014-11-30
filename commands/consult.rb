require 'zlib'
require 'base64'

if ARGV[2..-1].join(' ') == ''
  puts "#{ARGV[0]}, there's no question there"
  exit
end

answers = [
  "Yes!",
  "Yes",
  "Most definitely yes",
  "Yes, I'm sure",
  "Probably yes",
  "Yep.",
  "Likely",
  "Absolutely yes.",
  "Most likely",
  "Probably",
  "Maybe",
  "No",
  "No...",
  "Most likely no",
  "Most definitely no",
  "STOP THINKING ABOUT THIS",
  "Mu",
  "Sssshhh... It'll be all over soon",
  "Unsure",
  "Possibly",
  "Stop asking already",
  "I see what you did there",
  "There's no answer to this question",
  "...",
  "wat",
  "Absolutely.",
  "Not sure",
  "Not sure what to think about this",
  "*starts crying*",
  "There's insufficient information to answer that question.",
  "Ask your nearest alien civilization",
  "This question is so retarded even Amish's bot could answer it.",
  "Definitely"
]
answer = answers[Zlib.crc32(ARGV[2..ARGV.length] * ' ').abs % answers.length]

puts "#{ARGV[0]}, #{answer}"
