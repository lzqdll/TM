function search() {
	$("#deptleavegrid").jqGrid('setGridParam', {
		postData : {
			searchField : 'user.usrName',
			searchOper : 'eq',
			searchString : $("#usrName").val()
		}
	});
	$("#deptleavegrid").trigger('reloadGrid');
	$("#leavebalancetb").jqGrid('setGridParam', {
		postData : {
			searchField : 'user.usrName',
			searchOper : 'eq',
			searchString : $("#usrName").val()
		}
	});
	$("#leavebalancetb").trigger('reloadGrid');
}
$(function() {
	jQuery("#deptleavegrid").jqGrid({
		url : 'json/managerGridView.action',
		datatype : "json",
		height : '100%',
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
			label : '类型',
			editable : false,
			width : 200
		}, {
			name : 'status',
			label : '申请状态',
		}, {
			name : 'duration',
			label : '时长'
		}, {
			name : 'startTime',
			index : 'startTime',
			label : '开始时间',
			editable : true,
			edittype : 'text',
			width : 300
		}, {
			name : 'endTime',
			index : 'endTime',
			label : '结束时间',
			width : 300

		} ],
		sortname : 'startTime',
		sortorder : 'desc',
		jsonReader : {
			root : "gridData",
			repeatitems : false,
		},
		pager : '#deptleavegridpager',
		// editurl : "ajax/EditApplicationRecordAction",
		rowNum : 10,
		rowList : [ 10, 15, 30 ],
	}).navGrid("#deptleavegridpager", {
		edit : false,
		add : false,
		del : false
	});
	jQuery("#leavebalancetb").jqGrid({
		url : 'json/managerGridView_userLeaveBalance.action',
		datatype : "json",
		height : '100%',
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
