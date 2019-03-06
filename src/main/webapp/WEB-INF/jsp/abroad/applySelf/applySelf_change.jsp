<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/abroad/constants.jsp" %>
    <h3 class="header">因私出国申请变更</h3>
    <form class="form-horizontal" action="${ctx}/abroad/applySelf_change" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${applySelf.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>干部</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
							name="cadreId" data-placeholder="请选择干部">
						<option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>申请日期</label>
				<div class="col-xs-6">
					<div class="input-group" style="width: 200px">
						<input required class="form-control date-picker" name="_applyDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>出行时间范围</label>
				<div class="col-xs-6">
					<select required name="type" data-rel="select2" data-placeholder="请选择出行时间范围">
						<option></option>
						<c:forEach items="${ABROAD_APPLY_SELF_DATE_TYPE_MAP}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=type]").val('${applySelf.type}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>出发时间</label>
				<div class="col-xs-6">
					<div class="input-group"  style="width: 200px">
						<input required class="form-control date-picker" name="_startDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>返回时间</label>
				<div class="col-xs-6">
					<div class="input-group"  style="width: 200px">
						<input required class="form-control date-picker" name="_endDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">前往国家或地区</label>
				<div class="col-xs-6">
					<input type="text" name="toCountry" id="form-field-tags" value="${applySelf.toCountry}" placeholder="输入后选择国家或按回车 ..." />

					<%--<select name="toCountry" data-rel="select2" data-placeholder="请选择国家或地区">
						<option></option>
						<c:forEach items="${countryMap}" var="country">
							<option value="${country.key}">${country.value.cninfo}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=toCountry]").val('${applySelf.toCountry}');
					</script>--%>
				</div>
			</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">出国（境）事由</label>
			<div class="col-xs-9 choice label-text">
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
			<div class="col-xs-9 choice label-text">
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
			<div class="col-xs-9 choice label-text">
				<input  name="_costSource"type="radio" value="自费"> 自费&nbsp;
				<input name="_costSource" type="radio" value="其他来源"> 其他来源
				<input name="_costSource_other" type="text">
				<input name="costSource" type="hidden">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">所需证件</label>
			<div class="col-xs-9 choice label-text">
				<c:forEach items="${cm:getMetaTypes('mc_passport_type')}" var="type">
					<input name="_needPassports" type="checkbox" value="${type.key}"> ${type.value.name}&nbsp;
				</c:forEach>
				<input name="needPassports" type="hidden">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">备注</label>
			<div class="col-xs-2">
				<textarea  class="form-control limited" name="remark" maxlength="100">${applySelf.remark}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">本人说明材料</label>
			<div class="col-xs-2">
			<input  class="form-control" type="file" name="_modifyProof" />
				</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">变更原因</label>
			<div class="col-xs-2">
				<textarea  class="form-control" name="modifyRemark"></textarea>
			</div>
		</div>
    </form>
<div class="clearfix form-actions">
	<div class="col-md-offset-3 col-md-9">
		<button class="btn btn-info" type="submit">
			<i class="ace-icon fa fa-check bigger-110"></i>
			提交
		</button>
		&nbsp; &nbsp; &nbsp;
		<button class="hideView btn" type="button">
			<i class="ace-icon fa fa-undo bigger-110"></i>
			取消
		</button>
	</div>
</div>

<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>

	$.fileInput($("input[name=_modifyProof]"), {
		allowExt: ['pdf'],
		no_file:'请上传pdf文件 ...'
	})

	<c:forEach var="reason" items="${fn:split(applySelf.reason,'+++')}">
	$("input[name=_reason][value='${reason}']").prop("checked", true);
	</c:forEach>
	<c:forEach var="peerStaff" items="${fn:split(applySelf.peerStaff,'+++')}">
	$("input[name=_peerStaff][value='${peerStaff}']").prop("checked", true);
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

	$("button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

			// 前往国家或地区
			var toCountry = $("#modalForm input[name=toCountry]").val().trim();
			if(toCountry==''){
				SysMsg.info("请填写前往国家或地区",'',function(){
					$("#modalForm input[name=toCountry]").val('').focus();
				});
				return;
			}

			// 出国（境）事由
			var $_reason = $("#modalForm input[name=_reason][value='其他']");
			var _reason_other = $("input[name=_reason_other]").val().trim();
			if($_reason.is(":checked")){
				if(_reason_other==''){
					SysMsg.info("请输入其他出国（境）事由", '', function(){
						$("#modalForm input[name=_reason_other]").val('').focus();
					});
					return;
				}
			}
			var reasons = [];
			$.each($("#modalForm input[name=_reason]:checked"), function(){
				if($(this).val()=='其他'){
					reasons.push("其他:"+_reason_other);
				}else
					reasons.push($(this).val());
			});
			if(reasons.length==0){
				SysMsg.info("请选择出国（境）事由");
				return;
			}
			$("#modalForm input[name=reason]").val(reasons.join("+++"));

			// 同行人员
			var $_peerStaff = $("#modalForm input[name=_peerStaff][value='其他']");
			var _peerStaff_other = $("#modalForm input[name=_peerStaff_other]").val().trim();
			if($_peerStaff.is(":checked")){
				if(_peerStaff_other==''){
					SysMsg.info("请输入其他同行人员", '', function(){
						$("#modalForm input[name=_peerStaff_other]").val('').focus();
					});
					return;
				}
			}
			var peerStaffs = [];
			$.each($("#modalForm input[name=_peerStaff]:checked"), function(){
				if($(this).val()=='其他'){
					peerStaffs.push("其他:"+_peerStaff_other);
				}else
					peerStaffs.push($(this).val());
			});
			if(peerStaffs.length==0){
				SysMsg.info("请选择同行人员");
				return;
			}
			$("#modalForm input[name=peerStaff]").val(peerStaffs.join("+++"));


			// 费用来源
			var $_costSource = $("#modalForm input[name=_costSource][value='其他来源']");
			var _costSource_other = $("#modalForm input[name=_costSource_other]").val().trim();
			if($_costSource.is(":checked")){
				if(_costSource_other==''){
					SysMsg.info("请输入其他费用来源", '', function(){
						$("#modalForm input[name=_costSource_other]").val('').focus();
					});
					return;
				}
			}
			var costSources = [];
			$.each($("#modalForm input[name=_costSource]:checked"), function(){
				if($(this).val()=='其他来源'){
					costSources.push("其他来源:"+_costSource_other);
				}else
					costSources.push($(this).val());
			});
			if(costSources.length==0){
				SysMsg.info("请选择费用来源");
				return;
			}
			$("#modalForm input[name=costSource]").val(costSources.join("+++"));

			// 所需证件
			var needPassports = [];
			$.each($("#modalForm input[name=_needPassports]:checked"), function(){
				needPassports.push($(this).val());
			});
			if(needPassports.length==0){
				SysMsg.info("请选择所需证件");
				return;
			}
			$("#modalForm input[name=needPassports]").val(needPassports.join(","));

			/*if($('#modalForm input[type=file]').val()==''){
				SysMsg.info("请上传本人说明材料");
				return;
			}*/
			if($.trim($('#modalForm textarea[name=modifyRemark]').val())==''){
				SysMsg.info("请填写更改原因",'',function(){
					$('#modalForm textarea[name=modifyRemark]').focus();
				});
				return;
			}

			$(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						//SysMsg.success('修改成功。', '成功',function(){
							$("#jqGrid").trigger("reloadGrid");
							$.hideView();
						//});
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$.register.date($('.date-picker'))
	$.register.user_select($('[data-rel="select2-ajax"]'));
	$('textarea.limited').inputlimiter();
</script>