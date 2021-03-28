<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="/WEB-INF/jsp/member/memberCertify/colModels.jsp"/>
<c:set var="JASPER_PRINT_TYPE_MEMBER_CERTIFY" value="<%=SystemConstants.JASPER_PRINT_TYPE_MEMBER_CERTIFY%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="myTableDiv" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.fromUnit ||not empty param.toTitle||not empty param.toUnit
            ||not empty param.userId ||not empty param.sn ||not empty param.politicalStatus || not empty param.code
            || not empty param.year || not empty param.partyId || not empty param.branchId}"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="dropdown <c:if test="${cls==1||cls==2||cls==3}">active</c:if>" >
                            <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
                                <i class="fa fa-circle-o"></i> ${_p_partyName}审核${cls==1?"(新申请)":(cls==2)?"(返回修改)":(cls==3)?"(已审核)":""}
                                <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/member/memberCertify?cls=1"><i class="fa fa-hand-o-right"></i> 新申请</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/member/memberCertify?cls=2"><i class="fa fa-hand-o-right"></i> 返回修改</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/member/memberCertify?cls=3"><i class="fa fa-hand-o-right"></i> 已审核</a>
                                </li>

                            </ul>
                        </li>
                        <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                            <li class="<c:if test="${cls==4||cls==5}">active</c:if>" >
                                <a href="javascript:;" class="loadPage" data-url="${ctx}/member/memberCertify?cls=4"><i class="fa fa-circle-o"></i> 组织部审核</a>
                            </li>
                        </shiro:hasAnyRoles>
                        <li class="${cls==6?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/member/memberCertify?cls=6"}><i class="fa fa-times"></i> 未通过/已撤销</a>
                        </li>
                        <li class="${cls==7?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/member/memberCertify?cls=7"}><i class="fa fa-check"></i> 已完成审批</a>
                        </li>

                        <div class="buttons pull-left" style="margin-left: 25px">
                            <shiro:hasPermission name="memberCertify:edit">
                                <button class="popupBtn btn btn-info btn-sm"
                                        data-url="${ctx}/member/memberCertify_au">
                                    <i class="fa fa-plus"></i> 添加</button>
                            </shiro:hasPermission>
                        </div>
                        <c:if test="${(cls==1 || cls==2 || cls==4 || cls==5) && (approvalCountNew+approvalCountBack)>0}">
                            <div class="pull-right"  style="top: 3px; right:10px; position: relative; color: red;  font-weight: bolder">
                                有${approvalCountNew+approvalCountBack}条待审核记录（其中新申请：共${approvalCountNew}条，返回修改：共${approvalCountBack}条）
                            </div>
                        </c:if>
                        <%--<div class="buttons pull-left" style="margin-left: 25px">
                            <shiro:hasPermission name="memberOutImport:*">
                                <button class="popupBtn btn btn-success btn-sm tooltip-primary"
                                        data-url="${ctx}/member/memberCertify_import"
                                        data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                    批量导入</button>
                            </shiro:hasPermission>
                        </div>--%>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="memberCertify:edit">
                                    <c:if test="${cls!=3&&cls!=6}">
                                        <button id="modifyBtn" class="jqOpenViewBtn btn btn-primary btn-sm"
                                           data-url="${ctx}/member/memberCertify_au"
                                           data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                            修改</button>
                                    </c:if>
                                    <c:if test="${cls==6}">
                                        <button id="reapplyBtn" class="jqOpenViewBtn btn btn-primary btn-sm"
                                                data-url="${ctx}/member/memberCertify_au?reapply=1"
                                                data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                            重新申请</button>
                                    </c:if>
                                </shiro:hasPermission>
                                <c:if test="${cls==1||cls==2}">
                                    <c:if test="${cm:isPermitted(PERMISSION_PARTYVIEWALL) || cm:hasRole(ROLE_PARTYADMIN)}">
                                        <button data-url="${ctx}/member/memberCertify_check"
                                                data-grid-id="#jqGrid"
                                                data-querystr="&type=1&cls=${cls}"
                                                class="jqOpenViewBatchBtn btn btn-warning btn-sm">
                                            <i class="fa fa-check-circle-o"></i> ${_p_partyName}审核
                                        </button>
                                    </c:if>
                                </c:if>
                                <c:if test="${cls==4||cls==5}">
                                    <button data-url="${ctx}/member/memberCertify_check"
                                            data-grid-id="#jqGrid"
                                            data-querystr="&type=2&cls=${cls}"
                                            class="jqOpenViewBatchBtn btn btn-warning btn-sm">
                                        <i class="fa fa-check-circle-o"></i> 组织部审核
                                    </button>
                                </c:if>
                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/applyApprovalLog"
                                        data-querystr="&type=<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_CERTIFY%>"
                                        data-open-by="page">
                                    <i class="fa fa-search"></i> 操作记录
                                </button>
                                <c:if test="${cls == 1 || cls == 2 || cls == 4 || cls == 5}">
                                    <button data-url="${ctx}/member/memberCertify_back"
                                            data-grid-id="#jqGrid"
                                            data-querystr="&cls=${cls}"
                                            class="jqOpenViewBatchBtn btn btn-danger btn-sm">
                                        <i class="fa fa-reply-all"></i> 批量退回
                                    </button>
                                </c:if>
                                <c:if test="${cls!=3&&cls!=7||(cls==7&&cm:hasRole(ROLE_ODADMIN))}">
                                    <shiro:hasPermission name="memberCertify:del">
                                        <button data-url="${ctx}/member/memberCertify_batchDel"
                                                data-title="删除"
                                                data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                                                data-grid-id="#jqGrid"
                                                class="jqBatchBtn btn btn-danger btn-sm">
                                            <i class="fa fa-trash"></i> 删除
                                        </button>
                                    </shiro:hasPermission>
                                </c:if>
                                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-url="${ctx}/member/memberCertify_data?cls=${cls}"
                                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                    <i class="fa fa-download"></i> 导出</button>
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
                                                <label>姓名</label>
                                                <select data-ajax-url="${ctx}/sysUser_selects" data-rel="select2-ajax"
                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label>年度</label>
                                                <input class="form-control date-picker" placeholder="请选择年份"
                                                       name="year" type="text" style="width: 100px;"
                                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                                       value="${param.year}"/>
                                            </div>
                                            <div class="form-group">
                                                <label>介绍信编号</label>
                                                <input class="form-control search-query" name="sn" type="text" value="${param.sn}"
                                                       placeholder="请输入">
                                            </div>
                                            <div class="form-group">
                                                <label>政治面貌</label>
                                                <select required data-rel="select2" name="politicalStatus"
                                                        data-placeholder="请选择" data-width="120">
                                                    <option></option>
                                                    <c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="entity">
                                                        <option value="${entity.key}">${entity.value}</option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#searchForm select[name=politicalStatus]").val(${param.politicalStatus});
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>原单位</label>
                                                <input class="form-control search-query" name="fromUnit" type="text" value="${param.fromUnit}"
                                                       placeholder="请输入">
                                            </div>
                                            <div class="form-group">
                                                <label>介绍信抬头</label>
                                                <input class="form-control search-query" name="toTitle" type="text" value="${param.toTitle}"
                                                       placeholder="请输入">
                                            </div>
                                            <div class="form-group">
                                                <label>拟去往的工作学习单位</label>
                                                <input class="form-control search-query" name="toUnit" type="text" value="${param.toUnit}"
                                                       placeholder="请输入">
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/member/memberCertify"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/member/memberCertify"
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
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/member/memberCertify_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>