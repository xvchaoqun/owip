<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="APPLY_STATUS_INIT" value="<%=SystemConstants.APPLY_STATUS_INIT%>"/>
<c:set var="APPLY_STATUS_ACTIVE" value="<%=SystemConstants.APPLY_STATUS_ACTIVE%>"/>
<c:set var="APPLY_STATUS_PLAN" value="<%=SystemConstants.APPLY_STATUS_CANDIDATE%>"/>
<c:set var="APPLY_STATUS_DRAW" value="<%=SystemConstants.APPLY_STATUS_DRAW%>"/>
<c:set var="APPLY_STATUS_GROW" value="<%=SystemConstants.APPLY_STATUS_GROW%>"/>
<c:set var="APPLY_STATUS_POSITIVE" value="<%=SystemConstants.APPLY_STATUS_POSITIVE%>"/>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="tabbable tabs-left">
                        <ul class="nav nav-tabs" id="myTab3">
                            <c:forEach items="#{applyStageTypeMap}" var="applyStageType">
                                <li class="<c:if test="${param.stage==applyStageType.key}">active</c:if>">
                                    <a href="javascript:;" onclick="_go(${empty param.type?1:param.type}, ${applyStageType.key})">
                                        <i class="<c:if test="${param.stage==applyStageType.key}">pink</c:if> ace-icon fa fa-rocket bigger-110"></i>
                                        ${applyStageType.value}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>

                        <div class="tab-content">
                            <div id="home3" class="tab-pane in active">

                                <div class="tabbable">
                                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                                        <li class="<c:if test="${param.type==1}">active</c:if>">
                                            <a href="javascript:;" onclick="_go(1, ${empty param.stage?1:param.stage})"><i class="fa fa-flag"></i> 学生</a>
                                        </li>

                                        <li class="<c:if test="${param.type==2}">active</c:if>">
                                            <a href="javascript:;" onclick="_go(2, ${empty param.stage?1:param.stage})"><i class="fa fa-history"></i> 教职工</a>
                                        </li>
                                    </ul>

                                    <div class="tab-content">
                                        <div id="home4" class="tab-pane in active">
                                <div class="myTableDiv"
                                     data-url-au="${ctx}/memberApply_au"
                                     data-url-page="${ctx}/memberApply_page"
                                     data-url-del="${ctx}/memberApply_del"
                                     data-url-bd="${ctx}/memberApply_batchDel"
                                     data-url-co="${ctx}/memberApply_changeOrder"
                                     data-querystr="${pageContext.request.queryString}">
                                    <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                                        <input class="form-control search-query" name="userId" type="text" value="${param.userId}"
                                               placeholder="请输入用户">
                                        <input class="form-control search-query" name="partyId" type="text" value="${param.partyId}"
                                               placeholder="请输入所属分党委">
                                        <input class="form-control search-query" name="branchId" type="text" value="${param.branchId}"
                                               placeholder="请输入所属党支部">
                                        <input class="form-control search-query" name="type" type="text" value="${param.type}"
                                               placeholder="请输入类型">
                                        <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                                        <c:set var="_query" value="${not empty param.userId ||not empty param.partyId ||not empty param.branchId ||not empty param.type || not empty param.code || not empty param.sort}"/>
                                        <c:if test="${_query}">
                                            <button type="button" class="resetBtn btn btn-warning btn-sm">
                                                <i class="fa fa-reply"></i> 重置
                                            </button>
                                        </c:if>
                                        <div class="vspace-12"></div>
                                        <div class="buttons pull-right">

                                            <c:if test="${commonList.recNum>0}">
                                                <a class="exportBtn btn btn-success btn-sm tooltip-success"
                                                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                                                <shiro:hasPermission name="memberApply:del">
                                                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                                                </shiro:hasPermission>
                                            </c:if>
                                        </div>
                                    </mytag:sort-form>
                                    <div class="space-4"></div>
                                    <c:if test="${commonList.recNum>0}">
                                        <table class="table table-striped table-bordered table-hover table-condensed">
                                            <thead>
                                            <tr>
                                                <th class="center">
                                                    <label class="pos-rel">
                                                        <input type="checkbox" class="ace checkAll">
                                                        <span class="lbl"></span>
                                                    </label>
                                                </th>
                                                <th>用户</th>
                                                <th>所属分党委</th>
                                                <th>所属党支部</th>
                                                <th>类型</th>
                                                <th>当前阶段</th>
                                                <th>状态</th>
                                                <th nowrap></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${memberApplys}" var="memberApply" varStatus="st">
                                                <tr>
                                                    <td class="center">
                                                        <label class="pos-rel">
                                                            <input type="checkbox" value="${memberApply.userId}" class="ace">
                                                            <span class="lbl"></span>
                                                        </label>
                                                    </td>
                                                    <td>${cm:getUserById(memberApply.userId).username}</td>
                                                    <td>${partyMap.get(memberApply.partyId).name}</td>
                                                    <td>${branchMap.get(memberApply.branchId).name}</td>
                                                    <td>${applyTypeMap.get(memberApply.type)}</td>
                                                    <td>${applyStageTypeMap.get(memberApply.status)}</td>
                                                    <td>${cm:getApplyStatus(memberApply)}</td>
                                                    <td>
                                                        <div class="hidden-sm hidden-xs action-buttons">
                                                            <c:choose>
                                                                <c:when test="${memberApply.status==APPLY_STATUS_INIT}">
                                                                    <button data-id="${memberApply.userId}" class="btn btn-success btn-mini">
                                                                        <i class="fa fa-check"></i> 通过
                                                                    </button>
                                                                    <button data-id="${memberApply.userId}" class="btn btn-danger btn-mini">
                                                                        <i class="fa fa-times"></i> 不通过
                                                                    </button>
                                                                </c:when>
                                                            </c:choose>
                                                        </div>
                                                        <div class="hidden-md hidden-lg">
                                                            <div class="inline pos-rel">
                                                                <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                                                    <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                                                </button>

                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                        <c:if test="${!empty commonList && commonList.pageNum>1 }">
                                            <div class="row my_paginate_row">
                                                <div class="col-xs-6">第${commonList.startPos}-${commonList.endPos}条&nbsp;&nbsp;共${commonList.recNum}条记录</div>
                                                <div class="col-xs-6">
                                                    <div class="my_paginate">
                                                        <ul class="pagination">
                                                            <wo:page commonList="${commonList}" uri="${ctx}/memberApply_page" target="#page-content" pageNum="5"
                                                                     model="3"/>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${commonList.recNum==0}">
                                        <div class="well well-lg center">
                                            <h4 class="green lighter">暂无记录</h4>
                                        </div>
                                    </c:if>
                                </div>
                                        </div></div></div>
                            </div>

                            <div id="profile3" class="tab-pane">
                                <p>Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid.</p>
                                <p>Raw denim you probably haven't heard of them jean shorts Austin.</p>
                            </div>

                            <div id="dropdown13" class="tab-pane">
                                <p>Etsy mixtape wayfarers, ethical wes anderson tofu before they sold out mcsweeney's organic lomo retro fanny pack lo-fi farm-to-table readymade.</p>
                                <p>Raw denim you probably haven't heard of them jean shorts Austin.</p>
                            </div>
                        </div>
                    </div>

    </div>

</div>
<script>
    function _go(type, stage){

        location.href="${ctx}/memberApply?type="+type + "&stage="+ stage;
    }
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 200,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>
</div>