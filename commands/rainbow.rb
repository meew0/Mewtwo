colors = [
	'04',
	'05',
	'08',
	'09',
	'12',
	'13'
]

ARGV[2..ARGV.length].join(' ').each_char.each_with_index do
  |c, i| print [3].pack('U*') + colors[i % colors.length] + c
end
