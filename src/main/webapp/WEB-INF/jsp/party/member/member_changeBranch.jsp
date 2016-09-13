<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量分党委内部组织关系变动</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/member_changeBranch" id="modalForm" method="post">
        <input type="hidden" name="partyId" value="${party.id}">
        <input type="hidden" name="ids[]" value="${param['ids[]']}">
			<div class="form-group">
				<label class="col-xs-3 control-label">转移人数</label>
				<div class="col-xs-6 label-text">
                    ${fn:length(fn:split(param['ids[]'],","))}
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">所属分党委</label>
				<div class="col-xs-6 label-text">
                    ${party.name}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">选择党支部</label>
				<div class="col-xs-6">
                    <select required class="form-control"  data-rel="select2-ajax"
                            data-ajax-url="${ctx}/branch_selects?partyId=${party.id}"
                            name="branchId" data-placeholder="请选择" data-width="320">
                        <option value=""></option>
                    </select>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定转移"/>
</div>

<script>
    $('#modalForm [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        SysMsg.success('操作成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    }
                }
            });
        }
    });
</script>