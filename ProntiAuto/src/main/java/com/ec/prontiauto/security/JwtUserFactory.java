package com.ec.prontiauto.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ec.prontiauto.security.model.Autorizacion;

public final class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static JwtUser create(com.ec.prontiauto.entidad.Usuario user) {
		return new JwtUser(user.getId(),
				user.getUsername(),
				user.getPassword(),
				user.getNombres(),
				user.getApellidos(),
				user.getCorreo(),
				user.getFechaNacimiento(),
				mapToGrantedAuthorities(user.getAuthorities()),
				user.getUltimoReseteo(),
				user.getSisHabilitado().equals("A") ? true : false);
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Autorizacion> authorities) {
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
				.collect(Collectors.toList());
	}
}
