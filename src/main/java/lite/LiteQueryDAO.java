package lite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class LiteQueryDAO extends JdbcDaoSupport{
	RowMapper<Lite> rowMapper;
	
	public LiteQueryDAO(){
		rowMapper=
			new RowMapper<Lite>() {
				@Override
				public Lite mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Lite(rs.getString("liteID"), rs.getString("bookID"), rs.getInt("liteY"),
									rs.getString("liteAu1"), rs.getString("liteAu2"), rs.getString("liteAu3"),
									rs.getString("liteKwd1"), rs.getString("liteKwd2"), rs.getString("liteKwd3"));
				}
			};
	}
	
	public List<Lite> queryByID(String id){
		String sql="select liteID,bookID,liteY,liteAu1,liteAu2,liteAu3,liteKwd1,liteKwd2,liteKwd3 from lite "+
					"where liteID like ?";
		return getJdbcTemplate().query(sql, new Object[] {
				"%"+id+"%"
		}, rowMapper);
		//return getJdbcTemplate().query(sql, rowMapper);
	}

	public List<Lite> queryByBook(String book){
		String sql="select liteID,bookID,liteY,liteAu1,liteAu2,liteAu3,liteKwd1,liteKwd2,liteKwd3 from lite "+
				"where bookID like ?";
		return getJdbcTemplate().query(sql, new Object[] {
				"%"+book+"%"
		}, rowMapper);
	}
	
	public List<Lite> queryByYear(String yearStr) throws NumberFormatException{
		int year=Integer.parseInt(yearStr);
		String sql="select liteID,bookID,liteY,liteAu1,liteAu2,liteAu3,liteKwd1,liteKwd2,liteKwd3 from lite "+
				"where liteY=?";
		return getJdbcTemplate().query(sql, new Object[] {
				year
		}, rowMapper);
	}
	
	public List<Lite> queryByAuthor(String author){
		String sql="select liteID,bookID,liteY,liteAu1,liteAu2,liteAu3,liteKwd1,liteKwd2,liteKwd3 from lite "+
				"where liteAu1 like ? or liteAu2 like ? or liteAu3 like ?";
		return getJdbcTemplate().query(sql, new Object[] {
				"%"+author+"%",
				"%"+author+"%",
				"%"+author+"%",
		}, rowMapper);
	}
	
	public List<Lite> queryByKwd(String kwd){
		String sql="select liteID,bookID,liteY,liteAu1,liteAu2,liteAu3,liteKwd1,liteKwd2,liteKwd3 from lite "+
				"where liteKwd1 like ? or liteKwd2 like ? or liteKwd3 like ?";
		return getJdbcTemplate().query(sql, new Object[] {
				"%"+kwd+"%",
				"%"+kwd+"%",
				"%"+kwd+"%",
		}, rowMapper);
	}
}
