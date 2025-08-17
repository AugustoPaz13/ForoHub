package com.codigopiojoso.forohub.controller;

import com.codigopiojoso.forohub.domain.usuario.DatosAutenticacionUsuario;
import com.codigopiojoso.forohub.domain.usuario.Usuario;
import com.codigopiojoso.forohub.infra.security.DatosJWTToken;
import com.codigopiojoso.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private static final Logger logger = LoggerFactory.getLogger(AutenticacionController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacion) {
        logger.info("Intentando autenticar usuario: {}", datosAutenticacion.login());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(datosAutenticacion.login(), datosAutenticacion.clave());

        try {
            Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);
            logger.info("Usuario autenticado exitosamente: {}", usuarioAutenticado.isAuthenticated());
            logger.info("Principal obtenido: {}", usuarioAutenticado.getPrincipal());

            if (!(usuarioAutenticado.getPrincipal() instanceof Usuario)) {
                logger.error("El objeto principal no es una instancia de Usuario.");
                return ResponseEntity.internalServerError().body("Error interno: Tipo de usuario inesperado.");
            }

            Usuario usuario = (Usuario) usuarioAutenticado.getPrincipal();
            String JWTtoken = tokenService.generarToken(usuario);
            logger.info("Token JWT generado: {}", JWTtoken != null ? "Sí" : "No");

            return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
        } catch (AuthenticationException e) {
            logger.warn("Fallo de autenticación para el usuario {}: {}", datosAutenticacion.login(), e.getMessage());
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
        } catch (Exception e) {
            logger.error("Error inesperado durante la autenticación: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error interno del servidor.");
        }
    }
}