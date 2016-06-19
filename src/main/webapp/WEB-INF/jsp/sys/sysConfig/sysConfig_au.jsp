<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
        ${empty sysConfig?"添加配置":sysConfig.name}
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sysConfig_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${sysConfig.id}">
        <c:if test="${!param.editContent}">
        <div class="form-group">
            <label class="col-xs-3 control-label">名称</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${sysConfig.name}">
            </div>
        </div>
        <shiro:hasRole name="admin">
            <div class="form-group">
                <label class="col-xs-3 control-label">代码</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="code" value="${sysConfig.code}">
                </div>
            </div>
        </shiro:hasRole>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea name="remark" class="form-control">${sysConfig.remark}</textarea>
            </div>
        </div>
        </c:if>
            <div style="margin-left: auto; margin-right: auto;max-width: 600px; margin-top: 10px;">
                <textarea id="content">
                    ${sysConfig.content}
                </textarea>
                <input type="hidden" name="content">
            </div>

    </form>
</div>
<div class="modal-footer"><a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${sysConfig!=null}">确定</c:if><c:if test="${sysConfig==null}">添加</c:if>"/>
</div>
<script type="text/javascript" src="${ctx}/kindeditor/kindeditor.js"></script>
<script>
    KE.init({
        id: 'content',
        height: '500px',
        resizeMode: 1,
        width: '600px',
        //scriptPath:"${ctx}/js/kindeditor/",
        //skinsPath : KE.scriptPath + 'skins/',
        items: [
            'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
            'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'image', 'link', 'unlink', 'fullscreen']
    });
    KE.create('content');

    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $(function(){
        $("#modal form").validate({
            submitHandler: function (form) {

                $("#modal form input[name=content]").val(KE.util.getData('content'));

                $(form).ajaxSubmit({
                    success:function(ret){
                        if(ret.success){
                            $("#modal").modal('hide');
                            SysMsg.success('提交成功。', '成功',function(){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }
                    }
                });
            }
        });
    })
</script>