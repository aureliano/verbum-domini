require File.expand_path '../../pg_bridge.rb', __FILE__
require 'json'

index = JSON.parse(File.read File.join('data', 'index.json'))
bible_id = next_seq_value 'bible_seq'

begin
  insert 'bible', { :id => bible_id, :name => index['bible'], :language => 'italian', :url => index['url'],
                    :copyright => index['copyright'], :edition => index['edition'] }

  index['books'].each do |fbook|
    book_id = next_seq_value 'book_seq'
    insert 'book', { :id => book_id, :bible_fk => bible_id, :name => fbook.sub(/\.json/, '') }

    book = JSON.parse(File.read File.join('data', fbook))
    book['chapters'].each do |chapter, verses|
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