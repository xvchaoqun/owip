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
      <img data-src="${ctx}/report/passportSign?classId=${passportType.id}&userId=${passport.user.id}&id=${param.id}"
           src="${ctx}/img/loading.gif"
           onload="lzld(this)" />
    </div>

    <div class="info">
        <div class="alert alert-warning" style="font-size: 20px">
            请仔细核对申办人姓名、身份证号、所在单位和职务。若信息有误，请联系组织部。<br/>
            联系人：杨学玉、龙海明<br/>
            联系电话：58808302、58806879。
        </div>
        <div class="center" style="font:bold 30px Verdana, Arial, Helvetica, sans-serif; padding: 50px;">
          <input ${param.type=='view'?"disabled checked":""} id="agree" type="checkbox" class="chkBox" style="width: 30px; height: 30px; margin: 0;"/> 信息已确认无误
        </div>
        <c:if test="${param.type!='view'}">
        <div class="center" style="margin-top: 20px">
          <button id="needSign" class="btn btn-success btn-block" style="font-size: 30px">申请签注</button>
        </div>
        <div class="center" style="margin-top: 40px">
          <button id="notSign" class="btn btn-info btn-block" style="font-size: 30px">不需要签注</button>
        </div>
            <c:if test="${param.auth!='admin'}">
        <div class="center" style="margin-top: 40px">
            <button  data-url="${ctx}/user/passportDraw_self_select?applyId=${param.applyId}"
                    class="openView btn btn-default btn-block" style="font-size: 30px">返回选择证件</button>
        </div>
            </c:if>
        </c:if>
        <c:if test="${param.type=='view'}">
            <div class="center" style="margin-top: 40px">
                <c:if test="${passportDraw.status==PASSPORT_DRAW_STATUS_PASS}">
                <button class="printBtn btn btn-info btn-block" style="font-size: 30px">打印</button>
                </c:if>
                <button class="closeView btn btn-default btn-block" style="margin-top:20px;font-size: 30px">返回</button>
            </div>
        </c:if>
        <div class="well" style="margin-top: 40px; font-size: 16pt">
            <i class="fa fa-info-circle"></i> 推荐使用<a href="http://rj.baidu.com/soft/detail/14744.html?ald" target="_blank">谷歌浏览器</a>（点击下载）进行浏览和打印
        </div>
    </div>
  </div>
</div>

<script>
<c:if test="${param.type!='view'}">
    $("#notSign").click(function(){
        if($("#agree").is(":checked") == false){
            $('#agree').qtip({content:'请确认信息准确无误。',show: true, hide: 'unfocus'});
            return false;
        }

        $("#item-content").load("${ctx}/user/passportDraw_self_confirm?auth=${param.auth}&cadreId=${param.cadreId}&applyId=${param.applyId}&passportId=${passport.id}&sign=0");
    });
    $("#needSign").click(function(){
        if($("#agree").is(":checked") == false){
            $('#agree').qtip({content:'请确认信息准确无误。',show: true, hide: 'unfocus'});
            return false;
        }

        $("#item-content").load("${ctx}/user/passportDraw_self_confirm?auth=${param.auth}&cadreId=${param.cadreId}&applyId=${param.applyId}&passportId=${passport.id}&sign=1");
    });
</c:if>
<c:if test="${param.type=='view'}">
    $(".printBtn").click(function () {
        $.print("${ctx}/report/passportSign?id=${param.id}&format=pdf");
    });
</c:if>
</script>