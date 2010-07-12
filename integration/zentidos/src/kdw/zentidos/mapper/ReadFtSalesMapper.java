package kdw.zentidos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import kdw.core.model.DateValue;
import kdw.core.model.LkSimple;
import kdw.zentidos.model.Product;
import kdw.zentidos.model.Sale;

import org.springframework.jdbc.core.RowMapper;

public class ReadFtSalesMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Product p = new Product();
		p.setId(1);
		
		LkSimple ageRange = new LkSimple();
		ageRange.setId(1);
		
		Sale s = new Sale();
		s.setAgeRange(ageRange);
		s.setDate(new DateValue(rs.getDate("fecha")));
		s.setPrice(rs.getBigDecimal("precio"));
		s.setProduct(p);
		s.setQuantity(rs.getInt("cantidad"));
		
		return s;
	}
}
