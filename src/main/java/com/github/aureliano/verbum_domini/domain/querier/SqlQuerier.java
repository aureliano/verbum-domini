package com.github.aureliano.verbum_domini.domain.querier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.persistence.config.ResultSetConcurrency;
import org.eclipse.persistence.config.ResultSetType;

import com.github.aureliano.verbum_domini.AppConfiguration;
import com.github.aureliano.verbum_domini.db.ConnectionSingleton;
import com.github.aureliano.verbum_domini.domain.bean.IBean;
import com.github.aureliano.verbum_domini.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.helper.PropertyHelper;

public class SqlQuerier implements IQuerier {

	private static SqlQuerier instance;
	
	private Properties queries;
	private String databaseName;
	
	private SqlQuerier() {
		this.queries = PropertyHelper.loadProperties("domain-queries.properties");
		this.databaseName = AppConfiguration.instance().getProperty("database.application.name");
	}
	
	public static SqlQuerier instance() {
		if (instance == null) {
			instance = new SqlQuerier();
		}
		
		return instance;
	}
	
	@Override
	public <T extends IBean> Integer count(Class<T> type) {
		Integer count = null;
		String sql = this.queries.getProperty(this.beanKey(type) + ".relational." + this.databaseName + ".count");
		
		try (
			PreparedStatement ps = ConnectionSingleton.instance()
				.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
		) {
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
		
		return count;
	}

	@Override
	public Map<String, Object> get(IBean bean) {
		Map<String, Object> map = null;
		String sql = this.queries.getProperty(this.beanKey(bean.getClass()) + ".relational. " + this.databaseName + ".find.by.id");
		
		try (
			PreparedStatement ps = ConnectionSingleton.instance()
				.getConnection().prepareStatement(sql);
		) {
			ps.setInt(1, bean.getId());
			ResultSet rs = ps.executeQuery();
			
			map = new HashMap<>();
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			int rows = 0;
			
			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
					map.put(metaData.getColumnName(i), rs.getObject(i));
				}
				
				if (++rows > 1) {
					throw new VerbumDominiException(this.getClass().getName() +
						".get(" + bean.getClass().getName() + ") got more than one result with query " + sql +
						" and id => " + bean.getId());
				}
			}
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
		
		return (map.isEmpty() ? null : map);
	}

	@Override
	public <T extends IBean> List<Map<String, Object>> find(Class<T> type, int offset, int limit) {
		List<Map<String, Object>> list = null;
		String sql = this.queries.getProperty(this.beanKey(type) + ".relational." + this.databaseName + ".find.all");
		
		try (
			PreparedStatement ps = ConnectionSingleton.instance()
				.getConnection().prepareStatement(
					sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		) {
			ps.setFetchSize(limit);
			ps.setMaxRows(limit);
			ps.setFetchDirection(ResultSet.FETCH_FORWARD);
			
			ps.setInt(1, offset);
			ps.setInt(2, limit);
			ResultSet rs = ps.executeQuery();
			
			list = new ArrayList<>();
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			
			while (rs.next()) {
				Map <String, Object> map = new HashMap<>();
				for (int i = 1; i <= columns; i++) {
					map.put(metaData.getColumnName(i), rs.getObject(i));
				}
				list.add(map);
			}
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
		
		return list;
	}

	@Override
	public <T extends IBean> List<Map<String, Object>> find(Class<T> type, String filterName,
			Object filterValue, int offset, int limit) {
		
		List<Map<String, Object>> list = null;
		String sql = this.queries.getProperty(this.beanKey(type) + ".relational." + this.databaseName + ".find.by." + filterName);
		
		try (
			PreparedStatement ps = ConnectionSingleton.instance()
				.getConnection().prepareStatement(
					sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		) {
			ps.setFetchSize(limit);
			ps.setMaxRows(limit);
			ps.setFetchDirection(ResultSet.FETCH_FORWARD);
			
			ps.setObject(1, filterValue);
			ps.setInt(2, offset);
			ps.setInt(3, limit);
			ResultSet rs = ps.executeQuery();
			
			list = new ArrayList<>();
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			
			while (rs.next()) {
				Map <String, Object> map = new HashMap<>();
				for (int i = 1; i <= columns; i++) {
					map.put(metaData.getColumnName(i), rs.getObject(i));
				}
				list.add(map);
			}
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
		
		return list;
	}
	
	private String beanKey(Class<?> type) {
		return type.getSimpleName().replaceFirst("Bean$", "").toLowerCase();
	}
}