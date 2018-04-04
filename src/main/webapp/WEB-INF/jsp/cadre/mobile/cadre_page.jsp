<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<select data-rel="select2-ajax"
        data-ajax-url="${ctx}/m/cadre_selects?status=${status}" data-width="100%"
        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
    <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
</select>
<div id="result" style="padding-top: 15px;"></div>
<script>

  $("#result").load("${ctx}/m/cadre_info");
  var $select = $.register.user_select($('select[name=cadreId]'));
  $select.on("change",function(){
      $("#result").load("${ctx}/m/cadre_info?cadreId="+ $(this).val());
  })
</script>