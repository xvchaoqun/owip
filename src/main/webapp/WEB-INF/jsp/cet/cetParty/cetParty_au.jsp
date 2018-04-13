<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetParty!=null}">编辑</c:if><c:if test="${cetParty==null}">添加</c:if>院系级党委</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetParty_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetParty.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属院系级党委</label>
            <div class="col-xs-6">
                <select name="partyId" data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?del=0"
                        data-placeholder="请选择党委">
                    <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                </select>
                <script>
                    $.register.del_select($("#modalForm select[name=partyId]"), 350)
                </script>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetParty!=null}">确定</c:if><c:if test="${cetParty==null}">添加</c:if></button>
</div>

<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
</script>