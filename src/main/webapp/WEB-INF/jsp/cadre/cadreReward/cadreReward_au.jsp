<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreReward!=null}">编辑</c:if><c:if test="${cadreReward==null}">添加</c:if></h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreReward_au?toApply=${param.toApply}&cadreId=${cadre.id}" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="_isUpdate" value="${param._isUpdate}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="id" value="${cadreReward.id}">
        <input type="hidden" name="rewardType" value="${param.rewardType}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">获奖年份</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 120px">
                        <input required class="form-control date-picker" name="_rewardTime" type="text"
                               data-date-min-view-mode="2"
                               data-date-format="yyyy" value="${cm:formatDate(cadreReward.rewardTime,'yyyy')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">获得奖项</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cadreReward.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">颁奖单位</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="unit" value="${cadreReward.unit}">
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">获奖证书</label>
                <div class="col-xs-6">
                    <input class="form-control" type="file" name="_proof" />
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">排名</label>
				<div class="col-xs-6">
                        第 <input id="spinner" type="text" name="rank" value="${cadreReward.rank}"> 名
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" name="remark">${cadreReward.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>

    <button type="button" id="submitBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中，请不要关闭此窗口"
            data-success-text="上传成功" autocomplete="off">
        <c:if test="${cadreReward!=null}">确定</c:if><c:if test="${cadreReward==null}">添加</c:if>
    </button>
</div>
<script src="${ctx}/assets/js/fuelux/fuelux.spinner.js"></script>
<script src="${ctx}/assets/js/ace/elements.spinner.js"></script>
<script>
    $('#spinner').ace_spinner({value:0,min:0,max:100,step:1, on_sides: true, icon_up:'ace-icon fa fa-plus bigger-110', icon_down:'ace-icon fa fa-minus bigger-110', btn_up_class:'btn-success' , btn_down_class:'btn-danger'});
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
                        <c:if test="${param.toApply!=1}">
                        <c:if test="${param.rewardType==CADRE_REWARD_TYPE_RESEARCH}">
                        $("#orginal").load("${ctx}/cadreReward_fragment?cadreId=${cadre.id}")
                        </c:if>
                        $("#jqGrid_cadreReward").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#body-content-view").load("${ctx}/modifyTableApply_detail?applyId=${param.applyId}&module=${param.module}&_="+new Date().getTime())
                        </c:if>
                        <c:if test="${param._isUpdate!=1}">
                        $.hashchange('cls=1&rewardType=${param.rewardType}&module=${param.module}');
                        </c:if>
                        </c:if>
                    }else{
                        $btn.button('reset');
                    }
                }
            });
        }
    });
    $.fileInput($('#modalForm input[type=file]'))
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>