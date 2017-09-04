<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${pcsExcludeBranch!=null}">编辑</c:if><c:if test="${pcsExcludeBranch==null}">添加</c:if>党代会</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcsExcludeBranch_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${pcsExcludeBranch.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">选择分党委</label>
            <div class="col-xs-6">
                <select required class="form-control"  data-rel="select2-ajax"
                        data-ajax-url="${ctx}/party_selects?del=0&notDirect=1"
                        name="partyId" data-placeholder="请选择" data-width="320">
                    <option value="${party.id}">${party.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
            <label class="col-xs-3 control-label">选择党支部</label>
            <div class="col-xs-6">
                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?del=0"
                        name="branchId" data-placeholder="请选择" data-width="320">
                    <option value="${branch.id}">${branch.name}</option>
                </select>
            </div>
        </div>
        <script>
            register_party_branch_select($("#modalForm"), "branchDiv",
                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
        </script>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea style="width: 320px" class="form-control limited" type="text"
                          name="remark" maxlength="200">${pcsExcludeBranch.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${pcsExcludeBranch!=null}">确定</c:if><c:if test="${pcsExcludeBranch==null}">添加</c:if>"/>
</div>

<script>
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
    $("#modalForm :checkbox").bootstrapSwitch();
    $('textarea.limited').inputlimiter();
</script>