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
		Query query = em.createQuery("FROM "+UserDao.class.getName()+" where enabled = 1 AND nickname=:nickname");
		query.setParameter("nickname", nickname);
		return (User) query.getSingleResult();
	}

	public User getByEmail(final String email) {
		Query query = em.createQuery("FROM "+UserDao.class.getName()+" where enabled = 1 AND email=:email");
		query.setParameter("email", email);
		return (User) query.getSingleResult();
	}

	public User getByUserName(final String username) {
		if(Util.isValidEmailAddress(username))
			return getByEmail(username);
		else 
			return getByNickName(username);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return em.createQuery("FROM " +UserDao.class.getName()+ " WHERE enabled = 1").getResultList();
	}

	public void persist(User user) {
		try {
			em.getTransaction().begin();
			user.setCreatedAt(Util.getDateNow());
			user.setEnabled(true);
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
			user.setUpdateAt(Util.getDateNow());
			em.merge(user);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void remove(User user) {
		try {
			em.getTransaction().begin();
			user.setEnabled(false); 
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
			remove(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
