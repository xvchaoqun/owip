<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <a onclick="safeBox_au()" class="btn btn-default pull-right" style="margin-right: 20px">添加</a>
    <h3>保险箱管理</h3>
</div>
<div class="modal-body">
    <div class="popTableDiv"
         data-url-page="${ctx}/safeBox_page"
         data-url-del="${ctx}/safeBox_del"
         data-url-co="${ctx}/safeBox_changeOrder"
         data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover table-condensed">
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
                                    <button onclick="safeBox_au(${safeBox.id})" class="btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="safeBox:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${safeBox.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="text-center">
                <div class="pagination pagination-centered">
                    <c:if test="${!empty commonList && commonList.pageNum>1 }">
                        <wo:page commonList="${commonList}" uri="${ctx}/safeBox_page" target="#modal .modal-content"
                                 pageNum="5"
                                 model="3"/>
                    </c:if>
                </div>
            </div>
        </c:if>
        <c:if test="${commonList.recNum==0}">
            <div class="well well-lg center">
                <h4 class="green lighter">暂无记录</h4>
            </div>
        </c:if>
    </div>
</div>
<script>

    function safeBox_au(id){
        var url = "${ctx}/safeBox_au?pageNo=${commonList.pageNo}";
        if(id>0) url += "&id="+id;
        loadModal(url);
    }

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        pop_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
</script>