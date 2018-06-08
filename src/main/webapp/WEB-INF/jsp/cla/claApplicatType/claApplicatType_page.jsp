<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
        <div class="tabbable">
            <jsp:include page="/WEB-INF/jsp/cla/claApprovalAuth/claApprovalAuth_menu.jsp"/>

            <div class="tab-content">
                <div class="tab-pane in active">

        <div class="myTableDiv"
             data-url-au="${ctx}/cla/claApplicatType_au"
             data-url-page="${ctx}/cla/claApplicatType"
             data-url-del="${ctx}/cla/claApplicatType_del"
             data-url-bd="${ctx}/cla/claApplicatType_batchDel"
             data-url-co="${ctx}/cla/claApplicatType_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="buttons">
                <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                <a class="popupBtn btn btn-danger btn-sm"
                   data-url="${ctx}/cla/claApplicatType/selectCadresEscape"><i class="fa fa-search"></i> 未分配申请人身份的干部（${escapeCount}）</a>
            </div>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
							<th>申请人身份</th>
							<th>包含干部</th>
							<th>审批人身份</th>
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${applicatTypes}" var="applicatType" varStatus="st">
                        <tr>

								<td>${applicatType.name}</td>
                            <td>
                                <button data-url="${ctx}/cla/claApplicatType/selectCadres?id=${applicatType.id}" class="popupBtn btn btn-success btn-xs">
                                    <i class="fa fa-th-list"></i>  包含干部
                                </button>
                            </td>
                            <td>
                                <button data-url="${ctx}/cla/claApplicatType/claApprovalOrder?id=${applicatType.id}" class="popupBtn btn btn-warning btn-xs">
                                    <i class="fa fa-th-list"></i>  审批人身份
                                </button>
                            </td>
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td class="hidden-480">
                                    <a href="javascript:;" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn pageReload" data-id="${applicatType.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="javascript:;" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn pageReload" data-id="${applicatType.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <button data-id="${applicatType.id}" class="editBtn btn btn-primary btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${applicatType.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
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
                                            <shiro:hasPermission name="applicatType:edit">
                                            <li>
                                                <a href="javascript:;" data-id="${applicatType.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="applicatType:del">
                                            <li>
                                                <a href="javascript:;" data-id="${applicatType.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <wo:page commonList="${commonList}" uri="${ctx}/cla/claApplicatType" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
                </div></div></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>

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