package indi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class IndiSysQueryDAO extends JdbcDaoSupport{
	RowMapper<IndiSys> sysRowMapper;
	RowMapper<Indi> indiRowMapper;
	
	public IndiSysQueryDAO() {
		sysRowMapper=
			new RowMapper<IndiSys>(){
				@Override
				public IndiSys mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new IndiSys(rs.getString("name"), rs.getInt("id"));
				}
			};
		indiRowMapper=
			new RowMapper<Indi>(){
				@Override
				public Indi mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Indi(rs.getInt("sys"));
				}
			};
	}
	
	public List<IndiSys> queryBySys(String sys){
		String sql="select id,name from indisys "+
				"where name like ?";
		return getJdbcTemplate().query(sql, new Object[] {
				"%"+sys+"%"
		}, sysRowMapper);
	}
	
	public IndiSys queryBySys(int sys){
		String sql="select id,name from indisys "+
				"where id=?";
		return getJdbcTemplate().query(sql, new Object[] {
				sys
		}, sysRowMapper).get(0);
	}
	
	public List<IndiSys> queryByIndi(String indi){
		String sql="select distinct sys from indi "+
				"where name like ?";
		List<Indi> indis=getJdbcTemplate().query(sql, new Object[] {
				"%"+indi+"%"
		}, indiRowMapper);
		List<IndiSys> syses=new LinkedList<>();
		for (Indi i : indis) {
			syses.add(queryBySys(i.getSys()));
		}
		return syses;
	}

	public int insertSys(String name){
		KeyHolder keyHolder=new GeneratedKeyHolder();
		
		String sql="insert into indisys(name) values(?)";
		
		try{			
			getJdbcTemplate().update(new PreparedStatementCreator() {				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps=con.prepareStatement(sql, new String[]{"id"});
					ps.setString(1, name);
					return ps;
				}
			}, keyHolder);
			return keyHolder.getKey().intValue();
		}
		catch(DuplicateKeyException e){
			return 0;
		}
		catch(Exception e){
			return -1;
		}
	}
}
