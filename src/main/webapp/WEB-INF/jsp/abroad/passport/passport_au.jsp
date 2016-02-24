<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${passport!=null}">编辑</c:if><c:if test="${passport==null}">添加</c:if>因私出国证件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/passport_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${passport.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">干部</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                            name="cadreId" data-placeholder="请选择干部">
                        <option value="${cadre.id}">${sysUser.realname}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">证件名称</label>
				<div class="col-xs-6">
                    <select data-rel="select2" name="classId" data-placeholder="请选择证件名称">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_passport_type"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=classId]").val(${passport.classId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">证件号码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${passport.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发证机关</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="authority" value="${passport.authority}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发证日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_issueDate" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">有效期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_expiryDate" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">集中保管日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_keepDate" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(passport.keepDate,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">存放保险柜编号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="safeCode" value="${passport.safeCode}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${passport!=null}">确定</c:if><c:if test="${passport==null}">添加</c:if>"/>
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
</script>