package indi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
	
	public int insertIndis(List<Indi> indis){
		String sql="insert into indi(sys,name,weight,source) values(?,?,?,?)";
		int[] i=getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				Indi indi=indis.get(i);
				ps.setInt(1, indi.getSys());
				ps.setString(2, indi.getName());
				ps.setDouble(3, indi.getWeight());
				ps.setString(4, indi.getSource());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return indis.size();
			}
		});
		int sum=0;
		for (int j : i) {
			sum+=j;
		}
		return sum;
	}
}
