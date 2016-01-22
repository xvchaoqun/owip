<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dispatch!=null}">编辑</c:if><c:if test="${dispatch==null}">添加</c:if>发文</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatch_au" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${dispatch.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">年份</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                               data-date-format="yyyy" data-date-min-view-mode="2" value="${year}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发文类型</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/dispatchType_selects"
                            name="dispatchTypeId" data-placeholder="请选择发文类型">
                        <option value="${dispatchType.id}">${dispatchType.name}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发文号</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="code" value="${dispatch.code}">
                        <span class="label-inline"> * 留空自动生成</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">党委常委会日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_meetingTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(dispatch.meetingTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发文日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_pubTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(dispatch.pubTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任免日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_workTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(dispatch.workTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任免文件</label>
				<div class="col-xs-6">
                    <input class="form-control" type="file" name="_file" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">上会ppt</label>
				<div class="col-xs-6">
                        <input class="form-control" type="file" name="_ppt" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited"name="remark">${dispatch.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${dispatch!=null}">确定</c:if><c:if test="${dispatch==null}">添加</c:if>"/>
</div>

<script>
    $('textarea.limited').inputlimiter();
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
    register_dispatchType_select($('#modalForm select[name=dispatchTypeId]'), $("#modalForm input[name=year]"));
    register_date($('.date-picker'));

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>