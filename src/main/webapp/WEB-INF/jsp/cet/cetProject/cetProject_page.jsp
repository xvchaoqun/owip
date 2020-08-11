<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<c:set value="<%=CetConstants.CET_PROJECT_STATUS_UNREPORT%>" var="_UNREPORT"/>
<c:set value="<%=CetConstants.CET_PROJECT_STATUS_REPORT%>" var="_REPORT"/>
<c:set value="<%=CetConstants.CET_PROJECT_STATUS_PASS%>" var="_PASS"/>
<c:set value="<%=CetConstants.CET_PROJECT_STATUS_UNPASS%>" var="_UNPASS"/>
<div class="row rownumbers">
    <div class="col-xs-12 multi-row-head-table">

        <div id="body-content" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.name ||not empty param.projectTypeId ||not empty param.prePeriod
             ||not empty param.subPeriod ||not empty param.objCount}"/>

            <c:if test="${cls==3||cls==4}">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${status==_PASS}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetProject?cls=${cls}&status=${_PASS}"><i
                            class="fa fa-list"></i> 已审批(${cm:trimToZero(statusCountMap.get(_PASS))})
                    </a>
                </li>
                <li class="<c:if test="${status==_UNREPORT}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetProject?cls=${cls}&status=${_UNREPORT}"><i
                            class="fa fa-circle-o"></i> 待报送(${cm:trimToZero(statusCountMap.get(_UNREPORT))})
                    <span id="unreportCount"></span>
                    </a>
                </li>
                <li class="<c:if test="${status==_REPORT}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetProject?cls=${cls}&status=${_REPORT}"><i
                            class="fa fa-history"></i> 待组织部审核(${cm:trimToZero(statusCountMap.get(_REPORT))})
                    <span id="checkCount"></span>
                    </a>
                </li>
                <li class="<c:if test="${status==_UNPASS}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetProject?cls=${cls}&status=${_UNPASS}"><i
                            class="fa fa-times"></i> 未通过审核(${cm:trimToZero(statusCountMap.get(_UNPASS))})</a>
                </li>
                <div class="buttons pull-left" style="left:20px; position: relative">
                <button class="openView btn btn-success btn-sm"
                            data-url="${ctx}/cet/cetProject_au?cls=${cls}">
                        <i class="fa fa-plus"></i> 添加</button>
                </div>
            </ul>
                <div class="space-4"></div>
            </c:if>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasRole name="${ROLE_CET_ADMIN}">
                <c:if test="${status==_REPORT}">
                    <button class="jqOpenViewBatchBtn btn btn-success btn-sm"
                                    data-url="${ctx}/cet/cetProject_check"
                                    data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                    审批
                    </button>
                </c:if>
                <c:if test="${status==_PASS||status==_UNPASS}">
                    <button class="jqBatchBtn btn btn-warning btn-sm"
                            data-title="返回待报送"
                            data-msg="确定返回待报送？（已选{0}条数据）"
                       data-url="${ctx}/cet/cetProject_back"
                       data-grid-id="#jqGrid"><i class="fa fa-reply"></i>
                        返回待报送</button>
                </c:if>
                </shiro:hasRole>
                <c:if test="${cls==1||cls==2|| cm:hasRole(ROLE_CET_ADMIN) ||status==_UNREPORT||status==_UNPASS}">
                <shiro:hasPermission name="cetProject:edit">
                    <c:if test="${cls==1||cls==2}">
                    <button class="openView btn btn-info btn-sm"
                            data-url="${ctx}/cet/cetProject_au?cls=${cls}">
                        <i class="fa fa-plus"></i> 添加</button>
                    </c:if>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cet/cetProject_au?cls=${cls}"
                            data-open-by="page"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cetProject:del">
                    <button data-url="${ctx}/cet/cetProject_batchDel"
                            data-title="删除"
                            data-msg="删除这{0}条数据？（该培训班下的所有数据均将删除，删除后无法恢复，请谨慎操作！）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                </c:if>
                <c:if test="${cls==1||cls==2||status==_PASS}">
                <shiro:hasRole name="${ROLE_SUPER}">
                <button data-url="${ctx}/cet/archiveProject"
                        data-title="归档培训学时"
                        data-msg="确定统计并归档该培训班中所有学员最新的培训学时？"
                        data-grid-id="#jqGrid"
                        data-id-name="projectId"
                        data-loading-text="<i class='fa fa-spinner fa-spin'></i> 统计中，请稍后..."
                        class="jqItemBtn btn btn-warning btn-sm">
                     <i class="prompt fa fa-question-circle"
               data-prompt="统计汇总培训班中所有学员的培训学时（已完成学时数）"></i> 归档培训学时
                </button>
                </shiro:hasRole>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/cet/cetProject_data?cls=${cls}&status=${status}"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
                </c:if>
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
                    <div class="widget-main no-padding columns">
                        <form class="form-inline search-form" id="searchForm">
                        <div class="form-group">
                            <label>年度</label>
                            <input class="form-control date-picker" placeholder="请选择年份"
                                   name="year" type="text" style="width: 100px;"
                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                   value="${param.year}"/>
                        </div>
                        <div class="form-group">
                            <label>培训班名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入培训班名称">
                        </div>
                        <div class="form-group">
                            <label>培训类别</label>
                            <select data-rel="select2" name="projectTypeId"
                                    data-width="150"
                                    data-placeholder="请选择">
                                <option></option>
                                <c:forEach items="${cetProjectTypeMap}" var="entity">
                                    <option value="${entity.key}">${entity.value.name}</option>
                                </c:forEach>
                            </select>
                            <script type="text/javascript">
                                $("#searchForm select[name=projectTypeId]").val(${param.projectTypeId});
                            </script>
                        </div>
                        <div class="form-group column">
                            <label>培训学时</label>
                            <div class="input-group input">
                                <input style="width: 50px" class="form-control search-query float" type="text" name="prePeriod"
                                       value="${param.prePeriod}">
                            </div> <label>至</label>
                            <div class="input-group input">
                                <input style="width: 50px" class="form-control search-query float"
                                       type="text"
                                       name="subPeriod"
                                       value="${param.subPeriod}">
                            </div>
                        </div>

                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                        data-url="${ctx}/cet/cetProject?cls=${cls}&status=${status}"
                                        data-target="#page-content"
                                        data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetProject?cls=${cls}&status=${status}"
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
    function _reload(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $.register.date($('.date-picker'));

    var cetProjectTypeMap = ${cm:toJSONObject(cetProjectTypeMap)};
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/cet/cetProject_data?callback=?&status=${status}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${(cls==3||cls==4) && (status==0 || status==3)}">
            <c:if test="${status==3}">
            { label: '退回原因',name: 'backReason', width:120, frozen:true},
            </c:if>
            { label: '报送',name: '_report', width:80, formatter: function (cellvalue, options, rowObject) {
              return ('<button class="confirm btn btn-success btn-xs" data-msg="报送后不可修改，请核实后确认。" ' +
                  'data-callback="_reload" ' +
              'data-url="${ctx}/cet/cetProject_report?id={0}"><i class="fa fa-hand-paper-o"></i> 报送</button>')
                      .format(rowObject.id);
            }, frozen:true},
            </c:if>
            {label: '详情', name: '_detail', width:'80', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-warning btn-xs" ' +
                'data-url="${ctx}/cet/cetProject_detail?cls=1&projectId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }, frozen: true},

            { label: '年度',name: 'year', width: 60, frozen: true},
            { label: '培训时间',name: 'startDate', width: 200, formatter: function (cellvalue, options, rowObject) {
                return '{0} ~ {1}'.format($.date(rowObject.startDate, "yyyy.MM.dd"), $.date(rowObject.endDate, "yyyy.MM.dd"))
            }, frozen: true},
            { label: '培训班名称',name: 'name', width: 400, align:'left'},
            {
                label: '培训班类型', name: 'projectTypeId', width: 130, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--'
                if(cetProjectTypeMap[cellvalue]==undefined) return '--'
                return cetProjectTypeMap[cellvalue].name
            }},
            <c:if test="${cls==3 || cls==4}">
            { label: '培训班主办方',name: 'cetParty.name', align:'left', width: 310},
            { label: '主办单位',name: 'unitId', width: 150, align:'left', formatter: $.jgrid.formatter.unit},
            </c:if>
            {label: '培训内容分类', name: 'category', align:'left', width: 180, formatter: function (cellvalue, options, rowObject) {
                    if($.trim(cellvalue)=='') return '--'
                    return ($.map(cellvalue.split(","), function(category){
                        return $.jgrid.formatter.MetaType(category);
                    })).join("，")
                }},
            {label: '培训课件', name: '_file', formatter: function (cellvalue, options, rowObject) {
                    return ('<button data-url="${ctx}/cet/cetProjectFile?projectId={0}" data-width="800"' +
                        'class="popupBtn btn btn-xs btn-primary"><i class="ace-icon fa fa-files-o"></i> 查看({1})</button>')
                        .format(rowObject.id, Math.trimToZero(rowObject.fileCount))
                }},
            {
                label: '培训方案', width: 200, align:'left', formatter: function (cellvalue, options, rowObject) {

                var ret = "";
                var pdfFilePath = rowObject.pdfFilePath;
                if ($.trim(pdfFilePath) != '') {
                    var fileName = (rowObject.fileName || rowObject.id);
                    //console.log(fileName + " =" + pdfFilePath)
                    ret = '<button data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                    .format(pdfFilePath, encodeURI(fileName))
                            + '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                    .format(pdfFilePath, encodeURI(fileName));
                }
                var wordFilePath = rowObject.wordFilePath;
                if ($.trim(wordFilePath) != '') {

                    var fileName = (rowObject.fileName || rowObject.id);
                    ret += '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                            .format(wordFilePath, encodeURI(fileName));
                }
                return ret;
            }
            },

            { label: '总学时/<br/>结业学时', width: 90,name: 'period', formatter: function (cellvalue, options, rowObject) {

                return (rowObject.period>0?rowObject.period:"--")
                    + "/" + (rowObject.requirePeriod>0?rowObject.requirePeriod:"--");
            }},
            { label: '是否计入<br/>年度学习任务', name: 'isValid', formatter:$.jgrid.formatter.TRUEFALSE, formatoptions:{on:'<span class="green bolder">是</span>', off:'<span class="red bolder">否</span>'}},
            { label: '参训人数',name: 'objCount', formatter: function (cellvalue, options, rowObject) {

                return Math.trimToZero(rowObject.objCount)-Math.trimToZero(rowObject.quitCount);
            }},
            <shiro:hasRole name="${ROLE_SUPER}">
            { label: '归档状态', name: 'hasArchive', width: 90, formatter:$.jgrid.formatter.TRUEFALSE, formatoptions:{on:'<span class="green bolder">已归档</span>', off:'<span class="red bolder">未归档</span>'}},
            </shiro:hasRole>
            { label: '备注',name: 'remark', width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>