<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/passport_page"
             data-url-au="${ctx}/safeBox_au"
             data-url-del="${ctx}/safeBox_del"
             data-url-bd="${ctx}/safeBox_batchDel"
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
                        <a href="?status=4"><i class="fa fa-times"></i> 已作废证件</a>
                    </li>
                    <li  class="<c:if test="${status==5}">active</c:if>">
                        <a href="?status=5"><i class="fa fa-inbox"></i> 保险柜管理</a>
                    </li>

                    <div class="buttons pull-right" style="top: -3px; right:10px; position: relative">
                        <a class="editBtn btn btn-default btn-sm"><i class="fa fa-plus"></i> 添加保险柜</a>
                        <shiro:hasPermission name="safeBox:del">
                            <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 删除</a>
                        </shiro:hasPermission>
                    </div>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">

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
                                    <th>保险柜编号</th>
                                    <th>证件总数量</th>
                                    <th>有效证件数量</th>
                                    <th>取消集中管理证件数量（未确认）</th>
                                    <th>证件所属单位</th>
                                    <th>备注</th>
                                    <shiro:hasPermission name="safeBox:changeOrder">
                                        <c:if test="${commonList.recNum>1}">
                                            <th nowrap>排序</th>
                                        </c:if>
                                    </shiro:hasPermission>
                                    <th style="width: 120px"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${safeBoxBeans}" var="bean" varStatus="st">
                                    <tr>
                                        <td class="center">
                                            <label class="pos-rel">
                                                <input type="checkbox" value="${bean.id}" class="ace">
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td nowrap>${bean.code}</td>
                                        <td nowrap>
                                        <a href="javascript:;" class="openView"
                                           data-url="${ctx}/safeBoxPassportList?safeBoxId=${bean.id}">
                                                ${bean.totalCount}
                                           </a>
                                       </td>
                                        <td nowrap>
                                            <a href="javascript:;" class="openView"
                                               data-url="${ctx}/safeBoxPassportList?safeBoxId=${bean.id}&type=1">
                                                    ${bean.keepCount}
                                            </a>
                                        </td>
                                        <td nowrap>
                                            <a href="javascript:;" class="openView"
                                               data-url="${ctx}/safeBoxPassportList?safeBoxId=${bean.id}&type=2&cancelConfirm=0">
                                                    ${bean.totalCount-bean.keepCount}
                                            </a>
                                        </td>
                                        <td nowrap>
                                        <c:forEach items="${fn:split(bean.unitIds, ',')}" var="unitId" varStatus="vs">
                                            ${unitMap.get(cm:parseInt(unitId)).name}
                                            <c:if test="${!vs.last}"><br/></c:if>
                                        </c:forEach>
                                        </td>
                                        <td nowrap>${bean.remark}</td>
                                        <shiro:hasPermission name="safeBox:changeOrder">
                                            <c:if test="${commonList.recNum>1}">
                                                <td nowrap>
                                                    <a href="#"
                                                       <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                                       class="changeOrderBtn" data-id="${bean.id}" data-direction="1" title="上升"><i
                                                            class="fa fa-arrow-up"></i></a>
                                                    <input type="text" value="1"
                                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                                           title="修改操作步长">
                                                    <a href="#"
                                                       <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                                       class="changeOrderBtn" data-id="${bean.id}" data-direction="-1"
                                                       title="下降"><i class="fa fa-arrow-down"></i></a></td>
                                                </td>
                                            </c:if>
                                        </shiro:hasPermission>
                                        <td nowrap>
                                            <div class="hidden-sm hidden-xs action-buttons">
                                                <button class="openView btn btn-success btn-mini btn-xs"
                                                        data-url="${ctx}/safeBoxPassportList?safeBoxId=${bean.id}">
                                                    <i class="fa fa-info-circle"></i> 详情
                                                </button>
                                                <shiro:hasPermission name="safeBox:edit">
                                                    <button class="editBtn btn btn-default btn-mini btn-xs" data-id="${bean.id}">
                                                        <i class="fa fa-edit"></i> 编辑
                                                    </button>
                                                </shiro:hasPermission>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <%--<wo:page commonList="${commonList}" uri="${ctx}/passport_page" target="#page-content" pageNum="5"
                                     model="3"/>--%>
                        </c:if>
                        <c:if test="${commonList.recNum==0}">
                            <div class="well well-lg center">
                                <h4 class="green lighter">暂无记录</h4>
                            </div>
                        </c:if>

                    </div>
                </div></div></div>
        <div id="item-content">
        </div>
    </div>
</div>