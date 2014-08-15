etime = Time.new.to_i
dtime = Time.new
dtime = Time.new(dtime.year, dtime.month, dtime.day)
dtime = etime - dtime.to_i
puts "#{etime} / +#{dtime}"
