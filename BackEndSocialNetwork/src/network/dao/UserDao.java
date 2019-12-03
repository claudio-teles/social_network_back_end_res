package network.dao;

import java.util.ArrayList;
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

import network.model.User;

public class UserDao {
	
	public static boolean login = false;
	
	public static SessionFactory getUserSessionFactory() {
		Configuration configuration = new Configuration().configure();
		configuration.addAnnotatedClass(User.class);
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}
	
	public void createUser(User user) {
		Session sessionUser = UserDao.getUserSessionFactory().openSession();
		sessionUser.beginTransaction();
		
		sessionUser.save(user);
		
		sessionUser.getTransaction().commit();
		sessionUser.close();
	}
	
	public Boolean login(String userName, String password) {
		if (login == false) {
			User user = new User();
			Session sessionLogin = UserDao.getUserSessionFactory().openSession();
			sessionLogin.beginTransaction();
			
			CriteriaBuilder cb = sessionLogin.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(User.class);
			Root<User> root = cq.from(User.class);
			cq.select(root).where(cb.equal(root.get("userName"), userName));
			
			Query<User> query = sessionLogin.createQuery(cq);
			user = (User) query.getSingleResult();
			
			sessionLogin.getTransaction().commit();
			sessionLogin.close();
			if (user.getPassword().equals(password)) {
				login = true;
				return true;
			}
		}
		return false;
	}
	
	public User getUserById(Long id_user) {
		User user = new User();
		if (login == true) {
			Session sessionID = UserDao.getUserSessionFactory().openSession();
			sessionID.beginTransaction();
			
			user = sessionID.get(User.class, id_user);
			
			sessionID.getTransaction().commit();
			sessionID.close();
		}
		return user;
	}
	
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		if (login == true) {
			Session sessionUsers = UserDao.getUserSessionFactory().openSession();
			sessionUsers.beginTransaction();
			
			CriteriaBuilder cb = sessionUsers.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb .createQuery(User.class);
			Root<User> root = cq.from(User.class);
			cq.select(root).orderBy(cb.asc(root.get("idUser")));
			Query<User> query = sessionUsers.createQuery(cq);
			users = query.list();
			
			sessionUsers.getTransaction().commit();
			sessionUsers.close();
		}
		return users;
	}
	
	public List<User> getUsers(String search) {
		String text = search.replace("_", " ");
		List<User> users = new ArrayList<>();
		if (login == true) {
			Session sessionUsers = UserDao.getUserSessionFactory().openSession();
			sessionUsers.beginTransaction();
			
			CriteriaBuilder cb = sessionUsers.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(User.class);
			
			Root<User> user = cq.from(User.class);
			
			Predicate p1 = cb.like(user.get("firstName"), "%"+text+"%");
			Predicate p2 = cb.like(user.get("lastName"), "%"+text+"%");
			Predicate p3 = cb.like(user.get("email"), "%"+text+"%");
			Predicate p4 = cb.like(user.get("userName"), "%"+text+"%");
			
			Expression<Boolean> e1 = cb.or(p1, p2);
			Expression<Boolean> e2 = cb.or(p3, p4);
			Expression<Boolean> expression = cb.or(e1, e2);
			
			cq.select(user).where(expression).orderBy(cb.asc(user.get("idUser")));
			Query<User> query = sessionUsers.createQuery(cq);
			users = query.list();
			
//			Query<User> query = sessionUsers.createQuery(
//				"FROM User u WHERE u.firstName LIKE :fn OR u.lastName LIKE : ln OR u.email LIKE :_email "
//				+ "OR u.userName LIKE :un ORDER BY u.idUser ASC"
//			);
//			query.setParameter("fn", "%"+text+"%");
//			query.setParameter("ln", "%"+text+"%");
//			query.setParameter("_email", "%"+text+"%");
//			query.setParameter("un", "%"+text+"%");
//			
//			users = query.list();
			
			sessionUsers.getTransaction().commit();
			sessionUsers.close();
		}
		return users;
	}
	
	public Boolean updateUserById(Long id_user, Map<String, String> userProperties) {
		if (login == true) {
			User user = new User();
			user = this.getUserById(Long.parseLong(userProperties.get("id_user")));
			
			Session sessionUpdate = UserDao.getUserSessionFactory().openSession();
			sessionUpdate.beginTransaction();
			
			if (userProperties.containsKey("first_name")) {
				user.setFirstName(userProperties.get("first_name"));
			}
			if (userProperties.containsKey("last_name")) {
				user.setLastName(userProperties.get("last_name"));
			}
			if (userProperties.containsKey("email")) {
				user.setEmail(userProperties.get("email"));
			}
			if (userProperties.containsKey("user_name")) {
				user.setUserName(userProperties.get("user_name"));
			}
			if (userProperties.containsKey("password")) {
				user.setPassword(userProperties.get("password"));
			}
			if (userProperties.containsKey("user_icon_location")) {
				user.setUserIconLocation(userProperties.get("user_icon_location"));
			}
			sessionUpdate.update(user);
			
			sessionUpdate.getTransaction().commit();
			sessionUpdate.close();
			return true;
		}
		return false;
	}
	
	public Boolean deleteUserById(Long id_user) {
		if (login == true) {
			User user = new User();
			user = this.getUserById(id_user);
			Session sessionUpdate = UserDao.getUserSessionFactory().openSession();
			sessionUpdate.beginTransaction();
			
			sessionUpdate.delete(user);
			
			sessionUpdate.getTransaction().commit();
			sessionUpdate.close();
			return true;
		}
		return false;
	}

}
