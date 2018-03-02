<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>强制报送</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-unhover">
        <tr>
            <td class="bg-right">月份</td>
            <td>${cm:formatDate(pmdMonth.payMonth, "yyyy年MM月")}</td>
        </tr>
        <tr>
            <td class="bg-right">报送党委</td>
            <td>${partyMap.get(pmdParty.partyId).name}</td>
        </tr>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">返回</a>
    <button id="submitBtn" type="button" class="btn btn-success"><i class="fa fa-hand-paper-o"></i> 确定报送</button>
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
                <td>所在支部</td>
            </tr>
            </thead>
            <tbody>
            {{_.each(unsetDuepayMembers, function(m, idx){ }}
            <tr>
                <td>{{=m.user.realname}}</td>
                <td>{{=m.user.code}}</td>
                <td>{{=(_cMap.branchMap[m.branchId].name)}}</td>
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

        $.post("${ctx}/pmd/pmdParty_checkForceReport", {id: '${param.id}'}, function (ret) {
            if (ret.success) {
                $("#modal").modal('hide');
                if (ret.unsetDuepayMembers.length > 0) {
                    setTimeout(function(){//延时加载页面
                        $.showModal(_.template($("#itemListTpl").html())({unsetDuepayMembers: ret.unsetDuepayMembers}), 600)
                    },500);

                } else {
                    $.post("${ctx}/pmd/pmdParty_forceReport", {id: '${param.id}'}, function (ret) {

                        if (ret.success) {
                            SysMsg.info("报送成功。");
                            $("#jqGrid2").trigger("reloadGrid");
                        }
                    });
                }
            }
        });
    });
</script>