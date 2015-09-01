package com.github.aureliano.verbum_domini.query;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public class QueryBuilder {
	
	private static final String REGEX = "^SELECT\\s((\\*)|([\\w\\d\\s,]+))\\sFROM\\s[\\w\\d]+(\\sWHERE\\s[\\w\\d]+\\s=\\s\\?(\\s(AND|OR)\\s[\\w\\d]+\\s=\\s\\?)*)?$";

	private StringBuilder query;
	private List<Object> filters;
	
	public QueryBuilder() {
		this.query = new StringBuilder();
		this.filters = new ArrayList<>();
	}
	
	public QueryBuilder select(String...columns) {
		if (columns.length == 0) {
			columns = new String[] { "*" };
		}
		
		this.query.append("SELECT ");
		boolean started = false;
		for (String column : columns) {
			if (started) {
				this.query.append(",");
			}
			this.query.append(column);
			started = true;
		}
		
		return this;
	}
	
	public QueryBuilder from(String table) {
		this.query.append(" FROM ").append(table);
		return this;
	}
	
	public QueryBuilder where(FilterOperator operator, String columnName, Object value) {
		this.query.append(" ");
		if (this.filters.isEmpty()) {
			this.query.append("WHERE ");
		}
		
		if ((!this.filters.isEmpty()) && (operator != null)) {
			this.query.append(operator).append(" ");
		}
		
		this.filters.add(value);
		this.query.append(columnName).append(" = ?");
		
		return this;
	}
	
	public String build() {
		if (!this.isValidSql()) {
			throw new VerbumDominiException("Invalid query [" + this.query.toString() +
					"]. Exptected to match " + REGEX);
		}
		return this.query.toString();
	}
	
	public List<Object> getFilters() {
		return this.filters;
	}
	
	protected boolean isValidSql() {
		return this.query.toString().matches(REGEX);
	}
}