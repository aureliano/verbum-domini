require 'mechanize'
# http://www.vatican.va/archive/ESL0506/_INDEX.HTM

Dir.mkdir 'pages' unless File.exist? 'pages'

mechanize = Mechanize.new
page = mechanize.get 'http://www.vatican.va/archive/ESL0506/__P1.HTM'

loop do
  link = page.link_with :text => 'Siguiente'

  break if link.nil?
  page = link.click

  matcher = /<meta\sname="?part"?\s+content="?[\d\w\s]+&gt;([\d\w\s]+)(&gt;\s)?([\d\w]+)?"?/.match page.body
  next if matcher.nil?
  puts "#{matcher[1].strip} => #{matcher[3]}"

  File.write File.join('pages', page.filename), page.content
end