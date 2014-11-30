# Command to execute some arbitrary ruby code
# Use like "%admin/eval 1+2+3+4"

result = eval(ARGV[2..-1] * ' ')
puts " => #{result}"
