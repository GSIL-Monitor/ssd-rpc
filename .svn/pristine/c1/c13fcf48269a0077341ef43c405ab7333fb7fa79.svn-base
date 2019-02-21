package com.shabro.rpc.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document 
public class BasicEntityOld implements Entity {
	private static final long serialVersionUID = -4805511262169797229L;

	@Id
	private ObjectId id;
	private String iType;

	public String getId() {
		if(id == null) {
			id = new ObjectId();
		}
		return id.toString();
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
//	public void setId(long id) {
//  	this.id = new ObjectId(String.valueOf(id));
//	}

	public String getiType() {
		return iType;
	}
	public void setiType(String iType) {
		this.iType = iType;
	}

	public ObjectId getOid(){
		if(id == null) {
			id = new ObjectId();
		}
		return id;
	}  
}

