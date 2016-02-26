<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/user/applySelf_au"
             data-url-page="${ctx}/user/applySelf_page"
             data-querystr="${pageContext.request.queryString}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <div class="vspace-12"></div>
                <div class="buttons">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 新申请</a>
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

							<th>申请日期</th>
							<th>出行时间范围</th>
							<th>出发时间</th>
							<th>返回时间</th>
							<th>前往国家或地区</th>
							<th>出国事由</th>
							<th>同行人员</th>
							<th>费用来源</th>
							<th>所需证件</th>
							<th>其他说明材料</th>
							<th>创建时间</th>
                        <shiro:hasPermission name="applySelf:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${applySelfs}" var="applySelf" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(applySelf.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${applySelf.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</td>
								<td>${APPLY_SELF_DATE_TYPE_MAP.get(applySelf.type)}</td>
								<td>${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}</td>
								<td>${applySelf.toCountry}</td>
								<td>${applySelf.reason}</td>
								<td>${applySelf.peerStaff}</td>
								<td>${applySelf.costSource}</td>
								<td>${applySelf.needPassports}</td>
								<td>${applySelf.files}</td>
                            <td>${cm:formatDate(applySelf.createTime,'yyyy-MM-dd HH:mm')}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <button data-id="${applySelf.id}" class="editBtn btn btn-mini">
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
                                                <a href="#" data-id="${applySelf.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
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
                                    <wo:page commonList="${commonList}" uri="${ctx}/applySelf_page" target="#page-content" pageNum="5"
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