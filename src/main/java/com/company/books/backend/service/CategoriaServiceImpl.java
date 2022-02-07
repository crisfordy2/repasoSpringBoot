package com.company.books.backend.service;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDao;
import com.company.books.backend.response.CategoriaResponseRest;

@Service
public class CategoriaServiceImpl implements ICategoriaService{
	
	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);
	
	@Autowired
	private ICategoriaDao categoriaDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarCategorias() {	
		log.info("inicio metodo buscarCategorias()");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();
			response.getCategoriaResponse().setCategoria(categoria);
			
			response.setMetadata("respuesta Ok", "00", "Respuesta exitosa");
			
		} catch (Exception e) {
			response.setMetadata("respuesta nok", "-1", "Respuesta incorrecta");
			log.error("Error al consultar categoria", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}
	

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id) {
		log.info("inicio metodo buscarPorId()");
		
		CategoriaResponseRest response = new CategoriaResponseRest();	
		List<Categoria> list = new ArrayList<Categoria>();
		
		try {
			Optional<Categoria> categoria = categoriaDao.findById(id);
			
			if(categoria.isPresent()) {
				list.add(categoria.get());
				response.getCategoriaResponse().setCategoria(list);
			}else {
				log.info("Error categoria no encontrada");
				response.setMetadata("respuesta nok", "-1", "categoria no encontrada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			log.info("Error categoria no encontrada");
			response.setMetadata("respuesta nok", "-1", "categoria no encontrada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
		}
		
		response.setMetadata("respuesta Ok", "00", "Respuesta exitosa");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);		
	}


	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> crear(Categoria request) {
		log.info("inicio metodo Crear()");
		
		CategoriaResponseRest response = new CategoriaResponseRest();	
		List<Categoria> list = new ArrayList<Categoria>();
		
		try {
			Categoria categoriaGuardada = categoriaDao.save(request);
			if(categoriaGuardada != null) {
				list.add(categoriaGuardada);
				response.getCategoriaResponse().setCategoria(list);
			}else {
				log.info("Error categoria no encontrada");
				response.setMetadata("respuesta nok", "-1", "categoria no creada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			log.info("Error categoria no encontrada");
			response.setMetadata("respuesta nok", "-1", "categoria no creada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
		}
		
		response.setMetadata("respuesta Ok", "00", "categoria creada");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}


	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id) {
		
		log.info("inicio metodo Actualizar");
		
		CategoriaResponseRest response = new CategoriaResponseRest();	
		List<Categoria> list = new ArrayList<Categoria>();
		
		try {
			Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
			
			if(categoriaBuscada.isPresent()) {
				categoriaBuscada.get().setNombre(categoria.getNombre());
				categoriaBuscada.get().setDescripcion(categoria.getDescripcion());
				
				Categoria categoriActualizar = categoriaDao.save(categoriaBuscada.get());
				
				if(categoriActualizar != null) {
					response.setMetadata("respuesta Ok", "00", "categoria Actualizada");
					list.add(categoriActualizar);
					response.getCategoriaResponse().setCategoria(list);
				}else {
					log.info("Error categoria no actualizada");
					response.setMetadata("respuesta nok", "-1", "categoria no creada");
					return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
			}else {
				log.info("Error categoria no encontrada");
				response.setMetadata("respuesta nok", "-1", "categoria no creada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			log.info("Error categoria no encontrada", e.getMessage());
			e.getStackTrace();
			response.setMetadata("respuesta nok", "-1", "categoria no creada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
		}		
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);		
	}


	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> eliminar(Long id) {
		log.info("inicio metodo Actualizar");
		
		CategoriaResponseRest response = new CategoriaResponseRest();	
		
		
		try {
			categoriaDao.deleteById(id);
			response.setMetadata("respuesta Ok", "00", "categoria eliminada");
			
		} catch (Exception e) {
			log.info("Error categoria no encontrada", e.getMessage());
			e.getStackTrace();
			response.setMetadata("respuesta nok", "-1", "categoria no eliminada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
		}		
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);		
	}
	
	
	
	
	
}
