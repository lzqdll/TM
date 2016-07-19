$(function() {
	/*
	 * 休假记录Grid
	 */
	jQuery("#applicationtb").jqGrid({
		url : 'json/HRGridView.action?searchTable=Leave',
		datatype : "json",
		height : '250',
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
			editable : false,
			edittype : 'select',
			sortable : true,
			width : 200
		}, {
			name : 'status',
			label : '申请状态',
		}, {
			name : 'duration',
			search : false,
			label : '时长'
		}, {
			name : 'attType.id',
			label : '类型',
			editable : true,
			hidden : true,
			edittype : 'select',
			sortable : true,
			editoptions : {
				value : {
					1 : 'Annual_Leave',
					3 : 'Paid_Sick_Leave',
					4 : 'OVERTIME'
				}
			},
			editrules : {
				edithidden : true
			},
		// width : 200
		}, {
			name : 'startTime',
			index : 'startTime',
			label : '开始时间',
			search : false
		}, {
			name : 'endTime',
			index : 'endTime',
			label : '结束时间',
			search : false
		}, {
			name : 'submitTime',
			label : '申请时间',
			search : false
		} ],
		sortname : 'submitTime',
		sortorder : 'desc',
		jsonReader : {
			root : "gridData",
			repeatitems : false
		},
		pager : '#gridpager',
		editurl : "ajax/EditApplicationRecordAction.action",
		rowNum : 10,
		rowList : [ 10, 50, 300 ],
	}).navGrid("#gridpager", {
		edit : false,
		add : false,
		del : false
	});
	jQuery("#applicationtb").jqGrid('filterToolbar', {
		searchOnEnter : true,
		stringResult : true
	});

	/*
	 * 假期调整记录Grid
	 */
	jQuery("#vacationadjustmentgrid").jqGrid({
		url : 'json/HRGridView.action?searchTable=Vacation',
		datatype : "json",
		height : '100%',
		width:'100%',
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
			label : '加班类型',
			sortable : true
		}, {
			name : 'adjustedduration',
			label : '时长(小时)',
			editrules : {
				required : true,
				number : true
			},
			editable : true
		}, {
			name : 'status',
			label : '申请状态',
		}, {
			name : 'expiredTime',
			label : '过期时间'
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
					4 : '付费加班'
				// 10 : '其他'
				}
			},
			editrules : {
				edithidden : true
			}
		}, {
			name : 'startTime',
			index : 'startTime',
			label : '启始时间',
			editable : true,
			edittype : 'text',
			// width : 300,
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
			label : '结束时间',
			// width : 300,
			editable : true,
			editrules : {
				required : true
			// date : true
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
			name : 'submitTime',
			label : '申请时间'
		}, {
			name : 'description',
			label : '备注'
		} ],
		sortname : 'submitTime',
		sortorder : 'desc',
		jsonReader : {
			root : "gridData",
			repeatitems : false
		},
		pager : '#vacationadjustmentgridpager',
		editurl : "ajax/EditApplicationRecordAction.action",
		rowNum : 15,
		rowList : [ 15, 50, 300 ],
	}).navGrid("#vacationadjustmentgridpager", {
		edit : false,
		add : false,
		del : false
	});
	jQuery("#vacationadjustmentgrid").jqGrid('filterToolbar', {
		searchOnEnter : true,
		stringResult : true
	});
});

/*
 * Ajax提交假期调整
 */
$(document).ready(function() {
	jQuery('#expiredTime,#validTime').datetimepicker();
	$('#ff').form({
		success : function(data) {
			$.messager.alert('Info', data, 'info');
		}
	});
	// bind 'myForm' and provide a simple callback function
	$('#aladjustmentform').ajaxForm(function() {
		alert("提交成功！");
	});
});
