<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2016/2/28
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="passportType" value="${cm:getMetaType(passport.classId)}"/>
<div class="modal-header">
  <h3>申请签注(${passportType.name})</h3>
</div>
<div class="modal-body">
  <div class="row passport_apply">
    <div class="preview">
      <img data-src="${ctx}/report/passportSign?classId=${passportType.id}&userId=${_user.id}" src="${ctx}/img/loading.gif"
           onload="lzld(this)" />
    </div>

    <div class="info">

        <div class="center" style="font:bold 30px Verdana, Arial, Helvetica, sans-serif; padding: 50px;">
          <input ${param.type=='view'?"disabled checked":""} id="agree" type="checkbox" class="chkBox" style="width: 30px; height: 30px; margin: 0;"/> 信息已确认无误
        </div>

        <div class="center" style="margin-top: 20px">
            <button class="needSign btn btn-success btn-block" style="font-size: 30px">申请签注</button>
        </div>
        <div class="center" style="margin-top: 40px">
            <button class="hideView btn btn-default btn-block" style="font-size: 30px">返回</button>
        </div>
    </div>
  </div>
</div>
<c:if test="${param.type!='view'}">
<script>

    $(".needSign").click(function(){
        if($("#agree").is(":checked") == false){
            $('#agree').qtip({content:'请确认信息准确无误。',show: true, hide: 'unfocus'});
            return false;
        }

        $.post("${ctx}/user/abroad/passportDraw_self_sign_add",{id:${param.id}},function(ret){
            if(ret.success){
                SysMsg.success('申请成功。', '成功',function(){
                    page_reload();
                });
            }
        });
    });
</script>
    </c:if>

