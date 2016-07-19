$(function() {
	jQuery("#applicationtb")
			.jqGrid(
					{
						url : 'json/myApplications.action?searchTable=leaveapplication',
						datatype : "json",
						caption : "休假记录",
						height : '270',
						mtype : "GET",
						colModel : [ {
							name : 'Action',
							index : 'Action',
							width : 75,
							sortable : false
						}, {
							name : 'id',
							index : 'id',
							hidden : true
						}, {
							name : 'attType.description',
							index : 'attType.description',
							label : '类型名称',
							editable : false,
							edittype : 'select',
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
									1 : '带薪年假',
									2 : '带薪病假',
									3 : '加班倒休',
									// 4 : '付费加班',
									// 5 : '无薪事假',
									6 : '婚假',
									7 : '产假/陪产假',
									8 : '丧假',
								// 9 : '无薪病假',
								// 10 : '其他',
								}
							},
							editrules : {
								edithidden : true
							}
						// width : 200
						}, {
							name : 'startTime',
							index : 'startTime',
							label : '开始时间',
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
										minDate : '2015/01/1',
										// maxDate : '1970/01/2',
										lang : 'ch'
									});
								}
							}
						}, {
							name : 'duration',
							label : '时长',
							editrules : {
								required : true,
								number : true
							},
							editable : true
						}, {
							name : 'submitTime',
							label : '申请时间'
						} ],
						sortname : 'submitTime',
						sortorder : 'desc',
						jsonReader : {
							root : "gridData",
							repeatitems : false
						},

						pager : '#gridpager',
						editurl : "ajax/EditLeaveApplicationRecord.action",
						rowNum : 10,
						rowList : [ 10, 20, 30 ],
						gridComplete : function() {
							var ids = jQuery("#applicationtb").jqGrid(
									'getDataIDs');
							for (var i = 0; i < ids.length; i++) {
								var cl = ids[i];
								if (jQuery("#applicationtb").jqGrid(
										'getRowData', ids[i]).status == '待审批-PENDING') {
									be = "<input style='height:30px;width:50px;' type='button' value='撤销' onclick=\"aa("
											+ ids[i] + ")\"  />";
									jQuery("#applicationtb").jqGrid(
											'setRowData', ids[i], {
												Action : be
											});
								}
							}
						},
					}).navGrid("#gridpager", {
				edit : false,
				add : true,
				del : false,
				search : false
			}, {}, {
				addCaption : "新休假申请",
				afterSubmit : function(response, postdata) {
					jQuery("#applicationtb").jqGrid().trigger('reloadGrid');
					var json = response.responseText; // in my case response
					// text form server is
					// "{sc:true,msg:''}"
					var result = eval("(" + json + ")"); // create js object
					// from server
					// reponse
					return [ false, result.tips, null ]
				}
			}, {});
	jQuery("#leavebalancetb").jqGrid({
		url : 'json/myApplications.action?searchTable=leavebalance',
		datatype : "json",
		height : '100%',
		caption : '剩余假期',
		width:'200',
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
			repeatitems : false
		}
	})
})
function aa(id) {
	//alert(id);
	$.messager.defaults = {
		ok : "是",
		cancel : "否",
		width : 250
	};
	$.messager.confirm('确认', '删除该申请', function(r) {
		if (r) {
			$.ajax({
				url : 'ajax/EditLeaveApplicationRecord.action',
				data : {
					id : id,
					// result : r,
					oper : 'del'
				},
				success : function(data) {
					//alert(data.tips)
					jQuery("#applicationtb").jqGrid().trigger('reloadGrid');
				},
				error : function() {
					alert('error')
				}
			});
		}
	});

}
