<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_RETURN_STATUS_MAP" value="<%=SystemConstants.MEMBER_RETURN_STATUS_MAP%>"/>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/memberReturn_au"
             data-url-page="${ctx}/memberReturn_page"
             data-url-del="${ctx}/memberReturn_del"
             data-url-bd="${ctx}/memberReturn_batchDel"
             data-url-co="${ctx}/memberReturn_changeOrder"
             data-querystr="${pageContext.request.queryString}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                        name="partyId" data-placeholder="请选择分党委">
                    <option value="${party.id}">${party.name}</option>
                </select>
                <span style="${(empty branch)?'display: none':''}" id="branchDiv">
                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                        name="branchId" data-placeholder="请选择党支部">
                    <option value="${branch.id}">${branch.name}</option>
                </select>
                </span>
                <script>
                    register_party_branch_select($("#searchForm"), "branchDiv",
                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
                </script>
                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="memberReturn:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="memberReturn:del">
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
							<th>所属党组织</th>
							<th>确定为入党积极分子时间</th>
							<th>确定为发展对象时间</th>
							<th>入党时间</th>
							<th>转正时间</th>
							<th>状态</th>
							<th>备注</th>
							<th>创建时间</th>
                        <shiro:hasPermission name="memberReturn:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${memberReturns}" var="memberReturn" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${memberReturn.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>${cm:getUserById(memberReturn.userId).realname}</td>
								<td>${partyMap.get(memberReturn.partyId).name}
                                    <c:if test="${not empty memberReturn.branchId}">
                                        -${branchMap.get(memberReturn.branchId).name}
                                    </c:if></td>
								<td>${cm:formatDate(memberReturn.activeTime,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(memberReturn.candidateTime,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(memberReturn.growTime,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(memberReturn.positiveTime,'yyyy-MM-dd')}</td>
								<td>${MEMBER_RETURN_STATUS_MAP.get(memberReturn.status)}</td>
								<td>${memberReturn.remark}</td>
								<td>${cm:formatDate(memberReturn.createTime,'yyyy-MM-dd HH:mm:ss')}</td>
                            <shiro:hasPermission name="memberReturn:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td class="hidden-480">
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${memberReturn.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${memberReturn.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="memberReturn:edit">
                                    <button data-id="${memberReturn.id}" class="editBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="memberReturn:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${memberReturn.id}">
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
                                            <shiro:hasPermission name="memberReturn:edit">
                                            <li>
                                                <a href="#" data-id="${memberReturn.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="memberReturn:del">
                                            <li>
                                                <a href="#" data-id="${memberReturn.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                                    <wo:page commonList="${commonList}" uri="${ctx}/memberReturn_page" target="#page-content" pageNum="5"
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
    register_user_select($('#searchForm select[name=userId]'));
</script>