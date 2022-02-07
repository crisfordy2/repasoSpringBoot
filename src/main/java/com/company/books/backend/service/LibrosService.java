package com.company.books.backend.service;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.ILibrosDao;

@Service
public class LibrosService {
	
	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);
	
	@Autowired
	private ILibrosDao librosDao;
	
	@Transactional(readOnly = true)
	public ResponseEntity<List<Libro>> verLiros() {
		
		List<Libro> libros = (List<Libro>) librosDao.findAll();
		log.info("se jecuto el list");
		return new ResponseEntity<List<Libro>>(libros, HttpStatus.OK);
	}
		
	
	@Transactional()
	public ResponseEntity<Libro> crearLibro(Libro request) {
		
		log.info("llego");
		Libro libroNuevo = librosDao.save(request);
		return new ResponseEntity<Libro>(libroNuevo, HttpStatus.OK);
	}
	
	
	

}
