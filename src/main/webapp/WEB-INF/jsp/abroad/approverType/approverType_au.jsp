<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/abroad/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${approverType!=null}">编辑</c:if><c:if test="${approverType==null}">添加</c:if>审批人分类</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/abroad/approverType_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${approverType.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${approverType.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6">
                    <select required name="type" data-rel="select2" data-placeholder="请选择"> 
                        <option></option>
                          <c:forEach items="${ABROAD_APPROVER_TYPE_MAP}" var="type"> 
                            <option value="${type.key}">${type.value}</option>
                        </c:forEach> 
                    </select> 
                    <script>
                        $("#modalForm select[name=type]").val("${approverType.type}");
                    </script>
				</div>
			</div>
            <div class="form-group" id="leaderAuth">
                <label class="col-xs-3 control-label">审批范围</label>
                <div class="col-xs-6">
                    <div class="input-group">
                    <input required name="auth" type="checkbox" value="1" class="big chkBox"> 分管部门
                    <input required name="auth" type="checkbox" value="2" class="big chkBox"> 联系学院
                    </div>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${approverType!=null}">确定</c:if><c:if test="${approverType==null}">添加</c:if>"/>
</div>

<script>
    var auth = "${approverType.auth}";
    $.each(auth.split(","), function(idx, val){
        $("input[name=auth][value='"+val+"']").prop("checked", true);
    })

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });

    $('#modalForm select[name=type]').select2().on("change", function(){

        if($(this).val()=='${ABROAD_APPROVER_TYPE_LEADER}'){
            $("#leaderAuth input[name=auth]").prop("required", true);
            $("#leaderAuth").show();
        }else{
            $("#leaderAuth input[name=auth]").prop("required", false);
            $("#leaderAuth").hide();
        }
    }).change();
    $('[data-rel="tooltip"]').tooltip();
</script>