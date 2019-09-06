<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/abroad/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>取消集中管理</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/abroad/passport_abolish" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${passport.id}">
        <input type="hidden" name="cancelType">
        <div class="form-group">
            <label class="col-xs-3 control-label" style="line-height: 60px">请选择类别</label>
            <div class="col-xs-8"  style="font-size: 15px;">
                <input type="checkbox" class="big" value="1"/> 证件作废
                <div style="padding-top: 20px">
                <input type="checkbox"  class="big" value="2"/> 其他
                <input  type="text" name="cancelTypeOther" placeholder="请输入其他类别">
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <div class="text-primary bolder text-left">
        其他取消集中管理情况说明：<br/>
        <ul>
            <li>
                 一、证件过期。系统每天会进行证件过期扫描，过期的证件将自动转移到取消集中管理证件库；
            </li>
            <li>
                二、干部离任。在系统中进行干部离任操作时，该干部相关的集中管理证件会自动转移至取消集中管理证件库：<br/>
                 1、如果证件为“未借出”，则转移到“取消集中管理（未确认）”。<br/>
                 2、如果证件“已借出”，则转移到“取消集中管理证件（已确认）”中，最后一个字段“状态”为“免职前已领取”。<br/>
                 同时借出记录的“实交组织部日期”为“已免职”。
            </li>
        </ul>
    </div>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
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
                $("#modal input[name=cancelType]").val('${ABROAD_PASSPORT_CANCEL_TYPE_ABOLISH}');
            }else if(type==2){
                $("#modal input[name=cancelType]").val('${ABROAD_PASSPORT_CANCEL_TYPE_OTHER}');
                var cancelTypeOther = $.trim($("#modal input[name=cancelTypeOther]").val());
                if(cancelTypeOther==''){
                    //SysMsg.info("请输入其他类别", "", function(){
                        $("#modal input[name=cancelTypeOther]").val('').focus();
                    //});

                    return;
                }
            }else {
                SysMsg.warning("请选择类别");
                return;
            }

            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        if(${passport.isLent}){
                            $.hashchange("status=4");// 借出状态下，转移到 已确认取消集中管理
                        }else{
                            $.hashchange("status=2"); // 未借出状态下，转移到 未确认取消集中管理
                        }
                    }
                }
            });
        }
    });
</script>