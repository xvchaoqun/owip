<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.userId||not empty param.type
                   ||not empty param.traineeTypeId || not empty param.trainDate || not empty param.sort || not empty param.repeatType}"/>
            <div class="jqgrid-vertical-offset buttons">
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/cet/cetRecord_data"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出
                </button>
                <button data-url="${ctx}/cet/cetRecord_selectOrUpdateCertNo"
                        data-title="更新证书编码"
                        data-msg="确定更新证书编码（已选{0}条数据）？<br/>（已存在的证书不会覆盖）"
                        data-grid-id="#jqGrid"
                        class="jqBatchBtn btn btn-warning btn-sm">
                    <i class="fa fa-refresh"></i> 更新证书编码</button>

                <button data-url="${ctx}/cet/cetRecord_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？<br/>（同时会删除对应培训类别中的数据，删除的数据无法恢复，请谨慎删除）"
                        data-grid-id="#jqGrid"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-trash"></i> 删除</button>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <span class="widget-note">${note_searchbar}</span>
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
                                <label>年度</label>
                                <input class="form-control date-picker" placeholder="请选择年份"
                                   name="year" type="text" style="width: 80px;"
                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                   value="${param.year}"/>
                            </div>
                            <div class="form-group">
                                <label>培训类型</label>
                                 <select name="type" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="<%=CetConstants.CET_TYPE_MAP%>" var="entity">
                                    <option value="${entity.key}">${entity.value}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=type]").val('${param.type}')
                                </script>
                            </div>
                            <div class="form-group">
                                <label>参训人员类型</label>
                                <select data-rel="select2" name="traineeTypeId" data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="${traineeTypeMap}" var="entity">
                                        <option value="${entity.key}">${entity.value.name}</option>
                                    </c:forEach>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=traineeTypeId]").val(${param.traineeTypeId});
                                </script>
                            </div>
                            <div class="form-group">
                                <label>参训人</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/sysUser_selects"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>培训日期</label>
                                <input placeholder="请选择培训日期范围" data-rel="date-range-picker"
                                           class="form-control date-range-picker"
                                           type="text" name="trainDate" value="${param.trainDate}"/>
                            </div>
                            <div class="form-group">
                                <label>培训时间重叠情况</label>
                                <select data-rel="select2" name="repeatType" data-placeholder="请选择">
                                    <option></option>
                                    <option value="1">培训内容相同</option>
                                    <option value="2">培训内容不限</option>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=repeatType]").val(${param.repeatType});
                                </script>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cet/cetRecord"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetRecord"
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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    var traineeTypeMap = ${cm:toJSONObject(traineeTypeMap)};
    var specialProjectTypeMap = ${cm:toJSONObject(specialProjectTypeMap)};
    var dailyProjectTypeMap = ${cm:toJSONObject(dailyProjectTypeMap)};
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/cet/cetRecord_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年度', name: 'year', width: 60, frozen: true},
            {label: '参训人工号', width: 110, name: 'user.code', frozen:true},
            {label: '参训人姓名', name: 'user.realname', frozen:true},
            {label: '是否<br/>结业', name: 'isGraduate', formatter: function (cellvalue, options, rowObject) {
              if (cellvalue==undefined) {
                return '--'
              }
              return cellvalue?'是':'否'
            }, width: 50},
            <c:if test="${_p_cetSupportCert}">
            {label: '结业证书', name: 'isGraduate', width: 70, formatter: function (cellvalue, options, rowObject) {

                if(!rowObject.isGraduate) return '--'
                return $.button.modal({
                            style:"btn-success",
                            url:"${ctx}/cet/cert?ids="+rowObject.id,
                            icon:"fa-search",
                            label:"查看", attr:"data-width='850'"})
            }},
            </c:if>
            {label: '时任单位及职务', name: 'title', align: 'left', width: 250, formatter: function (cellvalue, options, rowObject) {
              if(cellvalue == undefined){
                  return "--";
              }else{
                  return rowObject.title;
              }}},
            { label: '参训人类型', name: 'traineeTypeId', width: 120, formatter: function (cellvalue, options, rowObject) {
                      if(cellvalue==null)return '--';
                      if(cellvalue==0) return rowObject.otherTraineeType;
                      return traineeTypeMap[cellvalue].name
            }},
            {label: '培训<br/>开始时间', name: 'startDate', width: 90, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '培训<br/>结束时间', name: 'endDate', width: 90, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '培训内容', name: 'name', align: 'left',width: 300},
            {label: '培训类别', name: 'type', width: 120, formatter: function (cellvalue, options, rowObject) {
                return _cMap.CET_TYPE_MAP[cellvalue]
            }},
            {
                label: '培训班类型', name: 'projectType', width: 120, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--'
                    if(rowObject.specialType==<%=CetConstants.CET_PROJECT_TYPE_SPECIAL%>) {
                        if (specialProjectTypeMap[cellvalue] == undefined) return '--'
                        return specialProjectTypeMap[cellvalue].name
                    }else{
                        if (dailyProjectTypeMap[cellvalue] == undefined) return '--'
                        return dailyProjectTypeMap[cellvalue].name
                    }
            }},
            {label: '培训主办方', name: 'organizer', align: 'left', width: 180},
            {label: '完成<br/>学时总数', name: 'period', width: 80},
            {label: '线上完成<br/>学时数', name: 'onlinePeriod', width: 80},

            {label: '是否计入<br/>年度学习任务', name: 'isValid', formatter: function (cellvalue, options, rowObject) {
              if (cellvalue==undefined) {
                return '--'
              }
              return cellvalue?'是':'否'
            }},
            {label: '备注', name: 'remark', align: 'left', width: 150},
            {label: '归档时间', name: 'archiveTime', width: 150}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>