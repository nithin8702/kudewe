package kdw.aliverti.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import kdw.aliverti.model.Brand;
import kdw.aliverti.model.Category;
import kdw.aliverti.model.Product;

import org.springframework.jdbc.core.RowMapper;

public class ReadLkProductMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Brand b = new Brand()
		 	.setId(rs.getInt("id_marca"))
		 	.setName(rs.getString("m_nombre"));
		
		Category c3 = new Category()
			.setId(rs.getInt("c3_id"))
			.setName(rs.getString("c3_nombre"));
		
		Category c2 = new Category()
			.setId(rs.getInt("c2_id"))
			.setName(rs.getString("c2_nombre"));
		
		Category c1 = new Category()
			.setId(rs.getInt("c1_id"))
			.setName(rs.getString("c1_nombre"));
		
		Product p = new Product()
			.setId(rs.getString("sku"))
			.setName(rs.getString("p_nombre"))
			.setBrand(b)
			.setCategory3(c3)
			.setCategory2(c2)
			.setCategory1(c1);
		
		return p;
	}
}