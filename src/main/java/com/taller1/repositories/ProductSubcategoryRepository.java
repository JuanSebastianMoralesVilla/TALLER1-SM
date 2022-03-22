package com.taller1.repositories;

import org.springframework.data.repository.CrudRepository;

import com.taller1.model.prod.Productinventory;
import com.taller1.model.prod.Productsubcategory;

public interface ProductSubcategoryRepository extends CrudRepository<Productsubcategory, Integer>{

}
