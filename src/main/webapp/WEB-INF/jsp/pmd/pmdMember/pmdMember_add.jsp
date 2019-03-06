<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>添加党员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdMember_add" id="modalForm" method="post">

        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>缴费月份</label>
            <div class="col-xs-6 label-text">
               ${cm:formatDate(pmdMonth.payMonth, "yyyy年MM月")}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">党员</label>
            <div class="col-xs-6">
                <select required name="userId"  class="form-control"
                        data-rel="select2-ajax" data-width="350"
                        data-ajax-url="${ctx}/member_selects?noAuth=1&partyId=${param.partyId}&branchId=${param.branchId}&status=${MEMBER_STATUS_NORMAL}"
                        data-placeholder="请输入账号或姓名或学工号">
                    <option></option>
                </select>
            </div>
        </div>

    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" ${empty pmdMonth?'disabled':''} class="btn btn-primary"><i class="fa fa-check"></i> 添加</button>
</div>

<script>

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#${param.type=='ow'?'jqGrid':'jqGrid2'}").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $.register.user_select($('#modalForm select[name=userId]'));
</script>