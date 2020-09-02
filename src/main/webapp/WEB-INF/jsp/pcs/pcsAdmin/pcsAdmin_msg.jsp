<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>短信催促</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcs/pcsAdmin_msg" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="type" value="${param.type}">
        <input type="hidden" name="stage" value="${param.stage}">
        <div class="form-group">
            <label class="col-xs-3 control-label">手机号码</label>
            <div class="col-xs-8">
                <input class="form-control" type="text" name="mobile" value="${uv.mobile}">
                <span class="help-block"><span class="star">*</span> 发送给指定手机号码，留空则发给全部管理员</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>短信内容</label>
            <div class="col-xs-8">
                <textarea required class="form-control" type="text" name="msg" rows="8"></textarea>
                <span class="help-block"><span class="star">*</span> 短信内容可修改</span>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定发送"/>
</div>
<script>
    var $msg = $("textarea[name=msg]", "#modalForm");
    function _resetMsg(){

        var msg = "${param.type==1?cm:getContentTpl('ct_pcs_wy_msg').content:cm:getContentTpl('ct_pcs_pr_msg').content}"
            .format("各位管理员", "${PCS_STAGE_MAP.get(cm:toByte(param.stage))}")
        $msg.val(msg);
    }

    $("input[name=adminType]", "#modalForm").change(function(){
        _resetMsg()
    })

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        //$("#jqGrid").trigger("reloadGrid");
                        SysMsg.info("共发送{0}条短信，其中发送成功{1}条".format(ret.totalCount, ret.successCount))
                    }
                }
            });
        }
    });
    //$('textarea.limited').inputlimiter();
</script>