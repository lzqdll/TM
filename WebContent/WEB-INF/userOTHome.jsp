<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@include file="../header.jsp"%>
<link href="css/userOThome.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(function() {
		jQuery("#applicationtb").jqGrid({
			url : 'json/myApplications.action?searchTable=OTApplication',
			datatype : "json",
			caption : "加班记录",
			height : '270',
			width:'900',
			mtype : "GET",
			colModel : [ {
				name : 'id',
				index : 'id',
				hidden : true
			}, {
				name : 'attType.description',
				index : 'attType.description',
				label : '加班类型',
				sortable : true
			}, {
				name : 'status',
				label : '申请状态',
			}, {
				name : 'attType.id',
				label : '类型',
				editable : true,
				hidden : true,
				edittype : 'select',
				sortable : true,
				editoptions : {
					value : {
						3 : '倒休加班',
					//4 : '付费加班'
					//	10 : '其他'
					}
				},
				editrules : {
					edithidden : true
				}
			}, {
				name : 'startTime',
				index : 'startTime',
				label : '从(时间):',
				editable : true,
				edittype : 'text',
				editrules : {
					required : true
				},
				//	width : 300,
				editoptions : {
					dataInit : function(element) {
						$(element).datetimepicker({
							maxDate : '+1970/01/30',
							lang : 'ch'
						});
					}
				}
			}, {
				name : 'endTime',
				index : 'endTime',
				label : '至(时间):',
				//		width : 300,
				editable : true,
				editrules : {
					required : true
				//date : true
				},
				editoptions : {
					dataInit : function(element) {
						$(element).datetimepicker({
							maxDate : '+1970/01/30',
							lang : 'ch'
						});
					}
				}
			}, {
				name : 'adjustedduration',
				label : '可用时长',
				editrules : {
					required : true,
					number : true
				},
				editable : true
			}, {
				name : 'submitTime',
				label : '申请时间'
			}, {
				name : 'expiredTime',
				label : '过期时间'
			}, {
				name : 'description',
				label : '备注',
				editable : true,
				edittype : 'text'
			} ],
			sortname : 'submitTime',
			sortorder : 'desc',
			jsonReader : {
				root : "gridData",
				repeatitems : false
			},
			pager : '#gridpager',
			editurl : "ajax/EditOTApplicationRecord.action",
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
		}).navGrid("#gridpager", {
			edit : false,
			add : true,
			del : false
		}, {}, {	addCaption : "新加班申请",
			afterSubmit : function(response, postdata) {
				jQuery("#applicationtb").jqGrid().trigger('reloadGrid');
				var json = response.responseText; //in my case response text form server is "{sc:true,msg:''}"
				var result = eval("(" + json + ")"); //create js object from server reponse
				return [ false, result.tips, null ]
			}
		}, {});
		jQuery("#leavebalancetb").jqGrid({
			url : 'json/myApplications.action?searchTable=leavebalance',
			datatype : "json",
			height : '100%',
			width:'170',
			caption : '剩余假期',
			mtype : "GET",
			colModel : [ {
				name : 'id',
				index : 'id',
				hidden : true
			}, {
				name : 'attType.description',
				label : '类型'
			}, {
				name : 'hours',
				index : 'hours',
				label : '剩余时长(小时)'
			} ],
			jsonReader : {
				root : "gridData",
				repeatitems : false,
			}
		});
	})
</script>
<div style="height: 100%">
	<div>
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
