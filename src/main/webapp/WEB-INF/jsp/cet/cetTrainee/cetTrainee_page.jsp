<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
<div class="jqgrid-vertical-offset buttons">
    <div class="type-select">
        <c:forEach items="${cetTraineeTypes}" var="cetTraineeType">
        <span class="typeCheckbox ${traineeTypeId==cetTraineeType.id?"checked":""}">
        <input ${traineeTypeId==cetTraineeType.id?"checked":""} type="checkbox" value="${cetTraineeType.id}"> ${cetTraineeType.name}(${cm:trimToZero(typeCountMap.get(cetTraineeType.id))})
        </span>
        </c:forEach>
    </div>
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
                <input type="hidden" name="traineeTypeId" value="${traineeTypeId}">
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
                       data-target="#detail-content2"
                       data-form="#searchForm2"
                       data-url="${ctx}/cet/cetTrainee?trainId=${param.trainId}&projectId=${param.projectId}&cls=${param.cls}"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                data-target="#detail-content2"
                                data-url="${ctx}/cet/cetTrainee?trainId=${param.trainId}&projectId=${param.projectId}&traineeTypeId=${traineeTypeId}&cls=${param.cls}">
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
    var yearPeriodMap = ${cm:toJSONObject(yearPeriodMap)};
    var courseCount = ${courseCount};
    $(".typeCheckbox").click(function () {
        var $input = $("input", $(this));
        $("#searchForm2 input[name=traineeTypeId]").val($input.val());
        $("#searchForm2 .jqSearchBtn").click();
    })

    $.register.user_select($("#searchForm2 select[name=userId]"));

    var cetTraineeTypeMap = ${cm:toJSONObject(cetTraineeTypeMap)};
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        url: '${ctx}/cet/cetTrainee_data?callback=?&traineeTypeId=${traineeTypeId}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '学习详情',name: '_detail', width: 80, formatter: function (cellvalue, options, rowObject) {
                  return ('<button class="popupBtn btn btn-success btn-xs" data-width="1100" ' +
                  'data-url="${ctx}/cet/cetTrainee_detail?userId={0}&trainId={1}"><i class="fa fa-search"></i> 查看</button>')
                          .format(rowObject.userId, rowObject.trainId);
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
              {label: '工作证号', name: 'obj.user.code', width: 110, frozen: true},
              {label: '姓名', name: 'obj.user.realname', width: 120, frozen: true},

              <c:if test="${cetTraineeType.code=='t_leader'||cetTraineeType.code=='t_cadre'
                    ||cetTraineeType.code=='t_cadre_kj'||cetTraineeType.code=='t_reserve'}">
                {label: '时任单位及职务', name: 'obj.title', align: 'left', width: 350},
                {label: '行政级别', name: 'obj.adminLevel', formatter:$.jgrid.formatter.MetaType},
                {label: '职务属性', name: 'obj.postType', width: 150, formatter:$.jgrid.formatter.MetaType},
                </c:if>
                <c:if test="${cetTraineeType.code=='t_party_member'}">
                 {
                    label: '所在党组织',
                    name: 'obj.partyId',
                    align: 'left',
                    width: 550,
                    formatter: function (cellvalue, options, rowObject) {
                        return $.party(rowObject.obj.partyId, rowObject.obj.branchId);
                    }
                },
                {label: '职务', name: 'obj.postId', formatter:$.jgrid.formatter.MetaType},
                {
                    label: '分工', name: 'obj.partyTypeIds', width: 300, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    var typeIdStrs = [];
                    var typeIds = cellvalue.split(",");
                    for(i in typeIds){
                        var typeId = typeIds[i];
                        //console.log(typeId)
                        if(typeId instanceof Function == false)
                            typeIdStrs.push($.jgrid.formatter.MetaType(typeId));
                    }
                    //console.log(typeIdStrs)
                    return typeIdStrs.join(",");
                }
                },
                {label: '任职时间', name: 'obj.assignDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                </c:if>
                <c:if test="${cetTraineeType.code=='t_branch_member'}">
                 {
                    label: '所在党组织',
                    name: 'obj.partyId',
                    align: 'left',
                    width: 550,
                    formatter: function (cellvalue, options, rowObject) {
                        return $.party(rowObject.obj.partyId, rowObject.obj.branchId);
                    }
                },
                {label: '类别', name: 'obj.branchTypeId', formatter:$.jgrid.formatter.MetaType},
                {label: '任职时间', name: 'obj.assignDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                </c:if>
                <c:if test="${cetTraineeType.code=='t_organizer'}">
                { label: '组织员类别', name: 'obj.organizerType', width: 150, formatter:function(cellvalue, options, rowObject){
                    return _cMap.OW_ORGANIZER_TYPE_MAP[cellvalue];
                }},
                { label: '联系单位',name: 'obj.organizerUnits', align:'left', formatter: function (cellvalue, options, rowObject) {
                        if($.trim(cellvalue)=='') return'--'
                            return ($.map(cellvalue.split(","), function(u){
                                return u.split("|")[0];
                            })).join("、")
                        }, width:700},
                { label: '联系${_p_partyName}', name: 'obj.organizerPartyId',align:'left', width: 350, formatter:function(cellvalue, options, rowObject){
                    return $.party(rowObject.organizerPartyId);
                }},
                {label: '任职时间', name: 'obj.assignDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                </c:if>
                <c:if test="${cetTraineeType.code=='t_candidate'||cetTraineeType.code=='t_activist'||cetTraineeType.code=='t_grow'}">
                 {
                    label: '联系党组织',
                    name: 'obj.partyId',
                    align: 'left',
                    width: 550,
                    formatter: function (cellvalue, options, rowObject) {
                        return $.party(rowObject.obj.partyId, rowObject.obj.branchId);
                    }
                },
                {label: '成为积极分子时间', name: 'obj.activeTime', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                <c:if test="${cetTraineeType.code=='t_candidate'}">
                {label: '成为发展对象时间', name: 'candidateTime', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                </c:if>
                <c:if test="${cetTraineeType.code=='t_grow'}">
                {label: '入党时间', name: 'owGrowTime', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                </c:if>
                </c:if>
              /*{label: '联系方式', name: 'cadre.mobile', width: 120},
              {label: '电子邮箱', name: 'cadre.email', width: 250, algin:"left"},*/

                { label: '本年度参加培训情况',name: 'courseCount', width: 150, formatter: function (cellvalue, options, rowObject) {

                  var yearPeriod = yearPeriodMap[rowObject.userId];
                  return yearPeriod==undefined?'-':yearPeriod;
              }}
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>