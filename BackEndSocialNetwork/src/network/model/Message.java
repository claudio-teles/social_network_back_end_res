package network.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "message")
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6149936469275912557L;
	
	@Id
	@GeneratedValue(generator = "sn", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "sn", sequenceName = "social_network_sequence", allocationSize = 1)
	@Column(name = "id_message")
	private Long idMessage;
	
	@ManyToOne
	@JoinColumn(name = "from_user", foreignKey = @ForeignKey(name = "message_send"), referencedColumnName = "id_user", nullable = false)
	private User fromUser;	
	@ManyToOne
	@JoinColumn(name = "to_user", foreignKey = @ForeignKey(name = "message_recipient"), referencedColumnName = "id_user", nullable = false)
	private User toUser;
	
	@Column(length = 40, nullable = false)
	private String subject;
	
	@Column(length = 1000, nullable = false)
	private String text;
	
	@Column(length = 3000, nullable = true)
	private String emojis[];
	@Column(length = 3000, nullable = true)
	private String images[];
	@Column(length = 3000, nullable = true)
	private String audios[];
	@Column(length = 3000, nullable = true)
	private String videos[];
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;

	public Message() {
		super();
	}

	public Message(User fromUser, User toUser, String subject, String text, Date date) {
		super();
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.subject = subject;
		this.text = text;
		this.date = date;
	}

	public Long getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(Long idMessage) {
		this.idMessage = idMessage;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getEmojis() {
		return emojis;
	}

	public void setEmojis(String[] emojis) {
		this.emojis = emojis;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String[] getAudios() {
		return audios;
	}

	public void setAudios(String[] audios) {
		this.audios = audios;
	}

	public String[] getVideos() {
		return videos;
	}

	public void setVideos(String[] videos) {
		this.videos = videos;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
