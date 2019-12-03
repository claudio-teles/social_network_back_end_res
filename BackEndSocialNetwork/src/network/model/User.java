package network.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5095328753639358881L;
	
	@Id
	@GeneratedValue(generator = "generator", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "generator", sequenceName = "social_network_sequence", initialValue = 1, allocationSize = 1)
	@Column(name = "id_user", nullable = false)
	private Long idUser;
	
	@Column(name = "first_name", nullable = false, length = 40)
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 40)
	private String lastName;
	
	@Column(name = "email", nullable = false, length = 40)
	private String email;
	
	@Column(name = "user_name", nullable = false, length = 40)
	private String userName;
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(name = "user_icon_location", nullable = true, length = 3000)
	private String userIconLocation;
	
	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, String userName, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userName = userName;
		this.password = password;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserIconLocation() {
		return userIconLocation;
	}

	public void setUserIconLocation(String userIconLocation) {
		this.userIconLocation = userIconLocation;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", userName=" + userName + ", password=" + password + ", userIconLocation=" + userIconLocation + "]";
	}

}
