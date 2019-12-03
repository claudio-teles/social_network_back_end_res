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
@Table(name = "publication")
public class Publication implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5597179216899613248L;
	
	@Id
	@GeneratedValue(generator = "sn", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "sn", sequenceName = "social_network_sequence", allocationSize = 1)
	@Column(name = "id_publication")
	private Long idPublication;
	
	@Column(length = 40,nullable = false)
	private String title;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "user_post"), referencedColumnName = "id_user", nullable = false)
	private User author;
	
	@Column(length = 1000,nullable = false)
	private String text;
	
	@Column(length = 3000, nullable = true)
	private String emojis[];
	@Column(length = 3000, nullable = true)
	private String images[];
	@Column(length = 3000, nullable = true)
	private String audios[];
	@Column(length = 3000, nullable = true)
	private String videos[];
	
	@Column(nullable = true)
	private Long _like = (long) 0;
	@Column(name = "did_not_like", nullable = true)
	private Long didNotLike = (long) 0;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;

	public Publication() {
		super();
	}

	public Publication(String title, User author, String text, Date date) {
		super();
		this.title = title;
		this.author = author;
		this.text = text;
		this.date = date;
	}

	public Long getIdPublication() {
		return idPublication;
	}

	public void setIdPublication(Long idPublication) {
		this.idPublication = idPublication;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
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

	public Long get_like() {
		return _like;
	}

	public void set_like(Long _like) {
		this._like = _like;
	}

	public Long getDidNotLike() {
		return didNotLike;
	}

	public void setDidNotLike(Long didNotLike) {
		this.didNotLike = didNotLike;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
