<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="ROLE_CET_ADMIN" value="<%=RoleConstants.ROLE_CET_ADMIN%>"/>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_PASS%>" var="CET_UNIT_PROJECT_STATUS_PASS"/>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>

        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
            ${cetUnitProject.projectName}
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" id="detail-ul">
                <li class="active">
                    <a href="javascript:;"> 培训记录</a>
                </li>

            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="tab-content padding-4" id="detail-content">
                <c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
                <div class="jqgrid-vertical-offset buttons">
                    <c:if test="${cm:hasRole(ROLE_CET_ADMIN) || cetUnitProject.status!=CET_UNIT_PROJECT_STATUS_PASS}">
                    <shiro:hasPermission name="cetUnitProject:edit">
                        <shiro:hasRole name="${ROLE_CET_ADMIN}">
                        <button class="popupBtn btn btn-warning btn-sm"
                                data-url="${ctx}/cet/cetUnitTrain_batchAdd?projectId=${param.projectId}">
                            <i class="fa fa-plus-square"></i> 批量添加
                        </button>
                        </shiro:hasRole>
                        <button class="popupBtn btn btn-success btn-sm"
                                data-url="${ctx}/cet/cetUnitTrain_au?projectId=${param.projectId}">
                            <i class="fa fa-plus"></i> ${cm:hasRole(ROLE_CET_ADMIN)?'个别添加':'添加'}
                        </button>
                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                data-url="${ctx}/cet/cetUnitTrain_au"
                                data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                            修改
                        </button>
                        <shiro:hasRole name="${ROLE_CET_ADMIN}">
                            <button class="popupBtn btn btn-info btn-sm tooltip-success"
                            data-url="${ctx}/cet/cetUnitTrain_import?projectId=${param.projectId}"
                            data-rel="tooltip" data-placement="top"
                            title="从Excel中导入培训记录"><i class="fa fa-upload"></i> 导入</button>
                        </shiro:hasRole>
                        <button data-url="${ctx}/cet/cetUnitTrain_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？（删除后不可恢复，请谨慎操作！）"
                                data-grid-id="#jqGrid2"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </shiro:hasPermission>
                    </c:if>
                    <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                            data-url="${ctx}/cet/cetUnitTrain_data"
                            data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                        <i class="fa fa-download"></i> 导出
                    </button>--%>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

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
                                    <label>参训人</label>
                                    <input class="form-control search-query" name="userId" type="text"
                                           value="${param.userId}"
                                           placeholder="请输入参训人">
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/cet/cetUnitTrain"
                                       data-target="#page-content"
                                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/cet/cetUnitTrain"
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
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    var traineeTypeMap = ${cm:toJSONObject(traineeTypeMap)};
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        url: '${ctx}/cet/cetUnitTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '参训人姓名', name: 'user.realname', frozen:true},
            {label: '参训人工号', width: 110, name: 'user.code', frozen:true},
            { label: '参训人类型', name: 'traineeTypeId', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined)return '--'
                return traineeTypeMap[cellvalue].name
            }, width:180},
            {label: '时任单位及职务', name: 'title', align: 'left', width: 350},
            {label: '职务属性', name: 'postType', width: 120, align: 'left',formatter: $.jgrid.formatter.MetaType},
            {label: '完成培训学时', name: 'period'},
            {label: '培训总结', name: '_note', width: 200, formatter: function (cellvalue, options, rowObject) {

              var ret = "";
              var fileName = "培训总结("+rowObject.user.realname+")";
              var pdfNote = rowObject.pdfNote;
              if ($.trim(pdfNote) != '') {

                //console.log(fileName + " =" + pdfNote.substr(pdfNote.indexOf(".")))
                ret = '<button href="javascript:void(0)" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                .format(encodeURI(pdfNote), encodeURI(fileName))
                        + '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                .format(encodeURI(pdfNote), encodeURI(fileName));
              }
              var wordNote = rowObject.wordNote;
              if ($.trim(wordNote) != '') {
                ret += '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                        .format(encodeURI(wordNote), encodeURI(fileName));
              }
              return ret;
            }},
            {label: '操作人', name: 'addUser.realname'},
            {label: '添加时间', name: 'addTime', width: 150},
            { label: '备注',name: 'remark', align: 'left', width: 150},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>