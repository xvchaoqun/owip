<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/safeBox_page"
             data-url-del="${ctx}/safeBox_del"
             data-url-co="${ctx}/safeBox_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">

            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li  class="<c:if test="${status==1}">active</c:if>">
                        <a href="?status=1"><i class="fa fa-circle-o"></i> 集中管理证件</a>
                    </li>
                    <li  class="<c:if test="${status==2}">active</c:if>">
                        <a href="?status=2"><i class="fa fa-circle-o"></i> 取消集中管理证件</a>
                    </li>
                    <li  class="<c:if test="${status==3}">active</c:if>">
                        <a href="?status=3"><i class="fa fa-circle-o"></i> 丢失的证件</a>
                    </li>
                    <li  class="<c:if test="${status==4}">active</c:if>">
                        <a href="?status=4"><i class="fa fa-trash"></i> 已作废证件</a>
                    </li>
                    <li  class="<c:if test="${status==5}">active</c:if>">
                        <a href="?status=5"><i class="fa fa-inbox"></i> 保险柜管理</a>
                    </li>

                    <div class="buttons pull-right" style="top: -3px; right:10px; position: relative">
                        <a class="editBtn btn btn-default btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </div>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">

                        <div class="space-4"></div>
                            <table class="table table-actived table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>备注</th>
                                    <th></th>
                                    <th style="width: 120px"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${safeBoxs}" var="safeBox" varStatus="st">
                                    <tr>
                                        <td nowrap>${safeBox.code}</td>
                                        <td nowrap>${safeBox.remark}</td>
                                        <shiro:hasPermission name="safeBox:changeOrder">
                                            <c:if test="${!_query && commonList.recNum>1}">
                                                <td nowrap>
                                                    <a href="#"
                                                       <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                                       class="changeOrderBtn" data-id="${safeBox.id}" data-direction="1" title="上升"><i
                                                            class="fa fa-arrow-up"></i></a>
                                                    <input type="text" value="1"
                                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                                           title="修改操作步长">
                                                    <a href="#"
                                                       <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                                       class="changeOrderBtn" data-id="${safeBox.id}" data-direction="-1"
                                                       title="下降"><i class="fa fa-arrow-down"></i></a></td>
                                                </td>
                                            </c:if>
                                        </shiro:hasPermission>
                                        <td nowrap>
                                            <div class="hidden-sm hidden-xs action-buttons">
                                                <shiro:hasPermission name="safeBox:edit">
                                                    <button onclick="safeBox_au(${safeBox.id})" class="btn btn-default btn-mini btn-xs">
                                                        <i class="fa fa-edit"></i> 编辑
                                                    </button>
                                                </shiro:hasPermission>
                                                <shiro:hasPermission name="safeBox:del">
                                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${safeBox.id}">
                                                        <i class="fa fa-trash"></i> 删除
                                                    </button>
                                                </shiro:hasPermission>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                    </div>
                </div></div></div>
        <div id="item-content">
        </div>
    </div>
</div>
