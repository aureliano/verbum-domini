package com.github.aureliano.verbum_domini.core.impl.codec;

import org.bson.BsonInt32;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;

import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoHelper;

public class BookCodec implements CollectibleCodec<BookBeanImpl> {

	private Codec<Document> documentCodec;
	
	public BookCodec() {
		 this.documentCodec = new DocumentCodec();
	}
	
	public BookCodec(Codec<Document> codec) {
		 this.documentCodec = codec;
	}
	
	@Override
	public void encode(BsonWriter writer, BookBeanImpl bean, EncoderContext context) {
		Document document = new Document();

		document.put("_id", bean.getId());
		if ((bean.getBible() == null) || (bean.getBible().getId() == null)) {
			throw new VerbumDominiException("Bible field must not be null nor have null id.");
		}
		document.put("bible_id", bean.getBible().getId());
		document.put("name", bean.getName());
		
		this.documentCodec.encode(writer, document, context);
	}

	@Override
	public Class<BookBeanImpl> getEncoderClass() {
		return BookBeanImpl.class;
	}

	@Override
	public BookBeanImpl decode(BsonReader reader, DecoderContext context) {
		Document document = this.documentCodec.decode(reader, context);
		BookBeanImpl bean = new BookBeanImpl();

		bean.setId(document.getInteger("_id"));
		BibleBeanImpl bible = new BibleBeanImpl();
		bible.setId(document.getInteger("bible_id"));
		bean.setBible(bible);
		bean.setName(document.get("name", String.class));
		
		return bean;
	}

	@Override
	public boolean documentHasId(BookBeanImpl bean) {
		return bean.getId() != null;
	}

	@Override
	public BookBeanImpl generateIdIfAbsentFromDocument(BookBeanImpl bean) {
		if (!this.documentHasId(bean)) {
			bean.setId(DaoHelper.nextId(bean));
		}
		
		return bean;
	}

	@Override
	public BsonValue getDocumentId(BookBeanImpl bean) {
		if (!documentHasId(bean)) {
			throw new IllegalStateException("The document does not contain an _id");
		}
		
		return new BsonInt32(bean.getId());
	}
}