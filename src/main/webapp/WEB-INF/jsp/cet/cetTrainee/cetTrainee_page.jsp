<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
<div class="jqgrid-vertical-offset buttons">
    <button class="jqOpenViewBtn btn btn-info btn-sm"
            data-grid-id="#jqGrid2"
            data-url="${ctx}/sysApprovalLog"
            data-width="850"
            data-querystr="&displayType=1&hideStatus=1&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ%>">
        <i class="fa fa-history"></i> 操作记录
    </button>
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
            <form class="form-inline search-form" id="searchForm2">
                <div class="form-group">
                    <label>姓名</label>
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/cet/cetProjectObj_selects?projectId=${param.projectId}"
                            data-width="280"
                            name="userId" data-placeholder="请输入账号或姓名或教工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-target="#detail-content${empty param.trainId?'':'2'}"
                       data-form="#searchForm2"
                       data-url="${ctx}/cet/cetTrainee?trainId=${param.trainId}&projectId=${param.projectId}&cls=${param.cls}"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                data-target="#detail-content${empty param.trainId?'':'2'}"
                                data-url="${ctx}/cet/cetTrainee?trainId=${param.trainId}&projectId=${param.projectId}&cls=${param.cls}">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="space-4"></div>
<div class="rownumbers">
    <table id="jqGrid2" class="jqGrid2 table-striped"></table>
    <div id="jqGridPager2"></div>
</div>

<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    var courseCount = ${courseCount};
    $.register.user_select($("#searchForm2 select[name=userId]"));

    var cetTraineeTypeMap = ${cm:toJSONObject(cetTraineeTypeMap)};
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        url: '${ctx}/cet/cetTrainee_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'obj.user.code', width: 110, frozen: true},
              {label: '姓名', name: 'obj.user.realname', width: 120, frozen: true},
            { label: '学习详情',name: '_detail', width: 80, formatter: function (cellvalue, options, rowObject) {
                  return ('<button class="popupBtn btn btn-success btn-xs" data-width="1100" ' +
                  'data-url="${ctx}/cet/cetTrainee_detail?userId={0}&projectId=${param.projectId}&trainId=${param.trainId}"><i class="fa fa-search"></i> 查看</button>')
                          .format(rowObject.userId);
              }, frozen: true},
              { label: '选课情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
                  if(courseCount==0) return '--'
                  return '{0}/{1}'.format(rowObject.courseCount, courseCount);
              }, frozen: true},
              { label: '签到情况',name: '_sign', width: 80, formatter: function (cellvalue, options, rowObject) {
                  if(rowObject.courseCount==undefined) return '--'
                  return '{0}/{1}'.format(rowObject.finishCount, rowObject.courseCount);
              }, frozen: true},
              { label: '完成学时数',name: 'finishPeriod', frozen: true},
              {name:"objId", hidden:true, key:true}
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>