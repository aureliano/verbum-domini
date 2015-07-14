require 'mechanize'
# http://www.vatican.va/archive/ITA0001/_INDEX.HTM

Dir.mkdir 'pages' unless File.exist? 'pages'

mechanize = Mechanize.new
page = mechanize.get 'http://www.vatican.va/archive/ITA0001/__P1.HTM'
first_read = true

loop do
  unless first_read
    link = page.link_with :text => 'Successivo'

    break if link.nil?
    page = link.click
  end

  matcher = /<meta\sname="?part"?\s+content="?([\d\w\s()&;]+)&gt;\s([\d\w]+)?"?/.match page.body
  next if matcher.nil?
  puts "#{matcher[1].strip} => #{matcher[2]}"
  first_read = false

  File.write File.join('pages', page.filename), page.content
end