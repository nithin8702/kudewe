package kdw.zentidos.repository.jdbc;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import kdw.zentidos.model.Product;
import kdw.zentidos.repository.LkProductRepository;

public class LkProductRepositoryJdbc extends SimpleJdbcDaoSupport implements LkProductRepository {
	private ParameterizedRowMapper<Product> rowMapper;
	
	public void setRowMapper(ParameterizedRowMapper<Product> rowMapper) {
		this.rowMapper = rowMapper;
	}
	
	@Override
	public Product getByDescription(String productName) {
		Product product = null;
		
		List<Product> products = getSimpleJdbcTemplate().query(
                "select product_id, product_name, category_name from lk_product where product_name = :product_name",
                rowMapper,
                new MapSqlParameterSource().addValue("product_name", productName));
        
		if (products.size() > 0) {
			product = products.get(0);
		}
		
		return product;
	}

}
