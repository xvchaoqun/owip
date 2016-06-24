<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/abroad/approvalAuth/approvalAuth_menu.jsp"/>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
        <div class="myTableDiv"
             data-url-au="${ctx}/approverType_au"
             data-url-page="${ctx}/approverType_page"
             data-url-del="${ctx}/approverType_del"
             data-url-bd="${ctx}/approverType_batchDel"
             data-url-co="${ctx}/approverType_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <%--<input class="form-control search-query" name="name" type="text" value="${param.name}"
                       placeholder="请输入名称">
                <input class="form-control search-query" name="type" type="text" value="${param.type}"
                       placeholder="请输入类型">
                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.name ||not empty param.type || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>--%>
                <div class="vspace-12"></div>
                <div class="buttons">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
							<th>名称</th>
							<th>类型</th>
                            <th>包含干部</th>
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">审批顺序</th>
                            </c:if>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${approverTypes}" var="approverType" varStatus="st">
                        <tr>
								<td>${approverType.name}</td>
								<td>${APPROVER_TYPE_MAP.get(approverType.type)}</td>
								<td>
                                    <c:if test="${approverType.type!=APPROVER_TYPE_OTHER}">
                                        <button data-url="${ctx}/approverType/selectCadres?type=${approverType.type}"
                                                class="popupBtn btn btn-primary btn-mini btn-xs">
                                            <i class="fa fa-th-list"></i>  查看干部
                                        </button>
                                     </c:if>
                                    <c:if test="${approverType.type==APPROVER_TYPE_OTHER}">
                                    <button data-url="${ctx}/approverType/selectCadres?type=${approverType.type}&id=${approverType.id}"
                                            class="popupBtn btn btn-success btn-mini btn-xs">
                                        <i class="fa fa-th-list"></i>  包含干部
                                    </button>
                                    </c:if>
								</td>
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td class="hidden-480">
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${approverType.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${approverType.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <button data-id="${approverType.id}" class="editBtn btn btn-primary btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${approverType.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
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
                                            <shiro:hasPermission name="approverType:edit">
                                            <li>
                                                <a href="#" data-id="${approverType.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="approverType:del">
                                            <li>
                                                <a href="#" data-id="${approverType.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <wo:page commonList="${commonList}" uri="${ctx}/approverType_page" target="#page-content" pageNum="5"
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
        <div id="item-content"></div>
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