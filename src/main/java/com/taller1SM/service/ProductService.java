package com.taller1SM.service;

import com.taller1SM.model.prod.Product;

public interface ProductService {

	public void saveProduct(Product product, Integer prCategoryId, Integer prScategoryId);

	public void editProduct(Product product, Integer prCategoryId, Integer prSubcategoryId);
}
