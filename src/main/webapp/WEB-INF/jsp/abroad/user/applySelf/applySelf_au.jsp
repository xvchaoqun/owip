<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="tabbable">
	<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
		<div style="margin-bottom: 8px">

			<div class="buttons">
				<a href="javascript:;" class="hideView btn btn-sm btn-success">
					<i class="ace-icon fa fa-backward"></i>
					返回
				</a>
			</div>
		</div>
	</ul>

	<div class="tab-content">
		<div id="home4" class="tab-pane in active">
			<form class="form-horizontal" action="${ctx}/user/abroad/applySelf_au" id="applyForm" method="post" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${applySelf.id}">
				<input type="hidden" name="cadreId" value="${param.cadreId}">
				<div class="form-group">
					<label class="col-xs-3 control-label">出行时间</label>
					<div class="col-xs-6">
						<select name="type" data-rel="select2" data-width="255"
								data-placeholder="请选择">
							<option></option>
							<c:forEach items="${ABROAD_APPLY_SELF_DATE_TYPE_MAP}" var="type">
								<option value="${type.key}">${type.value}</option>
							</c:forEach>
						</select>
						<script>
							$("#applyForm select[name=type]").val('${applySelf.type}');
						</script>
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">出发日期</label>
					<div class="col-xs-9">
						<div class="input-group"  style="width: 255px">
							<input  class="form-control date-picker" name="_startDate" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">回国日期</label>
					<div class="col-xs-9">
						<div class="input-group"  style="width: 255px">
							<input class="form-control date-picker" name="_endDate" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">前往国家或地区</label>
					<div class="col-xs-6">
						<input type="text" name="toCountry" id="form-field-tags" value="${applySelf.toCountry}"
							   placeholder="输入后选择国家或按回车 ..." style="width: 455px"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">出国（境）事由</label>
					<div class="col-xs-9 choice">
						<input name="_reason" type="checkbox" value="旅游"> 旅游&nbsp;
						<input name="_reason" type="checkbox" value="探亲"> 探亲&nbsp;
						<input name="_reason" type="checkbox" value="访友"> 访友&nbsp;
						<input name="_reason" type="checkbox" value="继承"> 继承&nbsp;
						<input name="_reason" type="checkbox" value="接受和处理财产"> 接受和处理财产&nbsp;
						<input name="_reason" type="checkbox" value="其他"> 其他
						<input name="_reason_other" type="text">
						<input name="reason" type="hidden"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">同行人员</label>
					<div class="col-xs-9 choice">
						<input name="_peerStaff" type="checkbox" value="配偶"> 配偶&nbsp;
						<input name="_peerStaff" type="checkbox" value="子女"> 子女&nbsp;
						<input name="_peerStaff" type="checkbox" value="无"> 无&nbsp;
						<input name="_peerStaff" type="checkbox" value="其他"> 其他
						<input name="_peerStaff_other" type="text">
						<input name="peerStaff" type="hidden">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">费用来源</label>
					<div class="col-xs-9 choice">
						<input  name="_costSource"type="radio" value="自费"> 自费&nbsp;
						<input name="_costSource" type="radio" value="其他来源"> 其他来源
						<input name="_costSource_other" type="text">
						<input name="costSource" type="hidden">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">所需证件</label>
					<div class="col-xs-9 choice">
						<c:forEach items="${passportTypeMap}" var="type">
							<c:set var="hasPassport" value="${passportMap.get(type.key)!=null}"/>
							<div>
							<input ${!hasPassport?'disabled':''} name="_needPassports" type="checkbox" value="${type.key}"> ${type.value.name}
							<c:if test="${!hasPassport}">
							(<span class="bolder text-danger">您没有有效的${type.value.name}</span>)
							</c:if>
							</div>
						</c:forEach>
						<input name="needPassports" type="hidden">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">备注</label>
					<div class="col-xs-2">
						<textarea  class="form-control limited" name="remark" maxlength="100"></textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">其他说明材料</label>
					<div class="col-xs-2 file">
						<div class="files">
							<input class="form-control" type="file" name="_files[]" />
						</div>
						<button type="button" onclick="addFile()" class="btn btn-default btn-xs"><i class="fa fa-plus"></i></button>
					</div>
				</div>
				<c:if test="${fn:length(files)>0}">
					<div class="form-group" id="fileGroup">
						<label class="col-xs-3 control-label">已上传材料</label>
						<div class="col-xs-3">
							<c:forEach items="${files}" var="file">
								<div id="file${file.id}" class="file row well well-sm col-xs-12">
									<div class="col-xs-9 ">
										<a href="${ctx}/abroad/applySelf_download?id=${file.id}" target="_blank">${file.fileName}</a></div>
									<div class="col-xs-3"><a href="javascript:;" onclick="_delFile(${file.id}, '${file.fileName}')">删除</a></div>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:if>
			</form>
			<%--<div class="center" style="font:bold 30px Verdana, Arial, Helvetica, sans-serif; padding-bottom: 50px;">
				<input id="agree" type="checkbox" class="chkBox" style="width: 30px; height: 30px; margin: 0;"/> 信息已确认无误
			</div>--%>
			<div class="modal-footer center">
				<input id="submitBtn"
					   ${fn:length(passportMap)==0?'disabled':''}
					   data-loading-text="提交中..."
					   data-success-text="您的申请已提交成功"
					   autocomplete="off" class="btn btn-primary btn-lg" value="${param.edit==1?"修改提交":"提交申请"}"/>
				<c:if test="${fn:length(passportMap)==0}">
					(<span class="bolder text-danger bigger">您没有有效的因私出国（境）证件</span>)
				</c:if>
			</div>
		</div>
	</div>
</div>

<style>
	#applyForm input[type=radio], #applyForm input[type=checkbox]{
		width: 20px;
		height: 20px;
		_vertical-align: -1px;/*针对IE6使用hack*/
		vertical-align: -5px;
	}
	#applyForm .tags{
		width: 256px;
		min-height: 50px;
	}
	#applyForm  .control-label{
		font-size: 20px;
		font-weight: bolder;
	}
	.choice{
		font-size: 20px;
	}
	#applyForm .form-group{
		padding-bottom: 5px;
		padding-top:0px!important;
	}
	.file label{
		margin-bottom:15px;
	}

</style>
<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>
	var reason;
	<c:forEach var="reason" items="${fn:split(applySelf.reason,'+++')}">
	reason = '${reason}';
	if(reason.startWith("其他:")){
		$("input[name=_reason][value='其他']").prop("checked", true);
		$("input[name=_reason_other]").val(reason.split(":")[1]);
	}else{
		$("input[name=_reason][value='${reason}']").prop("checked", true);
	}
	</c:forEach>
	var peerStaff;
	<c:forEach var="peerStaff" items="${fn:split(applySelf.peerStaff,'+++')}">
	peerStaff = '${peerStaff}';
	if(peerStaff.startWith("其他:")){
		$("input[name=_peerStaff][value='其他']").prop("checked", true);
		$("input[name=_peerStaff_other]").val(peerStaff.split(":")[1]);
	}else{
		$("input[name=_peerStaff][value='${peerStaff}']").prop("checked", true);
	}
	</c:forEach>
	var costSource = '${applySelf.costSource}';
	if(costSource=='自费')
		$("input[name=_costSource][value='自费']").prop("checked", true);
	else if(costSource.startWith("其他来源:")){
		$("input[name=_costSource][value='其他来源']").prop("checked", true);
		$("input[name=_costSource_other]").val(costSource.split(":")[1]);
	}
	<c:forEach var="needPassports" items="${fn:split(applySelf.needPassports,',')}">
	$("input[name=_needPassports][value='${needPassports}']").prop("checked", true);
	</c:forEach>

	function _delFile(id, name){
		bootbox.confirm("确定删除'"+name+"'吗？", function (result) {
			if (result) {
				$.post("${ctx}/user/abroad/applySelfFile_del",{id:id},function(ret){
					if(ret.success){
						SysMsg.success("删除成功",'',function(){
							$("#file"+id).remove();
							if($("#fileGroup").find(".file").length==0){
								$("#fileGroup").remove();
							}
						});
					}
				});
			}
		});
	}

	$.fileInput($('input[type=file]'));

	function addFile(){
		var _file = $('<input class="form-control" type="file" name="_files[]" />');
		$(".files").append(_file);
		$.fileInput(_file);
		return false;
	}
	var tag_input = $('#form-field-tags');
	try{
		tag_input.tag(
				{
					placeholder:tag_input.attr('placeholder'),
					//enable typeahead by specifying the source array
					source: ${countryList}
				}
		)
	} catch(e) {
		//display a textarea for old IE, because it doesn't support this plugin or another one I tried!
		tag_input.after('<textarea id="'+tag_input.attr('id')+'" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
		//autosize($('#form-field-tags'));
	}

	$("#submitBtn").click(function(){$("#applyForm").submit();return false;});
    $("#applyForm").validate({
        submitHandler: function (form) {

			// 出行时间
			var type = $("#applyForm select[name=type]").val().trim();
			if(type==''){
				SysMsg.info("请选择出行时间");
				$("input[name=type]").val('').focus();
				return;
			}
			// 出发日期
			var _startDate = $("#applyForm input[name=_startDate]").val().trim();
			if(_startDate==''){
				SysMsg.info("请选择出发日期",'',function(){
					$("input[name=_startDate]").val('').focus();
				});
				return;
			}
			// 回国日期
			var _endDate = $("#applyForm input[name=_endDate]").val().trim();
			if(_endDate==''){
				SysMsg.info("请选择回国日期",'',function(){
					$("input[name=_endDate]").val('').focus();
				});
				return;
			}

			// 前往国家或地区
			var toCountry = $("#applyForm input[name=toCountry]").val().trim();
			if(toCountry==''){
				SysMsg.info("请填写前往国家或地区",'',function(){
					$("input[name=toCountry]").val('').focus();
				});
				return;
			}

			// 出国（境）事由
			var $_reason = $("#applyForm input[name=_reason][value='其他']");
			var _reason_other = $("input[name=_reason_other]").val().trim();
			if($_reason.is(":checked")){
				if(_reason_other==''){
					SysMsg.info("请输入其他出国（境）事由", '', function(){
						$("#applyForm input[name=_reason_other]").val('').focus();
					});
					return;
				}
			}
			var reasons = [];
			$.each($("#applyForm input[name=_reason]:checked"), function(){
				if($(this).val()=='其他'){
					reasons.push("其他:"+_reason_other);
				}else
					reasons.push($(this).val());
			});
			if(reasons.length==0){
				SysMsg.info("请选择出国（境）事由");
				return;
			}
			$("#applyForm input[name=reason]").val(reasons.join("+++"));

			// 同行人员
			var $_peerStaff = $("#applyForm input[name=_peerStaff][value='其他']");
			var _peerStaff_other = $("#applyForm input[name=_peerStaff_other]").val().trim();
			if($_peerStaff.is(":checked")){
				if(_peerStaff_other==''){
					SysMsg.info("请输入其他同行人员", '', function(){
						$("#applyForm input[name=_peerStaff_other]").val('').focus();
					});
					return;
				}
			}
			var peerStaffs = [];
			$.each($("#applyForm input[name=_peerStaff]:checked"), function(){
				if($(this).val()=='其他'){
					peerStaffs.push("其他:"+_peerStaff_other);
				}else
					peerStaffs.push($(this).val());
			});
			if(peerStaffs.length==0){
				SysMsg.info("请选择同行人员");
				return;
			}
			$("#applyForm input[name=peerStaff]").val(peerStaffs.join("+++"));


			// 费用来源
			var $_costSource = $("#applyForm input[name=_costSource][value='其他来源']");
			var _costSource_other = $("#applyForm input[name=_costSource_other]").val().trim();
			if($_costSource.is(":checked")){
				if(_costSource_other==''){
					SysMsg.info("请输入其他费用来源", '', function(){
						$("#applyForm input[name=_costSource_other]").val('').focus();
					});
					return;
				}
			}
			var costSources = [];
			$.each($("#applyForm input[name=_costSource]:checked"), function(){
				if($(this).val()=='其他来源'){
					costSources.push("其他来源:"+_costSource_other);
				}else
					costSources.push($(this).val());
			});
			if(costSources.length==0){
				SysMsg.info("请选择费用来源");
				return;
			}
			$("#applyForm input[name=costSource]").val(costSources.join("+++"));

			// 所需证件
			var needPassports = [];
			$.each($("#applyForm input[name=_needPassports]:checked"), function(){
				needPassports.push($(this).val());
			});
			if(needPassports.length==0){
				SysMsg.info("请选择所需证件");
				return;
			}
			$("#applyForm input[name=needPassports]").val(needPassports.join(","));

			//alert($("input[name=needPassports]").val());
			//return ;

			/*if($("#agree").is(":checked") == false){
				$('#agree').qtip({content:'请确认信息准确无误。',show: true, hide: 'unfocus'});
				return false;
			}*/

			var $btn = $("#submitBtn").button('loading');
			//setTimeout(function () { $btn.button('reset'); },1000);
            $(form).ajaxSubmit({
				dataType:'json',
                success:function(ret){
                    if(ret.success){
						$btn.button("success").addClass("btn-success");

						<c:if test="${param.auth!='admin'}">
						_gotoPassportDrawPage(ret.applyId); // 强制跳转
						</c:if>
						<c:if test="${param.auth=='admin'}">
						$.hideView();
						</c:if>
                    }else{
						$btn.button('reset');
					}
                }
            });
        }
    });
	function _gotoPassportDrawPage(applyId){

		$("#sidebar a[href='/user/applySelf']").closest("li").removeClass("active");
		$("#sidebar a[href='/user/passportDraw']").closest("li").addClass("active");
		$("#body-content").hide();
		$.get("${ctx}/user/abroad/passportDraw_self_select",{applyId:applyId},function(html){
			$("#item-content").hide().html(html).fadeIn("slow");
		})
	}
    $('#applyForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$.register.date($('.date-picker'))
	$.register.user_select($('[data-rel="select2-ajax"]'));
	$('textarea.limited').inputlimiter();
</script>