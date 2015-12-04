<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_INOUT_TYPE_MAP" value="<%=SystemConstants.MEMBER_INOUT_TYPE_MAP%>"/>
<c:set var="MEMBER_OUT_STATUS_MAP" value="<%=SystemConstants.MEMBER_OUT_STATUS_MAP%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/memberOut_au"
             data-url-page="${ctx}/memberOut_page"
             data-url-del="${ctx}/memberOut_del"
             data-url-bd="${ctx}/memberOut_batchDel"
             data-url-co="${ctx}/memberOut_changeOrder"
             data-querystr="${pageContext.request.queryString}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select  data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <select data-rel="select2" name="type" data-placeholder="请选择类别">
                    <option></option>
                    <c:forEach items="${MEMBER_INOUT_TYPE_MAP}" var="_type">
                        <option value="${_type.key}">${_type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=type]").val(${param.type});
                </script>
                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId ||not empty param.type || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="memberOut:edit">
                        <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/memberOut_au">
                    <%--<a class="editBtn btn btn-info btn-sm" data-width="1000">--%><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="memberOut:del">
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
							<th>类别</th>
							<th>转入单位抬头</th>
							<th>转入单位</th>
							<th>转出单位</th>
							<th>介绍信有效期天数</th>
							<th>办理时间</th>
							<th>状态</th>
                        <shiro:hasPermission name="memberOut:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${memberOuts}" var="memberOut" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${memberOut.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>${cm:getUserById(memberOut.userId).realname}</td>
								<td>${MEMBER_INOUT_TYPE_MAP.get(memberOut.type)}</td>
								<td>${memberOut.toTitle}</td>
								<td>${memberOut.toUnit}</td>
								<td>${memberOut.fromUnit}</td>
								<td>${memberOut.validDays}</td>
								<td>${cm:formatDate(memberOut.handleTime,'yyyy-MM-dd')}</td>
								<td>${MEMBER_OUT_STATUS_MAP.get(memberOut.status)}</td>
                            <shiro:hasPermission name="memberOut:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td class="hidden-480">
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${memberOut.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${memberOut.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="memberOut:edit">
                                    <button data-url="${ctx}/memberOut_au?id=${memberOut.id}" class="openView btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="memberOut:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${memberOut.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
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
                                    <wo:page commonList="${commonList}" uri="${ctx}/memberOut_page" target="#page-content" pageNum="5"
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
    register_user_select($('#searchForm select[name=userId]'));
</script>