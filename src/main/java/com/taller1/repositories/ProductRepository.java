package com.taller1.repositories;

import org.springframework.data.repository.CrudRepository;


import com.taller1.model.prod.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{

}
