<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CET_UPPER_TRAIN_UPPER" value="<%=CetConstants.CET_UPPER_TRAIN_UPPER%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_SELF" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_OW" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_UNIT" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.unitId ||not empty param.userId ||not empty param.postId
                   ||not empty param.organizer ||not empty param.trainType
                   || not empty param.code || not empty param.sort}"/>
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${cls==1}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUpperTrain?cls=1&type=${param.type}&addType=${addType}&upperType=${upperType}"><i
                            class="fa fa-list"></i> ${upperType==CET_UPPER_TRAIN_UPPER?'调训':'培训'}信息汇总
                    </a>
                </li>
                <li class="<c:if test="${cls==2}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUpperTrain?cls=2&type=${param.type}&addType=${addType}&upperType=${upperType}"><i
                            class="fa fa-circle-o"></i> 待审核
                    <span id="checkCount"></span>
                    </a>
                </li>
                <c:if test="${empty param.type || param.type==0}">
                <li class="<c:if test="${cls==3}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUpperTrain?cls=3&type=${param.type}&addType=${addType}&upperType=${upperType}"><i
                            class="fa fa-times"></i> 未通过审核</a>
                </li>
                </c:if>
                <c:if test="${addType==CET_UPPER_TRAIN_ADD_TYPE_OW}">
                <li class="<c:if test="${cls==4}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUpperTrain?cls=4&type=${param.type}&addType=${addType}&upperType=${upperType}"><i
                            class="fa fa-trash"></i> 已删除</a>
                </li>
                </c:if>
            </ul>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">

                    <c:if test="${cls==1}">
                    <button class="popupBtn btn btn-success btn-sm"
                            data-url="${ctx}/cet/cetUpperTrain_au?type=${param.type}&addType=${addType}&upperType=${upperType}"
                            data-width="900">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                    <button id="modifyBtn" class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cet/cetUpperTrain_au?addType=${addType}&upperType=${upperType}"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改
                    </button>
                    <shiro:hasPermission name="cetUpperTrain:import">
                    <button class="popupBtn btn btn-info btn-sm tooltip-success"
                        data-url="${ctx}/cet/cetUpperTrain_import?upperType=${upperType}"
                        data-rel="tooltip" data-placement="top" title="从Excel导入"><i class="fa fa-upload"></i> 批量导入</button>
                    </shiro:hasPermission>
                    </c:if>
                    <c:if test="${cls==2}">
                    <button class="jqOpenViewBtn btn btn-success btn-sm"
                            data-url="${ctx}/cet/cetUpperTrain_au?addType=${addType}&upperType=${upperType}&check=1"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        审批
                    </button>
                    </c:if>
                <c:if test="${cls==1||cls==2}">
                    <button id="delBtn" data-url="${ctx}/cet/cetUpperTrain_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                    <button id="uploadNoteBtn" class="jqOpenViewBtn btn btn-warning btn-sm tooltip-success"
                        data-url="${ctx}/cet/cetUpperTrain_uploadNote?addType=${addType}&upperType=${upperType}"
                        data-grid-id="#jqGrid"
                        data-rel="tooltip" data-placement="top"
                        title="上传培训总结"><i class="fa fa-upload"></i> 上传培训总结</button>
                    </c:if>
                <c:if test="${addType==CET_UPPER_TRAIN_ADD_TYPE_OW}">
                <button class="jqOpenViewBtn btn btn-info btn-sm"
                        data-url="${ctx}/sysApprovalLog"
                        data-querystr="&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN%>"
                        data-open-by="page">
                    <i class="fa fa-search"></i> 操作记录
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
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">

                            <%--<div class="form-group">
                                <label>派出单位</label>
                                <select required data-rel="select2"
                                        name="type" data-placeholder="请选择">
                                    <option value="0">党委组织部</option>
                                    <option value="1">其他部门派出</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=type]").val('${param.type}')
                                </script>
                            </div>--%>
                            <c:if test="${type}">
                            <div class="form-group">
                                <label>${upperType==CET_UPPER_TRAIN_UPPER?'派出':'组织'}单位</label>
                                <select data-ajax-url="${ctx}/unit_selects"
                                        name="unitId" data-placeholder="请选择">
                                    <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                                </select>
                            </div>
                            </c:if>
                            <div class="form-group">
                                <label>参训人姓名</label>
                                <select data-ajax-url="${ctx}/cadre_selects?key=1"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>职务属性</label>
                                <select data-rel="select2" name="postId"
                                        data-width="150"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_post"/>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=postId]").val(${param.postId});
                                </script>
                            </div>
                            <div class="form-group">
                                <label>培训主办方</label>
                                <select data-rel="select2" name="organizer"
                                        data-width="200"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_cet_upper_train_organizer${upperType==CET_UPPER_TRAIN_UPPER?'':'2'}"/>
                                    <option value="0">其他</option>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=organizer]").val(${param.organizer});
                                </script>
                            </div>
                            <div class="form-group">
                                <label>培训班类型</label>
                                <select data-rel="select2" name="trainType"
                                        data-width="120"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_cet_upper_train_type${upperType==CET_UPPER_TRAIN_UPPER?'':'2'}"/>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=trainType]").val(${param.trainType});
                                </script>
                            </div>

                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cet/cetUpperTrain?cls=${cls}&type=${param.type}&addType=${addType}&upperType=${upperType}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetUpperTrain?cls=${cls}&type=${param.type}&addType=${addType}&upperType=${upperType}"
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
<jsp:include page="/WEB-INF/jsp/cet/cetUpperTrain/cetUpperTrain_colModel.jsp?addType=${addType}"/>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/cet/cetUpperTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel,
        beforeProcessing:function(data, status, xhr){
            $("#checkCount").html(data.checkCount>0?'('+data.checkCount+')':'')
        },
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('#searchForm select[name=userId]'));
    $.register.del_select($('#searchForm select[name=unitId]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>