package kdw.aliverti.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import kdw.aliverti.model.DateValue;
import kdw.aliverti.model.Product;
import kdw.aliverti.model.StockItem;

import org.springframework.jdbc.core.RowMapper;

public class ReadFtStockMapper implements RowMapper {
	private DateValue date;
	
	public ReadFtStockMapper(){
		// Set first day of month
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		date = new DateValue(calendar.getTime());
	}
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product p = new Product()
		.setId(rs.getString("sku"));
	
	BigDecimal currency = new BigDecimal(100);
	
	StockItem s = new StockItem()
		.setProduct(p)
		.setCost(rs.getBigDecimal("costo").divide(currency))
		.setQuantity(
			rs.getInt("stock")
			+ rs.getInt("stock_xxs")
			+ rs.getInt("stock_xs")
			+ rs.getInt("stock_s")
			+ rs.getInt("stock_m")
			+ rs.getInt("stock_l")
			+ rs.getInt("stock_xl")
			+ rs.getInt("stock_xxl")
		)
		.setDate(date);
	
	return s;
	}

}
