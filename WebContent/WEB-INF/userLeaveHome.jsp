<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@include file="../header.jsp"%>
<link href="css/userleavehome.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/TM/js/userLeaveHome.js"></script>
<script type="text/javascript">
</script>
<div style="height: 100%">
	<div style="margin-top: 10px">
		<table>
			<tbody style="vertical-align: top">
				<tr>
					<td>
						<table id="leavebalancetb"></table>
						<div id="lbgridpager"></div>
					</td>
					<td>
						<table id="applicationtb"></table>
						<div id="gridpager"></div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<%@include file="../footer.jsp"%>
