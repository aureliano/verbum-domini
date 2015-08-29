require 'mongo'
include Mongo

database = 'verbum_domini'
@bible_id = @book_id = @chapter_id = @verse_id = @annotation_id = 0
url = ENV['MONGOLAB_URI'] ||= "mongodb://#{Connection::DEFAULT_HOST}:#{Connection::DEFAULT_PORT}"

client = Connection.from_uri(ENV['MONGOLAB_URI']).db(database)

require 'json'

@coll_bible = client.create_collection 'bible'
@coll_book = client.create_collection 'book'
@coll_chapter = client.create_collection 'chapter'
@coll_verse = client.create_collection 'verse'
@coll_annotation = client.create_collection 'annotation'