package com.ec.prontiauto.security;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1215456216545L;

	private Integer id;

	private String username;

	private String password;

	private String nombres;

	private String apellidos;
	private String correo;

	private Date fechaNacimiento;

	private final Collection<? extends GrantedAuthority> authorities;

	private Date ultimoReseteo;
	private Boolean habilitado;

	public JwtUser(Integer id, String username, String password, String nombres, String apellidos, String correo,
			Date fechaNacimiento, Collection<? extends GrantedAuthority> authorities, Date ultimoReseteo,
			Boolean habilitado) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.correo = correo;
		this.fechaNacimiento = fechaNacimiento;
		this.authorities = authorities;
		this.ultimoReseteo = ultimoReseteo;
		this.habilitado = habilitado;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getNombres() {
		return nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getCorreo() {
		return correo;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Date getUltimoReseteo() {
		return ultimoReseteo;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return habilitado;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}
