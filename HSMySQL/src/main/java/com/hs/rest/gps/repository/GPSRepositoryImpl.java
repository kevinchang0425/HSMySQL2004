package com.hs.rest.gps.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hs.rest.gps.model.po.GPS;

//資料庫要提供哪些服務，依照規格interface實作
@Repository
public class GPSRepositoryImpl implements GPSRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<GPS> queryAllGps() {
		String sql = "select id, latitude, longitude, meter, location, location_Name from gps";
		List<GPS> gpsList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GPS.class));
		return gpsList;
	}

	@Override
	public GPS getGpsByID(Integer id) {
		String sql = "select id, latitude, longitude, meter, location, location_Name from gps where id = ?";
		try {
			GPS gps = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(GPS.class), id);
			return gps;
		} catch (Exception e) { // 若沒有找到會發生例外
			return null;
		}
	}

	@Override
	public GPS getGpsByLocationName(String locationName) {
		String sql = "select id, latitude, longitude, meter, location, location_Name from gps where locationName = ?";
		try {
			GPS gps = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(GPS.class), locationName);
			return gps;
		} catch (Exception e) { // 若沒有找到會發生例外
			return null;
		}
	}

	// 接收來自GPSservice組裝完成的Gps(gps)物件
	@Override
	public Boolean addGps(GPS gps) {
		String sql = "insert into gps(latitude, longitude, meter, location, location_name) values (?, ?, ?, ?, ?)";
		int rowcount = jdbcTemplate.update(sql, gps.getLatitude(), gps.getLongitude(), gps.getMeter(),
				gps.getLocation(), gps.getLocationName());
		return rowcount > 0; // 異動筆數，如果0則表示異動失敗
	}

	// 完整更新，同時每個欄位都要有
	//	@Override
	//	public Boolean updateGps(GPS gps) {
	//		String sql = "update gps set latitude=?, longitude=?, meter=?, location=?, location_name=? where id=?";
	//		int rowcount = jdbcTemplate.update(sql, gps.getLatitude(), gps.getLongitude(), gps.getMeter(), gps.getLocation(), gps.getLocationName(), gps.getId());
	//		return rowcount > 0;  //異動筆數，如果0則表示異動失敗
	//	}

	// 改寫成動態更新
	// 動態生成 update sql 的敘述,
	// 以下是可能動態生成 sql 的範例:
	// UPDATE gps SET latitude = ?, longitude = ? WHERE id = ?;
	// UPDATE gps SET latitude = ?, longitude = ?, meter = ? WHERE id = ?;
	// UPDATE gps SET meter = ? WHERE id = ?;
	// UPDATE gps SET location = ?, location_name = ? WHERE id = ?;
	// UPDATE gps SET latitude = ?, meter = ? WHERE id = ?;
	// 用StringBuilder將SQL敘述拼裝起來
	// 加入SQL開頭敘述：("UPDATE gps SET ")
	// 生成對應的SQL參數params以對應每一個?
	@Override
	public Boolean updateGps(GPS gps) {
		StringBuilder sqlBuilder = new StringBuilder("UPDATE gps SET ");
		List<Object> params = new ArrayList<>();

		// 依次檢查每個字段是否為 null，並相應地構建 SQL 語句
		if (gps.getLatitude() != null) {
			sqlBuilder.append("latitude = ?, ");
			params.add(gps.getLatitude());
		}
		if (gps.getLongitude() != null) {
			sqlBuilder.append("longitude = ?, ");
			params.add(gps.getLongitude());
		}
		if (gps.getMeter() != null) {
			sqlBuilder.append("meter = ?, ");
			params.add(gps.getMeter());
		}
		if (gps.getLocation() != null) {
			sqlBuilder.append("location = ?, ");
			params.add(gps.getLocation());
		}
		if (gps.getLocationName() != null) {
			sqlBuilder.append("location_name = ?, ");
			params.add(gps.getLocationName());
		}

		// 檢查是否有至少一個字段需要更新
		if (params.isEmpty()) {
			// 所有字段都是 null，不執行更新操作
			return false;
		}

		// 移除最後的逗號和空格
		sqlBuilder.setLength(sqlBuilder.length() - 2);

		// 添加 WHERE 條件
		sqlBuilder.append(" WHERE id = ?");
		params.add(gps.getId());

		// 執行更新操作
		// sqlBuilder 動態字串，可以伸縮建立較方便，但API規定要轉成傳統字串 toString()
		// params API規定集合要轉成傳統陣列 toArray()
		int rowCount = jdbcTemplate.update(sqlBuilder.toString(), params.toArray());
		return rowCount > 0;
	}

	@Override
	public Boolean deleteGPSByID(Integer id) {
		String sql = "delete from gps where id = ?";
		int rowcount = jdbcTemplate.update(sql, id);
		return rowcount > 0; // 異動筆數，如果0則表示異動失敗
	}

}
