require File.expand_path('../../mongo_data_fill_header.rb', __FILE__)

index = JSON.parse(File.read File.join('data', 'index.json'))
@bible_id += 1

begin
  @coll_bible.insert({ :_id => @bible_id.to_i, :name => index['bible'], :language => 'english', :url => index['url'],
                    :printed_source => index['credits']['printed_source'],
                    :copyright => index['copyright'], :edition => index['edition'],
                    :eletronic_transcription_source => index['credits']['eletronic_transcription_source'],
                    :eletronic_transcription_source_url => index['credits']['eletronic_transcription_source_url'] })

  index['books'].each do |fbook|
    @book_id += 1
    @coll_book.insert({ :_id => @book_id.to_i, :bible_id => @bible_id.to_i, :name => fbook.sub(/\.json/, '') })

    book = JSON.parse(File.read File.join('data', fbook))
    book['chapters'].each do |chapter, hash|
      @chapter_id += 1
      @coll_chapter.insert({ :_id => @chapter_id.to_i, :number => chapter, :book_id => @book_id.to_i })

      hash['verses'].each do |number, verse|
        @verse_id += 1
        
        text = (verse['verse'].nil?) ? verse : verse['verse']
        text = text.gsub(/'/ ,"''").gsub("\\", '\\\\\\\\')
        @coll_verse.insert({ :_id => @verse_id.to_i, :number => number, :text => text, :chapter_id => @chapter_id.to_i })
      end

      hash['annotations'].each do |number, annotation|
        @annotation_id += 1

        text = annotation.gsub(/'/ ,"''").gsub("\\", '\\\\\\\\')
        @coll_annotation.insert({ :_id => @annotation_id.to_i, :number => number, :text => text, :chapter_id => @chapter_id.to_i })
      end
    end
  end
rescue Exception => ex
  puts ex.to_s
  puts ex.backtrace
end