<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${!empty opRecord?'编辑':'添加'}组织处理</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/op/opRecord_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${opRecord.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 处理对象</label>
			<div class="col-xs-6">
				<select ${not empty opRecord?"disabled data-theme='default'":""} required data-rel="select2-ajax"
																				data-ajax-url="${ctx}/member_selects"
																				name="userId" data-width="270"
																				data-placeholder="请输入账号或姓名或学工号">
					<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
				</select>
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 执行日期</label>
				<div class="col-xs-6">
					<div class="input-group" style="width:270px">
						<input required class="form-control date-picker" name="startDate" type="text"
								data-date-format="yyyy.mm.dd" value="${cm:formatDate(opRecord.startDate, 'yyyy.MM.dd')}"/>
						<span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<c:if test="${not empty opRecord}">
			<div class="form-group">
				<label class="col-xs-3 control-label"> 行政级别</label>
				<div class="col-xs-6">
					<select data-rel="select2" name="adminLevel"
							data-width="272" data-placeholder="请选择">
						<option></option>
						<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_admin_level').id}"/>
					</select>
					<script type="text/javascript">
						$("#modal form select[name=adminLevel]").val(${opRecord.adminLevel});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 时任职务</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="post" value="${opRecord.post}">
				</div>
			</div>
			</c:if>
			<div class="form-group opRecordType">
				<label class="col-xs-3 control-label"><span class="star">*</span> 组织处理方式</label>
				<div class="col-xs-6">
					<select required data-width="270" class="form-control" name="type" data-rel="select2"
						data-placeholder="请选择组织处理方式">
						<option></option>
						<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_op_type').id}"/>
					</select>
					<script>
						$("#modalForm select[name=type]").val('${opRecord.type}');
					</script>
				</div>
			</div>
			<div class="form-group opAsk" disabled hidden>
		<label class="col-xs-3 control-label"><span class="star">*</span> 针对问题-函询</label>
		<div class="col-xs-6">
			<select data-width="270" class="form-control" name="issue" data-rel="select2"
					data-placeholder="请选择针对问题">
				<option></option>
				<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_op_type_ask').id}"/>
			</select>
			<script>
				$("#modalForm select[name=issue]").val('${opRecord.issue}');
			</script>
		</div>
	</div>
		<div class="form-group opRemind" disabled hidden>
			<label class="col-xs-3 control-label"><span class="star">*</span> 针对问题-提醒</label>
			<div class="col-xs-6">
				<select data-width="270" class="form-control" name="issue" data-rel="select2"
						data-placeholder="请选择针对问题">
					<option></option>
					<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_op_type_remind').id}"/>
				</select>
				<script>
					$("#modalForm select[name=issue]").val('${opRecord.issue}');
				</script>
			</div>
		</div>
		<div class="form-group opEncourage" disabled hidden>
			<label class="col-xs-3 control-label"><span class="star">*</span> 针对问题-诫勉</label>
			<div class="col-xs-6">
				<select data-width="270" class="form-control" name="issue" data-rel="select2"
						data-placeholder="请选择针对问题">
					<option></option>
					<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_op_type_encourage').id}"/>
				</select>
				<script>
					$("#modalForm select[name=issue]").val('${opRecord.issue}');
				</script>
			</div>
		</div>
			<div class="form-group otherIssue" disabled hidden>
				<label class="col-xs-3 control-label"><span class="star">*</span> 其他针对问题</label>
				<div class="col-xs-6">
					<input class="form-control" type="text" name="issueOther" value="${opRecord.issueOther}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 开展方式</label>
				<div class="col-xs-6">
					<select required data-width="270" class="form-control" name="way" data-rel="select2"
							data-placeholder="请选择开展方式">
						<option></option>
						<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_op_way').id}"/>
					</select>
					<script>
						$("#modalForm select[name=way]").val('${opRecord.way}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 谈话人类型</label>
				<div class="col-xs-6">
					<select required data-width="270" class="form-control" name="talkType" data-rel="select2"
							data-placeholder="请选择开展方式">
						<option></option>
						<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_op_talktype').id}"/>
					</select>
					<script>
						$("#modalForm select[name=talkType]").val('${opRecord.talkType}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 具体谈话人</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax"
																					 data-ajax-url="${ctx}/member_selects"
																					 name="talkUserId" data-width="270"
																					 data-placeholder="请输入账号或姓名或学工号">
						<option value="${talkSysUser.id}">${talkSysUser.realname}-${talkSysUser.code}</option>
					</select>
				</div>
			</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 备注</label>
			<div class="col-xs-8" style="width: 295px">
				<textarea class="form-control" name="remark">${opRecord.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty opRecord?'确定':'添加'}</button>
</div>
<script>

	var opType = $(".opRecordType select[name=type]");//组织处理方式
	var opIssueAsk = $(".opAsk select[name=issue]");//针对问题-函询
	var opIssueRemind = $(".opRemind select[name=issue]");//针对问题-提醒
	var opIssueEncourage = $(".opEncourage select[name=issue]");//针对问题-诫勉

	opType.change(function(){
		opTypeChange();
	});
	
	opIssueAsk.on("change",function(){
		opIssueChange();
	});
	opIssueRemind.on("change",function(){
		opIssueChange();
	});
	opIssueEncourage.on("change",function(){
		opIssueChange();
	});

    if (opType.find("option:selected").text()=='函询') {
        $(".opAsk").show();
        $(".opRemind").hide();
        $(".opEncourage").hide();
        $(".opAsk").attr("disabled","false");
        $(".opRemind").attr("disabled","true");
        $(".opEncourage").attr("disabled","true");

        //清空选择框中的值
        opIssueRemind.val(null);
        opIssueEncourage.val(null);

        //修改必填属性
        opIssueRemind.removeAttr("required");
        opIssueEncourage.removeAttr("required");
        opIssueAsk.attr("required","true");

    }else if (opType.find("option:selected").text()=='提醒') {
        $(".opAsk").hide();
        $(".opRemind").show();
        $(".opEncourage").hide();
        $(".opAsk").attr("disabled","true");
        $(".opRemind").attr("disabled","false");
        $(".opEncourage").attr("disabled","true");

        //清空选择框中的值
        opIssueAsk.val(null);
        opIssueEncourage.val(null);

        opIssueAsk.removeAttr("required");
        opIssueEncourage.removeAttr("required");
        opIssueRemind.attr("required", "true");

    }else if (opType.find("option:selected").text()=='诫勉') {
        $(".opAsk").hide();
        $(".opRemind").hide();
        $(".opEncourage").show();
        $(".opAsk").attr("disabled","true");
        $(".opRemind").attr("disabled","true");
        $(".opEncourage").attr("disabled","false");

        //清空选择框中的值
        opIssueAsk.val(null);
        opIssueRemind.val(null);

        opIssueAsk.removeAttr("required");
        opIssueRemind.removeAttr("required");
        opIssueEncourage.attr("required", "true");

    }

	if (opIssueAsk.val() != "" || opIssueRemind.val() != "" || opIssueRemind.val() != ""){
		opTypeChange();
		opIssueChange();
	}

	//根据组织处理方式改变针对问题
	function opTypeChange() {
		var typeText=opType.find("option:selected").text();
		if (typeText=='函询') {
			$(".opAsk").show();
			$(".opRemind").hide();
			$(".opEncourage").hide();
			$(".opAsk").attr("disabled","false");
			$(".opRemind").attr("disabled","true");
			$(".opEncourage").attr("disabled","true");

			//清空选择框中的值
			opIssueRemind.val(null);
			opIssueEncourage.val(null);

			//修改必填属性
			opIssueRemind.removeAttr("required");
			opIssueEncourage.removeAttr("required");
			opIssueAsk.attr("required","true");
			
		}else if (typeText=='提醒') {
			$(".opAsk").hide();
			$(".opRemind").show();
			$(".opEncourage").hide();
			$(".opAsk").attr("disabled","true");
			$(".opRemind").attr("disabled","false");
			$(".opEncourage").attr("disabled","true");

			//清空选择框中的值
			opIssueAsk.val(null);
			opIssueEncourage.val(null);

			opIssueAsk.removeAttr("required");
			opIssueEncourage.removeAttr("required");
			opIssueRemind.attr("required", "true");

		}else if (typeText=='诫勉') {
			$(".opAsk").hide();
			$(".opRemind").hide();
			$(".opEncourage").show();
			$(".opAsk").attr("disabled","true");
			$(".opRemind").attr("disabled","true");
			$(".opEncourage").attr("disabled","false");

			//清空选择框中的值
			opIssueAsk.val(null);
			opIssueRemind.val(null);

			opIssueAsk.removeAttr("required");
			opIssueRemind.removeAttr("required");
			opIssueEncourage.attr("required", "true");

		}else{
			$(".opAsk").hide();
			$(".opRemind").hide();
			$(".opEncourage").hide();
			$(".opAsk").attr("disabled","true");
			$(".opRemind").attr("disabled","true");
			$(".opEncourage").attr("disabled","true");
		}
	}
	
	//根据函询的问题判断是否弹出其他问题
	function opIssueChange(){
		var askText=opIssueAsk.find("option:selected").text();
		var encourageText=opIssueEncourage.find("option:selected").text();
		if (askText=='其他方面' || encourageText=='其他需要进行诫勉的问题'){
			$(".otherIssue").val(null);
			$(".otherIssue").show();
			$(".otherIssue").attr("required", "true");
		}else {
			$(".otherIssue").hide();
			$(".otherIssue").removeAttr("required");
		}
	}

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
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>