require 'mechanize'
# http://www.vatican.va/archive/ENG0839/_INDEX.HTM

Dir.mkdir 'pages' unless File.exist? 'pages'

mechanize = Mechanize.new
page = mechanize.get 'http://www.vatican.va/archive/ENG0839/__P1.HTM'

loop do
  link = page.link_with :text => 'Next'

  break if link.nil?
  page = link.click

  matcher = /<meta\sname="?part"?\s+content="?[\d\w\s]+&gt;([\d\w\s]+)(&gt;\s)?(C?hapter\s[\d\w]+)?"?/.match page.body
  next if matcher.nil?
  puts "#{matcher[1].strip} => #{matcher[3]}"

  File.write File.join('pages', page.filename), page.content
end