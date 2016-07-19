$(function() {
	jQuery("#pendingOTGrid").jqGrid(
			{
				url : 'json/managerGridView.action?searchTable=PendingOTApplication',
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
					label : '申请人(Applicant)'
				}, {
					name : 'attType.description',
					index : 'attType.description',
					label : '类型(Type)',
				// width : 200
				}, {
					name : 'status',
					label : '申请状态(Status)',
				}, {
					name : 'adjustedduration',
					label : '时长(Duration)'
				}, {
					name : 'attType.id',
					label : '类型',
					hidden : true,
				// width : 200
				}, {
					name : 'startTime',
					index : 'startTime',
					label : '开始时间(StartTime)',

				}, {
					name : 'endTime',
					index : 'endTime',
					label : '结束时间(EndTime)',
				// width : 300
				}, {
					name : 'submitTime',
					label : '申请时间(SubmissionTime)',
				}, {
					name : 'description',
					label : '备注(Memo)'
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
					$.messager.confirm('审批', '同意该申请?<br/> Approve?', function(r) {
						$.ajax({
							url : ' ajax/CheckApplication.action',
							data : {
								id : id,
								result : r,
								type : 'OT'
							},
							success : function(data) {
								alert(data.tips);
								jQuery("#pendingOTGrid").jqGrid().trigger(
										'reloadGrid');
							},
							error : function() {
								alert('error')
							}
						});
					});
				},
				pager : '#pendingOTGridPager',
//				editurl : "ajax/EditApplicationRecordAction",
				rowNum : 15,
				rowList : [ 5, 15, 30 ],
			}).navGrid("#pendingOTGridPager", {
		edit : false,
		add : false,
		del : false
	});
})