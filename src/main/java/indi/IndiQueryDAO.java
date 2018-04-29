package indi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import lite.Lite;

public class IndiQueryDAO extends JdbcDaoSupport{
	RowMapper<Indi> rowMapper;
	
	public IndiQueryDAO() {
		rowMapper=
			new RowMapper<Indi>(){
				@Override
				public Indi mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Indi(rs.getString("name"), rs.getDouble("weight"), rs.getString("source"));
				}
			};
	}
	
	public List<Indi> queryById(int id){
		String sql="select name,weight,source from indi "+
				"where sys=?";
		return getJdbcTemplate().query(sql, new Object[] {
				id
		}, rowMapper);
	}
	
}
