# Script to resolve the absolute path for a specific instance

script_dir = File.expand_path(File.dirname(__FILE__))  # Get the directory the script is saved in
require script_dir + '/lib/gems/parseconfig-1.0.6/lib/parseconfig.rb'
resolve_cfg = ParseConfig.new(script_dir + '/instances.cfg')

puts File.expand_path(resolve_cfg[ARGV[0]])
