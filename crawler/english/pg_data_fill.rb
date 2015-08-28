require File.expand_path '../../pg_bridge.rb', __FILE__
require 'json'

index = JSON.parse(File.read File.join('data', 'index.json'))
bible_id = next_seq_value 'bible_seq'

begin
  insert 'bible', { :id => bible_id, :name => index['bible'], :language => 'english', :url => index['url'],
                    :printed_source => index['credits']['printed_source'],
                    :copyright => index['copyright'], :edition => index['edition'],
                    :eletronic_transcription_source => index['credits']['eletronic_transcription_source'],
                    :eletronic_transcription_source_url => index['credits']['eletronic_transcription_source_url'] }

  index['books'].each do |fbook|
    book_id = next_seq_value 'book_seq'
    insert 'book', { :id => book_id, :bible_fk => bible_id, :name => fbook.sub(/\.json/, '') }

    book = JSON.parse(File.read File.join('data', fbook))
    book['chapters'].each do |chapter, hash|
      chapter_id = next_seq_value 'chapter_seq'
      insert 'chapter', :id => chapter_id, :number => chapter, :book_fk => book_id

      hash['verses'].each do |number, verse|
        verse_id = next_seq_value 'verse_seq'
        
        text = (verse['verse'].nil?) ? verse : verse['verse']
        text = text.gsub(/'/ ,"''").gsub("\\", '\\\\\\\\')
        insert 'verse', :id => verse_id, :number => number, :text => text, :chapter_fk => chapter_id
      end

      hash['annotations'].each do |number, annotation|
        annotation_id = next_seq_value 'annotation_seq'

        text = annotation.gsub(/'/ ,"''").gsub("\\", '\\\\\\\\')
        insert 'annotation', :id => annotation_id, :number => number, :text => text, :chapter_fk => chapter_id
      end
    end
  end
rescue Exception => ex
  puts ex.to_s
  puts ex.backtrace
end