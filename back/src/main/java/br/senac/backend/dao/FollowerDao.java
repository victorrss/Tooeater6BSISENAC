package br.senac.backend.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.senac.backend.model.Follower;

public class FollowerDao {
	
	private static FollowerDao instance;
	protected EntityManager em;

	public static FollowerDao getInstance(){
		if (instance == null)
			instance = new FollowerDao();
		return instance;
	}

	private FollowerDao() {
		em = Manager.getInstance().entityManager;
	}

	public Follower getById(final Integer id) {
		return em.find(Follower.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Follower> findAllFollowing(final Integer userId) {
		Query query = em.createQuery("FROM " +Follower.class.getName()+ " WHERE enabled = 1 AND slave_user_id=:user_id");
		query.setParameter("user_id", userId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Follower> findAllFollowers(final Integer userId) {
		Query query = em.createQuery("FROM " +Follower.class.getName()+ " WHERE enabled = 1 AND master_user_id=:user_id");
		query.setParameter("user_id", userId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Follower> findAllInvites(final Integer userId) {
		Query query = em.createQuery("FROM " +Follower.class.getName()+ " WHERE enabled is null AND master_user_id=:user_id");
		query.setParameter("user_id", userId);
		return query.getResultList();
	}

	public void persist(Follower follower) {
		try {
			em.getTransaction().begin();
			follower.setEnabled(null);
			em.persist(follower);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}
	
	public void merge(Follower follower) {
		try {
			em.getTransaction().begin();
			em.merge(follower);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void remove(Follower follower) {
		try {
			em.getTransaction().begin();
			em.remove(follower);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Follower follower = getById(id);
			remove(follower);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
