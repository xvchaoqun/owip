<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/branchMember_au"
             data-url-page="${ctx}/branchMember"
             data-url-del="${ctx}/branchMember_del"
             data-url-bd="${ctx}/branchMember_batchDel"
             data-url-co="${ctx}/branchMember_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <t:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <input class="form-control search-query" name="groupId" type="text" value="${param.groupId}"
                       placeholder="请输入所属支部委员会">
                <input class="form-control search-query" name="userId" type="text" value="${param.userId}"
                       placeholder="请输入账号">
                <input class="form-control search-query" name="typeId" type="text" value="${param.typeId}"
                       placeholder="请输入类别">
                <input class="form-control search-query" name="isAdmin" type="text" value="${param.isAdmin}"
                       placeholder="请输入是否管理员">
                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.groupId ||not empty param.userId ||not empty param.typeId ||not empty param.isAdmin || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="branchMember:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="branchMember:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>
            </t:sort-form>
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
							<th>所属支部委员会</th>
							<th>账号</th>
							<th>类别</th>
							<th>是否管理员</th>
                        <shiro:hasPermission name="branchMember:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${branchMembers}" var="branchMember" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${branchMember.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td nowrap>${branchMember.groupId}</td>
								<td nowrap>${branchMember.userId}</td>
								<td nowrap>${branchMember.typeId}</td>
								<td nowrap>${branchMember.isAdmin}</td>
                            <shiro:hasPermission name="branchMember:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="javascript:;" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${branchMember.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="javascript:;" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${branchMember.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="branchMember:edit">
                                    <button data-id="${branchMember.id}" class="editBtn btn btn-default btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="branchMember:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${branchMember.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
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
                                            <shiro:hasPermission name="branchMember:edit">
                                            <li>
                                                <a href="javascript:;" data-id="${branchMember.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="branchMember:del">
                                            <li>
                                                <a href="javascript:;" data-id="${branchMember.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <wo:page commonList="${commonList}" uri="${ctx}/branchMember" target="#page-content" pageNum="5"
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
<script>
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>