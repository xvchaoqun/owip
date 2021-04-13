<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>组织关系转出-撤销已完成审批</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberOut_abolish" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.id}">
        <div class="form-group">
            <label class="col-xs-1 control-label"></label>
            <div class="col-xs-10 label-text red" style="font-size: large; font-weight: bolder; ">
                撤销之后：
                <ul>
                    <li>该用户将重新进入党员库</li>
                    <li>申请被退回，可在"未通过"标签中查看之前的审批记录</li>
                </ul>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">申请人</label>
            <div class="col-xs-6 label-text">
                <c:set var="sysUser" value="${cm:getUserById(memberOut.userId)}"/>
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">撤销原因</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text" name="remark" rows="5"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <button id="submitBtn" type="button" class="btn btn-danger"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"> 确定撤销</button>

</div>

<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid").trigger("reloadGrid");
                    }
                     $btn.button('reset');
                }
            });
        }
    });
</script>