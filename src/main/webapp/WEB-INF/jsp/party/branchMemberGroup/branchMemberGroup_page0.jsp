<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/branchMemberGroup_au"
             data-url-page="${ctx}/branchMemberGroup_page"
             data-url-del="${ctx}/branchMemberGroup_del"
             data-url-bd="${ctx}/branchMemberGroup_batchDel"
             data-url-co="${ctx}/branchMemberGroup_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">

                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                       placeholder="请输入名称">
                <select name="branchId" data-rel="select2" data-placeholder="请输入所属党支部">
                    <option></option>
                    <c:forEach items="${branchMap}" var="branch">
                        <option value="${branch.key}">${branch.value.name}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=branchId]").val('${param.branchId}');
                </script>

                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.branchId ||not empty param.name || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="branchMemberGroup:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a>
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
							<th>名称</th>
							<th>所属党支部</th>
                        <mytag:sort-th field="tran_time">应换届时间</mytag:sort-th>
                        <mytag:sort-th field="actual_tran_time">实际换届时间</mytag:sort-th>
                        <mytag:sort-th field="appoint_time">任命时间</mytag:sort-th>
							<th>发文</th>
                        <shiro:hasPermission name="branchMemberGroup:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${branchMemberGroups}" var="branchMemberGroup" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${branchMemberGroup.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <td><c:if test="${branchMemberGroup.isPresent}">
                                <span class="label label-sm label-primary arrowed-in arrowed-in-right">现任委员会</span>
                            </c:if>${branchMemberGroup.name}</td>
                            <td>${branchMap.get(branchMemberGroup.branchId).name}</td>
                            <td>${cm:formatDate(branchMemberGroup.tranTime,'yyyy-MM-dd')}</td>
                            <td>${cm:formatDate(branchMemberGroup.actualTranTime,'yyyy-MM-dd')}</td>
                            <td>${cm:formatDate(branchMemberGroup.appointTime,'yyyy-MM-dd')}</td>
							 <td>
                                 <c:set var="dispatchUnit" value="${dispatchUnitMap.get(branchMemberGroup.dispatchUnitId)}"/>
                                 <c:set var="dispatch" value="${dispatchMap.get(dispatchUnit.dispatchId)}"/>
                                ${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year )}
                             </td>
                            <shiro:hasPermission name="branchMemberGroup:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${branchMemberGroup.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${branchMemberGroup.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="branchMemberGroup:edit">
                                    <button data-id="${branchMemberGroup.id}" class="editBtn btn btn-default btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                    <shiro:hasPermission name="branchMember:list">
                                        <button data-id="${branchMemberGroup.id}" class="memberBtn btn btn-primary btn-mini btn-xs">
                                            <i class="fa fa-user"></i> 编辑委员
                                        </button>
                                    </shiro:hasPermission>
                                     <%--<shiro:hasPermission name="branchMemberGroup:del">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${branchMemberGroup.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                      </shiro:hasPermission>--%>
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
                                            <shiro:hasPermission name="branchMemberGroup:edit">
                                            <li>
                                                <a href="#" data-id="${branchMemberGroup.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="branchMemberGroup:del">
                                            <li>
                                                <a href="#" data-id="${branchMemberGroup.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                <wo:page commonList="${commonList}" uri="${ctx}/branchMemberGroup_page" target="#page-content" pageNum="5"
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

    // 编辑成员
    $(".myTableDiv .memberBtn").click(function(){
        loadModal("${ctx}/branch_member?id="+$(this).data("id"));
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>