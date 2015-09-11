require 'mongo'
include Mongo

database = 'verbum_domini'
url = ENV['MONGOLAB_URI'] ||= "mongodb://#{Connection::DEFAULT_HOST}:#{Connection::DEFAULT_PORT}"

@client = Connection.from_uri(ENV['MONGOLAB_URI']).db(database)

require 'json'

def load_collection(name)
  coll = @client.collection name
  (coll.nil?) ? @client.create_collection(name) : coll
end

@coll_bible = load_collection 'bible'
@coll_book = load_collection 'book'
@coll_chapter = load_collection 'chapter'
@coll_verse = load_collection 'verse'
@coll_annotation = load_collection 'annotation'

@bible_id = @coll_bible.find().count()
@book_id = @coll_book.find().count()
@chapter_id = @coll_chapter.find().count()
@verse_id = @coll_verse.find().count()
@annotation_id = @coll_annotation.find().count()