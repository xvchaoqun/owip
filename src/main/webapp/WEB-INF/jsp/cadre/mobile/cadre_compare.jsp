<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="back-btn">
  <a href="javascript:;" class="hideView"><i class="fa fa-reply"></i> 返回</a>
</div>
<div class="center">
  <c:forEach items="${cadres}" var="cadre">
    <div class="select">
      <select data-rel="select2-aj1ax"
              data-ajax-url="${ctx}/m/cadre_selects?types=${CADRE_STATUS_MIDDLE}" data-width="300"
              name="cadreId" data-placeholder="请输入账号或姓名或学工号">
        <option value="${cadre.id}">${cadre.realname}-${cadre.code}-${cadre.unitName}</option>
      </select>
    </div>
  </c:forEach>
  <c:forEach begin="${fn:length(cadres)}" end="4">
  <div class="select">
  <select data-rel="select2-ajax"
          data-ajax-url="${ctx}/m/cadre_selects?types=${CADRE_STATUS_MIDDLE}" data-width="300"
          name="cadreId" data-placeholder="请输入账号或姓名或学工号">
    <option></option>
  </select>
  </div>
  </c:forEach>
</div>
<button id="compare" class="btn btn-success btn-block">
  <i class="ace-icon fa fa-exchange"></i> 确认
</button>
<%--<div class="space-4"></div>
<div class="space-4"></div>
<button class="hideView btn btn-info btn-block">
  <i class="ace-icon fa fa-backward"></i>
  返回
</button>--%>
<style>
  .select{
    padding-bottom: 20px;
  }
  .select2-container{
    text-align: left;
  }
</style>
<script>
  $.register.user_select($('select[name=cadreId]'),{allowClear: false,
    templateResult: $.register.formatState,
    templateSelection: $.register.formatState});

  $("#compare").click(function(){
    var cadreIds = [];
    $.each($('select[name=cadreId]'), function(i, select){
      if($(select).val()!='')
        cadreIds.push($(select).val());
    })
    if(cadreIds.length==0) return ;
    //console.log(cadreIds)
    $.openView("${ctx}/m/cadre_compare_result?cadreIds[]="+cadreIds);
  });
</script>