package com.github.aureliano.verbum_domini.core.impl.codec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoHelper;

public class VerseCodec implements CollectibleCodec<VerseBeanImpl> {

	private Codec<Document> documentCodec;
	
	public VerseCodec() {
		 this.documentCodec = new DocumentCodec();
	}
	
	public VerseCodec(Codec<Document> codec) {
		 this.documentCodec = codec;
	}
	
	@Override
	public void encode(BsonWriter writer, VerseBeanImpl bean, EncoderContext context) {
		Document document = new Document();

		document.put("_id", bean.getId());
		if ((bean.getChapter() == null) || (bean.getChapter().getId() == null)) {
			throw new VerbumDominiException("Chapter field must not be null nor have null id.");
		}
		document.put("chapter_id", bean.getChapter().getId());
		document.put("number", bean.getNumber());
		document.put("text", bean.getText());
		
		if ((bean.getAnnotations() != null) && (!bean.getAnnotations().isEmpty())) {
			List<Document> docs = new ArrayList<Document>();
			for (AnnotationBean a : bean.getAnnotations()) {
				Document doc = new Document();
				
				doc.put("number", a.getNumber());
				doc.put("text", a.getText());
				
				docs.add(doc);
			}
			document.put("annotations", docs);
		}
		
		this.documentCodec.encode(writer, document, context);
	}

	@Override
	public Class<VerseBeanImpl> getEncoderClass() {
		return VerseBeanImpl.class;
	}

	@Override
	public VerseBeanImpl decode(BsonReader reader, DecoderContext context) {
		Document document = this.documentCodec.decode(reader, context);
		VerseBeanImpl bean = new VerseBeanImpl();

		bean.setId(document.getInteger("_id"));
		ChapterBeanImpl chapter = new ChapterBeanImpl();
		chapter.setId(document.getInteger("chapter_id"));
		bean.setChapter(chapter);
		bean.setNumber(document.getString("number"));
		bean.setText(document.getString("text"));
		
		Collection<?> coll = document.get("annotations", Collection.class);
		
		return bean;
	}

	@Override
	public boolean documentHasId(VerseBeanImpl bean) {
		return bean.getId() != null;
	}

	@Override
	public VerseBeanImpl generateIdIfAbsentFromDocument(VerseBeanImpl bean) {
		if (!this.documentHasId(bean)) {
			bean.setId(DaoHelper.nextId(bean));
		}
		
		return bean;
	}

	@Override
	public BsonValue getDocumentId(VerseBeanImpl bean) {
		if (!documentHasId(bean)) {
			throw new IllegalStateException("The document does not contain an _id");
		}
		
		return new BsonInt32(bean.getId());
	}
}