package weighting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class WeightingDAO extends JdbcDaoSupport{
	public List<EristPoint> getAllPoints() {
		String sql="select distinct longitude,latitude from resident_questionnaire "
				+ "union select distinct longitude,latitude from monitor_questionnaire "
				+ "union select distinct longitude,latitude from expert_questionnaire;";
		return getJdbcTemplate().query(sql, new RowMapper<EristPoint>() {
			@Override
			public EristPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new EristPoint(rs.getDouble("longitude"), rs.getDouble("latitude"));
			}
		});
	}
}
