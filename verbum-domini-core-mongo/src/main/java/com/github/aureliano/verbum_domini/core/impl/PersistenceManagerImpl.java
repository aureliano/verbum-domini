package com.github.aureliano.verbum_domini.core.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.Environments;
import com.github.aureliano.verbum_domini.core.PersistenceManager;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.codec.AnnotationCodec;
import com.github.aureliano.verbum_domini.core.impl.codec.BibleCodec;
import com.github.aureliano.verbum_domini.core.impl.codec.BookCodec;
import com.github.aureliano.verbum_domini.core.impl.codec.ChapterCodec;
import com.github.aureliano.verbum_domini.core.impl.codec.UserCodec;
import com.github.aureliano.verbum_domini.core.impl.codec.VerseCodec;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class PersistenceManagerImpl implements PersistenceManager {

	private MongoClient mongoClient;
	private String databaseName;
	
	public PersistenceManagerImpl() {
		super();
	}

	@Override
	public void shutDown() {
		this.mongoClient.close();
	}

	@Override
	public void startUp() {
		Logger.getLogger("org.mongodb").setLevel(Level.SEVERE);
		Logger.getLogger("com.mongodb").setLevel(Level.SEVERE);
	    
		AppConfiguration configuration = AppConfiguration.instance();
		this.databaseName = configuration.getProperty("database.name");
		if (this.databaseName == null) {
			throw new VerbumDominiException("Property database.name not found in app-configuration.properties file.");
		}
		
		String connectionUrl = configuration.getProperty("mongodb.connection.url");
		
		if (Environments.TEST.equals(configuration.getEnvironment())) {
			this.databaseName = "verbum_domini_test";
			connectionUrl = "mongodb://localhost";
		} else if (Environments.PRODUCTION.equals(configuration.getEnvironment())) {
			this.databaseName = System.getenv("MONGOLAB_DB_NAME");
			connectionUrl = System.getenv("MONGOLAB_URI");
		}
		
		MongoClientOptions.Builder options = this.buildOptions(configuration);
		MongoClientURI uri = new MongoClientURI(connectionUrl, options);
		
		this.mongoClient = new MongoClient(uri);
	}
	
	public <T> MongoCollection<T> fetchCollection(Class<T> beanClass) {
		String entity = beanClass.getSimpleName().replaceFirst("Bean(Impl)?$", "").toLowerCase();
		return this.mongoClient.getDatabase(this.databaseName).getCollection(entity, beanClass);
	}
	
	public MongoDatabase fetchDatabase() {
		return this.mongoClient.getDatabase(this.databaseName);
	}
	
	private MongoClientOptions.Builder buildOptions(AppConfiguration configuration) {
		MongoClientOptions.Builder builder = MongoClientOptions.builder();
		if (configuration.getProperty("mongodb.connection.max.per.host") != null) {
			builder.connectionsPerHost(Integer.parseInt(configuration.getProperty("mongodb.connection.max.per.host")));
		}
		
		if (configuration.getProperty("mongodb.connection.min.per.host") != null) {
			builder.minConnectionsPerHost(Integer.parseInt(configuration.getProperty("mongodb.connection.min.per.host")));
		}
		
		if (configuration.getProperty("mongodb.connection.life.time") != null) {
			builder.maxConnectionLifeTime(Integer.parseInt(configuration.getProperty("mongodb.connection.life.time")));
		}
		
		if (configuration.getProperty("mongodb.connection.timeout") != null) {
			builder.connectTimeout(Integer.parseInt(configuration.getProperty("mongodb.connection.timeout")));
		}
		
		return builder.codecRegistry(this.buildCodecRegistries());
	}
	
	private CodecRegistry buildCodecRegistries() {
		Codec<Document> defaultDocumentCodec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
			MongoClient.getDefaultCodecRegistry(),
			CodecRegistries.fromCodecs(
				new BibleCodec(defaultDocumentCodec),
				new BookCodec(defaultDocumentCodec),
				new ChapterCodec(defaultDocumentCodec),
				new VerseCodec(defaultDocumentCodec),
				new AnnotationCodec(defaultDocumentCodec),
				new UserCodec(defaultDocumentCodec)
			)
		);
		
		return codecRegistry;
	}
}