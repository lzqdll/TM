<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@include file="../header.jsp"%>
<link href="css/hrhome.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/TM/js/hrhomepage.js"></script>
<div style="height: 100%">
	Gorbel HR
	<div style="width: 600px; padding: 10px;" class="easyui-dialog"
		data-options="closed:true" title="假期调整" id='ffdiag'>
		<form action="ajax/VacationAdjustment.action" id="ff">
			<table>
				<tr>
					<td>调整用户</td>
					<td><input type="text" name="user.usrName" required="true"></td>
				</tr>
				<tr>
					<td>假期类型</td>
					<td><input type="radio" name="attType.id" value='1'
						checked="checked"> 带薪年假 <input type="radio"
						name="attType.id" value='2'>带薪病假 <input type="radio"
						name="attType.id" value='3'>倒休假<input type="radio"
						name="attType.id" value='5'>无薪事假 <input type="radio"
						name="attType.id" value='6'>婚假 <input type="radio"
						name="attType.id" value='7'>产假/陪产假 <input type="radio"
						name="attType.id" value='8'>丧假</td>
				</tr>
				<tr>
					<td>调整假期时长</td>
					<td><input type="text" class="easyui-numberbox"
						name="adjustedduration" value="0" data-options="precision:1"></td>
				</tr>
				<tr>
					<td>生效日期</td>
					<td><input type="text" name="validTime" id='validTime'
						required="true"></td>
				</tr>
				<tr>
					<td>过期时间</td>
					<td><input type="text" name="expiredTime" id='expiredTime'></td>
				</tr>
				<tr>
					<td colspan="2"><input type="Submit" value='添加'></td>
				</tr>
			</table>
		</form>
	</div>

	<div class="easyui-tabs" style="width: auto; height: auto">
		<div title="假期调整记录" style="padding: 10px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="$('#ffdiag').dialog('open')">员工假期调整</a> 
			<a href="javascript:void(0)"
				class="easyui-linkbutton"
				onclick="$.ajax({
					url : ' ajax/RefreshUserLeaveBalance.action',	
					success : function(data) {
						alert(data.tips);
					}
				})">刷新用户数据</a>
			<div align="center">
				<table id="vacationadjustmentgrid"></table>
				<div id="vacationadjustmentgridpager"></div>
			</div>
			<ul class="easyui-tree"
				data-options="url:'tree_data1.json',method:'get',animate:true"></ul>
		</div>
		<div title="休假记录" style="padding: 10px">
			<div align="center">
				<table id="applicationtb"></table>
				<div id="gridpager"></div>
			</div>
		</div>
		<div title="Help" data-options="iconCls:'icon-help',closable:true"
			style="padding: 10px">This is the help content.</div>
	</div>
</div>
<%@include file="../footer.jsp"%>
