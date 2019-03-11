<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CADRE_RESERVE_ORIGIN_WAY_MAP" value="<%=CadreConstants.CADRE_RESERVE_ORIGIN_WAY_MAP%>"/>

<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.way ||not empty param.userId
            ||not empty param.reserveType ||not empty param.recommendUnit ||not empty param.recommendDate
            || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cadreReserveOrigin:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cadreReserveOrigin_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cadreReserveOrigin_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreReserveOrigin:del">
                    <button data-url="${ctx}/cadreReserveOrigin_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cadreReserveOrigin_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
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
                            <label>产生方式</label>
                            <select data-rel="select2" name="way" data-placeholder="请选择">
                                <option></option>
                                <c:forEach items="${CADRE_RESERVE_ORIGIN_WAY_MAP}" var="entity">
                                    <option value="${entity.key}">${entity.value}</option>
                                </c:forEach>
                            </select>
                            <script>
                                $("#searchForm select[name=way]").val(${param.way});
                            </script>
                        </div>
                        <div class="form-group">
                            <label>推荐人选</label>
                            <c:set var="user" value="${cm:getUserById(param.userId)}"/>
                            <select data-rel="select2-ajax" data-width="272" data-ajax-url="${ctx}/cadre_selects?key=1&type=0"
                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                <option value="${user.id}">${user.realname}-${user.code}</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>类别</label>
                            <select data-rel="select2" name="reserveType" data-placeholder="请选择">
                                <option></option>
                                <c:forEach items="${cm:getMetaTypes('mc_cadre_reserve_type')}" var="entity">
                                    <option value="${entity.key}">${entity.value.name}</option>
                                </c:forEach>
                            </select>
                            <script>
                                $("#searchForm select[name=reserveType]").val(${param.reserveType});
                            </script>
                        </div>
                        <div class="form-group">
                            <label>推荐单位</label>
                            <input class="form-control search-query" name="recommendUnit" type="text" value="${param.recommendUnit}"
                                   placeholder="请输入推荐单位">
                        </div>
                        <%--<div class="form-group">
                            <label>推荐日期</label>
                            <div class="input-group">
                                <input class="form-control date-picker" name="recommendDate" type="text"
                                       data-date-format="yyyy-mm-dd"
                                       value="${param.recommendDate}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>--%>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cadreReserveOrigin"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cadreReserveOrigin"
                                            data-target="#page-content">
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
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/cadreReserveOrigin_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '添加日期',name: 'addTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
                { label: '产生方式',name: 'way', width: 160, formatter: $.jgrid.formatter.MAP,
                    formatoptions:{map:_cMap.CADRE_RESERVE_ORIGIN_WAY_MAP}, frozen: true},
                {
                    label: '推荐人选', name: 'cadre.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                    return $.cadre(rowObject.cadre.id, cellvalue);
                }, frozen: true},
                { label: '类别',name: 'reserveType', width: 120, formatter: $.jgrid.formatter.MetaType, frozen: true},
                { label: '推荐形式',name: 'way', width: 160, formatter: $.jgrid.formatter.MAP,
                    formatoptions:{map:_cMap.CADRE_RESERVE_ORIGIN_WAY_MAP}},
                { label: '推荐单位',name: 'recommendUnit'},
                { label: '推荐日期',name: 'recommendDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
                {
                    label: '推荐材料', width: 200, align:'left', formatter: function (cellvalue, options, rowObject) {

                    var ret = "-";
                    var pdfFilePath = rowObject.pdfFilePath;
                    if ($.trim(pdfFilePath) != '') {
                        var fileName = (rowObject.fileName || rowObject.id) + (pdfFilePath.substr(pdfFilePath.indexOf(".")));
                        //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
                        ret = '<button href="javascript:void(0)" data-url="${ctx}/swf/preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                        .format(encodeURI(pdfFilePath), encodeURI(fileName))
                                + '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                        .format(encodeURI(pdfFilePath), encodeURI(fileName));
                    }
                    var wordFilePath = rowObject.wordFilePath;
                    if ($.trim(wordFilePath) != '') {

                        var fileName = (rowObject.fileName || rowObject.id) + (wordFilePath.substr(wordFilePath.indexOf(".")));
                        ret += '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                                .format(encodeURI(wordFilePath), encodeURI(fileName));
                    }
                    return ret;
                }
                },
                { label: '考察材料',name: 'sn', width: 180},
                { label: '备注',name: 'remark', width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $.register.user_select($('select[name=userId]'));
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>