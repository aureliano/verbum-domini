require File.expand_path '../../pg_bridge.rb', __FILE__
require 'json'

index = JSON.parse(File.read File.join('data', 'index.json'))
bible_id = next_seq_value 'bible_seq'

begin
  insert 'bible', { :id => bible_id, :name => index['bible'], :language => 'spanish', :url => index['url'],
                    :printed_source => index['credits']['printed_source'],
                    :copyright => index['copyright'], :edition => index['edition'],
                    :eletronic_transcription_source => index['credits']['eletronic_transcription_source'],
                    :eletronic_transcription_source_url => index['credits']['eletronic_transcription_source_url'] }

  index['books'].each do |fbook|
    book_id = next_seq_value 'book_seq'
    book_name = fbook.sub(/\.json/, '')
    insert 'book', { :id => book_id, :bible_fk => bible_id, :name => book_name }

    book = JSON.parse(File.read File.join('data', fbook))
    book['chapters'].each do |chapter, verses|
      next if book_name.end_with? chapter
      chapter_id = next_seq_value 'chapter_seq'
      insert 'chapter', :id => chapter_id, :number => chapter, :book_fk => book_id

      verses.each do |verse|
        verse_id = next_seq_value 'verse_seq'
        text = ((verse[1]['verse'].nil?) ? verse[1] : verse[1]['verse']).gsub(/'/ ,"''").gsub("\\", '\\\\\\\\')
        insert 'verse', :id => verse_id, :number => verse.first, :text => text, :chapter_fk => chapter_id
      end
    end
  end
rescue Exception => ex
  puts ex.to_s
  puts ex.backtrace
end