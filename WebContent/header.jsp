<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">  
<title>高博起重设备</title>
<link rel="stylesheet" type="text/css"
	href="/TM/jquery-ui-1.11.4/jquery-ui.min.css">
<link rel="stylesheet" type="text/css"
	href="/TM/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/TM/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="/TM/jqGrid_JS_5.0.0/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css"
	href="/TM/datetimepicker-master/jquery.datetimepicker.css">
<link href="css/header.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="/TM/jqGrid_JS_5.0.0/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript"
	src="/TM/jqGrid_JS_5.0.0/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="/TM/jqGrid_JS_5.0.0/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="/TM/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="/TM/datetimepicker-master/jquery.datetimepicker.js"></script>
</head>
<body style="font: 15px Verdana, Arial, Helvetica,"宋体",sans-serif">
	<div id="header-wrap">
		<div id="header" class="cbody">
			<div id="language">
				<table>
					<tr>
						<td width="173"><h1>
								<a href="http://www.gorbel.com.cn/"><img
									src="http://www.gorbel.com.cn/theme/skins/2009/images/logo.png"
									alt="起重机/起重设备" /></a>
							</h1></td>
						<td width="440"><div align="center">
								<font size="+2" color="#CC6600"><b>休假&加班管理</b></font>
							</div></td>
						<td width="173">欢迎 <s:property
								value="#session.user.displayName" /><br> <a href="logout.action"
							class="logout"> 注销(Logout)</a>
						</td>
					</tr>
				</table>
			</div>
			<div class="headyyright"></div>
		</div>
	</div>
	<div id="nav">
		<ul id="nav-list">
			<li id="l01"><a href="userLeaveHome.action"> 我的假期<br/>My Leave Application</a></li>
			<li id="l02"><a href="userOTHome.action"> 加班申請<br/>My OT Application</a></li>
			<s:if test="#session.user.employeeType.contains('MGR')">
				<li id="l03"><a href="dptleavehistory.action"> 部门休假管理<br/>Manager Portal</a></li>
			</s:if>
			<s:if test="#session.user.employeeType.contains('HR')">
				<li id="l04"><a href="HRHome.action"> HR管理<br/>HR Portal</a>
					</td></li>
			</s:if>
			<li id="l05"></li>
		</ul>
	</div>