<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId || not empty param.cetPartyId || not empty param.sort}"/>
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
                                <label>培训主办方</label>

                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cet/cetParty_selects?auth=${cm:isPermitted(PERMISSION_CETADMIN)?0:1}"
                                             data-width="308" name="cetPartyId" data-placeholder="请选择">
                                    <option value="${cetParty.id}" delete="${cetParty.isDeleted}">${cetParty.name}</option>
                                </select>
                                <script>
                                    $.register.del_select($("#searchForm select[name=cetPartyId]"))
                                </script>
                            </div>
                            <div class="form-group">
                                <label>姓名</label>
                                <select name="userId" data-rel="select2-ajax" data-width="280"
                                        data-ajax-url="${ctx}/sysUser_selects"
                                        data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cet/cetTraineeList"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetTraineeList">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $.register.user_select($("#searchForm select[name=userId]"));
    var cetTraineeTypeMap = ${cm:toJSONObject(cetTraineeTypeMap)};
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/cet/cetTraineeList_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '培训时间', name: 'startDate', width: 200, formatter: function (cellvalue, options, rowObject) {
                    return '{0} ~ {1}'.format($.date(rowObject.startDate, "yyyy.MM.dd"), $.date(rowObject.endDate, "yyyy.MM.dd"))
                }, frozen: true
            },
            {label: '培训班名称', name: 'projectName', width: 300, align: 'left', frozen: true},
            {label: '培训班主办方', name: 'cetParty.name', align: 'left', width: 210},
            {label: '工作证号', name: 'obj.user.code', width: 110},
            {label: '姓名', name: 'obj.user.realname', width: 120},
            {label: '已选课程', name: 'courseCount', width: 80},
            {label: '已签到', name: 'finishCount', width: 80},
            {label: '完成学时数', name: 'finishPeriod'},
            {label: '线上<br/>完成学时数', name: 'onlineFinishPeriod'},
            {label: '培训班状态', name: 'projectStatus', formatter: function (cellvalue, options, rowObject){
                  if(cellvalue==undefined) return '--'
                  return _cMap.CET_PROJECT_STATUS_MAP[cellvalue];
              } },
            {name: "objId", hidden: true, key: true}
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>