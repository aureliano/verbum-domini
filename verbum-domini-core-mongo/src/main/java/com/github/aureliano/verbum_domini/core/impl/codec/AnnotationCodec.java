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
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoHelper;

public class AnnotationCodec implements CollectibleCodec<AnnotationBeanImpl> {

	private Codec<Document> documentCodec;
	
	public AnnotationCodec() {
		 this.documentCodec = new DocumentCodec();
	}
	
	public AnnotationCodec(Codec<Document> codec) {
		 this.documentCodec = codec;
	}
	
	@Override
	public void encode(BsonWriter writer, AnnotationBeanImpl bean, EncoderContext context) {
		Document document = new Document();

		document.put("_id", bean.getId());
		if ((bean.getChapter() == null) || (bean.getChapter().getId() == null)) {
			throw new VerbumDominiException("Chapter field must not be null nor have null id.");
		}
		document.put("chapter_id", bean.getChapter().getId());
		document.put("number", bean.getNumber());
		document.put("text", bean.getText());
		
		this.documentCodec.encode(writer, document, context);
	}

	@Override
	public Class<AnnotationBeanImpl> getEncoderClass() {
		return AnnotationBeanImpl.class;
	}

	@Override
	public AnnotationBeanImpl decode(BsonReader reader, DecoderContext context) {
		Document document = this.documentCodec.decode(reader, context);
		AnnotationBeanImpl bean = new AnnotationBeanImpl();

		bean.setId(document.getInteger("_id"));
		ChapterBeanImpl chapter = new ChapterBeanImpl();
		chapter.setId(document.getInteger("chapter_id"));
		bean.setChapter(chapter);
		bean.setNumber(document.getString("number"));
		bean.setText(document.getString("text"));
		
		return bean;
	}

	@Override
	public boolean documentHasId(AnnotationBeanImpl bean) {
		return bean.getId() != null;
	}

	@Override
	public AnnotationBeanImpl generateIdIfAbsentFromDocument(AnnotationBeanImpl bean) {
		if (!this.documentHasId(bean)) {
			bean.setId(DaoHelper.nextId(bean));
		}
		
		return bean;
	}

	@Override
	public BsonValue getDocumentId(AnnotationBeanImpl bean) {
		if (!documentHasId(bean)) {
			throw new IllegalStateException("The document does not contain an _id");
		}
		
		return new BsonInt32(bean.getId());
	}
}