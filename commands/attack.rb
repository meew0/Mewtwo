require './include/player.rb'

p = Player.new(ARGV[0])

if ARGV[0] == ARGV[2]
  puts "You can't attack yourself!"
end

weapon = ItemsList.item(ARGV[3])

if(!weapon)
  puts "That item doesn't exist!"
  exit
end

unless FightList.fighting?(ARGV[0], ARGV[2])
  puts "Starting new fight between #{ARGV[0]} and #{ARGV[2]}!"
  FightList.start(ARGV[0], ARGV[2])
end

player1 = FightList.player1(ARGV[0], ARGV[2])
player2 = FightList.player2(ARGV[0], ARGV[2])

unless FightList.turn(player1, player2) == ARGV[0]
  puts "It's not your turn!"
  exit
end

damage = 30 #weapon['damage'].to_i

if(weapon['damage'] == '0')
  puts "You can't attack somebody with that!"
  exit
end

if(p.use(weapon))
  p.write
  puts "#{ARGV[0]} attacked #{ARGV[2]} with #{ARGV[3]}!"
  q = Player.new(ARGV[2])
  if(q.damage(damage))
    puts "#{ARGV[2]} now has a health of #{q.health}"
    q.write
    FightList.next_turn(player1, player2)
  else
    puts "#{ARGV[2]} died..."
    q.health = 100
    q.write
    FightList.end(player1, player2)
    puts "The fight between #{ARGV[0]} and #{ARGV[2]} ended! #{ARGV[0]} won!"
  end
else
  puts "You don't have that item!"
  p.write
end

FightList.write
