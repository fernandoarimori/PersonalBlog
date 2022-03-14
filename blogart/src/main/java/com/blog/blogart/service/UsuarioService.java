package com.blog.blogart.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.blog.blogart.model.UserLogin;
import com.blog.blogart.model.Usuario;
import com.blog.blogart.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
///*	public Optional<Usuario> CadastrarUsuario(Usuario usuario) {
//		if (repository.findByUsuario(usuario.getUsuario()).isPresent()) {
//			return Optional.empty();
//		}
//
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String senhaEncoder = encoder.encode(usuario.getSenha());
//		usuario.setSenha(senhaEncoder);
//		
//		return Optional.ofNullable(repository.save(usuario));
//	}
//	
	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
		if (repository.findByUsuario(usuario.getUsuario()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		return Optional.of(repository.save(usuario));
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		if (repository.findById(usuario.getId()).isPresent()) {
			Optional<Usuario> buscaUsuario = repository.findByUsuario(usuario.getUsuario());
			if (buscaUsuario.isPresent()) {
				if (buscaUsuario.get().getId() != usuario.getId())
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
			}
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			return Optional.of(repository.save(usuario));
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);
	}
	
	private String criptografarSenha(String senha) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);
	}
	
	
	
	public Optional<UserLogin> Logar(Optional<UserLogin> user){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());
		
		if(usuario.isPresent()) {
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				
				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader ="Basic " + new String(encodedAuth);
				
				user.get().setToken(authHeader);
				user.get().setNome(usuario.get().getNome());
				
				return user;
			}
		}
		
	
		
		return null;
	}
	
}
