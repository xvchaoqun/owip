<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${param.dismiss==1?"离任":"重新任命"}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/branchMember_dismiss" id="modalForm" method="post">
        <input type="hidden" name="id" value="${branchMember.id}">
        <input type="hidden" name="dismiss" value="${param.dismiss}">
        <div class="form-group">
            <label class="col-xs-4 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <c:if test="${param.dismiss==1}">
        <div class="form-group">
            <label class="col-xs-4 control-label">离任时间</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 120px">
                    <input class="form-control date-picker" name="dismissDate" type="text"
                           data-date-min-view-mode="1" placeholder="yyyy.mm"
                           data-date-format="yyyy.mm" value="${cm:formatDate(branchMember.dismissDate,'yyyy.MM')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                  </div>
            </div>
        </div>
        </c:if>
        <c:if test="${param.dismiss==0}">
        <div class="form-group">
            <label class="col-xs-4 control-label">任命时间</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 120px">
                    <input class="form-control date-picker" name="assignDate" type="text"
                           data-date-min-view-mode="1" placeholder="yyyy.mm"
                           data-date-format="yyyy.mm" value="${cm:formatDate(now,'yyyy.MM')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                  </div>
            </div>
        </div>
        </c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>
    $.register.date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
</script>