<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@include file="../../header.jsp"%>
<script type="text/javascript">
	$(function() {
		jQuery("#applicationtb").jqGrid({
			url : 'json/managerGridView?searchTable=PendingApplication',
			datatype : "json",
			height : '130',
			emptyrecords : '当前没有待审批的申请',
			viewrecords : true,
			mtype : "GET",
			colModel : [ {
				name : 'id',
				index : 'id',
				hidden : true
			}, {
				name : 'user.usrName',
				label : '申请人'
			}, {
				name : 'attType.description',
				index : 'attType.description',
				label : '类型',
			//	width : 200
			}, {
				name : 'status',
				label : '申请状态',
			}, {
				name : 'duration',
				label : '时长'
			}, {
				name : 'attType.id',
				label : '类型',
				hidden : true,
			//	width : 200
			}, {
				name : 'startTime',
				index : 'startTime',
				label : '开始时间',

			}, {
				name : 'endTime',
				index : 'endTime',
				label : '结束时间',
			//		width : 300
			}, {
				name : 'submitTime',
				label : '申请时间',
			} ],
			jsonReader : {
				root : "gridData",
				repeatitems : false,
			},
			onSelectRow : function(id) {
				$.messager.defaults = {
					ok : "是(Yes)",
					cancel : "否(No)",
					width : 250
				};
				$.messager.confirm('审批', '同意该申请(Agree)?', function(r) {
					$.ajax({
						url : ' ajax/CheckApplicationAction',
						data : {
							id : id,
							result : r,
							type : 'leave'
						},
						success : function(data) {
							alert(data.tips)
						},
						error : function() {
							alert('error')
						}
					});
				});
			},
			pager : '#gridpager',
			editurl : "ajax/EditApplicationRecordAction",
			rowNum : 15,
			rowList : [ 5, 15, 30 ],
		}).navGrid("#gridpager", {
			edit : false,
			add : false,
			del : false
		});
	})
</script>
<div style="height: 700px">
	<h2>
		<a href="dptleavehistory">部门休假记录</a>
	</h2>
	<div align="center">
		<table id="applicationtb" align="center"></table>
		<div id="gridpager"></div>
		<div style="margin-top: 100px">
			<table id="leavebalancetb"></table>
			<div id="lbgridpager"></div>
		</div>
	</div>
</div>
<%@include file="../../footer.jsp"%>
