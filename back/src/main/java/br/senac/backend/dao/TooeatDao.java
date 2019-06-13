package br.senac.backend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import br.senac.backend.model.Follower;
import br.senac.backend.model.Tooeat;
import br.senac.backend.model.User;
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
		em = Manager.getInstance().getEntityManager();
	}

	public Tooeat getById(final int id) {
		em.clear();
		return em.find(Tooeat.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Tooeat> getByUserId(User user) {
		Query query = em.createQuery("FROM Tooeat WHERE enabled = 1 AND user=:user");
		query.setParameter("user", user);
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Tooeat> findAll(final Integer userId) {
		em.clear();
		Query query = em.createQuery("FROM " +Tooeat.class.getName()+ " t"+
				" WHERE t.enabled = 1 "+
				" AND t.user IN ("+userId+", (SELECT f.userMaster FROM "+Follower.class.getName()+" f WHERE f.userSlave = " + userId+ "))"+
				" ORDER BY 1 DESC");
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public void persist(Tooeat tooeat) {
		try {
			em.detach(tooeat.getUser());
			em.getTransaction().begin();
			tooeat.setCreatedAt(Util.getDateNow());
			em.persist(tooeat);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
		em.clear();
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
		em.clear();
	}

	public void removeById(final int id) {
		try {
			Tooeat tooeat = getById(id);
			em.getTransaction().begin();
			tooeat.setEnabled(false); 
			em.merge(tooeat);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
		em.clear();
	}

}
