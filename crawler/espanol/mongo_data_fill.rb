require File.expand_path('../../mongo_data_fill_header.rb', __FILE__)

index = JSON.parse(File.read File.join('data', 'index.json'))
@bible_id += 1

begin
	@coll_bible.insert({ :_id => @bible_id.to_s, :name => index['bible'], :language => 'spanish', :url => index['url'],
                    :printed_source => index['credits']['printed_source'],
                    :copyright => index['copyright'], :edition => index['edition'],
                    :eletronic_transcription_source => index['credits']['eletronic_transcription_source'],
                    :eletronic_transcription_source_url => index['credits']['eletronic_transcription_source_url'] })

  index['books'].each do |fbook|
    @book_id += 1
    book_name = fbook.sub(/\.json/, '')
    @coll_book.insert({ :_id => @book_id.to_s, :bible_id => @bible_id.to_s, :name => book_name })

    book = JSON.parse(File.read File.join('data', fbook))
    book['chapters'].each do |chapter, verses|
      next if book_name.end_with? chapter
      @chapter_id += 1
      @coll_chapter.insert({ :_id => @chapter_id.to_s, :number => chapter, :book_id => @book_id.to_s })

      verses.each do |verse|
        @verse_id += 1
        text = ((verse[1]['verse'].nil?) ? verse[1] : verse[1]['verse']).gsub(/'/ ,"''").gsub("\\", '\\\\\\\\')
        @coll_verse.insert({ :_id => @verse_id.to_s, :number => verse.first, :text => text, :chapter_id => @chapter_id.to_s })
      end
    end
  end
rescue Exception => ex
  puts ex.to_s
  puts ex.backtrace
end