package network.model;

import java.io.Serializable;

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

@Entity
@Table(name = "contact")
public class Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5810240733342972470L;
	
	@Id
	@GeneratedValue(generator = "sn", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "sn", sequenceName = "social_network_sequence", allocationSize = 1)
	@Column(name = "id_contact")
	private Long idContact;
	
	@ManyToOne
	@JoinColumn(name = "fk_user" ,foreignKey = @ForeignKey(name = "contact_owner"), referencedColumnName = "id_user", nullable = false)
	private User fkUser;
	
	@ManyToOne
	@JoinColumn(name = "contact", foreignKey = @ForeignKey(name = "friend"), referencedColumnName = "id_user", nullable = false)
	private User contact;
	
	public Contact() {
		super();
	}

	public Contact(User fkUser, User contact) {
		super();
		this.fkUser = fkUser;
		this.contact = contact;
	}

	public Long getIdContact() {
		return idContact;
	}

	public void setIdContact(Long idContact) {
		this.idContact = idContact;
	}
	
	public User getFkUser() {
		return fkUser;
	}

	public void setFkUser(User fkUser) {
		this.fkUser = fkUser;
	}

	public User getContact() {
		return contact;
	}

	public void setContact(User contact) {
		this.contact = contact;
	}

}
