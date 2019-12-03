package network.controller.contact;

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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import network.dao.ContactDao;
import network.model.Contact;
import network.model.User;
import network.util.NetworkUtil;

@SessionScoped
@Path("/contacts")
public class ContcatController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8412258257922735472L;
	
	private void contactsIterator(List<Contact> contacts) {
		for (Contact c : contacts) {
			Contact contact = new Contact();
			contact = c;
			User u1 = new User();
			User u2 = new User();
			
			u1 = contact.getFkUser();
			u1.setPassword(new NetworkUtil().hidePassword(u1));
			u2 = contact.getContact();
			u2.setPassword(new NetworkUtil().hidePassword(u2));
			
			contact.setFkUser(u1);
			contact.setContact(u2);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveContact(Map<String, String> jsonRequest) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		boolean result = new ContactDao().saveContact(jsonRequest);
		if (result == true) {
			response.put("contactSaved", true);
			return Response.status(201).entity(response).build();
		} else {
			response.put("contactSaved", false);
			return Response.status(404).entity(response).build();
		}
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContact(@PathParam("id") Long id_contact) {
		Contact contact = new Contact();
		ContactDao contactDao = new ContactDao();
		contact = contactDao.getContactById(id_contact);
		
		User u1 = new User();
		User u2 = new User();
		u1 = contact.getFkUser();
		u1.setPassword(new NetworkUtil().hidePassword(u1));
		u2 = contact.getContact();
		u2.setPassword(new NetworkUtil().hidePassword(u2));
		
		contact.setFkUser(u1);
		contact.setContact(u2);
		return Response.status(200).entity(contact ).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContacts() {
		List<Contact> contacts = new ArrayList<Contact>();
		
		contacts = new ContactDao().getContacts();
		contactsIterator(contacts);
		return Response.status(200).entity(contacts).build();
	}

	@Path("/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteContact(@PathParam("id") Long id_contact) {
		boolean result = new ContactDao().deleteContact(id_contact);
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
