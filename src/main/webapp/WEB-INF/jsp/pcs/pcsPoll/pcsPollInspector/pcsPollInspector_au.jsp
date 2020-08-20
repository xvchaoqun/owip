<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>生成投票人账号</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcs/pcsPollInspector_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="pollId" value="${param.pollId}">
        <div class="form-group">
            <label class="col-xs-5 control-label"> 本支部党员数量</label>
            <div class="col-xs-5 label-text">
                ${pcsBranch.memberCount}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-5 control-label"> 本支部正式党员数量</label>
            <div class="col-xs-5 label-text">
                ${pcsBranch.positiveCount}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-5 control-label"> 已生成账号数量</label>
            <div class="col-xs-5 label-text">
                ${pcsPoll.inspectorNum}
            </div>
        </div>

		<div class="form-group">
			<label class="col-xs-5 control-label"><span class="star">*</span> 新生成账号数量</label>
            <c:if test="${pcsBranch.memberCount>pcsPoll.inspectorNum}">
                <div class="col-xs-7">
                    <input required style="width: 80px;" class="form-control digits" type="text"
                           name="count" data-rule-min="1" data-rule-max="${pcsBranch.memberCount-pcsPoll.inspectorNum}">
                    <span class="help-block">注：此数量是追加生成新账号的数量，不影响已生成的账号</span>
                </div>
            </c:if>
            <c:if test="${pcsBranch.memberCount<=pcsPoll.inspectorNum}">
                <div class="col-xs-7" style="padding-top: 7px">
                    账号数量不允许超过本支部党员数量
                </div>
            </c:if>

		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
    <c:if test="${pcsBranch.memberCount>pcsPoll.inspectorNum}">
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty pcsPollInspector?'确定':'添加'}</button>
         </c:if>
</div>
<style>
    .label-text{
            padding-top: 2px;
            font-size: 16pt;
            color: red;
    }
</style>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>