<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<div id="body-content">
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/memberAbroad_au"
             data-url-page="${ctx}/memberAbroad_page"
             data-url-del="${ctx}/memberAbroad_del"
             data-url-bd="${ctx}/memberAbroad_batchDel"
             data-url-co="${ctx}/memberAbroad_changeOrder"
             data-querystr="${pageContext.request.queryString}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select  class="form-control" data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
                         name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <div class="input-group tooltip-success" data-rel="tooltip" title="出国时间范围">
                                                    <span class="input-group-addon">
                                                        <i class="fa fa-calendar bigger-110"></i>
                                                    </span>
                    <input placeholder="请选择出国时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_abroadTime" value="${param._abroadTime}"/>
                </div>
                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId ||not empty param._abroadTime || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <%--<div class="buttons pull-right">
                    <shiro:hasPermission name="memberAbroad:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="memberAbroad:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>--%>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-actived table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
                        <th>学工号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>年龄</th>
							<th>出国时间</th>
							<th>出国缘由</th>
							<th>预计归国时间</th>
							<th>实际归国时间</th>
                        <%--<th nowrap></th>--%>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${memberAbroads}" var="memberAbroad" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${memberAbroad.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <c:set value="${cm:getUserById(memberAbroad.userId)}" var="_sysUser"/>
                            <td>${_sysUser.code}</td>
                            <td>
                                <a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId=${memberAbroad.userId}">
                            ${_sysUser.realname}
                            </a>
                            </td>
                            <td>${GENDER_MAP.get(_sysUser.gender)}</td>
                            <td>${cm:formatDate(_sysUser.birth, "yyyy-MM-dd")}</td>

                                <td>${cm:formatDate(memberAbroad.abroadTime, "yyyy-MM-dd")}</td>
								<td>${memberAbroad.reason}</td>
								<td>${cm:formatDate(memberAbroad.expectReturnTime, "yyyy-MM-dd")}</td>
								<td>${cm:formatDate(memberAbroad.actualReturnTime, "yyyy-MM-dd")}</td>
                            <%--<td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="memberAbroad:edit">
                                    <button data-id="${memberAbroad.userId}" class="editBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="memberAbroad:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${memberAbroad.userId}">
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
                                            &lt;%&ndash;<li>
                                            <a href="#" class="tooltip-info" data-rel="tooltip" title="查看">
                                                        <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                            </a>
                                        </li>&ndash;%&gt;
                                            <shiro:hasPermission name="memberAbroad:edit">
                                            <li>
                                                <a href="#" data-id="${memberAbroad.userId}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="memberAbroad:del">
                                            <li>
                                                <a href="#" data-id="${memberAbroad.userId}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
                                                    <span class="red">
                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                        </ul>
                                    </div>
                                </div>
                            </td>--%>
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
                                    <wo:page commonList="${commonList}" uri="${ctx}/memberAbroad_page" target="#page-content" pageNum="5"
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
</div><div id="item-content"></div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    register_user_select($('#searchForm select[name=userId]'));
</script>