package com.example.as.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class ApplicationUser implements UserDetails{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="user_id")
	private Integer id;

	@Column(name="userName",unique=true)
	private String userName;
	
	@Column(name="email_id",nullable=true)
	private String emailId;
	
	@Column(name="password",nullable=true)
	private String password;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="user_role_junction",
			joinColumns={@JoinColumn(name="user_id")},
			inverseJoinColumns= {@JoinColumn(name="role_id")}
			)
	@Column(name="authorities")
	private Set<Role> authorities;
	
	public ApplicationUser() {
		super();
		this.authorities = new HashSet<>();
	}

	public ApplicationUser(String userName, String emailId, String password, Set<Role> authorities) {
		super();
		this.userName = userName;
		this.emailId = emailId;
		this.password = password;
		this.authorities = authorities;
	}

	public ApplicationUser(Integer id, String userName, String emailId, String password, Set<Role> authorities) {
		super();
		this.id = id;
		this.userName = userName;
		this.emailId = emailId;
		this.password = password;
		this.authorities = authorities;
	}

	public ApplicationUser(String userName, String emailId) {
		super();
		this.userName = userName;
		this.emailId = emailId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getEmailId() {
		// TODO Auto-generated method stub
		return this.emailId;
	}
	
	public void setEmailId(String emailId) {
		// TODO Auto-generated method stub
		this.emailId = emailId;
	}


	public void setAuthorities(Set<Role> authorities) {
		this.authorities=authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		this.password = password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}
	
	public String getUserName() {
		// TODO Auto-generated method stub
		return this.userName;
	}
	
	public void setUserName(String userName) {
		// TODO Auto-generated method stub
		this.userName = userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
