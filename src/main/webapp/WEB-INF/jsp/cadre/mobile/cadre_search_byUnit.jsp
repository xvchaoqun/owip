<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="ace-settings-container" id="ace-settings-container">
  <div class="btn btn-app btn-xs btn-info ace-settings-btn" id="ace-settings-btn">
    <i class="ace-icon fa fa-search bigger-130"></i>
  </div>
  <div class="ace-settings-box clearfix" id="ace-settings-box">
    <c:if test="${not empty param.unitId}">
    <c:set var="unit" value="${unitMap.get(cm:toInt(param.unitId))}"/>
    </c:if>
    <div style="height: 35px;margin: 15px 0px;">
      <select data-rel="select2-ajax"
              data-ajax-url="${ctx}/m/unit_selects?status=${UNIT_STATUS_RUN}" data-width="300"
              name="unitId" data-placeholder="请输入单位名称">
        <option value="${unit.id}">${unit.name}</option>
      </select>
    </div>
  </div>
</div>
<div id="result" style="min-height: 300px"></div>
<%--<button class="closeView btn btn-success btn-block">
  <i class="ace-icon fa fa-backward"></i>
  返回
</button>--%>
<script src="/assets/js/ace/ace.settings.js"></script>
<script>

  $("#result").load("${ctx}/m/unit_cadre_info?unitId=${param.unitId}");
  var $select = $.register.ajax_select($('select[name=unitId]'),{allowClear: false});
  $select.on("change",function(){
    $("#result").load("${ctx}/m/unit_cadre_info?unitId="+ $(this).val());
    $('#ace-settings-box').toggleClass('open');
  })
</script>