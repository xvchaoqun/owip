<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量校内组织关系转移</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/member_changeParty" id="modalForm" method="post">
        <input type="hidden" name="ids[]" value="${param['ids[]']}">
			<div class="form-group">
				<label class="col-xs-3 control-label">转移人数</label>
				<div class="col-xs-6 label-text">
                    ${fn:length(fn:split(param['ids[]'],","))}
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">转入分党委</label>
            <div class="col-xs-9">
                <select required class="form-control"  data-rel="select2-ajax"
                        data-width="360"
                        data-ajax-url="${ctx}/party_selects"
                        name="partyId" data-placeholder="请选择" >
                    <option value=""></option>
                </select>
            </div>
        </div>
        <div class="form-group" style="display: none" id="branchDiv">
            <label class="col-xs-3 control-label">转入党支部</label>
            <div class="col-xs-9">
                <select required class="form-control"  data-rel="select2-ajax"
                        data-width="360"
                        data-ajax-url="${ctx}/branch_selects"
                        name="branchId" data-placeholder="请选择">
                    <option value=""></option>
                </select>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定转移"/>
</div>

<script>
    $.register.party_branch_select($("#modalForm"), "branchDiv",
            '${cm:getMetaTypeByCode("mt_direct_branch").id}',
            "${party.id}", "${party.classId}" , "partyId", "branchId");
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        //SysMsg.success('操作成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                        //});
                    }
                }
            });
        }
    });
</script>