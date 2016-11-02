<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${(empty cadre||!cadre.isDp)?"添加":"更新"}民主党派干部</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/democraticParty_au" id="modalForm" method="post">
            <c:if test="${empty cadre}">
			<div class="form-group">
				<label class="col-xs-3 control-label">账号</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                            name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
				</div>
			</div>
            </c:if>
            <c:if test="${not empty cadre}">
                <div class="form-group">
                    <label class="col-xs-3 control-label">账号</label>
                    <div class="col-xs-6 label-text">
                            ${sysUser.realname}-${sysUser.code}
                        <input type="hidden" name="cadreId" value="${cadre.id}">
                    </div>
                </div>
                </c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label">民主党派</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="dpTypeId" data-placeholder="请选择民主党派">
                        <option></option>
                        <jsp:include page="/metaTypes?__code=mc_democratic_party"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=dpTypeId]").val(${cadre.dpTypeId});
                    </script>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">党派加入时间</label>
                <div class="col-xs-6">
                    <div class="input-group"  style="width: 150px">
                        <input class="form-control date-picker" name="_dpAddTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadre.dpAddTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">担任党派职务</label>
                <div class="col-xs-6">
                    <input  class="form-control" type="text" name="dpPost" value="${cadre.dpPost}">
                </div>
            </div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" name="dpRemark" rows="5">${cadre.dpRemark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value='${(empty cadre||!cadre.isDp)?"添加":"更新"}'/>
</div>

<script>
    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $('textarea.limited').inputlimiter();
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        //SysMsg.success('提交成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                        //});
                    }
                }
            });
        }
    });
    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    });
    $('[data-rel="select2"]').select2();
    register_user_select($('[data-rel="select2-ajax"]'));
</script>