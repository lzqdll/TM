package com.tm.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Component
public class JqGridSearchHelper {
	// multiplesearch parameter
	protected String filters;
	// 对应prmNames的search，是否是用于查询的请求
	protected Boolean search;



	public String assemblingSQL() {
		// if (this.getSearchString() != null && this.getSearchString().length()
		// > 0) {
		// Calendar cal = Calendar.getInstance();
		// int month = Integer.parseInt(this.getSearchString());
		// cal.set(2011, month - 1, 1);
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// String starttime = df.format(cal.getTime());
		// cal.add(Calendar.MONTH, 1);
		// cal.add(Calendar.DATE, -1);
		// String endtime = df.format(cal.getTime());
		// return " AND " + this.getSearchField() + " between '" + starttime +
		// "' AND '" + endtime + "'";
		// }
		if (this.getSearch()) {
			StringBuilder ssql = new StringBuilder();
			ssql.append(" AND ");
			List<Criterion> mscriteria = new ArrayList<Criterion>();
			if (this.getFilters() != null && this.getFilters().length() > 0) {
				// 过虑的查询条件
				mscriteria.addAll(this.generateSearchCriteriaFromFilters(filters));
			}
			Criterion criterion = null;
			for (int i = 0; i < mscriteria.size(); i++) {
				criterion = mscriteria.get(i);
				if (criterion.getData().trim().length() > 0)
					// 转译查询条件
					ssql.append(this.translateOp(criterion) + " AND ");
				System.out.println("SSQ:" + ssql.toString());
			}
			// ssql.substring(0, ssql.length()-4);
			System.out.println("SSQL:" + ssql.substring(0, ssql.length() - 4));
			return ssql.substring(0, ssql.length() - 4);
		} else {
			return "";
		}
	}

	// 分析Filter查询条件，成List< Criterion>
	public List<Criterion> generateSearchCriteriaFromFilters(String filters) {
		List<Criterion> criteria = new ArrayList<Criterion>();
		JSONObject jsonobj = JSONObject.fromObject(filters);
		JSONArray rules = jsonobj.getJSONArray("rules");
		for (Object obj : rules) {
			JSONObject rule = (JSONObject) obj;
			Criterion criterion = new Criterion(rule.getString("field"), rule.getString("op"), rule.getString("data"));
			if (criterion != null)
				criteria.add(criterion);
		}
		return criteria;
	}

	public String translateOp(Criterion criterion) {
		String data = criterion.getData().trim();
		// String1,String2类型的返回值为Fild Between String1 AND String2
		System.out.println(data.split(",").length);
		if (data.split(",").length > 1) {
			return criterion.getField() + " between " + "'" + data.split(",")[0] + "'" + " AND " + "'"
					+ data.split(",")[1] + "'";
		}
		// 判断是否以数学符号开头
		String[] mathsymbol = { ">=", "<=", ">", "<" };
		for (int i = 0; i < mathsymbol.length; i++)
			if (data.startsWith(mathsymbol[i])) {
				System.out.println(mathsymbol[i].length());
				System.out.println(data.substring(mathsymbol[i].length()));
				return criterion.getField() + mathsymbol[i] + "'" + data.substring(mathsymbol[i].length()) + "' ";
			}
		// 正常字符串转译
		if (criterion.getOp().equals("bw"))
			return criterion.getField() + " like '" + criterion.getData() + "%'";
		else if (criterion.getOp().equals("eq")) {
			return criterion.getField() + "='" + criterion.getData() + "' ";
		} else if (criterion.getOp().equals("le"))
			return criterion.getField() + "<='" + criterion.getData() + "' ";
		else if (criterion.getOp().equals("ge"))
			return criterion.getField() + ">='" + criterion.getData() + "' ";
		else if (criterion.getOp().equals("tm"))
			return "CONVERT(varchar(12)," + criterion.getField() + ",111)='" + criterion.getData() + "' ";
		else
			return criterion.getField() + criterion.getData();
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public Boolean getSearch() {
		return search;
	}

	public void setSearch(Boolean search) {
		this.search = search;
	}
}
