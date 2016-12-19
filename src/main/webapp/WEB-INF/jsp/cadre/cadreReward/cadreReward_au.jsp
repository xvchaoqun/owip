<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreReward!=null}">编辑</c:if><c:if test="${cadreReward==null}">添加</c:if></h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreReward_au?cadreId=${cadre.id}" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${cadreReward.id}">
        <input type="hidden" name="rewardType" value="${param.rewardType}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">日期</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 120px">
                        <input required class="form-control date-picker" name="_rewardTime" type="text"
                               data-date-min-view-mode="1"
                               data-date-format="yyyy.mm" value="${cm:formatDate(cadreReward.rewardTime,'yyyy.MM')}" />
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
                        <input required class="form-control" type="text" name="unit" value="${cadreReward.unit}">
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
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreReward!=null}">确定</c:if><c:if test="${cadreReward==null}">添加</c:if>"/>
</div>
<script src="${ctx}/assets/js/fuelux/fuelux.spinner.js"></script>
<script src="${ctx}/assets/js/ace/elements.spinner.js"></script>
<script>
    $('#spinner').ace_spinner({value:0,min:0,max:100,step:1, on_sides: true, icon_up:'ace-icon fa fa-plus bigger-110', icon_down:'ace-icon fa fa-minus bigger-110', btn_up_class:'btn-success' , btn_down_class:'btn-danger'});
    register_date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid_cadreReward").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('#modalForm input[type=file]').ace_file_input({
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
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>