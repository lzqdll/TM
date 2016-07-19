<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@include file="../..//header.jsp"%>
<link href="css/leavehistory.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/TM/js/departmentpage.js"></script>
<script type="text/javascript" src="/TM/js/pendingLeaveApplication.js"></script>
<script type="text/javascript" src="/TM/js/pendingOTApplication.js"></script>
<label>.</label>
<div class="easyui-tabs" style="width: auto; height: auto">
	<div title="待审批休假申请-Pending Leave" style="padding: 10px">
		<div align="center">
			<table id="pendingleaveGrid"></table>
			<div id="pendingleaveGridPager"></div>
		</div>
	</div>
	<div title="待审批加班申请-Pending OT" style="padding: 10px">
		<div align="center">
			<table id="pendingOTGrid"></table>
			<div id="pendingOTGridPager"></div>
		</div>
	</div>
	<div title="部门休假记录" style="padding: 10px">
		<input type="text" id=usrName> <input type="submit"
			onclick="search()">
		<div>
			<table id="deptleavegrid"></table>
			<div id="deptleavegridpager"></div>
		</div>
		<div>
			<table id="leavebalancetb"></table>
			<div id="lbgridpager"></div>
		</div>
	</div>
</div>
<%@include file="../../footer.jsp"%>
