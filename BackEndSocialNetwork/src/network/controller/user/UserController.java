package network.controller.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import network.dao.UserDao;
import network.model.User;
import network.util.NetworkUtil;

@SessionScoped
@Path("/users")
public class UserController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1679738497823646612L;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveUser(User user) {
		user.setPassword(new NetworkUtil().genHash(user.getPassword()));
		UserDao userDao = new UserDao();
		userDao.createUser(user);
		new NetworkUtil().hidePassword(user);
		return Response.status(201).entity(user).build();
	}

	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(Map<String, String> jsonRequest) {
		String userName = jsonRequest.get("user_name");
		String password = new NetworkUtil().genHash(jsonRequest.get("password"));
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		
		UserDao userDao = new UserDao();
		boolean result = userDao.login(userName, password);
		
		if (result == true) {
			response.put("logged", true);
			return Response.status(200).entity(response).build();
		} else {
			response.put("logged", false);
			return Response.status(404).entity(response).build();
		}
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") Long id_user) {
		User user = new User();
		UserDao userDao = new UserDao();
		user = userDao.getUserById(id_user);
		new NetworkUtil().hidePassword(user);
		return Response.status(200).entity(user).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		List<User> users = new ArrayList<User>();
		UserDao userDao = new UserDao();
		users = userDao.getUsers();
		for (User u : users) {
			new NetworkUtil().hidePassword(u);
		}
		return Response.status(200).entity(users).build();
	}
	
	@Path("/users_search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@QueryParam(value = "string") String search) {
		List<User> users = new ArrayList<User>();
		UserDao userDao = new UserDao();
		users = userDao.getUsers(search);
		for (User u : users) {
			new NetworkUtil().hidePassword(u);
		}
		return Response.status(200).entity(users).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(Map<String, String> userProperties) {
		UserDao userDao = new UserDao();
		boolean result = userDao.updateUserById(null, userProperties);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		if (result == true) {
			response.put("updated", true);
			return Response.status(200).entity(response).build();
		} else {
			response.put("updated", false);
			return Response.status(404).entity(response).build();
		}
	}
	
	@Path("/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("id") Long id_user) {
		UserDao userDao = new UserDao();
		boolean result = userDao.deleteUserById(id_user);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		if (result == true) {
			response.put("deleted", true);
			return Response.status(200).entity(response).build();
		} else {
			response.put("deleted", false);
			return Response.status(404).entity(response).build();
		}
	}

}
