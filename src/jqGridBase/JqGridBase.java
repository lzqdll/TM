package jqGridBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionSupport;
import com.tm.utils.JqGridSearchHelper;

public class JqGridBase extends ActionSupport {
	protected boolean _search;// 表示是否是搜索请求的参数名称
	protected String filters;
	protected long nd;// 表示已经发送请求的次数的参数名称
	protected int page;// 表示请求页码的参数名称
	protected int rows;// 表示请求行数的参数名称
	protected String sidx;// 表示用于排序的列名的参数名称
	protected String sord;// 表示采用的排序方式的参数名称
	protected int total; // json中代表页码总数的数据
	protected int records; // json中代表数据行总数的数据
	protected String searchField;
	protected String searchOper;
	protected String searchString;
	protected String searchSQL;
	protected List gridData = new ArrayList<>();
	protected JqGridSearchHelper jqgsHelper;

	protected String assembleSQL() {
		if (this.getSearchString() != null && this.getSearchString().length() > 0) {
			if (this.searchOper.equals("eq"))
				searchSQL = " AND " + this.getSearchField() + " = '" + this.getSearchString() + "'";
		} else
			searchSQL = "";
		if (this.filters != null && this.getFilters().length() > 0) {
			this.getJqgsHelper().setSearch(this._search);
			jqgsHelper.setFilters(this.getFilters());
			searchSQL += this.getJqgsHelper().assemblingSQL();
		}
		if (this.getSidx() != null && this.getSidx().length() > 0) {
			searchSQL = searchSQL + " order by " + this.getSidx();
			if (this.getSord() != null)
				searchSQL = searchSQL +" "+ this.getSord();
		}
		// System.out.println(searchSQL);
		return searchSQL;
	}

	public boolean is_search() {
		return _search;
	}

	public void set_search(boolean _search) {
		this._search = _search;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public long getNd() {
		return nd;
	}

	public void setNd(long nd) {
		this.nd = nd;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public List getGridData() {
		return gridData;
	}

	public void setGridData(List gridData) {
		this.gridData = gridData;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public JqGridSearchHelper getJqgsHelper() {
		return jqgsHelper;
	}

	@Resource
	public void setJqgsHelper(JqGridSearchHelper jqgsHelper) {
		this.jqgsHelper = jqgsHelper;
	}

}
