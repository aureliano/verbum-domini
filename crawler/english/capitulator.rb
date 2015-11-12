require 'json'

def prepare_dir(dir)
  if File.exist? dir
    Dir.entries(dir) {|file| File.delete file }
  else
    Dir.mkdir dir
  end
end

def prepare_json(book, chapter, verses, annotations)
  hash = {
    :book => book,
    :chapter => chapter.to_s,
    :verses => verses,
    :annotations => annotations
  }

  hash.to_json
end

replacements = {
  /<[^>]+>/ => '',
  /&nbsp;/ => '',
  /&quot;?/ => '"'
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

  chapter = meta.match(/(C?hapter)\s+([\d\w]+)?/)
  chapter = chapter.nil? ? '0' : chapter[2]

  next if book.nil? || chapter.nil?

  verses = []
  text.scan /(<p\s+class="?MsoNormal"?>\d+\s*(<\/p>)?\s*(<p[^>]+>)?([\W\w\d](?!<\/p>))+)/ do |m|
    verses << [m[0], m[1], m[2]].join('')
  end

  dt_verses = {}
  verses.each do |verse|
    replacements.each  {|regex, replacement| verse.gsub! regex, replacement }
    number = verse.match(/^\d+/).to_s
    verse.sub! /^\d+/, ""
    annotations = verse.scan(/(\d+)/).flatten

    verse.gsub! /\d+/, ''
    verse.gsub! /\s{2,}/, ' '
    verse.strip!

    dt_verses[number] = { :verse => verse, :annotations => annotations }
  end

  if dt_verses.empty? && chapter == '0'
    _text = text.dup
    verses = []
    _text.scan /(<p[^>]*>([\W\wd](?!<\/p>))+)/ do |m|
      verses << m[0]
    end

    content = []
    verses.each do |verse|
      replacements.each  {|regex, replacement| verse.gsub! regex, replacement }
      verse.gsub! /\s{2,}/, ' '
      verse.gsub! /&nbsp;?/, ''
      verse.gsub! /\s?&#8658;\s?/, ' '
      verse.gsub! /<\/b/, ''
      verse.gsub! "\n", ''
      verse.strip!

      content << verse
    end
    
    dt_verses['1'] = content.join("\n")
  end

  dt_annotations = {}
  annotations = []
  text.scan(/(<a\sname="?\$[^>]+>\d+([\W\w\d](?!<\/p>))+)/) {|m| annotations << m[0] }

  annotations.each do |annotation|
    replacements.each  {|regex, replacement| annotation.gsub! regex, replacement }
    number = annotation.match(/^\d+/).to_s

    annotation.sub! /^\d+/, ''
    annotation.gsub! /\s{2,}/, ' '
    annotation.gsub! /\s?&#8658;\s?/, ' '
    annotation.gsub!(/<\/\w+/, '')
    annotation.strip!

    dt_annotations[number] = annotation
  end

  path = File.join('books', book)
  prepare_dir path

  File.write File.join(path, "#{chapter}.json"), prepare_json(book, chapter, dt_verses, dt_annotations)
end

books = []
Dir.entries('books').each do |dir|
  next if ignorable_files.include? dir
  data = { :book => dir, :chapters => {} }

  Dir.entries(File.join 'books', dir).each do |fchapter|
    next if ignorable_files.include? fchapter

    hash = JSON.parse(File.read File.join('books', dir, fchapter))
    data[:chapters][hash['chapter']] = { :verses => hash['verses'], :annotations => hash['annotations'] }
  end

  name = "#{data[:book]}.json"
  books << name
  File.write File.join('data', name), data.to_json
end

index = {
  :bible => 'THE NEW AMERICAN BIBLE',
  :url => 'http://www.vatican.va/archive/ENG0839/_INDEX.HTM',
  :credits => {
    :printed_source => "United States Conference of Catholic Bishops\n3211 4th Street, N.E., Washington, DC 20017-1194 (202) 541-3000\nNovember 11, 2002 Copyright (c) by United States Conference of Catholic Bishops",
    :eletronic_transcription_source => 'United States Conference of Catholic Bishops',
    :eletronic_transcription_source_url => 'http://www.usccb.org/'
  },
  :copyright => 'Libreria Editrice Vaticana',
  :books => books
}

File.write File.join('data', 'index.json'), index.to_json