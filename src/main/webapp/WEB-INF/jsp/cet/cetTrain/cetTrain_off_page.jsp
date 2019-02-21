<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/cet/cetTrain"
                 data-url-export="${ctx}/cet/cetTrain_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cetTrain:edit">
                    <a class="popupBtn btn btn-info btn-sm"
                       data-url="${ctx}/cet/cetTrain_au?isOnCampus=0"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cet/cetTrain_au"
                       data-grid-id="#jqGrid"
                       data-querystr="&isOnCampus=0"><i class="fa fa-edit"></i>
                        修改</a>
                    <a class="jqOpenViewBtn btn btn-info btn-sm"
                       data-url="${ctx}/cet/cetTrain_evaCloseTime"
                       data-id-name="trainId"
                       ><i class="fa fa-gear"></i>
                        评课设置</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cet/cetTrain_inspectors"
                       ><i class="fa fa-user-plus"></i>
                        生成评课账号</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="cetTrain:del">
                    <button data-url="${ctx}/cet/cetTrain_batchDel"
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
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                            <input type="hidden" name="isOnCampus" value="0">
                        <div class="form-group">
                            <label>名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入名称">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-querystr="isOnCampus=0">
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
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    function to_print_inspector(trainId){

        bootbox.prompt({
            size:'small',
            title: "设置每页打印数量",
            value:'2',
            inputType: 'number',
            callback: function (result) {
                if(result!=null) {
                    print_inspector(trainId, result)
                }
            }
        })
    }

    function print_inspector(trainId, result){
        $.print("${ctx}/cet/cetTrainInspector_list?export=2&pagesize="+result+"&trainId="+ trainId)
    }

    $("#jqGrid").jqGrid({
        url: '${ctx}/cet/cetTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '名称',name: 'name', width: 380, align:'left', frozen: true,formatter:function(cellvalue, options, rowObject){
                var str = '<i class="fa fa-id-card-o red" title="实名测评"></i>&nbsp;';
                return (rowObject.evaCount>0&&!rowObject.evaAnonymous)?str+cellvalue:cellvalue;
            }},
            { label: '开始日期',name: 'startDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label: '结束日期',name: 'endDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '培训课程', name: 'courseNum', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue==0)
                    return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/cet/cetTrainCourse?trainId={0}&isOnCampus=0">编辑课程</a>'
                                    .format(rowObject.id);
                else
                    return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/cet/cetTrainCourse?trainId={0}&isOnCampus=0">查看课程（{1}）</a>'
                            .format(rowObject.id, cellvalue);
            }, width: 200},
            {label: '评课说明', name: '_note', formatter: function (cellvalue, options, rowObject) {
                return '<a href="javascript:void(0)" class="popupBtn" data-width="750" data-url="${ctx}/cet/cetTrain_evaNote?trainId={0}">编辑</a>'
                        .format(rowObject.id);
            }},
            {label: '评课账号（总数）', name: '_eva', width: 130, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.evaCount==undefined || rowObject.evaCount==0) return '-'
                return '<a href="javascript:void(0)" class="popupBtn" data-width="750" ' +
                        'data-url="${ctx}/cet/cetTrainInspector_list?trainId={0}">查看（{1}）</a>'
                                .format(rowObject.id, rowObject.evaCount);
            }},
            {label: '导出账号', name: '_export', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.evaCount==undefined || rowObject.evaCount==0) return '-'
                return '<a href="javascript:void(0)" class="downloadBtn" ' +
                        'data-url="${ctx}/cet/cetTrainInspector_list?export=1&trainId={0}">导出</a>'
                                .format(rowObject.id);
            }},
            {label: '打印', name: '_print', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.evaCount==undefined || rowObject.evaCount==0) return '-'
                /*return '<a href="javascript:void(0)" class="linkBtn" data-target="_blank"' +
                        'data-url="${ctx}/cet/cetTrainInspector_list?export=2&trainId={0}">打印</a>'
                                .format(rowObject.id);*/
                if(rowObject.evaAnonymous)
                    return '<a href="javascript:void(0)" onclick="to_print_inspector({0})">打印账号</a>'
                        .format(rowObject.id);

                return '<a href="javascript:void(0)" onclick="print_inspector({0})">打印二维码</a>'
                        .format(rowObject.id);
            }},
            <shiro:hasPermission name="cetTrainStat:list">
            {label: '测评结果', name: '_result', formatter: function (cellvalue, options, rowObject) {

                return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/cet/cetTrainStat?trainId={0}"><i class="fa fa-line-chart"></i> 查看</button>'
                            .format(rowObject.id);
            }},
            </shiro:hasPermission>
            {label: '是否关闭评课', name: 'evaClosed', width:110, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return ''
                return cellvalue?"是":"否"
            }},
            {label: '评课关闭时间', name: 'evaCloseTime', width: 150, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.evaClosed==undefined || rowObject.evaClosed) return '-'
                if(cellvalue==undefined) return '-'
                return cellvalue.substr(0,16)
            }},
            { label: '简介',name: 'summary', align: 'left', width: 400},
            { label: '备注',name: 'remark', width: 200},
            { label: '创建时间',name: 'createTime', width: 200}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>