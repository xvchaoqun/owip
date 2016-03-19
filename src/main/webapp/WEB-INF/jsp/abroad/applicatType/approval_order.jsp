<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${applicatType.name}-审批人身份</h3>
</div>
<div class="modal-body">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    添加
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal no-footer" action="${ctx}/applicatType/approvalOrder" id="modalForm" method="post">
                        <input type="hidden" name="applicatTypeId" value="${applicatType.id}">
                        <div class="form-group">
                            <label class="col-xs-3 control-label">审批人身份</label>
                            <div class="col-xs-6">
                                <select name="approverTypeId" data-rel="select2" data-placeholder="请选择"> 
                                    <option></option>
                                      <c:forEach items="${approverTypeMap}" var="type"> 
                                        <option value="${type.key}">${type.value.name}</option>
                                     </c:forEach> 
                                </select> 
                            </div>
                        </div>
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button class="btn btn-info btn-sm" type="submit">
                                    <i class="ace-icon fa fa-check "></i>
                                    确定
                                </button>

                                &nbsp; &nbsp; &nbsp;
                                <button class="btn btn-default btn-sm" type="reset">
                                    <i class="ace-icon fa fa-undo"></i>
                                    重置
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="space-10"></div>
    <div class="popTableDiv"
         data-url-page="${ctx}/applicatType/approvalOrder?id=${applicatType.id}"
         data-url-del="${ctx}/applicatType/approvalOrder_del"
         data-url-co="${ctx}/applicatType/approvalOrder_changeOrder?applicatTypeId=${applicatType.id}">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-5">审批人身份</th>
                    <%--<c:if test="${!_query && commonList.recNum>1}">
                        <th nowrap>审批顺序</th>
                    </c:if>--%>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${approvalOrders}" var="approvalOrder" varStatus="st">
                    <tr>
                        <td nowrap>${approverTypeMap.get(approvalOrder.approverTypeId).name}</td>
                        <%--<c:if test="${!_query && commonList.recNum>1}">
                            <td nowrap>
                                <a href="#"
                                   <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${approvalOrder.id}" data-direction="1" title="上升"><i
                                        class="fa fa-arrow-up"></i></a>
                                <input type="text" value="1"
                                       class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                       title="修改操作步长">
                                <a href="#"
                                   <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${approvalOrder.id}" data-direction="-1"
                                   title="下降"><i class="fa fa-arrow-down"></i></a></td>
                            </td>
                        </c:if>--%>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${approvalOrder.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="text-center">
                <div class="pagination pagination-centered">
                    <c:if test="${!empty commonList && commonList.pageNum>1 }">
                        <wo:page commonList="${commonList}" uri="${ctx}/applicatType/approvalOrder" target="#modal .modal-content"
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
    $('#modalForm [data-rel="select2"]').select2();

    $("#modal button[type=submit]").click(function(){$("#modalForm").submit(); return false;})
    $("#modalForm").validate({
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
    register_user_select($('#modal [data-rel="select2-ajax"]'));
</script>