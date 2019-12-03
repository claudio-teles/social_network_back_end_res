package network.controller.message;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import network.dao.MessageDao;
import network.model.Message;
import network.model.User;
import network.util.NetworkUtil;

@SessionScoped
@Path("/messages")
public class MessageController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 997712021528829290L;
	
	private void messageIterator(List<Message> messages) {
		for (Message m : messages) {
			Message message = new Message();
			message = m;
			User u1 = new User();
			User u2 = new User();
			
			u1 = message.getFromUser();
			u1.setPassword(new NetworkUtil().hidePassword(u1));
			u2 = message.getToUser();
			u2.setPassword(new NetworkUtil().hidePassword(u2));
			
			message.setFromUser(u1);
			message.setToUser(u2);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendMessage(Map<String, String> jsonRequest) {
		MessageDao messageDao = new MessageDao();
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		
		boolean result = messageDao.saveMessage(jsonRequest);
		if (result == true) {
			response.put("messageSaved", true);
			return Response.status(201).entity(response).build();
		} else {
			response.put("messageSaved", false);
			return Response.status(404).entity(response).build();
		}
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessage(@PathParam("id") Long id_message) {
		Message message = new Message();
		message = new MessageDao().getMessageById(id_message);
		
		User u1 = new User();
		User u2 = new User();
		
		u1 = message.getFromUser();
		u1.setPassword(new NetworkUtil().hidePassword(u1));
		u2 = message.getToUser();
		u2.setPassword(new NetworkUtil().hidePassword(u2));
		
		message.setFromUser(u1);
		message.setToUser(u2);
		
		return Response.status(200).entity(message).build();
	}
	
	@Path("prop_contact_sender/{from}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessagesSender(@PathParam("from") Long idUser) {
		List<Message> messages = new ArrayList<Message>();
		messages = new MessageDao().getMessagesFROM(idUser);
		
		messageIterator(messages);
		return Response.status(200).entity(messages).build();
	}
	
	@Path("prop_contact_recipient/{to}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessagesRecipient(@PathParam("to") Long toUser) {
		List<Message> messages = new ArrayList<Message>();
		messages = new MessageDao().getMessagesTO(toUser);
		
		messageIterator(messages);
		return Response.status(200).entity(messages).build();
	}

	@Path("/messages_search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessagesSearch(@QueryParam(value = "string") String search) {
		List<Message> messages = new ArrayList<Message>();
		MessageDao messageDao = new MessageDao();
		messages = messageDao.getMessages(search);
		
		messageIterator(messages);
		return Response.status(200).entity(messages).build();
	}
	
	@Path("/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMessage(@PathParam("id") Long id_message) {
		boolean result = new MessageDao().deleteMessageById(id_message);
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
