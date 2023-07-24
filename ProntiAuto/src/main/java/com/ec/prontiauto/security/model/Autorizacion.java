package com.ec.prontiauto.security.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ec.prontiauto.entidad.Usuario;

@Entity
@Table(name = "autorizacion")
public class Autorizacion {

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = 50)
	@NotNull
	@Enumerated(EnumType.STRING)
	private UsuarioAutorizacion name;

	@ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
	private List<Usuario> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UsuarioAutorizacion getName() {
		return name;
	}

	public void setName(UsuarioAutorizacion name) {
		this.name = name;
	}

	public List<Usuario> getUsers() {
		return users;
	}

	public void setUsers(List<Usuario> users) {
		this.users = users;
	}
}