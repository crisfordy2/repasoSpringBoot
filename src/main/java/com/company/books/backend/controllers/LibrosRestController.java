package com.company.books.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.books.backend.model.Libro;
import com.company.books.backend.service.LibrosService;


@RestController
@RequestMapping("/v1")
public class LibrosRestController {
	
	@Autowired
	private LibrosService servicios;
	
	@GetMapping("/libros")
	public ResponseEntity<List<Libro>> consultarLibros() {
		
		ResponseEntity<List<Libro>> response = servicios.verLiros();
		return response;
	}
	
	@PostMapping("/libros")
	public ResponseEntity<Libro> crear(@RequestBody Libro request){
		ResponseEntity<Libro> response = servicios.crearLibro(request);
		return response;
	}

}
