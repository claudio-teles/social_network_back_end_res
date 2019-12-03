package network.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import network.model.Message;
import network.model.Publication;
import network.model.User;

public class NetworkUtil {
	
	public String hidePassword(User user) {
		String password = "";
		for (int i = 0; i < user.getPassword().length(); i++) {
			password += "*";
		}
		user.setPassword(password);
		password = "";
		return user.getPassword();
	}
	
	public Message setArrayMessage(Map<String, String> jsonRequest, Message message) {
		if (jsonRequest.containsKey("emojis")) {
			message.setEmojis(jsonRequest.get("emojis").split(", "));
		}
		if (jsonRequest.containsKey("images")) {
			message.setImages(jsonRequest.get("images").split(", "));
		}
		if (jsonRequest.containsKey("audios")) {
			message.setAudios(jsonRequest.get("audios").split(", "));
		}
		if (jsonRequest.containsKey("videos")) {
			message.setVideos(jsonRequest.get("videos").split(", "));
		}
		return message;
	}
	
	public Publication setArrayPublication(Map<String, String> jsonRequest, Publication publication) {
		if (jsonRequest.containsKey("emojis")) {
			publication.setEmojis(jsonRequest.get("emojis").split(", "));
		}
		if (jsonRequest.containsKey("images")) {
			publication.setImages(jsonRequest.get("images").split(", "));
		}
		if (jsonRequest.containsKey("audios")) {
			publication.setAudios(jsonRequest.get("audios").split(", "));
		}
		if (jsonRequest.containsKey("videos")) {
			publication.setVideos(jsonRequest.get("videos").split(", "));
		}
		return publication;
	}
	
	public String genHash(String pwd) {
		String word;
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(pwd.getBytes("UTF-8"));
			 
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
			  hexString.append(String.format("%02X", 0xFF & b));
			}
			word = hexString.toString();
			return word;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
