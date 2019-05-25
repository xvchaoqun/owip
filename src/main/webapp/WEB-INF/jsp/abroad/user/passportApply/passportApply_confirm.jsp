<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row passport_apply">
    <div class="preview">
        <img data-src="${ctx}/report/passportApply?classId=${param.classId}&userId=${cadre.userId}&id=${param.id}"
             src="${ctx}/img/loading.gif"
             onload="lzld(this)" />
    </div>
    <div class="info">
        <div class="alert alert-warning" style="font-size: 20px">
            ${cm:getHtmlFragment('hf_abroad_passport_apply_note').content}
        </div>
        <c:if test="${param.type!='view'}">
        <div class="center" style="font:bold 30px Verdana, Arial, Helvetica, sans-serif; padding: 50px;">
            <input id="agree" type="checkbox" class="chkBox" style="width: 30px; height: 30px; margin: 0;"/> 信息已确认无误
        </div>
        <div class="center" style="margin-top: 20px">
        <button id="submit" class="btn btn-success btn-block" style="font-size: 30px">提交申请</button>
        </div>
        <div class="center" style="margin-top: 40px">
            <button id="back" class="btn btn-default btn-block" style="font-size: 30px">返回选择证件</button>
        </div>
        </c:if>
        <c:if test="${param.type=='view'}">
            <div class="center" style="margin-top: 40px">
                <button class="hideView btn btn-default btn-block" style="font-size: 30px">返回</button>
            </div>
            </c:if>
    </div>
</div>
<script>
    $("#back").click(function(){
        $("#apply-content").load("${ctx}/user/abroad/passportApply_select?cadreId=${param.cadreId}&auth=${param.auth}");
    });

    $("#submit").click(function(){
        if($("#agree").is(":checked") == false){
            $('#agree').qtip({content:'请确认信息准确无误。',show: true, hide: 'unfocus'});
            return false;
        }

        $.post("${ctx}/user/abroad/passportApply_apply",{classId:"${param.classId}", cadreId:"${param.cadreId}"},function(ret){
            if(ret.success){
                <c:if test="${param.auth!='admin'}">
                $.loadModal("${ctx}/abroad/shortMsg_view?id={0}&type=passportApplySubmit".format(ret.applyId));
                $.loadPage({url:"${ctx}/user/abroad/passport?type=2"});
                </c:if>
                <c:if test="${param.auth=='admin'}">
                $.hideView();
                </c:if>
            }
        });
    });
</script>