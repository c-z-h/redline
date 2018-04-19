package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class MessageInplDAO extends JdbcDaoSupport implements MessageDAO {

	public int insertMessage(Message message) {
		String sql = "insert into message(cookie,post_time,content,idRef) values(?,?,?,?)";
		int i;
		i=getJdbcTemplate().update(sql, new Object[] {message.getCookie(), message.getPostTime(), message.getContent(), 
				message.getIdRef()==0?null:message.getIdRef()});
			
		return i;
	}

	public int selectMessage() {
		String sql = "select * from message";
		List<Map<String, Object>> list=getJdbcTemplate().queryForList(sql);
		ArrayList<Message> messages=new ArrayList<Message>();
		for (Map<String, Object> map : list) {
			Message message=new Message();
			message.setContent((String)map.get("content"));
			message.setCookie((String)map.get("cookie"));
			message.setId((Integer)map.get("id"));
			Object idRef=map.get("idRef");
			if (idRef!=null) 
				message.setIdRef((Integer)idRef);
			message.setPostTime((Timestamp)map.get("post_time"));			
		}
		return 0;
	}

	class MessageRowMapper implements RowMapper<Message>{

		public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
			Message message=new Message();
			message.setContent(rs.getString("content"));
			message.setCookie(rs.getString("cookie"));
			message.setId(rs.getInt("id"));
			message.setIdRef(rs.getInt("idRef"));
			message.setPostTime(rs.getTimestamp("post_time"));
			return message;
		}
		
	}
}
