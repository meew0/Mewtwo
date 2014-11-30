# In this directory, files are defined that can be included by modules or commands
# but aren't regarded modules or commands themselves

class ARGVWrapper
  attr_reader :argv

  def initialize(argv)
    @argv = argv
  end

  def user
    @argv[0]
  end

  def channel
    @argv[1]
  end

  def message
    @argv[2..-1] * ' '
  end
end
