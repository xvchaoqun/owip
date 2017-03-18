<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/train_page"
                 data-url-export="${ctx}/train_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="train:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/train_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/train_au"
                       data-grid-id="#jqGrid"
                       data-querystr="&"><i class="fa fa-edit"></i>
                        修改</a>
                    <a class="jqOpenViewBtn btn btn-info btn-sm"
                       data-url="${ctx}/train_evaCloseTime"
                       data-querystr="&"><i class="fa fa-gear"></i>
                        评课设置</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/train_inspectors"
                       data-querystr="&"><i class="fa fa-user-plus"></i>
                        生成评课账号</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="train:del">
                    <button data-url="${ctx}/train_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}个培训班次（注：与其相关的所有数据都将删除，不可恢复，请谨慎操作！）？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>

                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                        <div class="form-group">
                            <label>名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入名称">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<script>
    function print_inspector(trainId){
        printWindow("${ctx}/trainInspector_list?export=2&pagesize=4&trainId="+ trainId)
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}/train_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '名称',name: 'name', width: 300, align:'left', frozen: true,formatter:function(cellvalue, options, rowObject){
                var str = '<i class="fa fa-id-card-o red" title="实名测评"></i>&nbsp;';
                return (rowObject.totalCount>0&&!rowObject.isAnonymous)?str+cellvalue:cellvalue;
            }},
            { label: '开始日期',name: 'startDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label: '结束日期',name: 'endDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '培训课程', name: 'courseNum', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue==0)
                    return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/trainCourse_page?trainId={0}">编辑课程</a>'
                                    .format(rowObject.id);
                else
                    return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/trainCourse_page?trainId={0}">查看课程（{1}）</a>'
                            .format(rowObject.id, cellvalue);
            }, width: 200},
            {label: '评课说明', name: '_note', formatter: function (cellvalue, options, rowObject) {
                return '<a href="javascript:void(0)" class="popupBtn" data-width="750" data-url="${ctx}/train_note?id={0}">编辑</a>'
                        .format(rowObject.id);
            }},
            {label: '评课账号（总数）', name: '_eva', width: 130, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.totalCount==0) return '-'
                return '<a href="javascript:void(0)" class="popupBtn" data-width="750" ' +
                        'data-url="${ctx}/trainInspector_list?trainId={0}">查看（{1}）</a>'
                                .format(rowObject.id, rowObject.totalCount);
            }},
            {label: '导出账号', name: '_export', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.totalCount==0) return '-'
                return '<a href="javascript:void(0)" class="linkBtn" data-target="_blank"' +
                        'data-url="${ctx}/trainInspector_list?export=1&trainId={0}">导出</a>'
                                .format(rowObject.id);
            }},
            {label: '打印', name: '_print', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.totalCount==0) return '-'
                /*return '<a href="javascript:void(0)" class="linkBtn" data-target="_blank"' +
                        'data-url="${ctx}/trainInspector_list?export=2&trainId={0}">打印</a>'
                                .format(rowObject.id);*/
                if(rowObject.isAnonymous)
                    return '<a href="javascript:void(0)" onclick="print_inspector({0})">打印账号</a>'
                        .format(rowObject.id);

                return '<a href="javascript:void(0)" onclick="print_inspector({0})">打印二维码</a>'
                        .format(rowObject.id);
            }},
            <shiro:hasPermission name="statTrain:list">
            {label: '测评结果', name: '_result', formatter: function (cellvalue, options, rowObject) {

                return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/stat_train_page?trainId={0}"><i class="fa fa-line-chart"></i> 查看</button>'
                            .format(rowObject.id);
            }},
            </shiro:hasPermission>
            {label: '是否关闭评课', name: 'isClosed', width:110, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return ''
                return cellvalue?"是":"否"
            }},
            {label: '评课关闭时间', name: 'closeTime', width: 130, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.isClosed==undefined || rowObject.isClosed) return '-'
                if(cellvalue==undefined) return '-'
                return cellvalue.substr(0,16)
            }},
            { label: '简介',name: 'summary', align: 'left', width: 300},
            { label: '备注',name: 'remark'},
            { label: '创建时间',name: 'createTime'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })
    _initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>