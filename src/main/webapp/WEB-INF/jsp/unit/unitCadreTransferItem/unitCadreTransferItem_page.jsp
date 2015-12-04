<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/unitCadreTransferItem_au"
             data-url-page="${ctx}/unitCadreTransferItem_page"
             data-url-del="${ctx}/unitCadreTransferItem_del"
             data-url-bd="${ctx}/unitCadreTransferItem_batchDel"
             data-url-co="${ctx}/unitCadreTransferItem_changeOrder"
             data-querystr="${pageContext.request.queryString}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <input class="form-control search-query" name="transferId" type="text" value="${param.transferId}"
                       placeholder="请输入所属任免">
                <input class="form-control search-query" name="dispatchCadreId" type="text" value="${param.dispatchCadreId}"
                       placeholder="请输入干部发文">
                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.transferId ||not empty param.dispatchCadreId || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="unitCadreTransferItem:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="unitCadreTransferItem:del">
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
							<th>所属任免</th>
							<th>干部发文</th>
                        <shiro:hasPermission name="unitCadreTransferItem:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${unitCadreTransferItems}" var="unitCadreTransferItem" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${unitCadreTransferItem.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td nowrap>${unitCadreTransferItem.transferId}</td>
								<td nowrap>${unitCadreTransferItem.dispatchCadreId}</td>
                            <shiro:hasPermission name="unitCadreTransferItem:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${unitCadreTransferItem.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${unitCadreTransferItem.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="unitCadreTransferItem:edit">
                                    <button data-id="${unitCadreTransferItem.id}" class="editBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="unitCadreTransferItem:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${unitCadreTransferItem.id}">
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
                                            <shiro:hasPermission name="unitCadreTransferItem:edit">
                                            <li>
                                                <a href="#" data-id="${unitCadreTransferItem.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="unitCadreTransferItem:del">
                                            <li>
                                                <a href="#" data-id="${unitCadreTransferItem.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                                    <wo:page commonList="${commonList}" uri="${ctx}/unitCadreTransferItem_page" target="#page-content" pageNum="5"
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
    $('[data-ref="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>
