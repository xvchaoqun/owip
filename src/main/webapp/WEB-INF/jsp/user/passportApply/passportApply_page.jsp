<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="myTableDiv"
             data-url-page="${ctx}/user/passportApply_page">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">

                <div class="buttons">
                        <a class="openView btn btn-info btn-sm" data-url="${ctx}/user/passportApply_begin"><i class="fa fa-plus"></i> 申请办理因私出国（境）证件</a>
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
							<th>申办证件名称</th>
							<th>申办日期</th>
							<th>审批状态</th>
							<th>审批人</th>
							<th>审批时间</th>
							<th>应交组织部日期</th>
							<th>实交组织部日期</th>
							<th>申请时间</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${passportApplys}" var="passportApply" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(passportApply.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${passportApply.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>${passportTypeMap.get(passportApply.classId).name}</td>
								<td>${cm:formatDate(passportApply.applyDate,'yyyy-MM-dd')}</td>
								<td>${passportApply.status}</td>
								<td>${passportApply.userId}</td>
								<td>${cm:formatDate(passportApply.approveTime,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(passportApply.expectDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(passportApply.handleDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(passportApply.createTime,'yyyy-MM-dd HH:mm')}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">

                                    <button data-id="${passportApply.id}" class="editBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>

                                     <shiro:hasPermission name="passportApply:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${passportApply.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
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
                                                <a href="#" data-id="${passportApply.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
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
                                    <wo:page commonList="${commonList}" uri="${ctx}/passportApply_page" target="#page-content" pageNum="5"
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
        <div id="item-content">

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