package br.senac.backend.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.senac.backend.model.Comment;
import br.senac.backend.util.Util;

public class CommentDao {
	
	private static CommentDao instance;
	protected EntityManager em;

	public static CommentDao getInstance(){
		if (instance == null)
			instance = new CommentDao();
		return instance;
	}

	private CommentDao() {
		em = Manager.getInstance().entityManager;
	}

	public Comment getById(final int id) {
		return em.find(Comment.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Comment> findAll(final int tooeatId) {
		Query query = em.createQuery("FROM " +Comment.class.getName()+ " WHERE enabled = 1 AND tooeat_id=:tooeat_id");
		query.setParameter("tooeat_id", tooeatId);
		return query.getResultList();
	}

	public void persist(Comment comment) {
		try {
			em.getTransaction().begin();
			comment.setCreatedAt(Util.getDateNow());
			comment.setEnabled(true);
			em.persist(comment);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void remove(Comment comment) {
		try {
			em.getTransaction().begin();
			comment.setEnabled(false); 
			em.merge(comment);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void removeById(final int id) {
		try {
			Comment comment = getById(id);
			remove(comment);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
