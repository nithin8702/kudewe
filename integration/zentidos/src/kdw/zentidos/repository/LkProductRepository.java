package kdw.zentidos.repository;

import kdw.zentidos.model.Product;

public interface LkProductRepository {
	Product getByDescription(String productDescription);
}
