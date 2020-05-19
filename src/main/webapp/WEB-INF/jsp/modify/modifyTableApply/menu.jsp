<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp"%>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <shiro:hasAnyRoles name="${ROLE_CADRE_CJ},${ROLE_CADRE_DP},${ROLE_CADRE_KJ}">
    <li class="${cls==0?"active":""}">
      <a href="javascript:;" class="hashchange" data-querystr="cls=0&module=${module}"><i class="fa fa-th"></i> ${MODIFY_TABLE_APPLY_MODULE_MAP.get(cm:toByte(module%100))}</a>
    </li>
  </shiro:hasAnyRoles>
  <li class="${cls==1?"active":""}">
    <a href="javascript:;" class="hashchange" data-querystr="cls=1&module=${module}"><i class="fa fa-edit"></i> 信息修改</a>
  </li>
  <li class="${cls==2?"active":""}">
    <a href="javascript:;" class="hashchange" data-querystr="cls=2&module=${module}"><i class="fa fa-check"></i> 审核完成</a>
  </li>
  <shiro:hasRole name="${ROLE_ADMIN}">
    <li class="${cls==3?"active":""}">
      <a href="javascript:;" class="hashchange" data-querystr="cls=3&module=${module}"><i class="fa fa-times"></i> 已删除</a>
    </li>
  </shiro:hasRole>
</ul>
<script>
  <c:if test="${cls==0}">
    $(document).off("change", ".cadre-info-check").on("change", ".cadre-info-check", function (e) {
          var $this = $(this);
          var name = $this.data("name");
          var isChecked = $this.prop("checked");

          $.post("${ctx}/cadreInfoCheck_update?cadreId=${cadre.id}&toApply=1", {
              name: name,
              isChecked: isChecked
          }, function (ret) {
              //console.log(name + ":" + isChecked)
              if (ret.success) {
                  $this.tip({content: '操作成功'});
                  $("button.btn").prop("disabled", isChecked);
              } else {
                  $this.prop("checked", !isChecked);
              }
          })
      })
    $(function(){
      <c:if test="${!canUpdate}">
      $("button.btn").prop("disabled", true);
      </c:if>
      $(".cadre-info-check").prop("checked", ${!canUpdate});
      <c:if test="${!canUpdateInfoCheck && canUpdate}">
      $(".cadre-info-check").prop("disabled", true);
      </c:if>
    })
  </c:if>
</script>