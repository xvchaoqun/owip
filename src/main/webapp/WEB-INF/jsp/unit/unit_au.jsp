<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${unit!=null}">编辑</c:if><c:if test="${unit==null}">添加</c:if></h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unit_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${unit.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">单位编号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${unit.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">单位名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${unit.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">单位类型</label>
				<div class="col-xs-6">
                        <select required data-rel="select2" name="typeId" data-placeholder="请选择单位类型">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_unit_type"/>
                        </select>
                        <script type="text/javascript">
                            $("#modal form select[name=typeId]").val(${unit.typeId});
                        </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">成立时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input  class="form-control date-picker" name="_workTime"
                               type="text" data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(unit.workTime, "yyyy-MM-dd")}"/>
                        <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">成立文件</label>
				<div class="col-xs-6">
                    <input class="form-control" type="file" name="_file"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited" name="remark">${unit.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        <c:if test="${unit!=null}">确定</c:if><c:if test="${unit==null}">添加</c:if></button>
</div>
<script>
    $('textarea.limited').inputlimiter();
    $.fileInput($('#modalForm input[name=_file]'),{
        no_file: '请选择pdf文件 ...',
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modal form").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $.reloadMetaData(function(){
                            page_reload();
                        });
                        //SysMsg.success('操作成功。', '成功');
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $.register.date($('.date-picker'));
</script>