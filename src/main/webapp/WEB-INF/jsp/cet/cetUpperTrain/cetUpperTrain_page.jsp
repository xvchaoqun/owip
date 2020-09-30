<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CET_UPPER_TRAIN_TYPE_OW" value="<%=CetConstants.CET_UPPER_TRAIN_TYPE_OW%>"/>
<c:set var="CET_UPPER_TRAIN_TYPE_ABROAD" value="<%=CetConstants.CET_UPPER_TRAIN_TYPE_ABROAD%>"/>
<c:set var="CET_UPPER_TRAIN_TYPE_SCHOOL" value="<%=CetConstants.CET_UPPER_TRAIN_TYPE_SCHOOL%>"/>

<c:set var="CET_UPPER_TRAIN_TYPE_UNIT" value="<%=CetConstants.CET_UPPER_TRAIN_TYPE_UNIT%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_SELF" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_OW" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_UNIT" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT%>"/>
<c:set var="CET_PROJECT_TYPE_MAP" value="<%=CetConstants.CET_PROJECT_TYPE_MAP%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.unitId ||not empty param.userId || not empty param.postType || not empty param.identities
                   ||not empty param.organizer || not empty param.specialType || not empty param.trainType || not empty param.year || not empty param.title || not empty param.otherOrganizer
                   || not empty param.startDate || not empty param.endDate || not empty param.prePeriod || not empty param.trainName}"/>
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${cls==1}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUpperTrain?cls=1&type=${param.type}&addType=${param.addType}"><i
                            class="fa fa-list"></i> 信息汇总
                    </a>
                </li>
                <c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_OW}">
                <li class="<c:if test="${cls==2}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUpperTrain?cls=2&type=${param.type}&addType=${param.addType}"><i
                            class="fa fa-circle-o"></i>
                        待组织部${param.type==CET_UPPER_TRAIN_TYPE_OW
                        ||param.type==CET_UPPER_TRAIN_TYPE_ABROAD
                        ||param.type==CET_UPPER_TRAIN_TYPE_SCHOOL?'审核':'确认'}
                    <span id="checkCount"></span>
                    </a>
                </li>
                 </c:if>
                <c:if test="${param.type==CET_UPPER_TRAIN_TYPE_UNIT && param.addType!=CET_UPPER_TRAIN_ADD_TYPE_SELF}">
                <li class="<c:if test="${cls==5}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUpperTrain?cls=5&type=${param.type}&addType=${param.addType}"><i
                            class="fa fa-circle-o"></i> 待单位审核
                        <span id="unitCheckCount"></span>
                    </a>
                </li>
                </c:if>
                <c:if test="${param.type==CET_UPPER_TRAIN_TYPE_OW||param.type==CET_UPPER_TRAIN_TYPE_ABROAD}">
                <li class="<c:if test="${cls==3}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUpperTrain?cls=3&type=${param.type}&addType=${param.addType}"><i
                            class="fa fa-times"></i> 未通过审核</a>
                </li>
                </c:if>
                <c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_OW}">
                <li class="<c:if test="${cls==4}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUpperTrain?cls=4&type=${param.type}&addType=${param.addType}"><i
                            class="fa fa-trash"></i> 已删除</a>
                </li>
                </c:if>
            </ul>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">

                    <c:if test="${cls==1}">
                    <button class="popupBtn btn btn-success btn-sm"
                            data-url="${ctx}/cet/cetUpperTrain_au?type=${param.type}&addType=${param.addType}"
                            data-width="1000">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                    <button id="modifyBtn" class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cet/cetUpperTrain_au?type=${param.type}&addType=${param.addType}"
                            data-grid-id="#jqGrid" data-width="1000"><i class="fa fa-edit"></i>
                        修改
                    </button>
                    <shiro:hasPermission name="cetUpperTrain:import">
                    <button class="popupBtn btn btn-info btn-sm tooltip-success"
                        data-url="${ctx}/cet/cetUpperTrain_import?type=${param.type}"
                        data-rel="tooltip" data-placement="top" title="从Excel导入"><i class="fa fa-upload"></i> 批量导入</button>
                    </shiro:hasPermission>
                    </c:if>
                    <c:if test="${cls==2||cls==5}">
                    <button class="jqOpenViewBtn btn btn-success btn-sm"
                            data-url="${ctx}/cet/cetUpperTrain_au?type=${param.type}&addType=${param.addType}&check=1"
                            data-grid-id="#jqGrid" data-width="1000"><i class="fa fa-check-circle-o"></i>
                        <c:if test="${cls==2}">
                            ${param.type==CET_UPPER_TRAIN_TYPE_OW
                            ||param.type==CET_UPPER_TRAIN_TYPE_ABROAD
                            ||param.type==CET_UPPER_TRAIN_TYPE_SCHOOL?'审核':'组织部确认'}
                        </c:if>
                        <c:if test="${cls==5}">
                            审核
                        </c:if>
                    </button>
                    </c:if>
                <c:if test="${cls==1||cls==2||cls==3||cls==5}">
                    <button id="uploadNoteBtn" class="jqOpenViewBtn btn btn-warning btn-sm tooltip-success"
                        data-url="${ctx}/cet/cetUpperTrain_uploadNote?addType=${param.addType}"
                        data-grid-id="#jqGrid"
                        data-rel="tooltip" data-placement="top"
                        title="上传培训总结"><i class="fa fa-upload"></i> 上传培训总结</button>
                    <c:if test="${param.type!=CET_UPPER_TRAIN_TYPE_SCHOOL}">
                        <a href="javascript:;" data-width="700" class="jqOpenViewBatchBtn btn btn-danger btn-sm" data-url="${ctx}/cet/cetUpperTrain_batchTransfer?addType=${param.addType}">
                            <i class="fa fa-random"></i> 批量转移</a>
                    </c:if>
                </c:if>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/cet/cetUpperTrain_data?addType=${param.addType}&type=${param.type}"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
                <c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_OW}">
                <button class="jqOpenViewBtn btn btn-info btn-sm"
                        data-url="${ctx}/sysApprovalLog"
                        data-querystr="&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN%>"
                        data-open-by="page">
                    <i class="fa fa-search"></i> 操作记录
                </button>
                </c:if>
                <c:if test="${cls==1||cls==2||cls==5}">
                     <button id="delBtn" data-url="${ctx}/cet/cetUpperTrain_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </c:if>

                <c:if test="${cls==4}">
                    <button id="delBtn" data-url="${ctx}/cet/cetUpperTrain_batchDel?real=1"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？（删除后不可恢复）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 完全删除
                    </button>

               </c:if>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cet/cetUpperTrain_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
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
                            <c:if test="${param.type==CET_UPPER_TRAIN_TYPE_UNIT && param.addType==CET_UPPER_TRAIN_ADD_TYPE_OW}">
                            <div class="form-group">
                                <label>派出单位</label>
                                <select data-ajax-url="${ctx}/unit_selects"
                                        name="unitId" data-placeholder="请选择">
                                    <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                                </select>
                            </div>
                            </c:if>
                            <div class="form-group">
                                <label>年度</label>
                                <input class="form-control date-picker" placeholder="请选择年份"
                                       name="year" type="text" style="width: 80px;"
                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                       value="${param.year}"/>
                            </div>
                            <div class="form-group">
                                <label>参训人姓名</label>
                                <select data-ajax-url="${ctx}/cadre_selects?key=1"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>时任单位及职务</label>
                                <input class="form-control search-query" name="title" type="text" value="${param.title}"
                                       placeholder="请输入">
                            </div>
                            <div class="form-group">
                                <label>时任职务属性</label>
                                <select data-rel="select2" name="postType"
                                        data-width="150"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_post"/>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=postType]").val(${param.postType});
                                </script>
                            </div>
                            <c:if test="${cm:getMetaTypes('mc_cet_identity').size()>0}">
                                <div class="form-group">
                                    <label>参训人身份</label>
                                    <select  class="multiselect" multiple="" name="identities"
                                            data-width="120"
                                            data-placeholder="请选择">
                                        <c:import url="/metaTypes?__code=mc_cet_identity"/>
                                    </select>
                                    <script type="text/javascript">
                                        $.register.multiselect($('#searchForm select[name=identities]'), ${cm:toJSONArray(selectIdentities)});
                                    </script>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label>培训主办方</label>
                                <select data-rel="select2" name="organizer"
                                        data-width="200"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_cet_upper_train_organizer"/>
                                    <option value="0">其他</option>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=organizer]").val(${param.organizer});
                                </script>
                            </div>
                            <c:if test="${param.type==CET_UPPER_TRAIN_TYPE_SCHOOL}">
                                <div class="form-group">
                                    <label>培训类别</label>
                                    <select data-rel="select2" name="specialType"
                                            data-width="200"
                                            data-placeholder="请选择">
                                        <option></option>
                                        <c:forEach items="${CET_PROJECT_TYPE_MAP}" var="typeMap">
                                            <option value="${typeMap.key}">${typeMap.value}</option>
                                        </c:forEach>
                                    </select>
                                    <script type="text/javascript">
                                        $("#searchForm select[name=organizer]").val(${param.organizer});
                                    </script>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label>培训班类型</label>
                                <select data-rel="select2" name="trainType"
                                        data-width="120"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_cet_upper_train_type"/>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=trainType]").val(${param.trainType});
                                </script>
                            </div>
                            <div class="form-group">
                                <label>${param.type==CET_UPPER_TRAIN_TYPE_ABROAD ? '研修方向' : '培训班名称'}</label>
                                <input class="form-control search-query" name="trainName" type="text" value="${param.trainName}"
                                       placeholder="请输入">
                            </div>
                            <div class="form-group">
                                <label>培训开始时间</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="开始时间范围">
                                                <span class="input-group-addon">
                                                    <i class="fa fa-calendar bigger-110"></i>
                                                </span>
                                    <input placeholder="请选择开始时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="startDate" value="${param.startDate}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>培训结束时间</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="结束时间范围">
                                                <span class="input-group-addon">
                                                    <i class="fa fa-calendar bigger-110"></i>
                                                </span>
                                    <input placeholder="请选择结束时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="endDate" value="${param.endDate}"/>
                                </div>
                            </div>
                            <div class="form-group column">
                                <label>培训学时</label>
                                <div class="input-group input">
                                    <input style="width: 50px" class="form-control search-query float" type="text" name="prePeriod"
                                           value="${param.prePeriod}">
                                </div>
                                <label>至</label>
                                <div class="input-group input">
                                    <input style="width: 50px" class="form-control search-query float"
                                                            type="text"
                                                            name="subPeriod"
                                                            value="${param.subPeriod}">
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cet/cetUpperTrain?cls=${cls}&type=${param.type}&addType=${param.addType}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetUpperTrain?cls=${cls}&type=${param.type}&addType=${param.addType}"
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
<jsp:include page="/WEB-INF/jsp/cet/cetUpperTrain/cetUpperTrain_colModel.jsp?addType=${param.addType}"/>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/cet/cetUpperTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel,
        beforeProcessing:function(data, status, xhr){
            $("${param.addType==CET_UPPER_TRAIN_ADD_TYPE_OW?'#checkCount':'#unitCheckCount'}").html(data.checkCount>0?'('+data.checkCount+')':'')
        },
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('#searchForm select[name=userId]'));
    $.register.del_select($('#searchForm select[name=unitId]'));
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>