package kdw.aliverti.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import kdw.aliverti.model.DateValue;
import kdw.aliverti.model.Product;
import kdw.aliverti.model.SaleItem;

import org.springframework.jdbc.core.RowMapper;

public class ReadFtSalesMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product p = new Product()
			.setId(rs.getString("sku"));
		
		BigDecimal currency = new BigDecimal(100);
		
		SaleItem s = new SaleItem()
			.setProduct(p)
			.setCost(rs.getBigDecimal("costo").divide(currency))
			.setPrice(rs.getBigDecimal("precio").divide(currency))
			.setQuantity(rs.getInt("cantidad"))
			.setDate(new DateValue(rs.getDate("fecha")));
		
		return s;
	}
}
