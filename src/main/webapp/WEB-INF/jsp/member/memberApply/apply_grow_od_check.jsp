<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>审核领取志愿书</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/apply_grow_od_check" autocomplete="off" disableautocomplete
          id="modalForm" method="post">
        <input type="hidden" name="ids[]" value="${ids}">
        <c:if test="${totalCount==0}">
            <span class="msg info">没有需要分配志愿书编码的记录</span>
        </c:if>
        <c:if test="${totalCount>0}">
            <c:if test="${totalCount==1}">
                <div class="form-group">
                    <label class="col-xs-4 control-label">分配对象</label>
                    <div class="col-xs-7 label-text">
                            ${memberApply.user.realname}
                    </div>
                </div>
            </c:if>
            <c:if test="${totalCount>1}">
                <div class="form-group">
                    <label class="col-xs-4 control-label">处理记录</label>
                    <div class="col-xs-7 label-text">
                            ${totalCount} 条 （其中${totalCount}条记录需要分配志愿书编码）
                    </div>
                </div>
            </c:if>

            <div class="form-group">
                <label class="col-xs-4 control-label">选择分配方式</label>
                <div class="col-xs-7">
                    <div class="input-group">
                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                            <input required type="radio" name="assignType" id="type0" value="1">
                            <label for="type0">
                                系统自动分配
                            </label>
                        </div>
                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                            <input required type="radio" name="assignType" id="type1" value="2">
                            <label for="type1">
                                指定编码
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="manualAssign form-group" style="display: none">
                <label class="col-xs-4 control-label">${totalCount>1?"起始":"选择"}志愿书编码</label>
                <div class="col-xs-7 label-text">
                    <select data-rel="select2-ajax" data-ajax--url="${ctx}/applySn_selects?isAbolished=0&isUsed=0"
                            id="startSnId" data-placeholder="请选择">
                    <option></option>
                    </select>
                </div>
            </div>
            <c:if test="${totalCount>1}">
            <div class="manualAssign form-group" style="display: none">
                <label class="col-xs-4 control-label">结束志愿书编码</label>
                <div class="col-xs-7 label-text">
                    <select data-rel="select2-ajax" data-ajax--url="${ctx}/applySn_selects?isAbolished=0&isUsed=0"
                            id="endSnId" data-placeholder="请选择">
                    <option></option>
                    </select>
                    <span class="help-block">注：系统将分配起止编码之间可用的编码</span>
                </div>
            </div>
            </c:if>
            <div class="autoAssign form-group" style="display: none">
                <label class="col-xs-4 control-label">系统将分配志愿书编码</label>
                <div class="col-xs-7 label-text">
                        ${empty startSn?'<span class="text-danger">当无可用编码</span>':startSn.displaySn}
                        <c:if test="${not empty endSn}"> ~ ${endSn.displaySn}</c:if>
                    <div>（共${assignCount}个可用编码<c:if test="${assignCount<totalCount}"><span class="text-danger">，少于需要分配的数量</span></c:if> ）
                    </div>
                </div>
            </div>
        </c:if>
    </form>
</div>
<div class="modal-footer">
    <c:if test="${totalCount==0}">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
    </c:if>
    <c:if test="${totalCount>=1}">
    <button id="submitBtn" type="button"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确定
    </button>
    </c:if>
</div>

<script>

    $("#modalForm input[name=assignType]").click(function(){
		if($(this).val()==2){
			$(".manualAssign").show();
			$(".autoAssign").hide();
			$("#modalForm .manualAssign select").prop("disabled", false).attr("required", "required");
		}else{
		    $(".manualAssign").hide();
			$(".autoAssign").show();
			$("#modalForm .manualAssign select").val(null).trigger("change").prop("disabled", true).removeAttr("required");
		}
	});

    $.register.ajax_select($('[data-rel="select2-ajax"]'));

    $.register.date($('.date-picker'), {endDate: "${_today}"});
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modal form").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');

            var startSnId;
            var endSnId;
            var assignType = $("#modalForm input[name=assignType]:checked").val();
            //console.log(assignType)
            if(assignType==2){
                startSnId = $("#startSnId").val();
                endSnId = $("#endSnId").val();
            }else if(assignType==1){
                startSnId = '${startSn.id}';
                endSnId = '${endSn.id}';
            }

            $(form).ajaxSubmit({
                data:{startSnId:startSnId, endSnId:endSnId},
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal("hide");
                        goto_next("${param.gotoNext}");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>