<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=SystemConstants.CADRE_STATUS_NOW%>" var="CADRE_STATUS_NOW"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_TEMP%>" var="CADRE_STATUS_TEMP"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_LEAVE%>" var="CADRE_STATUS_LEAVE"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_LEADER_LEAVE%>" var="CADRE_STATUS_LEADER_LEAVE"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_MAP%>" var="CADRE_STATUS_MAP"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><span style="font-size:25px;font-weight: bolder">${sysUser.realname}</span>离任
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadre_leave" id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.id}">
        <input type="hidden" name="status">
        <div class="well" style="font-size: 25px; padding-bottom: 10px; padding-left: 150px">
        <input type="checkbox" class="big" value="1"/> 处级干部离任<br/>
        <input type="checkbox" class="big" value="2"/> 校领导离任
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>
    $("input[type=checkbox]").click(function(){
        if($(this).prop("checked")){
            $("input[type=checkbox]").not(this).prop("checked", false);
        }
    });
    $("#modal form").validate({
        submitHandler: function (form) {
            var type = $('#modal input[type=checkbox]:checked').val();
            if(type==1){
                $("#modal input[name=status]").val('${CADRE_STATUS_LEAVE}');
            }else if(type==2){
                $("#modal input[name=status]").val('${CADRE_STATUS_LEADER_LEAVE}');
            }else {
                SysMsg.warning("请选择离任类别");
                return;
            }
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        SysMsg.success('操作成功。', '成功',function(){
                                location.href='${ctx}/cadre?status='+$("#modal input[name=status]").val()
                        });
                    }
                }
            });
        }
    });
</script>