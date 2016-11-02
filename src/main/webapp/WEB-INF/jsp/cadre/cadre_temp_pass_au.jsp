<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>通过常委会任命</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadre_temp_pass_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadre.id}">
        <input type="hidden" name="status" value="${status}">
			<div class="form-group">
				<label class="col-xs-3 control-label">账号</label>
				<div class="col-xs-6 label-text">
                    ${sysUser.realname}-${sysUser.code}
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">任职单位</label>
            <div class="col-xs-8">
                <select  class="form-control" name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
                    <option></option>
                    <c:forEach items="${unitMap}" var="unit">
                        <option value="${unit.key}">${unit.value.name}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=unitId]").val('${cadre.unitId}');
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">任命职务</label>
            <div class="col-xs-6">
                <input  class="form-control" type="text" name="post" value="${cadre.post}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">任职单位及职务</label>
            <div class="col-xs-6">
                <input  class="form-control" type="text" name="title" value="${cadre.title}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">职务属性</label>
            <div class="col-xs-6">
                <select  data-rel="select2" name="postId" data-placeholder="请选择职务属性">
                    <option></option>
                    <jsp:include page="/metaTypes?__code=mc_post"/>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=postId]").val(${cadre.postId});
                </script>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">行政级别</label>
				<div class="col-xs-6">
                    <select  data-rel="select2" name="typeId" data-placeholder="请选择行政级别">
                        <option></option>
                        <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=typeId]").val(${cadre.typeId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" name="remark" rows="5">${cadre.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-success" value="通过任命"/>
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
                        //SysMsg.success('操作成功。', '成功',function(){
                            //$("#jqGrid").trigger("reloadGrid");
                            //location.href='${ctx}/cadre?status=1'
                            _openView('${ctx}/cadre_view?id=${cadre.id}&to=cadrePost_page')
                       // });
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    register_user_select($('[data-rel="select2-ajax"]'));
</script>