package com.shabro.rpc.dao.mongodb;


import java.util.Collection;
import java.util.List;

import com.shabro.rpc.entity.mongodb.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

public interface RepositoryOld<E extends Entity> {

    void insert(E entity);

    void insertAll(Collection<E> listToInsert);

    void save(E entity);

    void remove(E entity);

    void removeByQuery(Query query);
    
    List<E> findAll();

    E findById(Object id);
    
    /**
	 * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
	 * 
	 * @param pageable
	 * @return a page of entities
	 */
	//Page<E> findAll(Pageable pageable);
	
	/**
	 * Returns a {@link Page} of entities by query  meeting the paging restriction provided in the {@code Pageable} object.
	 * 
	 * @param query
	 * @param pageable
	 * @return a page of entities
	 */
	//Page<E> findAll(Query query, Pageable pageable);
	
	//long getSequence(String tableName);
	
}
