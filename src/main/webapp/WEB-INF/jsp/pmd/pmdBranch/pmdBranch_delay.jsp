<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量延迟缴费</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-unhover">
        <tr>
            <td class="bg-right">党支部名称</td>
            <td>${branchMap.get(pmdBranch.branchId).name}</td>
        </tr>
        <tr>
            <td class="bg-right"> 延迟缴费原因</td>
            <td>
                <textarea id="delayReason" class="limited" maxlength="100" style="width: 100%"></textarea>
            </td>
        </tr>
    </table>
</div>
<div class="modal-footer">
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
</div>
<script type="text/template" id="itemListTpl">
    <div class="modal-header">
        <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
        <h3>未设置应缴额度的党员</h3>
    </div>
    <div class="modal-body">
        <table class="table table-striped table-bordered table-condensed table-unhover2">
            <thead>
            <tr>
                <td width="100">姓名</td>
                <td width="100">工号</td>
            </tr>
            </thead>
            <tbody>
            {{_.each(unsetDuepayMembers, function(m, idx){ }}
            <tr>
                <td>{{=m.user.realname}}</td>
                <td>{{=m.user.code}}</td>
            </tr>
            {{});}}
            </tbody>
        </table>
    </div>
    <div class="modal-footer">
        <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
    </div>
</script>
<script>
    $("#submitBtn").click(function () {
        var $this = $(this);
        var delayReason = $.trim($("#delayReason").val());
        $.post("${ctx}/pmd/pmdBranch_checkDelay", {id: '${param.id}'}, function (ret) {
            if (ret.success) {
                $("#modal").modal('hide');
                if (ret.unsetDuepayMembers.length > 0) {
                    setTimeout(function(){//延时加载页面
                        $.showModal(_.template($("#itemListTpl").html())({unsetDuepayMembers: ret.unsetDuepayMembers}), 600)
                    },500);

                } else {
                    $.post("${ctx}/pmd/pmdBranch_delay",{id:${pmdBranch.id}, delayReason:delayReason},function(ret){
                        if(ret.success){
                            $("#modal").modal('hide');
                            $("#jqGrid2").trigger("reloadGrid");
                        }
                    });

                }
            }
        });
    });
    $('textarea.limited').inputlimiter();
</script>