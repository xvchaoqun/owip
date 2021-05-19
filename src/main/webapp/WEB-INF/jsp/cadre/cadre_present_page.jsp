<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/cadre_staff"
             data-url-export="${ctx}/cadre_present_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId  ||not empty param.realname
                   ||not empty param.unitIds ||not empty param.gender
                   ||not empty param.nation||not empty param.dpTypes||not empty param._birth
                   ||not empty param.postTypes || not empty param.code || not empty param.title}"/>
            <div class="tabbable">
                <div class="tab-content multi-row-head-table">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>
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
                                        <input name="type" type="hidden" value="${param.type}"/>
                                        <input name="isFinished" type="hidden" value="${isFinished}"/>

                                        <div class="form-group">
                                            <label>姓名</label>
                                            <select data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/sysUser_selects"
                                                    name="userId" data-placeholder="请输入账号或姓名或工作证号">
                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>所在单位</label>
                                            <select class="multiselect" multiple="" name="unitIds">
                                                <c:forEach var="unitType"
                                                           items="${cm:getMetaTypes('mc_unit_type')}">
                                                    <optgroup label="${unitType.value.name}">
                                                        <c:forEach
                                                                items="${unitListMap.get(unitType.value.id)}"
                                                                var="unitId">
                                                            <c:set var="unit"
                                                                   value="${unitMap.get(unitId)}"></c:set>
                                                            <option value="${unit.id}">${unit.name}</option>
                                                        </c:forEach>
                                                    </optgroup>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>所在单位及职务</label>
                                            <input class="form-control search-query" name="title" type="text"
                                                   value="${param.title}"
                                                   placeholder="请输入所在单位及职务">
                                        </div>
                                        <div class="form-group">
                                            <label>行政级别</label>
                                            <select class="multiselect" multiple="" name="adminLevels">
                                                <c:import url="/metaTypes?__code=mc_admin_level"/>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>职务属性</label>
                                            <select class="multiselect" multiple="" name="postTypes">
                                                <c:import url="/metaTypes?__code=mc_post"/>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>性别</label>
                                            <select name="gender" data-width="100" data-rel="select2"
                                                    data-placeholder="请选择">
                                                <option></option>
                                                <option value="<%=SystemConstants.GENDER_MALE%>">男</option>
                                                <option value="<%=SystemConstants.GENDER_FEMALE%>">女
                                                </option>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=gender]").val('${param.gender}');
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>民族</label>
                                                <select class="multiselect" multiple="" name="nation">
                                                    <option value="-1">少数民族</option>
                                                    <c:forEach var="nation" items="${cm:getMetaTypes('mc_nation').values()}">
                                                        <option value="${nation.name}">${nation.name}</option>
                                                    </c:forEach>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                            <label>出生时间</label>
                                                <input placeholder="请选择出生日期范围" data-rel="date-range-picker"
                                                       class="form-control date-range-picker"
                                                       type="text" name="_birth" value="${param._birth}"/>
                                        </div>
                                        <div class="form-group">
                                            <label>政治面貌</label>
                                            <select class="multiselect" multiple="" name="dpTypes"
                                                    style="width: 250px;">
                                                <option value="-1">非中共党员</option>
                                                <option value="0">中共党员</option>
                                                <c:import url="/metaTypes?__code=mc_democratic_party"/>
                                                <shiro:hasRole name="${ROLE_SUPER}">
                                                    <option value="-2">空</option>
                                                </shiro:hasRole>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="type=${param.type}&isFinished=${isFinished}">
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
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/cadre_present_data?type=${param.type}&isFinished=${isFinished}&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'code', width: 110, frozen: true},
            {label: '姓名', name: 'realname', width: 120},
            {label: '所在单位', name: 'unitId', width: 350, align: 'left', formatter: $.jgrid.formatter.unit},
            {label: '所在单位及职务', name: 'title', width: 350, align: 'left'},
            {label: '行政级别', name: 'adminLevel', formatter: $.jgrid.formatter.MetaType},
            {label: '职务属性', name: 'postType', width: 150, formatter:$.jgrid.formatter.MetaType},
            {label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER},
            {label: '民族', name: 'nation', width: 60},
            {label: '出生时间', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '政治面貌', name: '_cadreParty', width: 80, formatter: $.jgrid.formatter.cadreParty},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('#searchForm select[name=userId]'));
    $.register.multiselect($('#searchForm select[name=unitIds]'), ${cm:toJSONArray(selectUnitIds)}, {
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false
    });
    $.register.multiselect($('#searchForm select[name=adminLevels]'), ${cm:toJSONArray(selectAdminLevels)});
    $.register.multiselect($('#searchForm select[name=postTypes]'), ${cm:toJSONArray(selectPostTypes)});
    $.register.multiselect($('#searchForm select[name=nation]'), ${cm:toJSONArray(selectNations)});
    $.register.multiselect($('#searchForm select[name=dpTypes]'), ${cm:toJSONArray(selectDpTypes)});
</script>