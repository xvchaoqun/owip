<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改暂留支部</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberStay_transfer_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberStay.id}">
        <c:if test="${len>1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">申请人</label>
                <div class="col-xs-6 label-text">
                       ${cm:getUserById(memberStay.userId).realname}
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">原支部</label>
                <div class="col-xs-6 label-text">
                        ${branchMap.get(memberStay.branchId).name}
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>请选择暂留所在党支部</label>
            <div class="col-xs-6">
                <select required class="form-control"  data-rel="select2-ajax"
                        data-ajax-url="${ctx}/branch_selects?partyId=${party.id}"
                        name="branchId" data-placeholder="请选择" data-width="320">
                    <option value="${branch.id}">${branch.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>原支部负责人</label>
            <div class="col-xs-6">
                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                        name="orgBranchAdminId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>原支部负责人联系电话</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="orgBranchAdminPhone" value="${memberStay.orgBranchAdminPhone}">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">

     <button id="submitBtn" type="button" class="btn btn-primary"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"> 确定</button>
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

    $("#submitBtn").click(function(){$("#modalForm").submit(); return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        //SysMsg.success('提交成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                        //});
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>