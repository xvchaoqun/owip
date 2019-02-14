<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_APPLY_STAGE_MAP" value="<%=OwConstants.OW_APPLY_STAGE_MAP%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">

            <div class="myTableDiv"
                 data-url-au="${ctx}/applyOpenTime_au"
                 data-url-page="${ctx}/applyOpenTime"
                 data-url-del="${ctx}/applyOpenTime_del"
                 data-url-bd="${ctx}/applyOpenTime_batchDel"
                 data-url-co="${ctx}/applyOpenTime_changeOrder"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="widget-box transparent">
                <div class="widget-header">
                    <div class="widget-toolbar no-border">
                        <jsp:include page="/WEB-INF/jsp/member/memberApply/menu.jsp"/>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-12 no-padding-left no-padding-right">
                        <div class="tab-content padding-4">
                            <div class="tab-pane in active">
                                    <t:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                                        <input type="hidden" name="cls" value="${param.cls}">
                                        <select name="type" data-rel="select2" data-placeholder="请选择阶段">
                                            <option></option>
                                            <c:forEach items="${OW_APPLY_STAGE_MAP}" var="type">
                                                <c:if test="${type.key>0}">
                                                    <option value="${type.key}">${type.value}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                        <script type="text/javascript">
                                            $("#searchForm select[name=type]").val(${param.type});
                                        </script>
                                        <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                        <c:set var="_query" value="${not empty param.type ||not empty param.branchId ||not empty param.startTime ||not empty param.endTime || not empty param.code || not empty param.sort}"/>
                                        <c:if test="${_query}">
                                            <button type="button"
                                                    class="reloadBtn btn btn-warning btn-sm"
                                                    data-querystr="cls=${param.cls}">
                                                <i class="fa fa-reply"></i> 重置
                                            </button>
                                        </c:if>
                                        <div class="vspace-12"></div>
                                        <div class="buttons pull-right">
                                            <shiro:hasPermission name="applyOpenTime:edit">
                                                <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                                            </shiro:hasPermission>

                                        </div>
                                    </t:sort-form>
                                    <div class="space-4"></div>
                                    <c:if test="${commonList.recNum>0}">
                                        <table class="table table-actived table-striped table-bordered table-hover">
                                            <thead>
                                            <tr>
                                                <th class="center">
                                                    <label class="pos-rel">
                                                        <input type="checkbox" class="ace checkAll">
                                                        <span class="lbl"></span>
                                                    </label>
                                                </th>
                                                <th>所属分党委</th>
                                                <th>开始时间</th>
                                                <th>结束时间</th>
                                                <th>生效阶段</th>
                                                <th>是否全局</th>
                                                <th nowrap></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${applyOpenTimes}" var="applyOpenTime" varStatus="st">
                                                <tr>
                                                    <td class="center">
                                                        <label class="pos-rel">
                                                            <input type="checkbox" value="${applyOpenTime.id}" class="ace">
                                                            <span class="lbl"></span>
                                                        </label>
                                                    </td>
                                                    <td>
                                                    <c:if test="${applyOpenTime.isGlobal}">
                                                        --
                                                    </c:if>
                                                        <c:if test="${!applyOpenTime.isGlobal}">
                                                            ${cm:displayParty(applyOpenTime.partyId, applyOpenTime.branchId)}
                                                                </c:if>
                                                    </td>
                                                    <td>${cm:formatDate(applyOpenTime.startTime,'yyyy-MM-dd')}</td>
                                                    <td>${cm:formatDate(applyOpenTime.endTime,'yyyy-MM-dd')}</td>
                                                    <td>${OW_APPLY_STAGE_MAP.get(applyOpenTime.type)}</td>
                                                    <td>${applyOpenTime.isGlobal?"是":"否"}</td>
                                                    <td>
                                                        <div class="hidden-sm hidden-xs action-buttons">
                                                            <shiro:hasPermission name="applyOpenTime:edit">
                                                                <button data-id="${applyOpenTime.id}" class="editBtn btn btn-default btn-xs">
                                                                    <i class="fa fa-edit"></i> 编辑
                                                                </button>
                                                            </shiro:hasPermission>
                                                            <shiro:hasPermission name="applyOpenTime:del">
                                                                <button class="delBtn btn btn-danger btn-xs" data-id="${applyOpenTime.id}">
                                                                    <i class="fa fa-trash"></i> 删除
                                                                </button>
                                                            </shiro:hasPermission>
                                                        </div>
                                                        <div class="hidden-md hidden-lg">
                                                            <div class="inline pos-rel">
                                                                <button class="btn btn-xser btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                                                    <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                                                </button>

                                                                <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                                                        <%--<li>
                                                                        <a href="javascript:;" class="tooltip-info" data-rel="tooltip" title="查看">
                                                                                    <span class="blue">
                                                                                        <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                                                    </span>
                                                                        </a>
                                                                    </li>--%>
                                                                    <shiro:hasPermission name="applyOpenTime:edit">
                                                                        <li>
                                                                            <a href="javascript:;" data-id="${applyOpenTime.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                                            </a>
                                                                        </li>
                                                                    </shiro:hasPermission>
                                                                    <shiro:hasPermission name="applyOpenTime:del">
                                                                        <li>
                                                                            <a href="javascript:;" data-id="${applyOpenTime.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
                                                    <span class="red">
                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                    </span>
                                                                            </a>
                                                                        </li>
                                                                    </shiro:hasPermission>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                        <wo:page commonList="${commonList}" uri="${ctx}/applyOpenTime" target="#page-content" pageNum="5"
                                                 model="3"/>
                                    </c:if>
                                    <c:if test="${commonList.recNum==0}">
                                        <div class="well well-lg center">
                                            <h4 class="green lighter">暂无记录</h4>
                                        </div>
                                    </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <div id="body-content-view">

        </div>
    </div>
</div>
<script>
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>