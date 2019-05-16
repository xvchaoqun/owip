<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="CADRE_REWARD_TYPE_RESEARCH" value="<%=CadreConstants.CADRE_REWARD_TYPE_RESEARCH%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreReward!=null}">编辑</c:if><c:if test="${cadreReward==null}">添加</c:if></h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreReward_au?toApply=${param.toApply}&cadreId=${cadre.id}" autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
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
            <label class="col-xs-3 control-label"><span class="star">*</span>奖励级别</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="rewardLevel"
                        data-placeholder="请选择">
                    <option></option>
                    <c:import url="/metaTypes?__code=mc_reward_level"/>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=rewardLevel]").val(${cadreReward.rewardLevel});
                </script>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>获奖年份</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 100px">
                        <input required class="form-control date-picker" name="_rewardTime" type="text"
                               data-date-min-view-mode="2"
                               data-date-format="yyyy" value="${cm:formatDate(cadreReward.rewardTime,'yyyy')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>获得奖项</label>
				<div class="col-xs-6">
                    <textarea required class="form-control" name="name">${cadreReward.name}</textarea>
                    <span class="help-block">注：不要加书名号。</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">颁奖单位</label>
				<div class="col-xs-6">
                    <textarea class="form-control" name="unit">${cadreReward.unit}</textarea>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">获奖证书</label>
                <div class="col-xs-6">
                    <input class="form-control" type="file" name="_proof" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">是否独立获奖</label>
                <div class="col-xs-6">
                    <label>
                        <input name="isIndependent" ${cadreReward.isIndependent?"checked":""}  type="checkbox" />
                        <span class="lbl"></span>
                    </label>
                </div>
            </div>
			<div class="form-group" id="rankDiv">
				<label class="col-xs-3 control-label">排名</label>
				<div class="col-xs-6">
                        第 <input type="text" class="digits" data-rule-min="1" name="rank" style="width: 50px;" value="${cadreReward.rank}"> 名
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

    function isIndependentChange(){
        if($("input[name=isIndependent]").bootstrapSwitch("state")){
            $("#rankDiv").hide();
            //$("input[name=rank]").removeAttr("required");
        }else{
            $("#rankDiv").show();
            //$("input[name=rank]").attr("required", "required");
        }
    }
    $('input[name=isIndependent]').on('switchChange.bootstrapSwitch', function(event, state) {
        isIndependentChange();
    });
    isIndependentChange();

    //$('#spinner').ace_spinner({value:0,min:0,max:100,step:1, on_sides: true, icon_up:'ace-icon fa fa-plus bigger-110', icon_down:'ace-icon fa fa-minus bigger-110', btn_up_class:'btn-success' , btn_down_class:'btn-danger'});
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