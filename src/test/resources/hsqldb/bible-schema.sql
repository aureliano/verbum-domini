CREATE TABLE bible(
	id INTEGER PRIMARY KEY NOT NULL,
	name VARCHAR(100) NOT NULL,
	language VARCHAR(50) NOT NULL,
	url VARCHAR(200),
	edition VARCHAR(100),
	printed_source VARCHAR(300),
	eletronic_transcription_source VARCHAR(200),
	eletronic_transcription_source_url VARCHAR(200),
	copyright VARCHAR(200)
);