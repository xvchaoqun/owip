<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>短信催促</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcsAdmin_msg" id="modalForm" method="post">
        <input type="hidden" name="type" value="${param.type}">
        <input type="hidden" name="stage" value="${param.stage}">
        <div class="form-group">
            <label class="col-xs-3 control-label">发送对象</label>
            <div class="col-xs-8 label-text">
                <div class="input-group">
                <c:forEach items="${PCS_ADMIN_TYPE_MAP}" var="_type">
                    <label>
                        <input required name="adminType" type="radio" class="ace" value="${_type.key}"/>
                        <span class="lbl" style="padding-right: 5px;"> ${_type.value}</span>
                    </label>
                </c:forEach>
                    </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">手机号码</label>
            <div class="col-xs-8">
                <input class="form-control" type="text" name="mobile" value="${uv.mobile}">
                <span class="help-block">* 发送给指定手机号码，留空则发给全部管理员</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">短信内容</label>
            <div class="col-xs-8">
                <textarea required class="form-control" type="text" name="msg" rows="8"></textarea>
                <span class="help-block">* 短信内容可修改</span>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定发送"/>
</div>
<script type="text/template" id="wyMsgTpl">
{{=adminType}}：您好！按照第十三次党员代表大会筹备工作领导小组的统一部署，两委委员候选人酝酿提名工作“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段于9月6日结束，目前尚未收到贵单位的报送材料。为了保证学校的整体进程不受影响，请务必今天完成报送。联系电话：58808302、58806879。谢谢！[系统短信，请勿回复]
</script>
<script type="text/template" id="prMsgTpl">
{{=adminType}}：您好！按照第十三次党员代表大会筹备工作领导小组的统一部署，代表选举“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段于9月6日结束，目前尚未收到贵单位的报送材料。为了保证学校的整体进程不受影响，请务必今天完成报送。联系电话：58808302、58806879。谢谢！[系统短信，请勿回复]
</script>
<script>
    var $msg = $("textarea[name=msg]", "#modalForm");
    function _resetMsg(){
        var adminType = "各位管理员";
        var type = $("input[name=adminType]:checked", "#modalForm").val();
        if(type=="${PCS_ADMIN_TYPE_SECRETARY}"){
            adminType = "各位书记";
        }else if(type=="${PCS_ADMIN_TYPE_VICE_SECRETARY}"){
            adminType = "各位副书记";
        }
        var msg = _.template($.trim($("${param.type==1?"#wyMsgTpl":"#prMsgTpl"}").html()))({adminType:adminType})
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