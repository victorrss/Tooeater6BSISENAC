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
		em.clear(); 
		return em.find(Follower.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Follower> findAllFollowing(final Integer userId) {
		em.clear();
		Query query = em.createQuery("FROM " +Follower.class.getName()+ " WHERE enabled = 1 AND slave_user_id=:user_id");
		query.setParameter("user_id", userId);
		return query.getResultList();
	}

	public Follower getByMasterAndSlaveWithoutEnabled(final Integer userSlaveId, final Integer userMasterId) {
		em.clear();
		Query query = em.createQuery("FROM " +Follower.class.getName()+ " WHERE slave_user_id=:userSlaveId AND master_user_id=:userMasterId");
		query.setParameter("userSlaveId", userSlaveId);
		query.setParameter("userMasterId", userMasterId);
		try {
			return (Follower) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Follower> findAllFollowers(final Integer userId) {
		em.clear();
		Query query = em.createQuery("FROM " +Follower.class.getName()+ " WHERE enabled = 1 AND master_user_id=:user_id");
		query.setParameter("user_id", userId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Follower> findAllInvites(final Integer userId) {
		Query query = em.createQuery("FROM " +Follower.class.getName()+ " WHERE enabled is null AND master_user_id=:user_id");
		query.setParameter("user_id", userId);
		em.clear();
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
		em.clear();
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
		em.clear();
	}

	public void removeById(final int id) {
		try {
			em.getTransaction().begin();
			Follower follower = getById(id);
			follower.setEnabled(true);
			em.merge(follower);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
		em.clear();
	}
	
	public void remove(final Follower follower) {
		try {
			em.getTransaction().begin();
			em.remove(follower);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
		em.clear();
	}

}
