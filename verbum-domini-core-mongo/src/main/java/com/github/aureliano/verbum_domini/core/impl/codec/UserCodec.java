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

import com.github.aureliano.verbum_domini.core.impl.bean.UserBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoHelper;

public class UserCodec implements CollectibleCodec<UserBeanImpl> {

	private Codec<Document> documentCodec;
	
	public UserCodec() {
		 this.documentCodec = new DocumentCodec();
	}
	
	public UserCodec(Codec<Document> codec) {
		 this.documentCodec = codec;
	}
	
	@Override
	public void encode(BsonWriter writer, UserBeanImpl bean, EncoderContext context) {
		Document document = new Document();

		document.put("_id", bean.getId());
		document.put("active", bean.isActive());
		document.put("creation", bean.getCreation());
		document.put("last_sign_in", bean.getLastSignIn());
		document.put("login", bean.getLogin());
		document.put("password", bean.getPassword());
		document.put("salt_number", bean.getSaltNumber());
		
		this.documentCodec.encode(writer, document, context);
	}

	@Override
	public Class<UserBeanImpl> getEncoderClass() {
		return UserBeanImpl.class;
	}

	@Override
	public UserBeanImpl decode(BsonReader reader, DecoderContext context) {
		Document document = this.documentCodec.decode(reader, context);
		UserBeanImpl bean = new UserBeanImpl();

		bean.setId(document.getInteger("_id"));
		bean.setActive(document.getBoolean("active"));
		bean.setCreation(document.getDate("creation"));
		bean.setLastSignIn(document.getDate("last_sign_in"));
		bean.setLogin(document.getString("login"));
		bean.setPassword(document.getString("password"));
		bean.setSaltNumber(document.getString("salt_number"));
		
		return bean;
	}

	@Override
	public boolean documentHasId(UserBeanImpl bean) {
		return bean.getId() != null;
	}

	@Override
	public UserBeanImpl generateIdIfAbsentFromDocument(UserBeanImpl bean) {
		if (!this.documentHasId(bean)) {
			bean.setId(DaoHelper.nextId(bean));
		}
		
		return bean;
	}

	@Override
	public BsonValue getDocumentId(UserBeanImpl bean) {
		if (!documentHasId(bean)) {
			throw new IllegalStateException("The document does not contain an _id");
		}
		
		return new BsonInt32(bean.getId());
	}
}