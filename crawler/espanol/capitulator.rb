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
  /&quot;/ => '"',
  /&Aacute;/ => 'Á',
  /&aacute;/ => 'á',
  /&Eacute;/ => 'É',
  /&eacute;/ => 'é',
  /&Iacute;/ => 'Í',
  /&iacute;/ => 'í',
  /&Ntilde;/ => 'Ñ',
  /&ntilde;/ => 'ñ',
  /&Oacute;/ => 'Ó',
  /&oacute;/ => 'ó',
  /&Uacute;/ => 'Ú',
  /&uacute;/ => 'ú',
  /&Uuml;/ => 'Ü',
  /&uuml;/ => 'ü',
  /&laquo;/ => '«',
  /&raquo;/ => '»',
  /&iquest;/ => '¿',
  /&iexcl;/ => '¡'
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
  book = meta.match(/&gt;\s([\w\d\s]+)(&gt;\s)?/)[1].strip

  chapter = meta.match(/([\d\w]+)?"\s\//)
  chapter = chapter.nil? ? '0' : chapter[1]

  next if book.nil? || chapter.nil?

  verses = []
  text.scan /(<p\s+class="?MsoNormal"?[^>]+>\d+\s*(<\/p>)?\s*(<p[^>]+>)?([\W\w\d](?!<\/p>))+)/ do |m|
    verses << [m[0], m[1], m[2]].join('')
  end

  dt_verses = {}
  verses.each do |verse|
    replacements.each  {|regex, replacement| verse.gsub! regex, replacement }
    number = verse.match(/^\d+/).to_s
    verse.sub! /^\d+/, ""

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
  :bible => 'EL LIBRO DEL PUEBLO DE DIOS',
  :url => 'http://www.vatican.va/archive/ESL0506/_INDEX.HTM',
  :credits => {
    :printed_source => "Conferencia Episcopal Argentina",
    :eletronic_transcription_source => 'Suipacha 1034 - Ciudad Autónoma de Buenos Aires',
    :eletronic_transcription_source_url => 'http://www.cea.org.ar'
  },
  :copyright => 'Libreria Editrice Vaticana',
  :books => books
}

File.write File.join('data', 'index.json'), index.to_json