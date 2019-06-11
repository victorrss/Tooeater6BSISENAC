package br.senac.backend.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.senac.backend.model.Like;
import br.senac.backend.model.Tooeat;
import br.senac.backend.model.User;

public class LikeDao {

	private static LikeDao instance;
	protected EntityManager em;

	public static LikeDao getInstance(){
		if (instance == null)
			instance = new LikeDao();
		return instance;
	}

	private LikeDao() {
		em = Manager.getInstance().entityManager;
	}

	public Like getById(final int id) {
		em.clear(); 
		return em.find(Like.class, id);
	}


	public Like getByTooeatAndUser(User user, Tooeat tooeat) {
		em.clear();
		Query query = em.createQuery("FROM " +Like.class.getName()+ " WHERE user=:user AND tooeat=:tooeat");
		query.setParameter("user",user);
		query.setParameter("tooeat", tooeat);

		try {
			return (Like) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Like> findAll(final int tooeatId) {
		em.clear();
		Query query = em.createQuery("FROM " +Like.class.getName()+ " WHERE tooeat_id=:tooeat_id");
		query.setParameter("tooeat_id", tooeatId);
		return query.getResultList();
	}

	public void persist(Like like) {
		try {
			em.getTransaction().begin();
			em.persist(like);
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
			Like like = getById(id);
			em.remove(like);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
		em.clear();
	}

}
