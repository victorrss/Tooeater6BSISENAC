package br.senac.backend.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.senac.backend.model.User;
import br.senac.backend.util.Util;

public class UserDao {


	private static UserDao instance;
	protected EntityManager em;

	public static UserDao getInstance(){
		if (instance == null)
			instance = new UserDao();
		return instance;
	}

	private UserDao() {
		em = Manager.getInstance().entityManager;
	}

	public User getById(final int id) {
		return em.find(User.class, id);
	}

	public User getByNickName(final String nickname) {
		Query query = em.createQuery("FROM User where enabled = 1 AND nickname=:nickname");
		query.setParameter("nickname", nickname);

		try {
			return (User) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public User getByEmail(final String email) {
		Query query = em.createQuery("FROM User where enabled = 1 AND email=:email");
		query.setParameter("email", email);

		try {
			return (User) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public User getByUserName(final String username) {
		if(Util.isValidEmailAddress(username))
			return getByEmail(username);
		else 
			return getByNickName(username);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return em.createQuery("FROM User WHERE enabled = 1").getResultList();
	}

	public void persist(User user) {
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void merge(User user) {
		try {
			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	void remove(User user) {
		try {
			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			User user = getById(id);
			user.setEnabled(false); 
			remove(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
