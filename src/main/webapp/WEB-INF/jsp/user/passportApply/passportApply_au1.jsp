<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="alert alert-info">
    <button class="close" data-dismiss="alert">
        <i class="ace-icon fa fa-times"></i>
    </button>
    申请办理因私出国（境）证件的程序：<br/>
    1. 选择需要申办的证件名称，确认信息准确无误之后提交申请；<br/>
    2. 组织部备案之后，发短信通知申请人；<br/>
    3. 申请人登录系统打印申请表，到党委/校长办公室机要室盖章；<br/>
    4. 领取证件之后要及时交到组织部集中保管，不可擅自办理签证、签注和持证出国（境）。
</div>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${passportApply!=null}">编辑</c:if><c:if test="${passportApply==null}">添加</c:if>申请办理因私出国证件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/user/passportApply_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${passportApply.id}">

			<div class="form-group">
				<label class="col-xs-3 control-label">申办证件名称</label>
				<div class="col-xs-6">
                    <select data-rel="select2" name="classId" data-placeholder="请选择证件名称">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_passport_type"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=classId]").val(${passportApply.classId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">申办日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_applyDate" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(passportApply.applyDate,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">应交组织部日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_expectDate" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(passportApply.expectDate,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">实交组织部日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_handleDate" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(passportApply.handleDate,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea required class="form-control limited" type="text" name="remark" rows="5">${passportApply.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${passportApply!=null}">确定</c:if><c:if test="${passportApply==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
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
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })
    register_user_select($('[data-rel="select2-ajax"]'));
    $('textarea.limited').inputlimiter();
</script>