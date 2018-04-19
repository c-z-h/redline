package indi;

import java.sql.ResultSet;
import java.sql.SQLException;
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
					return new Indi(rs.getString("indiID"), rs.getInt("indiRk"), 
									rs.getString("indiRptWd1"), rs.getString("indiRptWd2"), rs.getString("indiRptWd3"),
									rs.getString("indiRptWd4"),rs.getString("indiRptWd5"));
				}
			};
	}
	
	public List<Indi> queryByID(String id){
		String sql="select indiNum,indiID,indiRk,indiRptWd1,indiRptWd2,indiRptWd3,indiRptWd4,indiRptWd5 from indi "+
				"where indiID like ?";
		return getJdbcTemplate().query(sql, new Object[] {
				"%"+id+"%"
		}, rowMapper);
	}
	public List<Indi> queryByWord(String word){
		String sql="select indiNum,indiID,indiRk,indiRptWd1,indiRptWd2,indiRptWd3,indiRptWd4,indiRptWd5 from indi "+
				"where indiRptWd1 like ? or indiRptWd2 like ? "+
				"or indiRptWd3 like ? or indiRptWd4 like ? or indiRptWd5 like ?";
		return getJdbcTemplate().query(sql, new Object[] {
				"%"+word+"%",
				"%"+word+"%",
				"%"+word+"%",
				"%"+word+"%",
				"%"+word+"%",
		}, rowMapper);
	}
}
