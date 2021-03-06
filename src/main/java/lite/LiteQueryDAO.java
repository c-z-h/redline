package lite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import indi.Indi;

public class LiteQueryDAO extends JdbcDaoSupport{
	RowMapper<Lite> rowMapper;
	
	public LiteQueryDAO(){
		rowMapper=
			new RowMapper<Lite>() {
				@Override
				public Lite mapRow(ResultSet rs, int rowNum) throws SQLException {
					return 
						new Lite(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getString("corp"),
								rs.getString("book"), rs.getString("issn"), rs.getString("page"), rs.getString("abstract"),
								rs.getString("keyword"), rs.getString("doi"), rs.getString("localurl"));
				}
			};
	}
	
	public List<Lite> queryByID(String id){
		String sql="select id,title,author,corp,book,issn,page,abstract,keyword,doi,localurl from lite "+
					"where title like ?";
		return getJdbcTemplate().query(sql, new Object[] {
				"%"+id+"%"
		}, rowMapper);
		//return getJdbcTemplate().query(sql, rowMapper);
	}

	public List<Lite> queryByBook(String book){
		String sql="select id,title,author,corp,book,issn,page,abstract,keyword,doi,localurl from lite "+
				"where book like ?";
		return getJdbcTemplate().query(sql, new Object[] {
				"%"+book+"%"
		}, rowMapper);
	}
	
	public List<Lite> queryByYear(String yearStr) throws NumberFormatException{
		int year=Integer.parseInt(yearStr);
		String sql="select id,title,author,corp,book,issn,page,abstract,keyword,doi,localurl from lite "+
				"where book like ?";
		
		return getJdbcTemplate().query(sql, new Object[] {
				"%"+year+"%"
		}, rowMapper);
		
	}
	
	public List<Lite> queryByAuthor(String author){
		String sql="select id,title,author,corp,book,issn,page,abstract,keyword,doi,localurl from lite "+
				"where author like ?";
		return getJdbcTemplate().query(sql, new Object[] {
				"%"+author+"%"
		}, rowMapper);
	}
	
	public List<Lite> queryByKwd(String kwd){
		String sql="select id,title,author,corp,book,issn,page,abstract,keyword,doi,localurl from lite "+
				"where keyword like ?";
		return getJdbcTemplate().query(sql, new Object[] {
				"%"+kwd+"%"
		}, rowMapper);
	}
	
	//获取前n个点击量最大的
	public List<Lite> limitByClick(int count){
		String sql="select id,title,author,corp,book,issn,page,abstract,keyword,doi,localurl from lite "+
				"order by click desc limit 0,?";
		return getJdbcTemplate().query(sql, new Object[] {
				count
		}, rowMapper);
	}

	public void updateClick(int id){
		//实际+1的操作用数据库的触发器实现，只需提交一个update请求
		String sql="update lite set click=1 where id=?";
		getJdbcTemplate().update(sql, new Object[]{id});
	}

	public void insertLite(List<Lite> lites){
		String sql="insert into lite(title,author,corp,book,issn,page,abstract,keyword,doi) values(?,?,?,?,?,?,?,?,?)";
		int[] i=getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				Lite lite=lites.get(i);
				ps.setString(1, lite.getTitle());
				ps.setString(2, lite.getAuthor());
				ps.setString(3, lite.getCorp());
				ps.setString(4, lite.getBook());
				ps.setString(5, lite.getIssn());
				ps.setString(6, lite.getPage());
				ps.setString(7, lite.get_abstract());
				ps.setString(8, lite.getKeyword());
				ps.setString(9, lite.getDoi());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return lites.size();
			}
		});
	}
}
