require './include/player.rb'

p = Player.new(ARGV[0])

cookie = ItemsList.item('Cookie')
supercookie = ItemsList.item('SuperCookie')

if(p.use(supercookie))
  p.write
  q = Player.new(ARGV[2])
  q.give(cookie)
  q.write
else
  if(p.take(cookie))
    p.write
    q = Player.new(ARGV[2])
    q.give(cookie)
    q.write
    puts "Hey, #{ARGV[2]}! You! You got a cookie from #{ARGV[0]}!"
  else
    puts "You don't have any cookies! :("
    p.write
  end
end
