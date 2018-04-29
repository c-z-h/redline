package indi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

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
}
