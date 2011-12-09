package com.mdm.processor.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdm.processor.model.ClusteredColumnData;
import com.mdm.processor.model.IModel;
import com.mdm.processor.model.MyEntity;
import com.mdm.processor.model.ProcessStats;

@Service
public class EntityService {

	private static final Logger logger = LoggerFactory
			.getLogger(EntityService.class);

	@PersistenceContext
	EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Transactional(readOnly = true)
	public ProcessStats findEntity(String id) {

		// Uncomment when you have defined your entity
		//
		if ("new".equals(id)) {
			return new ProcessStats();
		} else {
			return em.find(ProcessStats.class, id);
		}

		// ...meanwhile here's a fake entity (remove this when the real one is
		// ready)

		// MyEntity entity = new MyEntity();
		// entity.setId("fake_id");
		// entity.setName("Fake Entity");
		// return entity;

	}

	@Transactional
	public ProcessStats save(ProcessStats entity) {

		// Once you have defined your entity in the MyEntity model object, you
		// can
		// uncomment this code. The entity must have an id property.
		if (entity.getId() != null) {
			entity = em.merge(entity);
		} else {
			em.persist(entity);
		}
		logger.info("entity saved: " + em);
		return entity;

	}
	
	@Transactional
	public IModel save(IModel entity) {

		// Once you have defined your entity in the MyEntity model object, you
		// can
		// uncomment this code. The entity must have an id property.
		if (entity.getId() != null) {
			entity = em.merge(entity);
		} else {
			em.persist(entity);
		}
		logger.info("entity saved: " + em);
		return entity;

	}


	@Transactional
	public boolean delete(String entityId) {
		ProcessStats entity = em.find(ProcessStats.class, entityId);
		if (entity == null) {
			return false;
		}
		em.remove(entity);
		return true;
	}

	public List getAll() {

		Query qr = em
				.createQuery("select o from com.mdm.processor.model.ProcessStats o");

		return qr.getResultList();

	}

	public ClusteredColumnData save(ClusteredColumnData newInstance) {
		// Once you have defined your entity in the MyEntity model object, you
		// can
		// uncomment this code. The entity must have an id property.
		if (newInstance.getId() != null) {
			newInstance = em.merge(newInstance);
		} else {
			em.persist(newInstance);
		}
		logger.info("entity saved: " + em);
		return newInstance;

	}

	public List getAllClusteredColumnData() {
		Query qr = em
				.createQuery("select o from com.mdm.processor.model.ClusteredColumnData o ORDER BY o.sequence");

		return qr.getResultList();
	}

}
