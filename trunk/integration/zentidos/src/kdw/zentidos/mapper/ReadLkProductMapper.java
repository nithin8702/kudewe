package kdw.zentidos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import kdw.zentidos.model.Product;

import org.springframework.jdbc.core.RowMapper;

public class ReadLkProductMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Product p = new Product();
		p.setId(rowNum);
		p.setName(rs.getString("productName"));
		
		return p;
	}
}