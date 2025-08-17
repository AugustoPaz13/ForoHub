package com.codigopiojoso.forohub.controller;

import com.codigopiojoso.forohub.domain.respuesta.DatosRegistroRespuesta;
import com.codigopiojoso.forohub.domain.respuesta.Respuesta;
import com.codigopiojoso.forohub.domain.respuesta.RespuestaRepository;
import com.codigopiojoso.forohub.domain.topico.TopicoRepository;
import com.codigopiojoso.forohub.domain.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<Void> crearRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistro,
                                               @AuthenticationPrincipal Usuario usuarioAutenticado,
                                               UriComponentsBuilder uriBuilder) {
        var topico = topicoRepository.getReferenceById(datosRegistro.topicoId());
        var respuesta = new Respuesta(
                null,
                datosRegistro.mensaje(),
                topico,
                LocalDateTime.now(),
                usuarioAutenticado,
                false
        );
        respuestaRepository.save(respuesta);
        URI url = uriBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).build();
    }

    @GetMapping("/topico/{topicoId}")
    public ResponseEntity<List<Respuesta>> listarRespuestasPorTopico(@PathVariable Long topicoId) {
        var respuestas = respuestaRepository.findByTopicoId(topicoId);
        return ResponseEntity.ok(respuestas);
    }
}