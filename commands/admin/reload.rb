# Command to reload module/alias/etc. configs
# Use like "%admin/reload"

require 'java'

ctx.pctx.reload_configs

puts 'Configs successfully reloaded'
