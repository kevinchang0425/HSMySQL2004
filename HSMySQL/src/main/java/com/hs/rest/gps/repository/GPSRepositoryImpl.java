package com.hs.rest.gps.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hs.rest.gps.model.GPS;

//資料庫要提供哪些服務，依照規格interface實作
@Repository
public class GPSRepositoryImpl implements GPSRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public List<GPS> findAllGps() {
		String sql = "select id, latitude, longitude, meter, location, locationName from gps";
		List<GPS> gpsList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GPS.class));
		return gpsList;
	}

	@Override
	public GPS getGpsByID(Integer id) {
		String sql = "select id, latitude, longitude, meter, location, locationName from gps where id = ?";
		try {
			GPS gps = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(GPS.class), id);
			return gps;
		} catch (Exception e) {  //若沒有找到會發生例外
			return null;
		}
	}

	@Override
	public GPS getGpsByLocationName(String locationName) {
		String sql = "select id, latitude, longitude, meter, location, locationName from gps where locationName = ?";
		try {
			GPS gps = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(GPS.class), locationName);
			return gps;
		} catch (Exception e) {  //若沒有找到會發生例外
			return null;
		}
	}

	@Override
	public Boolean addGps(GPS gps) {
		String sql = "insert into gps(latitude, longitude, meter, location, location_name) values (?, ?, ?, ?, ?)";
		int rowcount = jdbcTemplate.update(sql, gps.getLatitude(), gps.getLongitude(), gps.getMeter(), gps.getLocation(), gps.getLocationName());
		return rowcount > 0;  //異動筆數，如果0則表示異動失敗
	}

	@Override
	public Boolean updateGps(GPS gps) {
		String sql = "update gps set latitude=?, longitude=?, meter=?, location=?, location_name=? where id=?";
		int rowcount = jdbcTemplate.update(sql, gps.getLatitude(), gps.getLongitude(), gps.getMeter(), gps.getLocation(), gps.getLocationName(), gps.getId());
		return rowcount > 0;  //異動筆數，如果0則表示異動失敗
	}

	@Override
	public Boolean deleteGPSByID(Integer id) {
		String sql = "delete from gps where id = ?";
		int rowcount = jdbcTemplate.update(sql, id);
		return rowcount > 0;  //異動筆數，如果0則表示異動失敗
	}

}
