<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="${_pMap['rewardOnlyYear']=='true'}" var="_p_rewardOnlyYear"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadrePunish!=null}">编辑</c:if><c:if test="${cadrePunish==null}">添加</c:if></h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadrePunish_au?cadreId=${cadre.id}" autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${cadrePunish.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>

            <c:if test="${_p_rewardOnlyYear}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>受处分年份</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 100px">
                        <input required class="form-control date-picker" name="_punishTime" type="text"
                               data-date-min-view-mode="2"
                               data-date-format="yyyy" value="${cm:formatDate(cadrePunish.punishTime,'yyyy')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
            </c:if>
            <c:if test="${!_p_rewardOnlyYear}">
			<div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>受处分年月</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 120px">
                        <input required class="form-control date-picker" name="_punishTime" type="text"
                               data-date-min-view-mode="1" placeholder="yyyy.mm"
                               data-date-format="yyyy.mm" value="${cm:formatDate(cadrePunish.punishTime,'yyyy.MM')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            </c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>受何种处分</label>
				<div class="col-xs-6">
                    <textarea required class="form-control" name="name">${cadrePunish.name}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">处分单位</label>
				<div class="col-xs-6">
                    <textarea class="form-control" name="unit">${cadrePunish.unit}</textarea>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><%--${_cadreReward_needProof?'<span class="star">*</span>':''}--%>处分文件</label>
                <div class="col-xs-6">
                    <input <%--${(_cadreReward_needProof && empty cadrePunish.proof)?'required':''}--%> class="form-control" type="file" name="_proof" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">是否列入<br/>干部任免审批表</label>
                <div class="col-xs-6">
                    <label>
                        <input name="listInAd" ${cadrePunish.listInAd?"checked":""}  type="checkbox" />
                        <span class="lbl"></span>
                    </label>
                </div>
            </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" name="remark">${cadrePunish.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>

    <button type="button" id="submitBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            data-success-text="提交成功" autocomplete="off">
        <c:if test="${cadrePunish!=null}">确定</c:if><c:if test="${cadrePunish==null}">添加</c:if>
    </button>
</div>
<script>
    $.register.date($('.date-picker'));
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $btn.button("success").addClass("btn-success");
                        $("#modal").modal("hide");
                        $("#jqGrid_cadrePunish").trigger("reloadGrid");
                    }else{
                        $btn.button('reset');
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $.fileInput($('#modalForm input[type=file]'));
</script>