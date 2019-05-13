package br.senac.backend.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.senac.backend.model.Follower;
import br.senac.backend.model.Tooeat;
import br.senac.backend.util.Util;

public class TooeatDao {

	private static TooeatDao instance;
	protected EntityManager em;

	public static TooeatDao getInstance(){
		if (instance == null)
			instance = new TooeatDao();
		return instance;
	}

	private TooeatDao() {
		em = Manager.getInstance().entityManager;
	}

	public Tooeat getById(final int id) {
		return em.find(Tooeat.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Tooeat> getByUserId(final Integer userId) {
		Query query = em.createQuery("FROM "+Tooeat.class.getName()+" where user_id=:user_id");
		query.setParameter("user_id", userId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Tooeat> findAll(final Integer userId) {
		return em.createQuery("FROM " +Tooeat.class.getName()+ " t"+
				" WHERE t.enabled = 1 "+
				" AND t.user IN ("+userId+", (SELECT f.userMaster FROM "+Follower.class.getName()+" f WHERE f.userSlave = " + userId+ "))"+
				" ").getResultList();
	}

	public void persist(Tooeat tooeat) {
		try {
			em.getTransaction().begin();
			tooeat.setCreatedAt(Util.getDateNow());
			tooeat.setEnabled(true);
			em.persist(tooeat);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void merge(Tooeat tooeat) {
		try {
			em.getTransaction().begin();
			tooeat.setUpdateAt(Util.getDateNow());
			em.merge(tooeat);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void remove(Tooeat tooeat) {
		try {
			em.getTransaction().begin();
			tooeat.setEnabled(false); 
			em.merge(tooeat);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Tooeat tooeat = getById(id);
			remove(tooeat);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
