<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-url-page="${ctx}/cadreParty?cls=${cls}"
                 data-url-export="${ctx}/cadreParty_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.adminLevel||not empty param.classId
            ||not empty param.postType ||not empty param.title || not empty param.code }"/>
                <div class="tabbable">
                    <c:if test="${type==1}">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                      <li class="<c:if test="${cls==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/cadreParty?type=${type}&cls=1"><i class="fa fa-users"></i> 民主党派成员</a>
                      </li>
                      <li class="<c:if test="${cls==2}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/cadreParty?type=${type}&cls=2"><i class="fa fa-user"></i> 群众</a>
                      </li>
                    </ul>
                        </c:if>
                    <div class="tab-content">
                        <div class="tab-pane in active rownumbers">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="cadreParty:edit">
                                    <a class="popupBtn btn btn-sm btn-info"
                                       data-url="${ctx}/cadreParty_au?type=${type}&cls=${cls}">
                                        <i class="fa fa-plus"></i> 添加</a>
                                </shiro:hasPermission>
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/cadreParty_au?cls=${cls}">
                                    <i class="fa fa-edit"></i> 修改信息
                                </button>
                                <c:if test="${cls==1}">

                                <a class="popupBtn btn btn-success btn-sm tooltip-success"
                                   data-url="${ctx}/cadreParty_import?type=${type}"
                                   data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                    批量导入</a>
                                    </c:if>
                                <shiro:hasPermission name="cadreParty:del">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/cadreParty_batchDel" data-title="删除"
                                       data-msg="确定删除这{0}条数据吗？"><i class="fa fa-trash"></i> 删除</a>
                                </shiro:hasPermission>
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
                                            <input type="hidden" name="type" value="${type}">

                                            <div class="form-group">
                                                <label>姓名</label>

                                                <div class="input-group">
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/sysUser_selects"
                                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <c:if test="${param.type==1}">
                                                <div class="form-group">
                                                    <label>民主党派</label>
                                                    <select data-rel="select2" name="dpTypeId"
                                                            data-placeholder="请选择民主党派">
                                                        <option></option>
                                                        <jsp:include page="/WEB-INF/jsp/base/metaType/dpTypes.jsp?dp=1"/>
                                                    </select>
                                                    <script type="text/javascript">
                                                        $("#searchForm select[name=dpTypeId]").val(${param.dpTypeId});
                                                    </script>
                                                </div>
                                            </c:if>
                                            <div class="form-group">
                                                <label>行政级别</label>
                                                <select data-rel="select2" name="adminLevel" data-placeholder="请选择行政级别">
                                                    <option></option>
                                                    <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=adminLevel]").val(${param.adminLevel});
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>职务属性</label>
                                                <select data-rel="select2" name="postType" data-placeholder="请选择职务属性">
                                                    <option></option>
                                                    <jsp:include page="/metaTypes?__code=mc_post"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=postType]").val(${param.postType});
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>单位及职务</label>
                                                <input class="form-control search-query" name="title" type="text"
                                                       value="${param.title}"
                                                       placeholder="请输入单位及职务">
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i
                                                        class="fa fa-search"></i> 查找</a>

                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-querystr="type=${type}&cls=${cls}">
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
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view">

        </div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/cadreParty_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'code', width: 120, frozen: true},
            {
                label: '姓名', name: 'realname', width: 110, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadreId, cellvalue);
            }, frozen: true
            },
            <c:if test="${type==1}">
            {label: '${cls==1?"民主党派":"类别"}', name: 'classId', width: 120, formatter:function(cellvalue, options, rowObject){
                var str = '<span class="red" title="非第一民主党派">* </span>';
                var dp = $.jgrid.formatter.MetaType(cellvalue);
                return (!rowObject.isFirst)?str+dp:dp;
            }},
            <c:if test="${cls==1}">
            {label: '党派加入时间', name: 'growTime',
                formatter: $.jgrid.formatter.date, formatoptions: {newformat: '${_p_hasPartyModule?"Y.m.d":"Y.m"}'}, width: 110},
            {label: '担任党派职务', name: 'post',align:'left',  width: 250},
            </c:if>
            </c:if>
            <c:if test="${type==2}">
            {label: '党派加入时间', name: 'growTime', formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: '${_p_hasPartyModule?"Y.m.d":"Y.m"}'}, width: 110},
            <c:if test="${_p_hasPartyModule}">
            {
                label: '是否存在于党员信息库', width: 180, name: 'memberStatus', formatter: function (cellvalue, options, rowObject) {
                var str = "否";
                var ms = $.trim(cellvalue);
                if(ms!='' && (ms=='${MEMBER_STATUS_NORMAL}' || ms=='${MEMBER_STATUS_TRANSFER}')){
                    str = "是";
                }
                return str;
            }, cellattr:function(rowId, val, rowObject, cm, rdata) {
                return ($.trim(rowObject.memberStatus)=='')?"":"class='danger'";
            }},
            </c:if>
            </c:if>
            {label: '部门属性', name: 'unitId', width: 150, formatter: function (cellvalue, options, rowObject) {
                var unit = _cMap.unitMap[cellvalue];
                if(unit==undefined) return '--'
                return $.jgrid.formatter.MetaType(unit.typeId);
            }},
            {label: '所在单位', name: 'unitId', align:'left',  width: 200, formatter: $.jgrid.formatter.unit},
            {label: '现任职务', name: 'cadrePost', align:'left', width: 250},
            {label: '所在单位及职务', name: 'cadreTitle',  align:'left', width: 350},
            {label: '行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
            {label: '职务属性', name: 'postType', width: 150, formatter:$.jgrid.formatter.MetaType},
            {
                label: '在任情况', name: 'cadreStatus', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';

                return _cMap.CADRE_STATUS_MAP[cellvalue];
            }
            }, {label: '备注', name: 'remark', align:'left', width: 350}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=userId]'));
</script>