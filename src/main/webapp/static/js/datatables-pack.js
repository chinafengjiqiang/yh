/**
*处理DataTable的数据
*公共部分
*/
var DataTablePack = function(){

	//汉化分页条
	var language = {
		paginate:{
			first:'首页',
			last:'末页',
			previous:'上一页',
			next:'下一页'
		},
		lengthMenu:'显示 _MENU_ 条',
		zeroRecords:'没有检索到有效数据！',
		info:'显示 _START_ 到 _END_ 条记录',
		infoEmpty:'没有检索到有效数据！',
		search:'查找：',
		processing:'努力加载数据中...',
		infoFiltered:'共计 _TOTAL_ 条记录'
	}

	function selects(table){
		table.children('tbody').on( 'click', 'td:not(:last-child)', function () {
			$(this.parentNode).toggleClass('selected');
		} );
	}
	
	/** 公开调用方法 **/
	var DataTablePublic = {
		/* 处理处理静态数据  */
		baseTable:function(table){
			var oTable = table.dataTable({
				'language':language,//汉化工具条
				'lengthMenu':[10,20,30,50,100],//显示每页大小的下拉框中的选项
				'pagingType':'full_numbers',//分页样式
				'columnDefs':[{
					'targets':[0,1],
					'orderable':false
				}],//默认列参数
				"order":[
					[2, "asc"]
				] //默认排序的列
			});

			return oTable;
		},
		/* 即时后台获取数据*/
		serverTable:function(table,url,reqData,columns,startNum){
			//alert(url);
			var oTable = table.DataTable( {
				"bServerSide": true,             //指定从服务器端获取数据
				"sAjaxSource": fq.contextPath+'/'+url,    //获取数据的url (一般是action)
				"fnServerData": "retrieveData",   //获取数据的处理函数
				"bDestroy" : true,
				"bSortCellsTop" : true,
				"fnInitComplete": function() {
					this.fnAdjustColumnSizing(true);
				},
				"fnServerParams" : function(aoData) {
					aoData.push({
						"name" : "statId",
						"value" : encodeURI($("#s_statId").val())
					});
				},
				"fnServerData" : function(sSource, aoData, fnCallback) {

					//update 20160516为了传递多参数
					if(reqData != null){
						$.each(reqData,function (i,objData) {
							aoData.push(objData);
						});
					}

					$.ajax({
						"type" : 'post',
						"url" : sSource,
						"dataType" : "json",
						"data" : {
							aoData : JSON.stringify(aoData)
						},
						"success" : function(resp) {
							fnCallback(resp);
						}
					});
				},

				"bStateSave" : false,//状态保存
				"bJQueryUI" : true,
				"bAutoWidth": false,//自动宽度
				"bPaginate" : true,// 分页按钮
				"bInfo" : false,//页脚信息
				"searching":false,//搜索框
				"bFilter": true, //过滤功能
				"bLengthChange": true,//改变每页显示数据数
				"bPaginate": true, //翻页功能
				"sPaginationType": "full_numbers", // 数字的翻页样式
				"iDisplayLength" : 10,// 每页显示行数
				"iDisplayStart" : startNum,//初始化显示的时候从第几条数据开始显示(一开始显示第几页)
				"processing" : true,
				//水平限制宽度
				"sDom": '<"bread-crumb pull-right search-div"f>rt<"branches pull-left"l><"pagination pull-right"p><"clear">',//改变页面上元素的位置
				initComplete: function (setting, json) {
					//初始化完成之后替换原先的搜索框。
					//本来想把form标签放到hidden_filter 里面，因为事件绑定的缘故，还是拿出来。
					$(tablePrefix + "filter").html("<form id='filter_form'>" + $("#hidden_filter").html() + "</form>");
				},

				'aoColumns':columns,//定义列
				'language':language,//汉化工具条

				"order": [[ 0, "asc" ]],

			} );

			//可多选
			selects(table);

			return oTable;
		}
	};

	return DataTablePublic;
	
}();	