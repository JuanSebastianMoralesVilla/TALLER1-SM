package com.taller1.service;

import com.taller1.model.prod.Product;

public interface ProductService {

	public void saveProduct(Product product, Integer prCategoryId, Integer prScategoryId);

	void editProduct(Product product, Integer prCategoryId, Integer prSubcategoryId);
}
