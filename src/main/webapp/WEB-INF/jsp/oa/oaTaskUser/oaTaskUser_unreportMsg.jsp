<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>短信催促未报送对象</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/oa/oaTaskUser_unreportMsg" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="taskId" value="${oaTask.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">未报送对象</label>

            <div class="col-xs-8 label-text">
                共${fn:length(taskUserViews)}人：<br/>
                <c:forEach items="${taskUserViews}" var="tuv" varStatus="vs">
                    ${tuv.realname}${!vs.last?"、":""}
                </c:forEach>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>短信内容</label>

            <div class="col-xs-8">
                <textarea required class="form-control" name="msg" rows="8"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 发送中，请稍后"
            data-success-text="发送成功" autocomplete="off">
        确定发送
    </button>
</div>
<script type="text/template" id="msgRetTpl">
    <div>共发送{{=ret.totalCount}}条短信，其中发送成功{{=ret.successCount}}条。</div>
    {{if(ret.failedUsers.length>0){}}
    <div>发送失败名单（共{{=ret.failedUsers.length}}人）：</div>
    <table class="table table-bordered table-unhover">
        <thead>
        <tr>
            <th>姓名</th>
            <th>手机号</th>
        </tr>
        </thead>
        <tbody>
        {{_.each(ret.failedUsers, function(p, idx){}}
        <tr>
            <td>{{=p.realname}}</td>
            <td>{{=p.mobile}}</td>
        </tr>
        {{});}}
        </tbody>
    </table>
    {{}}}
</script>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $btn.button("success").addClass("btn-success");

                        $("#modal").modal('hide');
                        //$("#jqGrid").trigger("reloadGrid");

                        //console.log(_.template($("#msgRetTpl").html())({ret: ret}))
                        SysMsg.info(_.template($("#msgRetTpl").html())({ret: ret}))
                    } else {
                        $btn.button('reset');
                    }
                }
            });
        }
    });
</script>