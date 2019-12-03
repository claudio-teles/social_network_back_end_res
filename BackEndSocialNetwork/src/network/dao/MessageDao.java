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

import network.model.Message;
import network.model.User;
import network.util.NetworkUtil;

public class MessageDao {
	
	public static SessionFactory getMessageSessionFactory() {
		Configuration configuration = new Configuration().configure();
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Message.class);
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}
	
	public Boolean saveMessage(Map<String, String> jsonRequest) {
		if (UserDao.login == true) {
			Session sessionMessage = getMessageSessionFactory().openSession();
			sessionMessage.beginTransaction();
			
			UserDao userDao = new UserDao();
			User fromUser = new User();
			fromUser = userDao.getUserById(Long.parseLong(jsonRequest.get("from_user")));
			new NetworkUtil().hidePassword(fromUser);
			
			User toUser = new User();
			toUser = userDao.getUserById(Long.parseLong(jsonRequest.get("to_user")));
			new NetworkUtil().hidePassword(toUser);
			
			Date date = new Date();
			Message message = new Message(fromUser, toUser, jsonRequest.get("subject"), jsonRequest.get("text"), date);
			
			message = new NetworkUtil().setArrayMessage(jsonRequest, message);
			
			sessionMessage.save(message);
			
			sessionMessage.getTransaction().commit();
			sessionMessage.close();
			return true;
		}
		return false;
	}
	
	public Message getMessageById(Long id_message) {
		Message message = null;
		if (UserDao.login == true) {
			message = new Message();
			Session sessionID = getMessageSessionFactory().openSession();
			sessionID.beginTransaction();
			
			message = sessionID.get(Message.class, id_message);
			
			sessionID.getTransaction().commit();
			sessionID.close();
		}
		return message;
	}
	
	public List<Message> getMessages() {
		List<Message> messages = null;
		if (UserDao.login == true) {
			Session sessionMessages = getMessageSessionFactory().openSession();
			sessionMessages.beginTransaction();
			
			messages = new ArrayList<Message>();
			CriteriaBuilder cb = sessionMessages.getCriteriaBuilder();
			CriteriaQuery<Message> cq = cb.createQuery(Message.class);
			
			Root<Message> message = cq.from(Message.class);
			cq.select(message).orderBy(cb.asc(message.get("idMessage")));
			
			Query<Message> query = sessionMessages.createQuery(cq);
			messages = query.list();
			
			sessionMessages.getTransaction().commit();
			sessionMessages.close();
		}
		return messages ;
	}
	
	public List<Message> getMessagesFROM(Long idUser) {
		List<Message> messages = null;
		if (UserDao.login == true) {
			Session sessionMessagesFrom = getMessageSessionFactory().openSession();
			sessionMessagesFrom.beginTransaction();
			
			messages = new ArrayList<Message>();
			CriteriaBuilder cb = sessionMessagesFrom.getCriteriaBuilder();
			CriteriaQuery<Message> cqMessage = cb.createQuery(Message.class);
			
			Root<Message> message = cqMessage.from(Message.class);
			Predicate predicate = cb.equal(message.get("fromUser"), idUser);
			cqMessage.select(message).where(predicate).orderBy(cb.asc(message.get("idMessage")));
			
			Query<Message> query = sessionMessagesFrom.createQuery(cqMessage);
			messages = query.list();
			
			sessionMessagesFrom.getTransaction().commit();
			sessionMessagesFrom.close();
		}
		return messages ;
	}
	
	public List<Message> getMessagesTO(Long toUser) {
		List<Message> messages = null;
		if (UserDao.login == true) {
			Session sessionMessagesTo = getMessageSessionFactory().openSession();
			sessionMessagesTo.beginTransaction();
			
			messages = new ArrayList<Message>();
			CriteriaBuilder cb = sessionMessagesTo.getCriteriaBuilder();
			CriteriaQuery<Message> cqMessage = cb.createQuery(Message.class);
			
			Root<Message> message = cqMessage.from(Message.class);
			Predicate predicate = cb.equal(message.get("toUser"), toUser);
			cqMessage.select(message).where(predicate).orderBy(cb.asc(message.get("idMessage")));
			
			Query<Message> query = sessionMessagesTo.createQuery(cqMessage);
			messages = query.list();
			
			sessionMessagesTo.getTransaction().commit();
			sessionMessagesTo.close();
		}
		return messages ;
	}
	
	public List<Message> getMessages(String search) {
		String _text = search.replace("_", " ");
		List<Message> messages = null;
		if (UserDao.login == true) {
			Session sessionSearch = getMessageSessionFactory().openSession();
			sessionSearch.beginTransaction();
			
			messages = new ArrayList<Message>();
			CriteriaBuilder cb = sessionSearch.getCriteriaBuilder();
			CriteriaQuery<Message> cq = cb.createQuery(Message.class);
			
			Root<Message> message = cq.from(Message.class);
			
			Predicate p1 = cb.like(message.get("subject"), "%"+_text+"%");
			Predicate p2 = cb.like(message.get("text"), "%"+_text+"%");
			
			Expression<Boolean> p1_or_p2 = cb.or(p1, p2);
			
			cq.select(message).where(p1_or_p2).orderBy(cb.asc(message.get("idMessage")));
			
			Query<Message> query = sessionSearch.createQuery(cq);
			messages = query.list();
			
			sessionSearch.getTransaction().commit();
			sessionSearch.close();
		}
		return messages ;
	}
	
	public Boolean deleteMessageById(Long id_message) {
		if (UserDao.login == true) {
			Message message = new Message();
			Session sessionDelete = getMessageSessionFactory().openSession();
			sessionDelete.beginTransaction();
			
			message = this.getMessageById(id_message);
			sessionDelete.delete(message);
			
			sessionDelete.getTransaction().commit();
			sessionDelete.close();
			return true;
		}
		return false;
	}
	
}
