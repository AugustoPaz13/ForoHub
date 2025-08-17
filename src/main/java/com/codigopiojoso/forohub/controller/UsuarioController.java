package com.codigopiojoso.forohub.controller;

import com.codigopiojoso.forohub.domain.usuario.DatosRegistroUsuario;
import com.codigopiojoso.forohub.domain.usuario.Usuario;
import com.codigopiojoso.forohub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Void> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistro, UriComponentsBuilder uriBuilder) {
        var usuario = new Usuario();
        usuario.setLogin(datosRegistro.email());
        usuario.setClave(passwordEncoder.encode(datosRegistro.clave()));
        usuario.setPerfil(com.codigopiojoso.forohub.domain.perfil.Perfil.USER);

        usuarioRepository.save(usuario);
        URI url = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        var usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }
}