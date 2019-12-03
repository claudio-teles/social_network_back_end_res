package network.controller.publication;

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

import network.dao.PublicationDao;
import network.model.Publication;
import network.model.User;
import network.util.NetworkUtil;

@SessionScoped
@Path("/publications")
public class PublicationController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7750063948465270511L;
	
	private void publicationIterator(List<Publication> publications) {
		for (Publication publication : publications) {
			User u1 = new User();
			u1 = publication.getAuthor();
			u1.setPassword(new NetworkUtil().hidePassword(u1));
			
			publication.setAuthor(u1);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makePublication(Map<String, String> jsonRequest) {
		PublicationDao publicationDao = new PublicationDao();
		boolean result = publicationDao.savePublication(jsonRequest);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		
		if (result == true) {
			response.put("published", true);
			return Response.status(201).entity(response).build();
		} else {
			response.put("published", false);
			return Response.status(404).entity(response).build();
		}
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPublication(@PathParam("id") Long id_publication) {
		PublicationDao publicationDao = new PublicationDao();
		Publication publication = new Publication();
		publication = publicationDao.getPublicationById(id_publication);
		
		User u1 = new User();
		u1 = publication.getAuthor();
		u1.setPassword(new NetworkUtil().hidePassword(u1));
		
		publication.setAuthor(u1);
		return Response.status(200).entity(publication).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPublications() {
		List<Publication> publications = new ArrayList<Publication>();
		PublicationDao publicationDao = new PublicationDao();
		publications = publicationDao.getPublications();
		publicationIterator(publications);
		return Response.status(200).entity(publications).build();
	}

	@Path("/publications_search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPublications(@QueryParam("string") String search) {
		List<Publication> publications = new ArrayList<Publication>();
		PublicationDao publicationDao = new PublicationDao();
		publications = publicationDao.getPublications(search);
		publicationIterator(publications);
		return Response.status(200).entity(publications).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateLikePublication(Map<String, String> updateLike) {
		boolean result = new PublicationDao().updateLikeById(updateLike);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		
		if (result == true) {
			response.put("publicationUpdated", true);
			return Response.status(200).entity(response).build();
		} else {
			response.put("publicationUpdated", false);
			return Response.status(404).entity(response).build();
		}
	}
	
	@Path("/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePublication(@PathParam("id") Long id_publication) {
		boolean result = new PublicationDao().deletePublication(id_publication);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		
		if (result == true) {
			response.put("publicationDeleted", true);
			return Response.status(200).entity(response).build();
		} else {
			response.put("publicationDeleted", false);
			return Response.status(404).entity(response).build();
		}
	}

}
