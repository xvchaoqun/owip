<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${spTalent!=null?'编辑':'添加'}高层次人才</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sp/spTalent_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${spTalent.id}">
        <div class="col-xs-6">
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span> 姓名</label>
				<div class="col-xs-6">
					<select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
							name="userId" data-placeholder="请输入账号或姓名或学工号">
						<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 所在单位</label>
				<div class="col-xs-6">
                    <select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                            data-placeholder="请选择">
                        <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 到校日期</label>
				<div class="col-xs-6">
                    <div class="input-group date" data-date-format="yyyy-mm-dd">
						<input class="form-control date-picker" name="arriveDate" type="text"
							   data-date-format="yyyy-mm-dd"
							   value="${cm:formatDate(spTalent.arriveDate,'yyyy-MM-dd')}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                        </div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 编制类别</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="authorizedType" value="${spTalent.authorizedType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 人员类别</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="staffType" value="${spTalent.staffType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 政治面貌</label>
				<div class="col-xs-6">
                    <select class="col-xs-6" name="politicsStatus"
                            data-rel="select2" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_political_status"/>
                    </select>
				</div>
                <script>
                    $("#modalForm select[name=politicsStatus]").val('${spTalent.politicsStatus}');
                </script>
			</div>
            <div class="form-group">
            <label class="col-xs-4 control-label"> 专业技术职务</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="proPost" value="${spTalent.proPost}">
            </div>
        </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"> 专技岗位等级</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="proPostLevel" value="${spTalent.proPostLevel}">
                </div>
            </div>
        </div>
        <div class="col-xs-6">

			<div class="form-group">
				<label class="col-xs-4 control-label"> 一级学科</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="firstSubject" value="${spTalent.firstSubject}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 人才/荣誉称号</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="talentTitle" value="${spTalent.talentTitle}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 授予日期</label>
				<div class="col-xs-6">
                    <div class="input-group date" data-date-format="yyyy-mm-dd">
                        <input class="form-control date-picker" name="awardDate" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(spTalent.awardDate,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 联系方式</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="phone" value="${spTalent.phone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control" type="text" name="remark">${spTalent.remark}</textarea>
				</div>
			</div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty spTalent?'确定':'添加'}</button>
</div>
<script>

	$("select[name=userId]").change(function () {

		var userId = $(this).val();
		$.post("${ctx}/sp/spTalent_details",{userId:userId},function (ret) {
            var cadre = ret.cadre;

		    if (cadre != null){

		    	$("input[name=arriveDate]").val($.date(cadre.arriveTime,'yyyy-MM-dd'));
		        $("input[name=authorizedType]").val(cadre.authorizedType);
		        $("input[name=staffType]").val(cadre.staffType);
		        $("input[name=proPost]").val(cadre.proPost);
		        $("input[name=proPostLevel]").val(cadre.proPostLevel);
		        $("input[name=talentTitle]").val(cadre.talentTitle);
				$("input[name=phone]").val(cadre.mobile);
            }else {

				$("input[name=arriveDate]").val(null);
				$("input[name=authorizedType]").val(null);
				$("input[name=staffType]").val(null);
				$("input[name=proPost]").val(null);
				$("input[name=proPostLevel]").val(null);
				$("input[name=talentTitle]").val(null);
				$("input[name=phone]").val(null);
			}
		})
	});

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $.register.date($('.date-picker'));
</script>