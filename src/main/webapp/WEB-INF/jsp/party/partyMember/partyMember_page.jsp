<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/partyMember_au"
             data-url-page="${ctx}/partyMember_page"
             data-url-del="${ctx}/partyMember_del"
             data-url-bd="${ctx}/partyMember_batchDel"
             data-url-co="${ctx}/partyMember_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select name="groupId" data-rel="select2" data-placeholder="请选择所属班子">
                    <option></option>
                    <c:forEach items="${groupMap}" var="group">
                        <option value="${group.key}">${group.value.name}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=groupId]").val('${param.groupId}');
                </script>
                <input class="form-control search-query" name="userId" type="text" value="${param.userId}"
                       placeholder="请输入账号">
                <select name="typeId" data-rel="select2" data-placeholder="请选择类别">
                    <option></option>
                    <c:forEach items="${typeMap}" var="type">
                        <option value="${type.key}">${type.value.name}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=typeId]").val('${param.typeId}');
                </script>
                <input class="form-control search-query" name="isAdmin" type="text" value="${param.isAdmin}"
                       placeholder="请输入是否管理员">
                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.groupId ||not empty param.userId ||not empty param.typeId ||not empty param.isAdmin || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="partyMember:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="partyMember:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>
            </mytag:sort-form>
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
							<th>所属班子</th>
							<th>账号</th>
							<th>类别</th>
							<th>是否管理员</th>
                        <shiro:hasPermission name="partyMember:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${partyMembers}" var="partyMember" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${partyMember.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td nowrap>${partyMember.groupId}</td>
								<td nowrap>${partyMember.userId}</td>
								<td nowrap>${partyMember.typeId}</td>
								<td nowrap>${partyMember.isAdmin}</td>
                            <shiro:hasPermission name="partyMember:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${partyMember.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${partyMember.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="partyMember:edit">
                                    <button data-id="${partyMember.id}" class="editBtn btn btn-default btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="partyMember:del">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${partyMember.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
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
                                            <shiro:hasPermission name="partyMember:edit">
                                            <li>
                                                <a href="#" data-id="${partyMember.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="partyMember:del">
                                            <li>
                                                <a href="#" data-id="${partyMember.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <wo:page commonList="${commonList}" uri="${ctx}/partyMember_page" target="#page-content" pageNum="5"
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