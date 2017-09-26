<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/memberApply_au"
                 data-url-page="${ctx}/memberApply"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId
            ||not empty param.partyId ||not empty param.branchId ||not empty param.growStatus ||not empty param.positiveStatus || not empty param.code || not empty param.sort}"/>
            <div class="widget-box transparent">
                <div class="widget-header">
                    <div class="widget-toolbar no-border">
                        <jsp:include page="menu.jsp"/>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
                        <div class="tab-content padding-4">
                            <div class="tab-pane in active">
                                <!-- PAGE CONTENT BEGINS -->
                                <div class="tabbable tabs-left">
                                    <%
                                        String[] colors= new String[8];
                                        colors[0]= "badge-grey";
                                        colors[1]= "badge-inverse";
                                        colors[2]= "badge-danger";
                                        colors[3]= "badge-info";
                                        colors[4]= "badge-purple";
                                        colors[5]= "badge-warning";
                                        colors[6]= "badge-pink";
                                        colors[7]= "badge-pink";
                                        //colors[0]= "badge-yellow";
                                    %>
                                    <c:set value="<%=colors%>" var="colors"/>
                                    <ul class="nav nav-tabs" id="myTab3">
                                        <li class="<c:if test="${stage==APPLY_STAGE_OUT}">active</c:if>">
                                            <a href="javascript:;" class="hashchange" data-url='${ctx}/memberApply_layout?cls=${cls}&type=${type}&stage=${APPLY_STAGE_OUT}'>
                                                <span class="badge">*</span> 已转出的申请
                                            </a>
                                        </li>
                                        <c:forEach items="#{APPLY_STAGE_MAP}" var="applyStage">
                                            <li class="<c:if test="${stage==applyStage.key}">active</c:if>">
                                                <a href="javascript:;" class="hashchange" data-url='${ctx}/memberApply_layout?cls=${cls}&type=${type}&stage=${applyStage.key}'>
                                                        <%--<i class='${(stage==applyStageType.key)?"pink":"blue"} ace-icon fa fa-rocket bigger-110'></i>--%>
                                                    <c:set value="${applyStage.key==-1?0:(applyStage.key==0?1:applyStage.key)}" var="colorKey"/>
                                                    <span class="badge ${colors[colorKey]}">${colorKey}</span>
                                                        ${applyStage.value}
                                                            <c:if test="${applyStage.key==APPLY_STAGE_INIT}">
                                                                <c:set var="stageCount" value="${stageCountMap[APPLY_STAGE_INIT]+stageCountMap[APPLY_STAGE_PASS]}"/>
                                                            </c:if>
                                                            <c:if test="${applyStage.key!=APPLY_STAGE_INIT}">
                                                                <c:set var="stageCount" value="${stageCountMap[applyStage.key]}"/>
                                                             </c:if>

                                                    <c:if test="${stageCount>0}">
                                                    <span class="badge badge-success pull-right"
                                                        <c:if test="${applyStage.key!=APPLY_STAGE_POSITIVE}"> data-rel="tooltip" title="${stageCount}条待处理审批" </c:if> >${stageCount}</span>
                                                    </c:if>
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <div class="tab-content no-padding-top no-padding-bottom">
                                        <div id="home3" class="tab-pane in active">
                                            <div class="tabbable" >
                                                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                                                    <li class="<c:if test="${type==APPLY_TYPE_STU}">active</c:if>">
                                                        <a href="javascript:;" class="hashchange" data-url='${ctx}/memberApply_layout?cls=${cls}&type=${APPLY_TYPE_STU}&stage=${stage}'><i class="fa fa-graduation-cap"></i> 学生

                                                            <c:if test="${stage==APPLY_STAGE_INIT}">
                                                                <c:set value="${APPLY_STAGE_INIT}_${APPLY_TYPE_STU}" var="_key1"/>
                                                                <c:set value="${APPLY_STAGE_PASS}_${APPLY_TYPE_STU}" var="_key2"/>
                                                                <c:set var="stageTypeCount" value="${stageTypeCountMap.get(_key1)+stageTypeCountMap.get(_key2)}"/>
                                                            </c:if>
                                                            <c:if test="${stage!=APPLY_STAGE_INIT}">
                                                                <c:set value="${stage}_${APPLY_TYPE_STU}" var="_key"/>
                                                                <c:set var="stageTypeCount" value="${stageTypeCountMap.get(_key)}"/>
                                                            </c:if>

                                                            <c:if test="${stageTypeCount>0}">
                                                                <span class="badge badge-success"
                                                                      data-placement="right"
                                                                <c:if test="${stage!=APPLY_STAGE_POSITIVE}">data-rel="tooltip" title="${stageTypeCount}条待处理审批"</c:if>>${stageTypeCount}</span>
                                                            </c:if>
                                                        </a>
                                                    </li>

                                                    <li class="<c:if test="${type==APPLY_TYPE_TEACHER}">active</c:if>">
                                                        <a href="javascript:;" class="hashchange" data-url='${ctx}/memberApply_layout?cls=${cls}&type=${APPLY_TYPE_TEACHER}&stage=${stage}'><i class="fa fa-user-secret"></i> 教职工
                                                            <c:set value="${stage}_${APPLY_TYPE_TEACHER}" var="_key"/>
                                                            <c:if test="${stage==APPLY_STAGE_INIT}">
                                                                <c:set value="${APPLY_STAGE_INIT}_${APPLY_TYPE_TEACHER}" var="_key1"/>
                                                                <c:set value="${APPLY_STAGE_PASS}_${APPLY_TYPE_TEACHER}" var="_key2"/>
                                                                <c:set var="stageTypeCount" value="${stageTypeCountMap.get(_key1)+stageTypeCountMap.get(_key2)}"/>
                                                            </c:if>
                                                            <c:if test="${stage!=APPLY_STAGE_INIT}">
                                                                <c:set value="${stage}_${APPLY_TYPE_TEACHER}" var="_key"/>
                                                                <c:set var="stageTypeCount" value="${stageTypeCountMap.get(_key)}"/>
                                                            </c:if>

                                                            <c:if test="${stageTypeCount>0}">
                                                                <span class="badge badge-success"
                                                                      data-placement="right"
                                                                <c:if test="${stage!=APPLY_STAGE_POSITIVE}">  data-rel="tooltip" title="${stageTypeCount}条待处理审批" </c:if>>${stageTypeCount}</span>
                                                            </c:if>
                                                        </a>
                                                    </li>
                                                    <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                                                        <div class="buttons pull-right">
                                                            <a href="javascript:" class="addBtn btn btn-info btn-sm">
                                                                <i class="fa fa-plus"></i> 添加入党申请</a>
                                                        </div>
                                                    </shiro:hasAnyRoles>
                                                </ul>

                                                <div class="tab-content no-padding-bottom" >
                                                    <div id="home4" class="tab-pane in active">
                                                        <div class="jqgrid-vertical-offset buttons">
                                                            <c:if test="${stage!=APPLY_STAGE_OUT}">
                                                            <button id="editBtn" class="jqEditBtn btn btn-primary btn-sm"
                                                                    data-id-name="userId"
                                                                    data-querystr="&stage=${param.stage}">
                                                                <i class="fa fa-edit"></i> 修改信息
                                                            </button>
                                                            </c:if>
                                                            <c:choose>
                                                                <c:when test="${stage==APPLY_STAGE_INIT || stage==APPLY_STAGE_PASS}">
                                                                    <button id="applyBtn" ${applyCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-success btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_INIT}"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${applyCount}">
                                                                        <i class="fa fa-sign-in"></i> 支部审核（${applyCount}）
                                                                    </button>
                                                                    <button id="activeBtn" ${activeCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_PASS}"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${activeCount}">
                                                                        <i class="fa fa-sign-in"></i> 支部确定为入党积极分子（${activeCount}）
                                                                    </button>
                                                                </c:when>
                                                                <c:when test="${stage==APPLY_STAGE_ACTIVE}">
                                                                    <button id="candidateBtn" ${candidateCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-success btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_ACTIVE}&status=-1"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${candidateCount}">
                                                                        <i class="fa fa-sign-in"></i> 支部确定为发展对象（${candidateCount}）
                                                                    </button>
                                                                    <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_PARTYADMIN}">
                                                                    <button id="candidateCheckBtn" ${candidateCheckCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_ACTIVE}&status=0"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${candidateCheckCount}">
                                                                        <i class="fa fa-sign-in"></i> 分党委审核（${candidateCheckCount}）
                                                                    </button>
                                                                    </shiro:hasAnyRoles>
                                                                </c:when>
                                                                <c:when test="${stage==APPLY_STAGE_CANDIDATE}">
                                                                    <button id="planBtn" ${planCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-success btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_CANDIDATE}&status=-1"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${planCount}">
                                                                        <i class="fa fa-sign-in"></i> 支部列入发展计划（${planCount}）
                                                                    </button>
                                                                    <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_PARTYADMIN}">
                                                                    <button id="planCheckBtn" ${planCheckCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_CANDIDATE}&status=0"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${planCheckCount}">
                                                                        <i class="fa fa-sign-in"></i> 分党委审核（${planCheckCount}）
                                                                    </button>
                                                                    </shiro:hasAnyRoles>
                                                                </c:when>
                                                                <c:when test="${stage==APPLY_STAGE_PLAN}">
                                                                    <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_PARTYADMIN}">
                                                                    <button id="drawBtn" ${drawCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_PLAN}&status=-1"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${drawCount}">
                                                                        <i class="fa fa-sign-in"></i> 领取志愿书（${drawCount}）
                                                                    </button>
                                                                    </shiro:hasAnyRoles>
                                                                   <%-- <button id="drawCheckCount" ${drawCheckCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_PLAN}&status=0"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${drawCheckCount}">
                                                                        <i class="fa fa-sign-in"></i> 分党委审核（${drawCheckCount}）
                                                                    </button>--%>
                                                                </c:when>
                                                                <c:when test="${stage==APPLY_STAGE_DRAW}">
                                                                    <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                                                                        <button id="growOdCheckCount" ${growOdCheckCount>0?'':'disabled'}
                                                                                class="jqOpenViewBtn btn btn-danger btn-sm"
                                                                                data-url="${ctx}/memberApply_approval"
                                                                                data-open-by="page"
                                                                                data-querystr="&type=${type}&stage=${APPLY_STAGE_DRAW}&status=-1"
                                                                                data-need-id="false"
                                                                                data-id-name="userId"
                                                                                data-count="${growOdCheckCount}">
                                                                            <i class="fa fa-sign-in"></i> 组织部审核（${growOdCheckCount}）
                                                                        </button>
                                                                    </shiro:hasAnyRoles>
                                                                    <button id="growBtn" ${growCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-success btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_DRAW}&status=2"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${growCount}">
                                                                        <i class="fa fa-sign-in"></i> 支部发展为预备党员（${growCount}）
                                                                    </button>
                                                                    <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_PARTYADMIN}">
                                                                    <button id="growCheckBtn" ${growCheckCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_DRAW}&status=0"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${growCheckCount}">
                                                                        <i class="fa fa-sign-in"></i> 分党委审核（${growCheckCount}）
                                                                    </button>
                                                                    </shiro:hasAnyRoles>
                                                                   <%-- <button id="growCheckCount" ${growCheckCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_DRAW}&status=0"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${growCheckCount}">
                                                                        <i class="fa fa-sign-in"></i> 分党委审核（${growCheckCount}）
                                                                    </button>--%>

                                                                </c:when>
                                                                <c:when test="${stage==APPLY_STAGE_GROW}">
                                                                    <button id="positiveBtn" ${positiveCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-success btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_GROW}&status=-1"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${positiveCount}">
                                                                        <i class="fa fa-sign-in"></i> 支部预备党员转正（${positiveCount}）
                                                                    </button>
                                                                    <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_PARTYADMIN}">
                                                                    <button id="positiveCheckBtn" ${positiveCheckCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_GROW}&status=0"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${positiveCheckCount}">
                                                                        <i class="fa fa-sign-in"></i> 分党委审核（${positiveCheckCount}）
                                                                    </button>
                                                                    </shiro:hasAnyRoles>
                                                                    <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                                                                    <button id="positiveOdCheckBtn" ${positiveOdCheckCount>0?'':'disabled'}
                                                                            class="jqOpenViewBtn btn btn-danger btn-sm"
                                                                            data-url="${ctx}/memberApply_approval"
                                                                            data-open-by="page"
                                                                            data-querystr="&type=${type}&stage=${APPLY_STAGE_GROW}&status=1"
                                                                            data-need-id="false"
                                                                            data-id-name="userId"
                                                                            data-count="${positiveOdCheckCount}">
                                                                        <i class="fa fa-sign-in"></i> 组织部审核（${positiveOdCheckCount}）
                                                                    </button>
                                                                    </shiro:hasAnyRoles>
                                                                </c:when>
                                                            </c:choose>

                                                            <button class="jqOpenViewBtn btn btn-info btn-sm"
                                                                    data-url="${ctx}/applyApprovalLog"
                                                                    data-querystr="&type=${APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY}"
                                                                    data-open-by="page">
                                                                <i class="fa fa-sign-in"></i> 查看审批记录
                                                            </button>
                                                            <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN},${ROLE_PARTYADMIN}">
                                                                <c:if test="${stage<APPLY_STAGE_GROW && stage>=APPLY_STAGE_INIT}">
                                                                    <button class="jqOpenViewBatchBtn btn btn-danger btn-sm"
                                                                            data-url="${ctx}/memberApply_back"
                                                                            data-querystr="&stage=${param.stage}">
                                                                        <i class="fa fa-reply-all"></i> 打回申请（批量）
                                                                    </button>
                                                                </c:if>
                                                                <c:if test="${stage==APPLY_STAGE_POSITIVE || stage==APPLY_STAGE_GROW}">
                                                                    <button class="jqOpenViewBatchBtn btn btn-danger btn-sm"
                                                                            data-url="${ctx}/memberApply_back"
                                                                            data-querystr="&stage=${stage}">
                                                                        <i class="fa fa-reply-all"></i> 打回至预备党员初始状态（批量）
                                                                    </button>
                                                                </c:if>
                                                            </shiro:hasAnyRoles>
                                                        </div>
                                                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                                                            <div class="widget-header">
                                                                <h4 class="widget-title">搜索</h4>
                                                                <div class="widget-toolbar">
                                                                    <a href="javascript:;" data-action="collapse">
                                                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <form class="form-inline search-form" id="searchForm">
                                                                                <div class="form-group">
                                                                                    <label>用户</label>
                                                                                        <div class="input-group">
                                                                                            <input type="hidden" name="cls" value="${cls}">
                                                                                            <input type="hidden" name="type" value="${type}">
                                                                                            <input type="hidden" name="stage" value="${stage}">
                                                                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                                                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                                                            </select>
                                                                                        </div>
                                                                                </div>
                                                                                <div class="form-group">
                                                                                    <label>分党委</label>

                                                                                        <select class="form-control" data-width="350" data-rel="select2-ajax"
                                                                                                data-ajax-url="${ctx}/party_selects?auth=1"
                                                                                                name="partyId" data-placeholder="请选择分党委">
                                                                                            <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                                                                        </select>
                                                                                </div>

                                                                                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                                                                    <label>党支部</label>
                                                                                        <select class="form-control" data-rel="select2-ajax"
                                                                                                data-ajax-url="${ctx}/branch_selects?auth=1"
                                                                                                name="branchId" data-placeholder="请选择党支部">
                                                                                            <option value="${branch.id}" title="${branch.isDeleted}">${branch.name}</option>
                                                                                        </select>
                                                                                </div>

                                                                                <c:if test="${stage==APPLY_STAGE_DRAW}">
                                                                                    <div class="form-group">
                                                                                        <label>状态</label>
                                                                                        <div class="input-group">
                                                                                            <select name="growStatus" data-rel="select2" data-placeholder="请选择">
                                                                                                <option></option>
                                                                                                <option value="-1">待组织部审核</option>
                                                                                                <option value="2">组织部已审核，待分党委发展为预备党员</option>
                                                                                            </select>
                                                                                            <script>
                                                                                                $("#searchForm select[name=growStatus]").val("${param.growStatus}");
                                                                                            </script>
                                                                                        </div>
                                                                                    </div>
                                                                                </c:if>
                                                                        <c:if test="${stage==APPLY_STAGE_GROW}">
                                                                            <div class="form-group">
                                                                                <label>状态</label>
                                                                                <div class="input-group">
                                                                                    <select name="positiveStatus" data-rel="select2" data-placeholder="请选择">
                                                                                        <option></option>
                                                                                        <option value="-1">待支部提交预备党员转正</option>
                                                                                        <option value="0">支部已提交，待分党委审核</option>
                                                                                        <option value="1">分党委已审核，待组织部审核</option>
                                                                                    </select>
                                                                                    <script>
                                                                                        $("#searchForm select[name=positiveStatus]").val("${param.positiveStatus}");
                                                                                    </script>
                                                                                </div>
                                                                            </div>
                                                                        </c:if>
                                                                            <script>
                                                                                register_party_branch_select($("#searchForm"), "branchDiv",
                                                                                        '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                                            </script>
                                                                        <div class="clearfix form-actions center">
                                                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                                                        data-querystr="type=${type}&stage=${stage}">
                                                                                    <i class="fa fa-reply"></i> 重置
                                                                                </button>
                                                                            </c:if>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="space-4"></div>
                                                        <table id="jqGrid" class="jqGrid0 table-striped"></table>
                                                        <div id="jqGridPager"></div>
                                                    </div></div></div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        multiboxonly:false,
        ondblClickRow:function(){},
        url: '${ctx}/memberApply_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '${type==APPLY_TYPE_STU?"学生证号":"工作证号"}', name: 'user.code', width: 120, frozen:true},
            {label: '姓名', name: 'user.realname', width: 100, frozen:true},
            {
                label: '所属组织机构', name: 'party', align:'left',  width: 550, formatter:function(cellvalue, options, rowObject){
                return $.party(rowObject.partyId, rowObject.branchId);
            }
            },
            <c:if test="${stage<APPLY_STAGE_INIT}">
            {label: '提交书面申请书时间', name: 'applyTime', width: 180,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            </c:if>
            <c:if test="${stage==APPLY_STAGE_INIT || stage==APPLY_STAGE_OUT}">
            {label: '提交书面申请书时间', name: 'applyTime', width: 180,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '确定为入党积极分子时间', name: 'activeTime', width: 200,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            </c:if>
            <c:if test="${stage==APPLY_STAGE_ACTIVE || stage==APPLY_STAGE_OUT}">
            {label: '确定为入党积极分子时间', name: 'activeTime', width: 200,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '确定为发展对象时间', name: 'candidateTime', width: 180,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            </c:if>
            <c:if test="${stage==APPLY_STAGE_CANDIDATE || stage==APPLY_STAGE_OUT}">
            {label: '确定为发展对象时间', name: 'candidateTime', width: 180,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '列入发展计划时间', name: 'planTime', width: 180,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            </c:if>
            <c:if test="${stage==APPLY_STAGE_PLAN || stage==APPLY_STAGE_OUT}">
            {label: '列入发展计划时间', name: 'planTime', width: 180,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '领取志愿书时间', name: 'drawTime', width: 160,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            </c:if>
            <c:if test="${stage==APPLY_STAGE_DRAW || stage==APPLY_STAGE_OUT}">
            {label: '领取志愿书时间', name: 'drawTime', width: 160,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '发展时间', name: 'growTime', width: 100,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            </c:if>
            <c:if test="${stage==APPLY_STAGE_GROW||stage==APPLY_STAGE_POSITIVE || stage==APPLY_STAGE_OUT}">
            {label: '入党时间', name: 'growTime', width: 100,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '转正时间', name: 'positiveTime', width: 100,formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            </c:if>
            {label: '状态', name: 'applyStatus', width: 300},
            {hidden: true, name: 'stage'},
            {hidden: true, name: 'candidateStatus'},
            {hidden: true, name: 'planStatus'},
            {hidden: true, name: 'drawStatus'},
            {hidden: true, name: 'growStatus'},
            {hidden: true, name: 'positiveStatus'},
            {hidden: true,key:true, name: 'userId'}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#"+this.id);
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("*[data-count]").each(function(){
                    $(this).prop("disabled", true);
                })
            } else if (ids.length==1) {
              
                var rowData = $(this).getRowData(ids[0]);
                $("#applyBtn").prop("disabled", rowData.stage != "${APPLY_STAGE_INIT}");
                $("#activeBtn").prop("disabled", rowData.stage != "${APPLY_STAGE_PASS}");
                $("#candidateBtn").prop("disabled", rowData.candidateStatus != '');
                $("#candidateCheckBtn").prop("disabled", rowData.candidateStatus=='');
                $("#planBtn").prop("disabled", rowData.planStatus != '');
                $("#planCheckBtn").prop("disabled", rowData.planStatus == '');
                $("#drawBtn").prop("disabled", rowData.drawStatus != 0);
                $("#drawCheckBtn").prop("disabled", rowData.drawStatus != 1);
                $("#growBtn").prop("disabled", rowData.growStatus != 2);
                $("#growCheckBtn").prop("disabled", rowData.growStatus != 0);
                $("#growOdCheckBtn").prop("disabled", rowData.growStatus != '');
                $("#positiveBtn").prop("disabled", $.trim(rowData.positiveStatus) != '');
                $("#positiveCheckBtn").prop("disabled", parseInt(rowData.positiveStatus) != 0);
                $("#positiveOdCheckBtn").prop("disabled", parseInt(rowData.positiveStatus) != 1);
            } else {
                $("*[data-count]").each(function(){
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            $("*[data-count]").each(function(){
                $(this).prop("disabled", $(this).data("count") == 0);
            })
        }
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid0')
    });

    $.initNavGrid("jqGrid", "jqGridPager");
    <c:if test="${stage==APPLY_STAGE_INIT}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"支部批量通过",
        btnbase:"jqBatchBtn btn btn-success btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_pass" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });

    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"支部批量不通过",
        btnbase:"jqBatchBtn btn btn-danger btn-xs",
        buttonicon:"fa fa-times-circle-o",
        props:'data-url="${ctx}/apply_deny" data-title="不通过" data-msg="确定拒绝这{0}个申请吗？" data-callback="page_reload"'
    });

    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"支部确定为入党积极分子（批量）",
        btnbase:"jqOpenViewBatchBtn btn btn-warning btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_active"'
    });
    </c:if>
    <c:if test="${stage==APPLY_STAGE_ACTIVE}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"支部确定为发展对象（批量）",
        btnbase:"jqOpenViewBatchBtn btn btn-success btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_candidate"'
    });
    <shiro:hasRole name="${ROLE_PARTYADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"分党委批量审核",
        btnbase:"jqBatchBtn btn btn-warning btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_candidate_check" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </shiro:hasRole>
    </c:if>
    <c:if test="${stage==APPLY_STAGE_CANDIDATE}">

    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"支部列入发展计划（批量）",
        btnbase:"jqOpenViewBatchBtn btn btn-success btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_plan"'
    });

    <shiro:hasRole name="${ROLE_PARTYADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"分党委批量审核",
        btnbase:"jqBatchBtn btn btn-warning btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_plan_check" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </shiro:hasRole>
    </c:if>
    <c:if test="${stage==APPLY_STAGE_PLAN}">
    <shiro:hasRole name="${ROLE_PARTYADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"领取志愿书（批量）",
        btnbase:"jqOpenViewBatchBtn btn btn-warning btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_draw"'
    });
    </shiro:hasRole>
    /*$("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"分党委审核",
        btnbase:"jqBatchBtn btn btn-warning btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_draw_check" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });*/
    </c:if>

    <c:if test="${stage==APPLY_STAGE_DRAW}">
    <shiro:hasRole name="${ROLE_ODADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"组织部批量审核",
        btnbase:"jqBatchBtn btn btn-primary btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_grow_od_check" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </shiro:hasRole>
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"支部发展为预备党员（批量）",
        btnbase:"jqOpenViewBatchBtn btn btn-success btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_grow"'
    });
    <shiro:hasRole name="${ROLE_PARTYADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"分党委批量审核",
        btnbase:"jqBatchBtn btn btn-warning btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_grow_check" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </shiro:hasRole>
    </c:if>

    <c:if test="${stage==APPLY_STAGE_GROW}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"支部预备党员转正（批量）",
        btnbase:"jqOpenViewBatchBtn btn btn-success btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_positive"'
    });
    <shiro:hasRole name="${ROLE_PARTYADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"分党委批量审核",
        btnbase:"jqBatchBtn btn btn-warning btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_positive_check" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </shiro:hasRole>
    <shiro:hasRole name="${ROLE_ODADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"组织部批量审核",
        btnbase:"jqBatchBtn btn btn-primary btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/apply_positive_check2" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </shiro:hasRole>
    </c:if>

    <%--<shiro:hasRole name="${ROLE_PARTYADMIN}">
    <c:if test="${stage<APPLY_STAGE_GROW && stage>=APPLY_STAGE_INIT}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"打回申请",
        btnbase:"jqOpenViewBatchBtn btn btn-danger btn-xs",
        buttonicon:"fa fa-reply-all",
        props:'data-url="${ctx}/memberApply_back" data-querystr="&stage=${param.stage}"'
    });
    </c:if>
    </shiro:hasRole>--%>

    $(".addBtn").click(function(){
        $.loadModal("${ctx}/memberApply_au");
    });

    function goto_next(gotoNext){
        if(gotoNext==1){
            if($("#next").hasClass("disabled") && $("#last").hasClass("disabled") )
                $.hashchange();
            else if(!$("#next").hasClass("disabled"))
                $("#next").click();
            else
                $("#last").click();
        }else{
            page_reload();
        }
    }
    function apply_deny(userId, gotoNext){
        bootbox.confirm("确定拒绝该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_deny",{ids:[userId]},function(ret){
                    if(ret.success){
                        //page_reload();
                        //SysMsg.success('操作成功。', '成功');
                        goto_next(gotoNext);
                    }
                });
            }
        });
    }
    function apply_pass(userId, gotoNext){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_pass",{ids:[userId]},function(ret){
                    if(ret.success){
                        //page_reload();
                        //SysMsg.success('操作成功。', '成功');
                        goto_next(gotoNext);
                    }
                });
            }
        });
    }
    function apply_active(userId, gotoNext){
        var url = "${ctx}/apply_active?ids[]="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url);
    }
    function apply_candidate(userId, gotoNext){
        var url = "${ctx}/apply_candidate?ids[]="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url);
    }
    function apply_candidate_check(userId, gotoNext){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_candidate_check",{ids:[userId]},function(ret){
                    if(ret.success){
                        //page_reload();
                        //SysMsg.success('操作成功。', '成功');
                        goto_next(gotoNext);
                    }
                });
            }
        });
    }

    function apply_plan(userId, gotoNext){

        var url = "${ctx}/apply_plan?ids[]="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url);
    }
    function apply_plan_check(userId, gotoNext){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_plan_check",{ids:[userId]},function(ret){
                    if(ret.success){
                        //page_reload();
                        //SysMsg.success('操作成功。', '成功');
                        goto_next(gotoNext);
                    }
                });
            }
        });
    }

    function apply_draw(userId, gotoNext){

        var url = "${ctx}/apply_draw?ids[]="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url);
    }
    /*function apply_draw_check(userId, gotoNext){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_draw_check",{ids:[userId]},function(ret){
                    if(ret.success){
                        //page_reload();
                        //SysMsg.success('操作成功。', '成功');
                        goto_next(gotoNext);
                    }
                });
            }
        });
    }*/

    function apply_grow(userId, gotoNext){
        var url = "${ctx}/apply_grow?ids[]="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url);
    }
    function apply_grow_check(userId, gotoNext){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_grow_check",{ids:[userId]},function(ret){
                    if(ret.success){
                        //page_reload();
                        //SysMsg.success('操作成功。', '成功');
                        goto_next(gotoNext);
                    }
                });
            }
        });
    }
    function apply_grow_od_check(userId, gotoNext){
        //bootbox.confirm("确定通过该申请？", function (result) {
            //if(result){
                $.post("${ctx}/apply_grow_od_check",{ids:[userId]},function(ret){
                    if(ret.success){
                        //page_reload();
                        //SysMsg.success('操作成功。', '成功');
                        goto_next(gotoNext);
                    }
                });
            //}
        //});
    }
    function apply_positive(userId, gotoNext){
        var url = "${ctx}/apply_positive?ids[]="+userId;
        if(gotoNext!=undefined)
            url += "&gotoNext="+ gotoNext;
        $.loadModal(url)
    }
    function apply_positive_check(userId, gotoNext){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_positive_check",{ids:[userId]},function(ret){
                    if(ret.success){
                        //page_reload();
                        //SysMsg.success('操作成功。', '成功');
                        goto_next(gotoNext);
                    }
                });
            }
        });
    }
    function apply_positive_check2(userId, gotoNext){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/apply_positive_check2",{ids:[userId]},function(ret){
                    if(ret.success){
                        //page_reload();
                        //SysMsg.success('操作成功。', '成功');
                        goto_next(gotoNext);
                    }
                });
            }
        });
    }


    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip({container:'#page-content'});
    register_user_select($('#searchForm select[name=userId]'));

</script>