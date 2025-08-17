package com.codigopiojoso.forohub.controller;

import com.codigopiojoso.forohub.domain.topico.DatosActualizarTopico;
import com.codigopiojoso.forohub.domain.topico.DatosRegistroTopico;
import com.codigopiojoso.forohub.domain.topico.Topico;
import com.codigopiojoso.forohub.domain.topico.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRegistroTopico> registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
            UriComponentsBuilder uriComponentsBuilder) {

        if (topicoRepository.existsByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje())) {
            return ResponseEntity.badRequest().build();
        }

        Topico topico = new Topico(
                datosRegistroTopico.titulo(),
                datosRegistroTopico.mensaje(),
                datosRegistroTopico.autor(),
                datosRegistroTopico.curso()
        );

        topicoRepository.save(topico);

        // Crear la URI para el nuevo recurso creado
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(datosRegistroTopico);
    }

    @GetMapping
    public ResponseEntity<Page<Topico>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {

        Page<Topico> topicos = topicoRepository.findAll(paginacion);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/por-curso")
    public ResponseEntity<Page<Topico>> listarPorCurso(@RequestParam String curso,
                                                       @PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {
        Page<Topico> topicos = topicoRepository.findByCurso(curso, paginacion);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> mostrarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id).orElse(null);
        if (topico == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(topico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.findById(id).orElse(null);
        if (topico == null) {
            return ResponseEntity.notFound().build();
        }
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        if (topicoRepository.existsById(id)) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}