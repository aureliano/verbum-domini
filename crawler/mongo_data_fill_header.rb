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

def fetch_last_id(collection)
  data = collection.find().sort(:_id => -1).limit(1).first
  (data.nil?) ? 0 : data['_id']
end

@bible_id = fetch_last_id @coll_bible
@book_id = fetch_last_id @coll_book
@chapter_id = fetch_last_id @coll_chapter
@verse_id = fetch_last_id @coll_verse
@annotation_id = fetch_last_id @coll_annotation