package kdw.zentidos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import kdw.zentidos.model.Product;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class LkProductParametrizedMapper implements ParameterizedRowMapper<Product> {
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product prod = new Product();
        prod.setId(rs.getInt("product_id"));
        prod.setName(rs.getString("product_name"));
        return prod;
    }
}