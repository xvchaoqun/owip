<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

    <form class="form-horizontal" action="${ctx}/user/applySelf_au" id="applyForm" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label class="col-xs-3 control-label">出行时间</label>
				<div class="col-xs-6">
					<select required name="type" data-rel="select2" data-placeholder="请选择出行时间">
						<option></option>
						<c:forEach items="${APPLY_SELF_DATE_TYPE_MAP}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=type]").val('${applySelf.type}');
					</script>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">出发时间</label>
				<div class="col-xs-2">
					<div class="input-group">
						<input required class="form-control date-picker" name="_startDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">返回时间</label>
				<div class="col-xs-2">
					<div class="input-group">
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
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出国（境）事由</label>
				<div class="col-xs-6 choice">
					<input name="_reason" type="checkbox" value="旅游"> 旅游&nbsp;&nbsp;
					<input name="_reason" type="checkbox" value="探亲"> 探亲&nbsp;&nbsp;
					<input name="_reason" type="checkbox" value="访友"> 访友&nbsp;&nbsp;
					<input name="_reason" type="checkbox" value="继承"> 继承&nbsp;&nbsp;
					<input name="_reason" type="checkbox" value="接受和处理财产"> 接受和处理财产&nbsp;&nbsp;
					<input name="_reason" type="checkbox" value="其他"> 其他
					<input name="_reason_other" type="text">
					<input name="reason" type="hidden"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">同行人员</label>
				<div class="col-xs-6 choice">
					<input name="_peerStaff" type="checkbox" value="配偶"> 配偶&nbsp;&nbsp;
					<input name="_peerStaff" type="checkbox" value="子女"> 子女&nbsp;&nbsp;
					<input name="_peerStaff" type="checkbox" value="无"> 无&nbsp;&nbsp;
					<input name="_peerStaff" type="checkbox" value="其他"> 其他
					<input name="_peerStaff_other" type="text">
					<input name="peerStaff" type="hidden">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">费用来源</label>
				<div class="col-xs-6 choice">
					<input  name="_costSource"type="radio" value="自费"> 自费&nbsp;&nbsp;
					<input name="_costSource" type="radio" value="其他来源"> 其他来源
					<input name="_costSource_other" type="text">
					<input name="costSource" type="hidden">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所需证件</label>
				<div class="col-xs-6 choice">
					<c:forEach items="${passportTypeMap}" var="type">
						<input name="_needPassports" type="checkbox" value="${type.key}"> ${type.value.name}&nbsp;&nbsp;
					</c:forEach>
					<input name="needPassports" type="hidden">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">其他说明材料</label>
				<div class="col-xs-2 file">
					<div class="files">
						<input class="form-control" type="file" name="_files[]" />
					</div>
					<button type="button" onclick="addFile()" class="btn btn-mini"><i class="fa fa-plus"></i></button>
				</div>
			</div>
    </form>
<div class="center" style="font:bold 30px Verdana, Arial, Helvetica, sans-serif; padding: 50px;">
	<input id="agree" type="checkbox" class="chkBox" style="width: 30px; height: 30px; margin: 0;"/> 信息已确认无误
</div>
<div class="modal-footer center">
	<input id="submit" class="btn btn-success" value="提交申请"/>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input class="closeView btn btn-default" value="返回"/>
</div>
<style>

	input[type=radio], input[type=checkbox]{
		width: 20px;
		height: 20px;
		_vertical-align: -1px;/*针对IE6使用hack*/
		vertical-align: -3px;
	}
	.choice{
		font-size: 20px;
	}
	.form-group{
		padding-bottom: 10px;
	}
	.file label{
		margin-bottom:15px;
	}

</style>
<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>
	ace_file_input($('input[type=file]'))
	function ace_file_input($file){
		$file.ace_file_input({
			no_file:'请选择文件 ...',
			btn_choose:'选择',
			btn_change:'更改',
			droppable:false,
			onchange:null,
			thumbnail:false //| true | large
			//whitelist:'gif|png|jpg|jpeg'
			//blacklist:'exe|php'
			//onchange:''
			//
		});
	}
	function addFile(){
		var _file = $('<input class="form-control" type="file" name="_files[]" />');
		$(".files").append(_file);
		ace_file_input(_file);
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

	$("#submit").click(function(){$("#applyForm").submit();return false;});
    $("#applyForm").validate({
        submitHandler: function (form) {

			// 前往国家或地区
			var toCountry = $("input[name=toCountry]").val().trim();
			if(toCountry==''){
				SysMsg.info("请填写前往国家或地区");
				$("input[name=toCountry]").val('').focus();
				return;
			}

			// 出国（境）事由
			var $_reason = $("input[name=_reason][value='其他']");
			var _reason_other = $("input[name=_reason_other]").val().trim();
			if($_reason.is(":checked")){
				if(_reason_other==''){
					SysMsg.info("请输入其他出国（境）事由", '', function(){
						$("input[name=_reason_other]").val('').focus();
					});
					return;
				}
			}
			var reasons = [];
			$.each($("input[name=_reason]:checked"), function(){
				if($(this).val()=='其他'){
					reasons.push("其他:"+_reason_other);
				}else
					reasons.push($(this).val());
			});
			if(reasons.length==0){
				SysMsg.info("请选择出国（境）事由");
				return;
			}
			$("input[name=reason]").val(reasons.join("+++"));

			// 同行人员
			var $_peerStaff = $("input[name=_peerStaff][value='其他']");
			var _peerStaff_other = $("input[name=_peerStaff_other]").val().trim();
			if($_peerStaff.is(":checked")){
				if(_peerStaff_other==''){
					SysMsg.info("请输入其他同行人员", '', function(){
						$("input[name=_peerStaff_other]").val('').focus();
					});
					return;
				}
			}
			var peerStaffs = [];
			$.each($("input[name=_peerStaff]:checked"), function(){
				if($(this).val()=='其他'){
					peerStaffs.push("其他:"+_peerStaff_other);
				}else
					peerStaffs.push($(this).val());
			});
			if(peerStaffs.length==0){
				SysMsg.info("请选择同行人员");
				return;
			}
			$("input[name=peerStaff]").val(peerStaffs.join("+++"));


			// 费用来源
			var $_costSource = $("input[name=_costSource][value='其他来源']");
			var _costSource_other = $("input[name=_costSource_other]").val().trim();
			if($_costSource.is(":checked")){
				if(_costSource_other==''){
					SysMsg.info("请输入其他费用来源", '', function(){
						$("input[name=_costSource_other]").val('').focus();
					});
					return;
				}
			}
			var costSources = [];
			$.each($("input[name=_costSource]:checked"), function(){
				if($(this).val()=='其他来源'){
					costSources.push("其他来源:"+_costSource_other);
				}else
					costSources.push($(this).val());
			});
			if(costSources.length==0){
				SysMsg.info("请选择费用来源");
				return;
			}
			$("input[name=costSource]").val(costSources.join("+++"));

			// 所需证件
			var needPassports = [];
			$.each($("input[name=_needPassports]:checked"), function(){
				needPassports.push($(this).val());
			});
			if(needPassports.length==0){
				SysMsg.info("请选择所需证件");
				return;
			}
			$("input[name=needPassports]").val(needPassports.join(","));

			//alert($("input[name=needPassports]").val());
			//return ;

			if($("#agree").is(":checked") == false){
				$('#agree').qtip({content:'请确认信息准确无误。',show: true, hide: 'unfocus'});
				return false;
			}


            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        SysMsg.success('操作成功。', '成功', function(){
							page_reload();
						});
                    }
                }
            });
        }
    });
    $('#applyForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$('.date-picker').datepicker({
		language:"zh-CN",
		autoclose: true,
		todayHighlight: true
	})
	register_user_select($('[data-rel="select2-ajax"]'));
	$('textarea.limited').inputlimiter();
</script>