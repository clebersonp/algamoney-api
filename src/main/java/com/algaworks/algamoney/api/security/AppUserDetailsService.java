package com.algaworks.algamoney.api.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.algaworks.algamoney.api.model.Usuario;
import com.algaworks.algamoney.api.repository.UsuarioRepository;

@Component
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		final Optional<Usuario> usuarioOptional = this.usuarioRepository.findByEmail(email);
		final Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos!"));
		return new User(usuario.getEmail(), usuario.getSenha(), usuario.getGrantedAuthority());
	}
}
