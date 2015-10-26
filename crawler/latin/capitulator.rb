require 'json'

replacements = {
  /<[^>]+>/ => '',
  /&nbsp;/ => '',
  /&quot;/ => '"'
}

ignorable_files = ['.', '..']

if File.exist? 'data'
  Dir.entries('data') {|file| File.delete file }
else
  Dir.mkdir 'data'
end

["VT", "NT"].each do |testament|
  Dir.entries(testament).each do |fname|
    next if ignorable_files.include? fname

    path = File.join(testament, fname)
    text = File.read(path)
    text.encode!('UTF-8', 'binary', invalid: :replace, undef: :replace, replace: '"')
    
    replacements.each  {|regex, replacement| text.gsub! regex, replacement }

    text = text.gsub(/^[\d\s]+$/, '').gsub(/^[A-Z]{2,}.+/, '').gsub(/\s{3,}\d+\s+/, "\n\n\n\n1 ")

    chapter_count = 1
    book = {}
    chapters = text.split /\s{4,}/
    _chapters = {}

    chapters.each do |chapter|
      next if chapter.strip.empty?
      hash = {}
      verses = chapter.scan /\d+[^\d]+/
      
      if verses.empty?
        _chapters['0'] = { :verses => { '1' => chapter.gsub(/\r\n/, ' ').gsub(/\n/, ' ').strip } }
        next
      end

      verses.each do |verse|
        matcher = /^(\d+\w?)([\D]+)/.match verse
        raise "Could not match the verse #{verse}" if matcher.nil?
        
        hash[matcher[1].to_s] = matcher[2].gsub(/\r\n/, ' ').gsub(/\n/, ' ').strip
      end
      
      _chapters[chapter_count.to_s] = { :verses => hash }
      chapter_count += 1
    end

    book[:book] = fname.sub('.html', '')
    book[:chapters] = _chapters
    File.write(File.join('data', fname.sub('.html', '.json')), book.to_json)
  end
end

books = []
Dir.entries('data').each do |fname|
  next if ignorable_files.include? fname
  books << fname
end

index = {
  :bible => 'Nova Vulgata',
  :edition => 'Bibliorum Sacrorum',
  :url => 'http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_index_lt.html',
  :credits => {},
  :copyright => 'Libreria Editrice Vaticana',
  :books => books
}

File.write File.join('data', 'index.json'), index.to_json