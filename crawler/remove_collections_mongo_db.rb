require 'mongo'
include Mongo

database = 'verbum_domini'
@bible_id = @book_id = @chapter_id = @verse_id = @annotation_id = 0
url = ENV['MONGOLAB_URI'] ||= "mongodb://#{Connection::DEFAULT_HOST}:#{Connection::DEFAULT_PORT}"

client = Connection.from_uri(ENV['MONGOLAB_URI']).db(database)

client.create_collection('bible').remove
client.create_collection('book').remove
client.create_collection('chapter').remove
client.create_collection('verse').remove
client.create_collection('annotation').remove