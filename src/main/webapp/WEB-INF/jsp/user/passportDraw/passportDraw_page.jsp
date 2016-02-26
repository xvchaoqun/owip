<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/user/passportDraw_au"
             data-url-page="${ctx}/user/passportDraw_page"
             data-querystr="${pageContext.request.queryString}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <div class="vspace-12"></div>
                <div class="buttons">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
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
							<th>类型</th>
							<th>因私出国申请</th>
							<th>证件</th>
							<th>申请日期</th>
							<th>出发时间</th>
							<th>返回时间</th>
							<th>出国事由</th>
							<th>是否申请签注</th>
                        <shiro:hasPermission name="passportDraw:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${passportDraws}" var="passportDraw" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(passportDraw.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${passportDraw.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>${PASSPORT_DRAW_TYPE_MAP.get(passportDraw.type)}</td>
								<td>${passportDraw.applyId}</td>
								<td>${passportDraw.passportId}</td>
								<td>${cm:formatDate(passportDraw.applyDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(passportDraw.startDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(passportDraw.endDate,'yyyy-MM-dd')}</td>
								<td>${passportDraw.reason}</td>
								<td>${passportDraw.needSign}</td>
                            <shiro:hasPermission name="passportDraw:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td class="hidden-480">
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${passportDraw.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${passportDraw.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <button data-id="${passportDraw.id}" class="editBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>

                                </div>
                                <div class="hidden-md hidden-lg">
                                    <div class="inline pos-rel">
                                        <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
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
                                            <li>
                                                <a href="#" data-id="${passportDraw.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                        </ul>
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
                                    <wo:page commonList="${commonList}" uri="${ctx}/passportDraw_page" target="#page-content" pageNum="5"
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