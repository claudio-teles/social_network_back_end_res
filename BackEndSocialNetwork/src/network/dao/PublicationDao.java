package network.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import network.model.Publication;
import network.model.User;
import network.util.NetworkUtil;

public class PublicationDao {
	
	public static SessionFactory getPublicationSessionFactory() {
		Configuration configuration = new Configuration().configure();
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Publication.class);
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}
	
	public Boolean savePublication(Map<String, String> jsonRequest) {
		if (UserDao.login == true) {
			Session sessionPublication = getPublicationSessionFactory().openSession();
			sessionPublication.beginTransaction();
			
			UserDao userDao = new UserDao();
			User author = new User();
			author = userDao.getUserById(Long.parseLong(jsonRequest.get("author")));
			new NetworkUtil().hidePassword(author);
			
			
			Date date = new Date();
			Publication publication = new Publication(jsonRequest.get("title"), author, jsonRequest.get("text"), date);
			
			publication = new NetworkUtil().setArrayPublication(jsonRequest, publication);
			
			sessionPublication.save(publication);
			
			sessionPublication.getTransaction().commit();
			sessionPublication.close();
			return true;
		}
		return false;
	}

	public Publication getPublicationById(Long id_publication) {
		Publication publication = null;
		if (UserDao.login == true) {
			Session sessionID = getPublicationSessionFactory().openSession();
			sessionID.beginTransaction();
			
			publication = new Publication();
			publication = sessionID.get(Publication.class, id_publication);
			
			sessionID.getTransaction().commit();
			sessionID.close();
		}
		return publication ;
	}
	
	public List<Publication> getPublications() {
		List<Publication> publications = null;
		if(UserDao.login == true) {
			Session sessionPublications = getPublicationSessionFactory().openSession();
			sessionPublications.beginTransaction();
			
			publications = new ArrayList<Publication>();
			
			CriteriaBuilder cb = sessionPublications.getCriteriaBuilder();
			CriteriaQuery<Publication> cq = cb.createQuery(Publication.class);
			
			Root<Publication> _publications = cq.from(Publication.class);
			cq.select(_publications).orderBy(cb.asc(_publications.get("idPublication")));
			
			Query<Publication> query = sessionPublications.createQuery(cq);
			publications = query.list();
			
			sessionPublications.getTransaction().commit();
			sessionPublications.close();
		}
		return publications ;
	}
	
	public List<Publication> getPublications(String search) {
		String text = search.replace("_", " ");
		List<Publication> publications = null;
		if(UserDao.login == true) {
			Session sessionPublications = getPublicationSessionFactory().openSession();
			sessionPublications.beginTransaction();
			
			publications = new ArrayList<Publication>();
			
			CriteriaBuilder cb = sessionPublications.getCriteriaBuilder();
			CriteriaQuery<Publication> cq = cb.createQuery(Publication.class);
			
			Root<Publication> _publications = cq.from(Publication.class);
			
			Predicate p1 = cb.like(_publications.get("title"), ""+text+"%");
			Predicate p2 = cb.like(_publications.get("text"), ""+text+"%");
			
			Expression<Boolean> expression = cb.or(p1, p2);
			
			cq.select(_publications).where(expression).orderBy(cb.asc(_publications.get("idPublication")));
			
			Query<Publication> query = sessionPublications.createQuery(cq);
			publications = query.list();
			
			sessionPublications.getTransaction().commit();
			sessionPublications.close();
		}
		return publications ;
	}
	
	public Boolean updateLikeById(Map<String, String> updateLike) {
		if (UserDao.login == true) {
			
			Publication publication = new Publication();
			publication = this.getPublicationById(Long.parseLong(updateLike.get("id_publication")));
			
			if (updateLike.get("_like") == "true") {
				publication.set_like(publication.get_like() + (long) 1);
			} else {
				publication.set_like(publication.get_like() - (long) 1);
			}
			
			if (updateLike.get("did_not_like") == "true") {
				publication.setDidNotLike(publication.getDidNotLike() + (long) 1);
			} else {
				publication.setDidNotLike(publication.getDidNotLike() - (long) 1);
			}
			
			Session sessionUpdate = getPublicationSessionFactory().openSession();
			sessionUpdate.beginTransaction();
			
			sessionUpdate.update(publication);
			
			sessionUpdate.getTransaction().commit();
			sessionUpdate.close();
			return true;
		}
		return false;
	}
	
	public Boolean deletePublication(Long id_publication) {
		if (UserDao.login == true) {
			Publication publication = new Publication();
			Session sessionDelete = getPublicationSessionFactory().openSession();
			sessionDelete.beginTransaction();
			
			publication = this.getPublicationById(id_publication);
			sessionDelete.delete(publication);
			
			sessionDelete.getTransaction().commit();
			sessionDelete.close();
			return true;
		}
		return false;
	}

}
