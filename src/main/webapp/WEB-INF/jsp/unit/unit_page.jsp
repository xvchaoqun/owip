<%@ taglib prefix="shrio" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/unit_au?status=${cls==1?UNIT_STATUS_RUN:UNIT_STATUS_HISTORY}"
             data-url-page="${ctx}/unit"
             data-url-export="${ctx}/unit_data"
             data-url-bd="${ctx}/unit_batchDel"
             data-url-co="${ctx}/unit_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.code ||not empty param.name
            ||not empty param.typeId || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="<shiro:hasPermission name="unitPost:*">multi-row-head-table </shiro:hasPermission>tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${cls!=3}">
                            <shiro:hasPermission name="unit:edit">
                                <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                                <button class="jqEditBtn btn btn-primary btn-sm">
                                    <i class="fa fa-edit"></i> 修改信息
                                </button>
                            <div class="btn-group">
                                <button data-toggle="dropdown"
                                        class="btn btn-info btn-sm dropdown-toggle">
                                    <i class="fa fa-download"></i> 批量操作 <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu dropdown-success" role="menu" style="z-index: 1031">
                                    <li>
                                        <a href="javascript:;" class="popupBtn"
                                           data-url="${ctx}/unit_import?status=${cls==1?UNIT_STATUS_RUN:UNIT_STATUS_HISTORY}">
                                            <i class="fa fa-upload"></i> 批量导入</a>
                                    </li>
                                    <c:if test="${cls==1}">
                                    <li role="separator" class="divider"></li>
                                    <li>
                                        <a href="javascript:;" class="popupBtn"
                                           data-url="${ctx}/unit_importCodes">
                                            <i class="fa fa-refresh"></i> 批量更新单位编码</a>
                                    </li>
                                    <li role="separator" class="divider"></li>
                                    <li>
                                        <a href="javascript:;" class="popupBtn"
                                           data-url="${ctx}/unit_batchSort?status=${cls==1?UNIT_STATUS_RUN:UNIT_STATUS_HISTORY}">
                                            <i class="fa fa-file-excel-o"></i> 批量排序</a>
                                    </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqBatchBtn"
                                               data-url="${ctx}/unit_not_stat_post?notStatPost=1" data-title="转移"
                                               data-msg="确定这{0}个单位不列入配备一览表吗？">
                                                <i class="fa fa-times"></i> 不列入配备一览表</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqBatchBtn"
                                               data-url="${ctx}/unit_not_stat_post?notStatPost=0" data-title="转移"
                                               data-msg="确定这{0}个单位列入配备一览表吗？">
                                                <i class="fa fa-check"></i> 列入配备一览表</a>
                                        </li>
                                    </c:if>
                                </ul>
                            </div>
                            </shiro:hasPermission>
                            <div class="btn-group">
                                <button data-toggle="dropdown"
                                        data-rel="tooltip" data-placement="top" data-html="true"
                                        title="<div style='width:180px'>导出选中记录或所有搜索结果</div>"
                                        class="btn btn-success btn-sm dropdown-toggle tooltip-success">
                                    <i class="fa fa-download"></i> 导出 <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu dropdown-success" role="menu">
                                    <li><a href="javascript:;" class="jqExportBtn">
                                        <i class="fa fa-download"></i> 导出单位一览表</a>
                                    </li>
                                    <li role="separator" class="divider"></li>
                                    <li>
                                        <a href="javascript:;" class="jqExportBtn"
                                           data-need-id="false"
                                           data-url="${ctx}/unit_data?export=2">
                                            <i class="fa fa-file-excel-o"></i> 导出单位列表</a>
                                    </li>
                                </ul>
                            </div>
                            <shiro:hasPermission name="unit:abolish">
                                <c:if test="${cls==1}">
                                    <button class="jqBatchBtn btn btn-warning btn-sm"
                                       data-url="${ctx}/unit_abolish" data-title="转移"
                                       data-msg="确定将这{0}个单位转移到历史单位吗？">
                                        <i class="fa fa-history"></i> 转移至历史单位
                                    </button>
                                 </c:if>
                                 <c:if test="${cls==2}">
                                      <button class="jqBatchBtn btn btn-warning btn-sm"
                                           data-url="${ctx}/unit_abolish?isAbolish=0" data-title="返回正在运转单位"
                                           data-msg="确定将这{0}个单位返回到运转单位吗？">
                                            <i class="fa fa-reply"></i> 返回正在运转单位
                                        </button>
                                      </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="unit:del">
                                <a class="jqBatchBtn btn btn-danger btn-sm"
                                   data-url="${ctx}/unit_batchDel" data-title="删除单位"
                                   data-msg="确定删除这{0}个单位吗？"><i class="fa fa-trash"></i> 删除</a>
                            </shiro:hasPermission>
                            </c:if>
                             <c:if test="${cls==3}">
                            <a class="jqBatchBtn btn btn-warning btn-sm"
                                   data-url="${ctx}/unit_batchDel?isDeleted=0" data-title="恢复单位"
                                   data-msg="确定恢复这{0}个单位吗？"><i class="fa fa-reply"></i> 恢复</a>
                             </c:if>
                        </div>
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
                                <div class="widget-toolbar">
                                    <a href="javascript:;" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                                <div class="form-group">
                                                    <label>单位编号</label>
                                                        <input type="hidden" name="cls" value="${cls}">
                                                        <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                                               placeholder="请输入单位编号">
                                                </div>
                                                <div class="form-group">
                                                    <label>单位名称</label>
                                                        <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                                               placeholder="请输入单位名称">
                                                </div>
                                                <div class="form-group">
                                                    <label>单位类型</label>
                                                        <select data-rel="select2" name="typeId" data-placeholder="请选择单位类型">
                                                            <option></option>
                                                            <c:import url="/metaTypes?__code=mc_unit_type"/>
                                                        </select>
                                                        <script type="text/javascript">
                                                            $("#searchForm select[name=typeId]").val('${param.typeId}');
                                                        </script>
                                                </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm" data-querystr="cls=${cls}">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <%--<div class="space-4"></div>--%>
                        <table id="jqGrid" class="jqGrid"> </table>
                        <div id="jqGridPager"> </div>
                    </div>
                </div></div></div>
        <div id="body-content-view">
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/unit_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <shrio:hasPermission name="unit:view">
            { label: '详情', name: '_detail', width: 80, formatter:function(cellvalue, options, rowObject){

                return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/unit_view?id={0}"><i class="fa fa-search"></i> {1}</button>'
                        .format(rowObject.id, '详情');
            },frozen:true },
            </shrio:hasPermission>
            { label: '单位编号', name: 'code', width: 80,frozen:true },
            { label: '单位名称', name: 'id', width: 350, align:'left', formatter:$.jgrid.formatter.unit,frozen:true },
             <shrio:hasPermission name="unit:changeOrder">
            <c:if test="${!_query}">
            { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,frozen:true },
            </c:if>
            </shrio:hasPermission>
            { label: '单位类型', name: 'typeId', width: 140, formatter: $.jgrid.formatter.MetaType },
            <c:if test="${cls==1}">
            <shiro:hasPermission name="unitPost:*">
            { label: '正处级<br/>岗位数', name: 'mainPostCount', width: 80},
            { label: '副处级<br/>岗位数', name: 'vicePostCount', width: 80},
            { label: '${_p_label_adminLevelNone}<br/>岗位数', name: 'nonePostCount', width: 90},
            <c:if test="${_p_hasKjCadre}">
            { label: '正科级<br/>岗位数', name: 'mainKjPostCount', width: 80},
            { label: '副科级<br/>岗位数', name: 'viceKjPostCount', width: 80},
            </c:if>
            { label: '正处级<br/>干部职数', name: 'mainCount', width: 80},
            { label: '副处级<br/>干部职数', name: 'viceCount', width: 80},
            { label: '${_p_label_adminLevelNone}<br/>干部职数', name: 'noneCount', width: 90},
            <c:if test="${_p_hasKjCadre}">
            { label: '正科级<br/>干部职数', name: 'mainKjCount', width: 80},
            { label: '副科级<br/>干部职数', name: 'viceKjCount', width: 80},
            </c:if>
            {label: '是否列入<br/>配备一览表', name: 'notStatPost',
                formatter: $.jgrid.formatter.TRUEFALSE, formatoptions:{on: '否', off: '是'}},
            </shiro:hasPermission>
            </c:if>
            /*{ label: '成立时间', name: 'workTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '成立文件', name: 'filePath', width: 80, formatter: function (cellvalue, options, rowObject) {
                return $.pdfPreview(cellvalue, rowObject.name + "-成立文件", "查看");
            }},*/
            { label: '备注', align:'left', name: 'remark', width: 500, formatter: $.jgrid.formatter.htmlencodeWithNoSpace}
        ]}).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>