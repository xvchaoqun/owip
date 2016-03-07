<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2016/2/28
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="passportType" value="${cm:getMetaType('mc_passport_type', passport.classId)}"/>
<div class="modal-header">
  <h3>申请签注(${passportType.name})</h3>
</div>
<div class="modal-body">
  <div class="row passport_apply">
    <div class="preview">
      <iframe src="/report/passportSign?classId=${passportType.id}&userId=${_user.id}" width="595" height="842" frameborder="0"  border="0" marginwidth="0" marginheight="0"></iframe>
    </div>

    <div class="info">

        <div class="center" style="font:bold 30px Verdana, Arial, Helvetica, sans-serif; padding: 50px;">
          <input ${param.type=='view'?"disabled checked":""} id="_agree" type="checkbox" class="chkBox" style="width: 30px; height: 30px; margin: 0;"/> 信息已确认无误
        </div>

        <div class="center" style="margin-top: 20px">
            <button class="needSign btn btn-success btn-block" style="font-size: 30px">申请签注</button>
        </div>
        <div class="center" style="margin-top: 40px">
            <button class="closeView btn btn-default btn-block" style="font-size: 30px">取消</button>
        </div>
    </div>
  </div>
</div>
<c:if test="${param.type!='view'}">
<script>

    $(".needSign").click(function(){
        if($("#_agree").is(":checked") == false){
            $('#_agree').qtip({content:'请确认信息准确无误。',show: true, hide: 'unfocus'});
            return false;
        }

        $("#signBtn").html('<span class="label label-success" style="vertical-align: 4px; margin-left: 10px">已申请办理签注</span>');
        $("input[name=needSign]").val(1);

        var $this = $(this);
        $("#item-content").fadeOut("fast",function(){
                $("#body-content").show();
        });
    });
</script>
    </c:if>

