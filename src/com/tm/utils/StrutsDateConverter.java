package com.tm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class StrutsDateConverter extends DefaultTypeConverter {
	private static final String DATETIME_PATTERN = "yyyy/MM/dd HH:mm";

	@Override
	public Object convertValue(Map<String, Object> context, Object value, Class toType) {
		// TODO Auto-generated method stub
		SimpleDateFormat f = new SimpleDateFormat(DATETIME_PATTERN); // 转换为自己想要日期格式

		try {
			if (toType == Date.class && value instanceof String[]) { // 如果想转换的是Date类型时将做以下操作
				// 因为在Struts2里会表单传过来的非字符串数据转换为String[],所以这里得取第一个值
				String dataStr = ((String[]) value)[0];
				// System.out.println(dataStr);
				if (dataStr.length() > 0)
					return f.parse(dataStr);
			} else if (toType == String.class && value instanceof Date) {
				String dataStr = f.format(value);
				return dataStr;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.convertValue(context, value, toType);
	}
}
