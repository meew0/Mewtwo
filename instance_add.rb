# Script to add the path for a specific instance to the config

script_dir = File.expand_path(File.dirname(__FILE__))  # Get the directory the script is saved in
require script_dir + '/lib/gems/parseconfig-1.0.6/lib/parseconfig.rb'
resolve_cfg = ParseConfig.new(script_dir + '/instances.cfg')

resolve_cfg.add(ARGV[0], File.expand_path(ARGV[1..-1] * ' '))

# Write file to disk after changes were made
file = File.open(script_dir + '/instances.cfg', 'w')
resolve_cfg.write(file)
file.close
