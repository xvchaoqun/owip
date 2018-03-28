<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>生成评课账号</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainInspector_gen" id="modalForm" enctype="multipart/form-data" method="post">
        <input type="hidden" name="trainId" value="${cetTrain.id}">
        <input type="hidden" name="type" value="1">
			<div class="form-group">
				<label class="col-xs-3 control-label">培训班次</label>
				<div class="col-xs-6 label-text">
					${cetTrain.name}
				</div>
			</div>
        <c:if test="${cetTrain.evaCount==null||cetTrain.evaCount==0}">
        <div class="form-group">
            <label class="col-xs-3 control-label">是否匿名测评</label>
            <div class="col-xs-6">
                <label>
                    <input name="evaAnonymous" ${cetTrain.evaAnonymous?"checked":""} type="checkbox"/>
                    <span class="lbl"></span>
                </label>
            </div>
        </div>
        </c:if>

        <div id="un_anonymous" style="${(cetTrain.evaCount>0&&cetTrain.evaAnonymous)?'display:none':''}">
        <div class="form-group">
            <label class="col-xs-offset-1 col-xs-2 control-label">Excel文件</label>
            <div class="col-xs-4">
                <input type="file" name="xlsx" ${(cetTrain.evaCount>0&&cetTrain.evaAnonymous)?'':'required'} extension="xlsx"/>
            </div>
        </div>
        <div class="well">
            1、导入的文件请严格按照<a href="${ctx}/attach?code=sample_cetTrainInspector" target="_blank">培训人员录入样表.xlsx</a>（点击下载）的数据格式<br/>
            2、可以在excel文件中增加或修改人员信息后导入<br/>
            3、原人员信息会进行更新处理，但相关已测评结果保持不变
        </div>
        </div>
        <div id="anonymous"  style="${(cetTrain.evaCount>0&&!cetTrain.evaAnonymous)?'display:none':''}">
        <div class="form-group">
            <label class="col-xs-3 control-label">生成账号数量</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input ${(cetTrain.evaCount>0&&!cetTrain.evaAnonymous)?'':'required'} class="form-control digits" data-validation="min[${cetTrain.evaCount}]"
                           type="text" name="count" value="${cetTrain.evaCount}">
                </div>
            </div>
        </div>
        <div class="well">
            1、输入大于当前数量的一个值，可以增加账号
        </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    <c:if test="${cetTrain.evaCount==null||cetTrain.evaCount==0}">
    function evaAnonymousChanged(){
        if($('#modalForm input[name=evaAnonymous]').bootstrapSwitch("state")) {

            $("#un_anonymous").hide();
            $("#anonymous").show();
            $("#un_anonymous input").removeAttr("required");
            $("#anonymous input").attr("required", "required");
        }else{

            $("#un_anonymous").show();
            $("#anonymous").hide();
            $("#un_anonymous input").attr("required", "required");
            $("#anonymous input").removeAttr("required");
        }
    }
    $('#modalForm input[name=evaAnonymous]').on('switchChange.bootstrapSwitch', function(event, state) {
        evaAnonymousChanged()
    });
    evaAnonymousChanged()
    </c:if>

    $.fileInput($('#modalForm input[type=file]'))
	$.register.datetime($('.datetime-picker'));
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>