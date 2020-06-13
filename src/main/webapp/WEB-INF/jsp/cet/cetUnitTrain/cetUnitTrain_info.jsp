<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_UNREPORT%>" var="_UNREPORT"/>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_REPORT%>" var="_REPORT"/>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_PASS%>" var="_PASS"/>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_UNPASS%>" var="_UNPASS"/>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_DELETE%>" var="_DELETE"/>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_PARTY%>" var="CET_UNITTRAIN_RERECORD_PARTY"/>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_CET%>" var="CET_UNITTRAIN_RERECORD_CET"/>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_SAVE%>" var="CET_UNITTRAIN_RERECORD_SAVE"/>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_MAP%>" var="CET_UNITTRAIN_RERECORD_MAP"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.addUserId || not empty param.projectName || not empty param.traineeTypeId || not empty param.userId || not empty param.sort
             || not empty param._startDate || not empty param._endDate}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${cls==2}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/cet/cetUnitTrain_info?cls=2"><i
                                class="fa fa-list"></i> 审核通过(${cm:trimToZero(statusCountMap.get(_PASS))})
                        </a>
                    </li>
                    <li class="<c:if test="${cls==0}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/cet/cetUnitTrain_info?cls=0"><i
                                class="fa fa-circle-o"></i> 待报送(${cm:trimToZero(statusCountMap.get(_UNREPORT))})
                        <span id="unreportCount"></span>
                        </a>
                    </li>
                    <li class="<c:if test="${cls==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/cet/cetUnitTrain_info?cls=1"><i
                                class="fa fa-history"></i> 待组织部审核(${cm:trimToZero(statusCountMap.get(_REPORT))})
                        <span id="checkCount"></span>
                        </a>
                    </li>
                    <li class="<c:if test="${reRecord==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/cet/cetUnitTrain_info?reRecord=1"><i
                                class="fa fa-history"></i> 补录申请(${reRecondCount})
                            <span id="checkCount"></span>
                        </a>
                    </li>
                    <li class="<c:if test="${cls==3}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/cet/cetUnitTrain_info?cls=3"><i
                                class="fa fa-times"></i> 未通过审核(${cm:trimToZero(statusCountMap.get(_UNPASS))})</a>
                    </li>
                    <shiro:hasRole name="${ROLE_CET_ADMIN}">
                    <li class="<c:if test="${cls==4}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/cet/cetUnitTrain_info?cls=4"><i
                                class="fa fa-trash"></i> 已删除(${cm:trimToZero(statusCountMap.get(_DELETE))})</a>
                    </li>
                    </shiro:hasRole>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${reRecord==1 && cm:isPermitted('cetUnitProject:check')}">
                                <button data-url="${ctx}/cet/cetUnitTrain_check"
                                        data-grid-id="#jqGrid"
                                        class="jqOpenViewBatchBtn btn btn-success btn-sm">
                                    <i class="fa fa-check-circle-o"></i> 审批
                                </button>
                            </c:if>
                        </div>
                        <div class="space-4"></div>
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
                                            <label>参训人姓名</label>
                                            <select data-ajax-url="${ctx}/sysUser_selects" data-rel="select2-ajax"
                                                    name="userId" data-placeholder="请输入账号或姓名或工号">
                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>参训人类型</label>
                                            <select data-rel="select2" name="traineeTypeId" data-placeholder="请选择"data-width="272">
                                                <option></option>
                                                <c:forEach items="${traineeTypeMap}" var="entity">
                                                    <c:if test="${entity.value.code!='t_reserve' && entity.value.code!='t_candidate'}">
                                                        <option value="${entity.value.id}">${entity.value.name}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <script>$("#searchForm select[name=traineeTypeId]").val(${traineeTypeId});</script>
                                        <div class="form-group">
                                            <label>培训项目名称</label>
                                            <input class="form-control search-query" name="projectName" type="text" value="${param.projectName}"
                                                   placeholder="请输入">
                                        </div>
                                        <div class="form-group">
                                            <label>培训开始时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="培训开始时间范围">
                                                                <span class="input-group-addon">
                                                                    <i class="fa fa-calendar bigger-110"></i>
                                                                </span>
                                                <input placeholder="请选择培训开始时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_startDate" value="${param._startDate}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>培训结束时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="培训结束时间范围">
                                                                <span class="input-group-addon">
                                                                    <i class="fa fa-calendar bigger-110"></i>
                                                                </span>
                                                <input placeholder="请选择培训开始时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_endDate" value="${param._endDate}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>操作人</label>
                                            <select data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}" data-rel="select2-ajax"
                                                    name="addUserId" data-placeholder="请输入账号或姓名或工号">
                                                <option value="${addSysUser.id}">${addSysUser.realname}-${addSysUser.code}</option>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-url="${ctx}/cet/cetUnitTrain_info?cls=${cls}"
                                               data-target="#page-content"
                                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-url="${ctx}/cet/cetUnitTrain_info?cls=${cls}"
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
                        <table id="jqGrid" class="jqGrid table-striped" data-height-reduce="7"></table>
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
    var traineeTypeMap = ${cm:toJSONObject(traineeTypeMap)};
    $.register.date($('.date-picker'));
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/cet/cetUnitTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '参训人姓名', name: 'user.realname', frozen:true},
            {label: '参训人工号', width: 110, name: 'user.code', frozen:true},
            { label: '参训人类型', name: 'traineeTypeId', formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined)return '--'
                    return traineeTypeMap[cellvalue].name
                }, width:180},
            <c:if test="${reRecord==1}">
            {label: '补录进度', name: 'status', width: 120, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue==${CET_UNITTRAIN_RERECORD_CET})
                        return '${CET_UNITTRAIN_RERECORD_MAP.get(CET_UNITTRAIN_RERECORD_CET)}';
                    else if (cellvalue==${CET_UNITTRAIN_RERECORD_PARTY})
                        return '${CET_UNITTRAIN_RERECORD_MAP.get(CET_UNITTRAIN_RERECORD_PARTY)}';
                    else if (cellvalue==${CET_UNITTRAIN_RERECORD_SAVE})
                        return '${CET_UNITTRAIN_RERECORD_MAP.get(CET_UNITTRAIN_RERECORD_SAVE)}';
                }},
            </c:if>
            {label: '培训项目名称', name: 'project.projectName', align: 'left',width: 350},
            { label: '培训班主办方',name: 'cetParty.partyId',align:'left', width: 270, formatter:function(cellvalue, options, rowObject){
                    return $.party(cellvalue);
                }, frozen: true},
            {label: '培训<br/>开始时间', name: 'project.startDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '培训<br/>结束时间', name: 'project.endDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '时任单位及职务', name: 'title', align: 'left', width: 350},
            {label: '职务属性', name: 'postType', width: 140, align: 'center',formatter: $.jgrid.formatter.MetaType},
            {label: '完成培训学时', name: 'period'},
            {label: '培训总结', name: '_note', width: 200, align:"left", formatter: function (cellvalue, options, rowObject) {

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
                    return ret==""?"--":ret;
                }},
            {label: '操作人', name: 'addUser.realname'},
            {label: '添加时间', name: 'addTime', width: 150},
            { label: '备注',name: 'remark', align: 'left', width: 150},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>