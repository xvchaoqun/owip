<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改缴费方式</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdMember_setIsOnlinePay" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="ids" value="${param.ids}">
        <c:set var="num" value='${fn:length(fn:split(param.ids,","))}'/>
        <c:if test="${num==1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">姓名</label>

                <div class="col-xs-6 label-text">
                        ${pmdMember.user.realname}
                </div>
            </div>
        </c:if>
        <c:if test="${num>1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">已选择缴费记录</label>

                <div class="col-xs-6 label-text">
                        ${num}条
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>缴费方式</label>
            <c:if test="${param.isDelay!=1}">
            <div class="col-xs-6">
                <select required data-rel="select2" name="isOnlinePay"
                        data-width="270"
                        data-placeholder="请选择">
                    <option></option>
                    <option value="1">线上缴费</option>
                    <option value="0">现金缴费</option>
                </select>
            </div>
            </c:if>
            <c:if test="${param.isDelay==1}">
                <div class="col-xs-6 label-text">
                    现金缴费
                    <input type="hidden" name="isOnlinePay" value="0"/>
                </div>
            </c:if>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>${param.isDelay!=1?"修改原因":"补缴说明"}</label>
            <div class="col-xs-6">
            <textarea required class="form-control limited" type="text"
                      name="remark" maxlength="100"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        <c:if test="${param.auth==1}">
                        $("#jqGrid").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.auth!=1}">
                        $("#jqGrid2").trigger("reloadGrid");
                        </c:if>
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
</script>