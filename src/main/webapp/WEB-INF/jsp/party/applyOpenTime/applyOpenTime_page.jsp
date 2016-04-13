<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <!-- PAGE CONTENT BEGINS -->
            <div class="myTableDiv"
                 data-url-au="${ctx}/applyOpenTime_au"
                 data-url-page="${ctx}/applyOpenTime_page"
                 data-url-del="${ctx}/applyOpenTime_del"
                 data-url-bd="${ctx}/applyOpenTime_batchDel"
                 data-url-co="${ctx}/applyOpenTime_changeOrder"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="widget-box transparent">
                <div class="widget-header">
                    <div class="widget-toolbar no-border">
                        <jsp:include page="/WEB-INF/jsp/party/memberApply/menu.jsp"/>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-12 no-padding-left no-padding-right">
                        <div class="tab-content padding-4">
                            <div class="tab-pane in active">
                                    <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                                        <select name="type" data-rel="select2" data-placeholder="请选择阶段">
                                            <option></option>
                                            <c:forEach items="${APPLY_STAGE_MAP}" var="type">
                                                <c:if test="${type.key>0}">
                                                    <option value="${type.key}">${type.value}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                        <script type="text/javascript">
                                            $("#searchForm select[name=type]").val(${param.type});
                                        </script>
                                        <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                        <c:set var="_query" value="${not empty param.type ||not empty param.branchId ||not empty param.startTime ||not empty param.endTime || not empty param.code || not empty param.sort}"/>
                                        <c:if test="${_query}">
                                            <button type="button" class="resetBtn btn btn-warning btn-sm">
                                                <i class="fa fa-reply"></i> 重置
                                            </button>
                                        </c:if>
                                        <div class="vspace-12"></div>
                                        <div class="buttons pull-right">
                                            <shiro:hasPermission name="applyOpenTime:edit">
                                                <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                                            </shiro:hasPermission>
                                            <c:if test="${commonList.recNum>0}">
                                                <a class="exportBtn btn btn-success btn-sm tooltip-success"
                                                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                                                <shiro:hasPermission name="applyOpenTime:del">
                                                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 批量删除</a>
                                                </shiro:hasPermission>
                                            </c:if>
                                        </div>
                                    </mytag:sort-form>
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
                                                <shiro:hasPermission name="applyOpenTime:changeOrder">
                                                    <c:if test="${!_query && commonList.recNum>1}">
                                                        <th nowrap class="hidden-480">排序</th>
                                                    </c:if>
                                                </shiro:hasPermission>
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
                                                    <td>${applyOpenTime.isGlobal?"--":applyOpenTime.partyId}</td>
                                                    <td>${cm:formatDate(applyOpenTime.startTime,'yyyy-MM-dd')}</td>
                                                    <td>${cm:formatDate(applyOpenTime.endTime,'yyyy-MM-dd')}</td>
                                                    <td>${APPLY_STAGE_MAP.get(applyOpenTime.type)}</td>
                                                    <td>${applyOpenTime.isGlobal?"是":"否"}</td>
                                                    <shiro:hasPermission name="applyOpenTime:changeOrder">
                                                        <c:if test="${!_query && commonList.recNum>1}">
                                                            <td class="hidden-480">
                                                                <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${applyOpenTime.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                                                <input type="text" value="1"
                                                                       class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                                                <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${applyOpenTime.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                                            </td>
                                                        </c:if>
                                                    </shiro:hasPermission>
                                                    <td>
                                                        <div class="hidden-sm hidden-xs action-buttons">
                                                            <shiro:hasPermission name="applyOpenTime:edit">
                                                                <button data-id="${applyOpenTime.id}" class="editBtn btn btn-default btn-mini btn-xs">
                                                                    <i class="fa fa-edit"></i> 编辑
                                                                </button>
                                                            </shiro:hasPermission>
                                                            <shiro:hasPermission name="applyOpenTime:del">
                                                                <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${applyOpenTime.id}">
                                                                    <i class="fa fa-trash"></i> 删除
                                                                </button>
                                                            </shiro:hasPermission>
                                                        </div>
                                                        <div class="hidden-md hidden-lg">
                                                            <div class="inline pos-rel">
                                                                <button class="btn btn-mini btn-xser btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                                                    <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                                                </button>

                                                                <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                                                        <%--<li>
                                                                        <a href="#" class="tooltip-info" data-rel="tooltip" title="查看">
                                                                                    <span class="blue">
                                                                                        <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                                                    </span>
                                                                        </a>
                                                                    </li>--%>
                                                                    <shiro:hasPermission name="applyOpenTime:edit">
                                                                        <li>
                                                                            <a href="#" data-id="${applyOpenTime.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                                            </a>
                                                                        </li>
                                                                    </shiro:hasPermission>
                                                                    <shiro:hasPermission name="applyOpenTime:del">
                                                                        <li>
                                                                            <a href="#" data-id="${applyOpenTime.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                                        <wo:page commonList="${commonList}" uri="${ctx}/applyOpenTime_page" target="#page-content" pageNum="5"
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
        <div id="item-content">

        </div>
    </div>
</div>
<script>
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>