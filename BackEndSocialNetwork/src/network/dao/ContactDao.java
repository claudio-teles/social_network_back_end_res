package network.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import network.model.Contact;
import network.model.User;

public class ContactDao {
	
	public static SessionFactory getContactSessionFactory() {
		Configuration configuration = new Configuration().configure();
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Contact.class);
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}

	public Boolean saveContact(Map<String, String> userProperties) {
		if (UserDao.login == true) {
			Session sessionSaveContact = getContactSessionFactory().openSession();
			sessionSaveContact.beginTransaction();
			
			Contact contact = new Contact();
			contact.setFkUser(sessionSaveContact.get(User.class, Long.parseLong(userProperties.get("fk_user"))));
			contact.setContact(sessionSaveContact.get(User.class, Long.parseLong(userProperties.get("contact"))));
			sessionSaveContact.save(contact);
			
			sessionSaveContact.getTransaction().commit();
			sessionSaveContact.close();
			return true;
		}
		return false;
	}
	
	public Contact getContactById(Long id_contact) {
		Contact contact = new Contact();
		if (UserDao.login == true) {
			Session sessionGetContact = getContactSessionFactory().openSession();
			sessionGetContact.beginTransaction();
			
			contact = (Contact) sessionGetContact.get(Contact.class, id_contact);
			
			sessionGetContact.getTransaction().commit();
			sessionGetContact.close();
		}
		return contact;
	}
	
	public List<Contact> getContacts() {
		List<Contact> contacts = new ArrayList<>();
		if (UserDao.login == true) {
			Session sessionContacts = getContactSessionFactory().openSession();
			sessionContacts.beginTransaction();
			
			CriteriaBuilder cb = sessionContacts.getCriteriaBuilder();
			CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);
			
			Root<Contact> contact = cq.from(Contact.class);
			cq.select(contact).orderBy(cb.asc(contact.get("idContact")));
			
			Query<Contact> query = sessionContacts.createQuery(cq);
			contacts = query.list();
			
			sessionContacts.getTransaction().commit();
			sessionContacts.close();
		}
		return contacts;
	}
	
	public Boolean deleteContact(Long id_contact) {
		if (UserDao.login == true) {
			Contact contact = new Contact();
			contact = this.getContactById(id_contact);
			Session sessionUpdateContact = getContactSessionFactory().openSession();
			sessionUpdateContact.beginTransaction();
			
			sessionUpdateContact.delete(contact);
			
			sessionUpdateContact.getTransaction().commit();
			sessionUpdateContact.close();
			return true;
		}
		return false;
	}

}
