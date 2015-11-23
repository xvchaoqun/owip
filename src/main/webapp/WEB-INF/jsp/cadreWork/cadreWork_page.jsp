<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/cadreWork_au"
             data-url-page="${ctx}/cadreWork_page"
             data-url-del="${ctx}/cadreWork_del"
             data-url-bd="${ctx}/cadreWork_batchDel"
             data-url-co="${ctx}/cadreWork_changeOrder"
             data-querystr="${pageContext.request.queryString}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select data-rel="select2-ajax" data-ajax--url="${ctx}/cadre_selects"
                        name="cadreId" data-placeholder="请选择干部">
                    <option value="${cadre.id}">${sysUser.username}</option>
                </select>
                <input  type="hidden" name="fid" value="${param.fid}">

                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.fid || not empty param.cadreId || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="cadreWork:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="cadreWork:del">
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
                            <c:if test="${empty param.fid}">
							<th>所属干部</th>
                            </c:if>
							<th>开始日期</th>
							<th>结束日期</th>
							<th>工作单位</th>
							<th>担任职务或者专技职务</th>
							<th>行政级别</th>
							<th>院系/机关工作经历</th>
                        <shiro:hasPermission name="cadreWork:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cadreWorks}" var="cadreWork" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${cadreWork.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <c:if test="${empty param.fid}">
								<td>${cm:getUserById(cadreMap.get(cadreWork.cadreId).userId).realname}</td>
                            </c:if>
								<td>${cm:formatDate(cadreWork.startTime,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(cadreWork.endTime,'yyyy-MM-dd')}</td>
								<td>${cadreWork.unit}</td>
								<td>${cadreWork.post}</td>
								<td>${typeMap.get(cadreWork.typeId).name}</td>
								<td>${cadreWork.workType==1?"院系工作经历":"机关工作经历"}</td>
                            <shiro:hasPermission name="cadreWork:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td class="hidden-480">
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreWork.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreWork.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="cadreWork:edit">
                                        <c:if test="${empty cadreWork.fid}">
                                        <button data-id="${cadreWork.id}" data-cadreId="${cadreWork.cadreId}" class="addChildBtn btn btn-mini">
                                            <i class="fa fa-edit"></i> 添加期间工作
                                        </button>
                                        <button data-id="${cadreWork.id}" class="showChildBtn btn btn-mini">
                                            <i class="fa fa-edit"></i> 查看期间工作
                                        </button>
                                        </c:if>
                                    <button data-id="${cadreWork.id}" class="editBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                        <button data-id="${cadreWork.id}" class="addDispatchBtn btn btn-mini">
                                            <i class="fa fa-edit"></i> 添加任免文件
                                        </button>

                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="cadreWork:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${cadreWork.id}">
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
                                            <shiro:hasPermission name="cadreWork:edit">
                                            <li>
                                                <a href="#" data-id="${cadreWork.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="cadreWork:del">
                                            <li>
                                                <a href="#" data-id="${cadreWork.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <c:if test="${!empty commonList && commonList.pageNum>1 }">
                    <div class="row my_paginate_row">
                        <div class="col-xs-6">第${commonList.startPos}-${commonList.endPos}条&nbsp;&nbsp;共${commonList.recNum}条记录</div>
                        <div class="col-xs-6">
                            <div class="my_paginate">
                                <ul class="pagination">
                                    <wo:page commonList="${commonList}" uri="${ctx}/cadreWork_page" target="#page-content" pageNum="5"
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

    $(".addChildBtn").click(function(){

        loadModal("${ctx}/cadreWork_au?fid="+$(this).data("id") + "&cadreId=" + $(this).data("cadreId"));
    });

    $(".showChildBtn").click(function(){

        $("#searchForm input[name=fid]").val($(this).data("id"));
        $(".searchBtn").click();
    });

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