# encoding: utf-8

require 'json'

def prepare_dir(dir)
  if File.exist? dir
    Dir.entries(dir) {|file| File.delete file }
  else
    Dir.mkdir dir
  end
end

def prepare_json(book, chapter, verses)
  hash = {
    :book => book,
    :chapter => chapter.to_s,
    :verses => verses
  }

  hash.to_json
end

replacements = {
  /<[^>]+>/ => '',
  /&nbsp;/ => '',
  /&quot;?/ => '"',
  /&Agrave;/ => 'À',
  /&agrave;/ => 'à',
  /&Aacute;/ => 'Á',
  /&aacute;/ => 'á',
  /&Egrave;/ => 'È',
  /&egrave;/ => 'è',
  /&Eacute;/ => 'É',
  /&eacute;/ => 'é',
  /&Igrave;/ => 'Ì',
  /&igrave;/ => 'ì',
  /&Iacute;/ => 'Í',
  /&iacute;/ => 'í',
  /&Ograve;/ => 'Ò',
  /&ograve;/ => 'ò',
  /&Oacute;/ => 'Ó',
  /&oacute;/ => 'ó',
  /&Ugrave;/ => 'Ù',
  /&ugrave;/ => 'ù',
  /&Uacute;/ => 'Ú',
  /&uacute;/ => 'ú',
  /&laquo;/ => '«',
  /&raquo;/ => '»'
}

ignorable_files = ['.', '..']

prepare_dir 'data'
prepare_dir 'books'

Dir.entries('pages').each do |fname|
  next if ignorable_files.include? fname

  fname = File.join 'pages', fname
  text = File.read fname
  text.encode!('UTF-8', 'binary', invalid: :replace, undef: :replace, replace: '"')

  meta = text.match(/<meta\sname="?part"?[^>]+/).to_s
  meta.gsub! /amp;/, ''
  replacements.each  {|regex, replacement| meta.gsub! regex, replacement }
  book = meta.match(/content="([\p{L}\d\s()]+)(&gt;\s)?/)[1].strip

  chapter = meta.match(/([\d\w]+)?"\s\//)
  chapter = chapter.nil? ? '0' : chapter[1]

  chapter = '8' if book == 'Deuteronomio' && chapter.start_with?('deserto')
  chapter = '4' if book == 'Atti degli Apostoli' && chapter.start_with?('sinedrio')

  next if book.nil? || chapter.nil?

  verses = []
  text.scan /(\[\d+\]([\W\w\d](?!<br>))+)/ do |m|
    verses << m[0]
  end

  dt_verses = {}
  verses.each do |verse|
    replacements.each  {|regex, replacement| verse.gsub! regex, replacement }
    number = verse.match(/^\[([\d\w]+)\]\s*/)[1]
    verse.sub! /^\[([\d\w]+)\]\s+/, ""

    verse.gsub! /\d+/, ''
    verse.gsub! /\s{2,}/, ' '
    verse.gsub! /\n/, ' '
    verse.strip!

    dt_verses[number] = verse
  end

  path = File.join('books', book)
  prepare_dir path

  File.write File.join(path, "#{chapter}.json"), prepare_json(book, chapter, dt_verses)
end

books = []
Dir.entries('books').each do |dir|
  next if ignorable_files.include? dir
  data = { :book => dir, :chapters => {} }

  Dir.entries(File.join 'books', dir).each do |fchapter|
    next if ignorable_files.include? fchapter

    hash = JSON.parse(File.read File.join('books', dir, fchapter))
    data[:chapters][hash['chapter']] = { :verses => hash['verses'] }
  end

  name = "#{data[:book]}.json"
  books << name
  File.write File.join('data', name), data.to_json
end

index = {
  :bible => 'LA SACRA BIBBIA',
  :edition => 'CEI',
  :url => 'http://www.vatican.va/archive/ITA0001/_INDEX.HTM',
  :credits => {},
  :copyright => 'Conferenza Episcopale Italiana',
  :books => books
}

File.write File.join('data', 'index.json'), index.to_json