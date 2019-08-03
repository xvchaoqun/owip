<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>"/>
<div style="width: 900px">
<h3>党员信息修改申请</h3>
<hr/>
<form class="form-horizontal" action="${ctx}/member/memberCheck_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
    <input type="hidden" name="id" value="${memberCheck.id}">
    <input type="hidden" name="userId" value="${sysUser.id}">
    <input type="hidden" name="partyId" value="${party.id}">
    <input type="hidden" name="branchId" value="${branch.id}">
    <div class="row">
        <div class="col-xs-7">
            <div class="form-group">
                <label class="col-xs-4 control-label">头像</label>
                <div class="col-xs-6" style="width:170px">
                    <input type="file" name="_avatar" id="_avatar"/>
                </div>
            </div>
            <%--<div class="form-group">
                <label class="col-xs-4 control-label">姓名</label>
                <div class="col-xs-6 label-text">
                    ${sysUser.realname}
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>所属${_p_partyName}</label>
                <div class="col-xs-6 label-text">
                    ${party.name}
                </div>
            </div>
            <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                <label class="col-xs-4 control-label"><span class="star">*</span>所属党支部</label>
                <div class="col-xs-6 label-text">
                    ${branch.name}
                </div>
            </div>--%>

            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>党籍状态</label>
                <div class="col-xs-6">
					<select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"
							data-width="150">
						<option></option>
						<c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
							<option value="${_status.key}">${_status.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=politicalStatus]").val(${memberCheck.politicalStatus});
					</script>
				</div>
            </div>

            <div class="form-group">
                <label class="col-xs-4 control-label">组织关系转入时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="transferTime" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(memberCheck.transferTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
					<span class="help-block">注：本校发展党员请留空</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">提交书面申请书时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="applyTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberCheck.applyTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">确定为入党积极分子时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="activeTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberCheck.activeTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">确定为发展对象时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="candidateTime" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(memberCheck.candidateTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">入党介绍人</label>
                <div class="col-xs-6">
                    <input class="form-control" style="width: 150px" type="text" name="sponsor" value="${memberCheck.sponsor}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span> 入党时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input required class="form-control date-picker" name="growTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberCheck.growTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>

        </div>
        <div class="col-xs-5">
            <div class="form-group">
                <label class="col-xs-3 control-label">入党时所在党支部</label>
                <div class="col-xs-8">
                    <textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="growBranch" rows="3">${memberCheck.growBranch}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">${memberCheck.politicalStatus==MEMBER_POLITICAL_STATUS_POSITIVE?'<span class="star">*</span>':''} 转正时间</label>
                <div class="col-xs-8">
                    <div ${memberCheck.politicalStatus==MEMBER_POLITICAL_STATUS_POSITIVE?'required':''} class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="positiveTime" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(memberCheck.positiveTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">转正时所在党支部</label>
                <div class="col-xs-8">
                    <textarea class="form-control limited noEnter" type="text"  maxlength="100"
                                  name="positiveBranch" rows="3">${memberCheck.positiveBranch}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">党内职务</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text"  maxlength="50"
                                  name="partyPost" rows="2">${memberCheck.partyPost}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">党内奖励</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="partyReward" rows="3">${memberCheck.partyReward}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">其他奖励</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="otherReward" rows="3">${memberCheck.otherReward}</textarea>
                </div>
            </div>

            <div class="form-group">
                    <label class="col-xs-3 control-label">籍贯</label>
                    <div class="col-xs-8">
                        <input class="form-control" type="text" name="nativePlace" value="${memberCheck.nativePlace}">
                        <span class="help-block">${_pMap['nativePlaceHelpBlock']}</span>
                    </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">手机号</label>
                <div class="col-xs-8">
                    <input class="form-control mobile" type="text" name="mobile" value="${memberCheck.mobile}">
                </div>
            </div>
            <c:if test="${sysUser.type==USER_TYPE_JZG}">
            <div class="form-group">
                <label class="col-xs-3 control-label">办公电话</label>

                <div class="col-xs-8">
                    <input class="form-control" type="text" name="phone" value="${memberCheck.phone}">
                </div>
            </div>
            </c:if>
            <div class="form-group">
                <label class="col-xs-3 control-label">电子邮箱</label>

                <div class="col-xs-8">
                    <input class="form-control email" type="text" name="email" value="${memberCheck.email}">
                </div>
            </div>
        </div>
    </div>
</form>
<div class="clearfix form-actions center">
     <button id="submitBtn" type="button" class="btn btn-primary"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
         <i class="ace-icon fa fa-check bigger-110"></i> 提交</button>

    &nbsp; &nbsp; &nbsp;
    <button class="hideView btn" type="button">
        <i class="ace-icon fa fa-reply bigger-110"></i>
        返回
    </button>
</div>
</div>
<script>
    $.fileInput($("#_avatar"), {
        style: 'well',
        btn_choose: '更换头像',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        previewWidth: 143,
        previewHeight: 198,
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    });
    $("#_avatar").find('button[type=reset]').on(ace.click_event, function () {
        //$('#user-profile input[type=file]').ace_file_input('reset_input');
        $("#_avatar").ace_file_input('show_file_list', [{
            type: 'image',
            name: '${ctx}/avatar?path=${cm:encodeURI(memberCheck.avatar)}'
        }]);
    });
    $("#_avatar").ace_file_input('show_file_list', [{
        type: 'image',
        name: '${ctx}/avatar?path=${cm:encodeURI(memberCheck.avatar)}'
    }]);
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'), {endDate: '${_today}'});

    $("#modalForm select[name=politicalStatus]").change(function(){

        var politicalStatus = $(this).val();
        if(politicalStatus=='${MEMBER_POLITICAL_STATUS_POSITIVE}'){
            $("#modalForm input[name=positiveTime]").closest(".form-group")
                .find("label").html('<span class="star">*</span> 转正时间');
             $("#modalForm input[name=positiveTime]").attr("required", "required")
        }else{
            $("#modalForm input[name=positiveTime]").closest(".form-group")
                .find(".star").remove();
            $("#modalForm input[name=positiveTime]").removeAttr("required")
        }
    })

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
             var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //SysMsg.success('提交成功。', '成功',function(){
                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                        //});
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $.register.user_select($('#modalForm select[name=userId]'));
</script>