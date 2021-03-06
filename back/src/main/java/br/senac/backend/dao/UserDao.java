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
		em = Manager.getInstance().getEntityManager();
	}

	public User getById(final int id) { 
		em.clear();
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
		em.clear();
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
		em.clear();
		return em.createQuery("FROM User WHERE enabled = 1").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> search(String term) {
		em.clear();
		Query query = em.createQuery("FROM User WHERE enabled = 1 AND (email LIKE :term OR nickname LIKE :term OR bio LIKE :term OR firstname LIKE :term OR lastname LIKE :term)");
		query.setParameter("term", "%" + term + "%");
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
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
		em.clear();
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
		em.clear();
	}

	public void removeById(final int id) {
		try {
			em.getTransaction().begin();
			User user = getById(id);
			user.setEnabled(false); 
			em.merge(user);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
		em.clear();
	}

}
