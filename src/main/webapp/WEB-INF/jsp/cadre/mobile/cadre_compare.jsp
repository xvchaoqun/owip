<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="center">
  <div class="select">
  <select data-rel="select2-aj1ax"
          data-ajax-url="${ctx}/m/cadre_selects?types=${CADRE_STATUS_MIDDLE}" data-width="300"
          name="cadreId" data-placeholder="请输入账号或姓名或学工号">
    <option></option>
  </select>
  </div>

  <div class="select">
  <select data-rel="select2-ajax"
          data-ajax-url="${ctx}/m/cadre_selects?types=${CADRE_STATUS_MIDDLE}" data-width="300"
          name="cadreId" data-placeholder="请输入账号或姓名或学工号">
    <option></option>
  </select>
  </div>

  <div class="select">
  <select data-rel="select2-ajax"
          data-ajax-url="${ctx}/m/cadre_selects?types=${CADRE_STATUS_MIDDLE}" data-width="300"
          name="cadreId" data-placeholder="请输入账号或姓名或学工号">
    <option></option>
  </select>
  </div>

  <div class="select">
  <select data-rel="select2-ajax"
          data-ajax-url="${ctx}/m/cadre_selects?types=${CADRE_STATUS_MIDDLE}" data-width="300"
          name="cadreId" data-placeholder="请输入账号或姓名或学工号">
    <option></option>
  </select>
  </div>

  <div class="select">
  <select data-rel="select2-ajax"
          data-ajax-url="${ctx}/m/cadre_selects?types=${CADRE_STATUS_MIDDLE}" data-width="300"
          name="cadreId" data-placeholder="请输入账号或姓名或学工号">
    <option></option>
  </select>
  </div>

</div>
<button id="compare" class="btn btn-success btn-block">
  <i class="ace-icon fa fa-exchange"></i>
</button>
<style>
  .select{
    padding-bottom: 20px;
  }
</style>
<script>
  $.register.ajax_select($('select[name=cadreId]'),{allowClear: false,
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
    $.loadView("${ctx}/m/cadre_compare_result?cadreIds[]="+cadreIds);
  });
</script>