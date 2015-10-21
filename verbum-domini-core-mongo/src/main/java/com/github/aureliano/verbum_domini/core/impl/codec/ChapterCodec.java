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
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoHelper;

public class ChapterCodec implements CollectibleCodec<ChapterBeanImpl> {

	private Codec<Document> documentCodec;
	
	public ChapterCodec() {
		 this.documentCodec = new DocumentCodec();
	}
	
	public ChapterCodec(Codec<Document> codec) {
		 this.documentCodec = codec;
	}
	
	@Override
	public void encode(BsonWriter writer, ChapterBeanImpl bean, EncoderContext context) {
		Document document = new Document();

		document.put("_id", bean.getId());
		if ((bean.getBook() == null) || (bean.getBook().getId() == null)) {
			throw new VerbumDominiException("Book field must not be null nor have null id.");
		}
		document.put("book_id", bean.getBook().getId());
		document.put("number", bean.getNumber());
		
		this.documentCodec.encode(writer, document, context);
	}

	@Override
	public Class<ChapterBeanImpl> getEncoderClass() {
		return ChapterBeanImpl.class;
	}

	@Override
	public ChapterBeanImpl decode(BsonReader reader, DecoderContext context) {
		Document document = this.documentCodec.decode(reader, context);
		ChapterBeanImpl bean = new ChapterBeanImpl();

		bean.setId(document.getInteger("_id"));
		BookBeanImpl book = new BookBeanImpl();
		book.setId(document.getInteger("book_id"));
		bean.setBook(book);
		bean.setNumber(document.get("number", String.class));
		
		return bean;
	}

	@Override
	public boolean documentHasId(ChapterBeanImpl bean) {
		return bean.getId() != null;
	}

	@Override
	public ChapterBeanImpl generateIdIfAbsentFromDocument(ChapterBeanImpl bean) {
		if (!this.documentHasId(bean)) {
			bean.setId(DaoHelper.nextId(bean));
		}
		
		return bean;
	}

	@Override
	public BsonValue getDocumentId(ChapterBeanImpl bean) {
		if (!documentHasId(bean)) {
			throw new IllegalStateException("The document does not contain an _id");
		}
		
		return new BsonInt32(bean.getId());
	}
}