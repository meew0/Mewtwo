accents = {
  'Canada' => "How's that maple syrup doin' for ya eh?",
  'America' => "'MERICA THE LAND OF THE FREE AND McDONALDS",
  'Spain' => '*starts dancing the waltz with %user%*',
  'Britain' => 'I do say old chap, would you fancy another crumpet?',
  'France' => 'I like ze baguette and ze mime shows',
  'Italy' => 'Mamma Mia, thats-a-spicy meatball!',
  'Arabia' => 'حتى جوجل هو أفضل صديق لديك؟ جيدة.',
  'India' => 'Hello my friend my name is Rakesh and welcome to EA call center',
  'Japan' => "Its not like i don't want to be noticed...b-baka!",
  'Australia' => "'ello Gov'nor!",
  'South' => "*points shotgun at %user%* We don't like your kind in these parts..."
}

puts accents[ARGV[2]].sub('%user%', ARGV[0])
