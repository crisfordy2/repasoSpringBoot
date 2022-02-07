package com.company.books.backend.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.books.backend.model.Libro;

public interface ILibrosDao extends CrudRepository<Libro, Long>{

}
