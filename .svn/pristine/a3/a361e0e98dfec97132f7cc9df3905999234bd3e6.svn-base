package com.shabro.rpc.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document 
public class BasicEntity implements Entity {
	private static final long serialVersionUID = 3900312777526608112L;

	@Id
	private ObjectId id;

	public String getId() {
		if(id == null) {
			id = new ObjectId();
		}
		return id.toString();
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getOid(){
		if(id == null) {
			id = new ObjectId();
		}
		return id;
	}  
}

