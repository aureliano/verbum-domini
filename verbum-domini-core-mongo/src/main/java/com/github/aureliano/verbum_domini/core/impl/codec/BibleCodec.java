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

import com.github.aureliano.verbum_domini.core.impl.bean.BibleBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoHelper;

public class BibleCodec implements CollectibleCodec<BibleBeanImpl> {

	private Codec<Document> documentCodec;
	
	public BibleCodec() {
		 this.documentCodec = new DocumentCodec();
	}
	
	public BibleCodec(Codec<Document> codec) {
		 this.documentCodec = codec;
	}
	
	@Override
	public void encode(BsonWriter writer, BibleBeanImpl bean, EncoderContext context) {
		Document document = new Document();

		document.put("_id", bean.getId());
		document.put("copyright", bean.getCopyright());
		document.put("edition", bean.getEdition());
		document.put("eletronic_transcription_source", bean.getEletronicTranscriptionSource());
		document.put("eletronic_transcription_source_url", bean.getEletronicTranscriptionSourceUrl());
		document.put("language", bean.getLanguage());
		document.put("name", bean.getName());
		document.put("printed_source", bean.getPrintedSource());
		document.put("url", bean.getUrl());
		
		this.documentCodec.encode(writer, document, context);
	}

	@Override
	public Class<BibleBeanImpl> getEncoderClass() {
		return BibleBeanImpl.class;
	}

	@Override
	public BibleBeanImpl decode(BsonReader reader, DecoderContext context) {
		Document document = this.documentCodec.decode(reader, context);
		BibleBeanImpl bean = new BibleBeanImpl();

		bean.setId(document.getInteger("_id"));
		bean.setCopyright(document.getString("copyright"));
		bean.setEdition(document.getString("edition"));
		bean.setEletronicTranscriptionSource(document.getString("eletronic_transcription_source"));
		bean.setEletronicTranscriptionSourceUrl(document.getString("eletronic_transcription_source_url"));
		bean.setLanguage(document.getString("language"));
		bean.setName(document.getString("name"));
		bean.setPrintedSource(document.getString("printed_source"));
		bean.setUrl(document.getString("url"));
		
		return bean;
	}

	@Override
	public boolean documentHasId(BibleBeanImpl bean) {
		return bean.getId() != null;
	}

	@Override
	public BibleBeanImpl generateIdIfAbsentFromDocument(BibleBeanImpl bean) {
		if (!this.documentHasId(bean)) {
			bean.setId(DaoHelper.nextId(bean));
		}
		
		return bean;
	}

	@Override
	public BsonValue getDocumentId(BibleBeanImpl bean) {
		if (!documentHasId(bean)) {
			throw new IllegalStateException("The document does not contain an _id");
		}
		
		return new BsonInt32(bean.getId());
	}
}