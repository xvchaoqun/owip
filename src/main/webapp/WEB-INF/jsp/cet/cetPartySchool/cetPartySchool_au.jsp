<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetPartySchool!=null}">编辑</c:if><c:if test="${cetPartySchool==null}">添加</c:if>二级党校</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetPartySchool_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetPartySchool.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label">所属二级党校</label>
                <div class="col-xs-6">
                    <select name="partySchoolId" data-rel="select2-ajax" data-ajax-url="${ctx}/partySchool_selects?isHistory=0"
                            data-placeholder="请选择所属二级党校">
                        <option value="${partySchool.id}" title="${partySchool.isHistory}">${partySchool.name}</option>
                    </select>
                    <script>
                        $.register.del_select($("#modalForm select[name=partySchoolId]"), 350)
                    </script>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetPartySchool!=null}">确定</c:if><c:if test="${cetPartySchool==null}">添加</c:if></button>
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