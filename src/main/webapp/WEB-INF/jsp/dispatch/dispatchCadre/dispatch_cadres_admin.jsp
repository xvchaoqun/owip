<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="widget-box">
  <div class="widget-header">
    <h4 class="smaller">
      添加干部任免事项
    </h4>
  </div>
  <div class="widget-body">
    <div class="widget-main">
  <form class="form-horizontal" action="${ctx}/dispatchCadre_au" id="cadreForm" method="post">
    <div class="row">
      <div class="col-xs-6">
        <input type="hidden" name="dispatchId" value="${dispatch.id}">
        <div class="form-group">
          <label class="col-xs-3 control-label">类别</label>
          <div class="col-xs-6">
            <c:forEach var="DISPATCH_CADRE_TYPE" items="${DISPATCH_CADRE_TYPE_MAP}">
              <label class="label-text">
                <input required name="type" type="radio" class="ace" value="${DISPATCH_CADRE_TYPE.key}"
                <c:if test="${dispatchCadre.type==DISPATCH_CADRE_TYPE.key}">checked</c:if>/>
                <span class="lbl"> ${DISPATCH_CADRE_TYPE.value}</span>
              </label>
            </c:forEach>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">干部类型</label>
          <div class="col-xs-6">
            <select required data-rel="select2" name="cadreTypeId" data-placeholder="请选择干部类型">
              <option></option>
              <c:forEach var="cadreType" items="${cadreTypeMap}">
                <option value="${cadreType.value.id}">${cadreType.value.name}</option>
              </c:forEach>
            </select>
            <script type="text/javascript">
              $("#modalForm select[name=cadreTypeId]").val('${dispatchCadre.cadreTypeId}');
            </script>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">任免方式</label>
          <div class="col-xs-6">
            <select required data-rel="select2" name="wayId" data-placeholder="请选择任免方式">
              <option></option>
              <c:forEach var="way" items="${wayMap}">
                <option value="${way.value.id}">${way.value.name}</option>
              </c:forEach>
            </select>
            <script type="text/javascript">
              $("#modalForm select[name=wayId]").val('${dispatchCadre.wayId}');
            </script>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">任免程序</label>
          <div class="col-xs-6">
            <select required class="form-control" data-rel="select2" name="procedureId" data-placeholder="请选择任免程序">
              <option></option>
              <c:forEach var="procedure" items="${procedureMap}">
                <option value="${procedure.value.id}">${procedure.value.name}</option>
              </c:forEach>
            </select>
            <script type="text/javascript">
              $("#modalForm select[name=procedureId]").val('${dispatchCadre.procedureId}');
            </script>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">工作证号</label>
          <div class="col-xs-8">
            <select data-ajax-url="${ctx}/cadre_selects"
                    name="cadreId" data-placeholder="请选择干部">
              <option></option>
            </select>
          </div>
        </div>

        <div class="form-group">
          <label class="col-xs-3 control-label">姓名</label>
          <div class="col-xs-8">
            <input disabled class="form-control" type="text" name="_name">
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">职务</label>
          <div class="col-xs-8">
            <input required class="form-control" type="text" name="post" value="${dispatchCadre.post}">
          </div>
        </div>
      </div>
      <div class="col-xs-6">

        <div class="form-group">
          <label class="col-xs-3 control-label">职务属性</label>
          <div class="col-xs-6">
            <select name="postId" data-rel="select2" data-placeholder="请选择职务属性">
              <option></option>
              <c:forEach items="${postMap}" var="post">
                <option value="${post.key}">${post.value.name}</option>
              </c:forEach>
            </select>
            <script>
              $("#modalForm select[name=postId]").val('${dispatchCadre.postId}');
            </script>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">行政级别</label>
          <div class="col-xs-6">
            <select required class="form-control" data-rel="select2" name="adminLevelId" data-placeholder="请选择行政级别">
              <option></option>
              <c:forEach var="adminLevel" items="${adminLevelMap}">
                <option value="${adminLevel.value.id}">${adminLevel.value.name}</option>
              </c:forEach>
            </select>
            <script type="text/javascript">
              $("#modalForm select[name=adminLevelId]").val('${dispatchCadre.adminLevelId}');
            </script>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">单位类别</label>
          <div class="col-xs-6">
            <select class="form-control" name="_unitStatus" data-rel="select2" data-placeholder="请选择单位类别">
              <option></option>
              <option value="1">正在运转单位</option>
              <option value="2">历史单位</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">所属单位</label>
          <div class="col-xs-6">
            <select required data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                    name="unitId" data-placeholder="请选择单位">
              <option value="${unit.id}">${unit.name}</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">单位类型</label>
          <div class="col-xs-8">
            <input class="form-control" name="_unitType" type="text" disabled>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">备注</label>
          <div class="col-xs-8">
            <textarea class="form-control limited" name="remark" rows="5">${dispatchCadre.remark}</textarea>
          </div>
        </div>
      </div>
    </div>
    <div class="clearfix form-actions">
      <div class="col-md-offset-3 col-md-9">
        <button class="btn btn-info btn-sm" type="submit">
          <i class="ace-icon fa fa-check "></i>
          确定
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="btn btn-sm" type="reset">
          <i class="ace-icon fa fa-undo"></i>
          重置
        </button>
      </div>
    </div>
  </form>
    </div>
  </div>
</div>

<div style="padding-top: 20px">
  <table class="table table-actived table-striped table-bordered table-hover table-condensed">
    <thead>
    <tr>
      <th>类别</th>
      <th>任免方式</th>
      <th>任免程序</th>
      <th>姓名</th>
      <th>所属单位</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${dispatchCadres}" var="dispatchCadre" varStatus="st">
      <c:set value="${cm:getUserById(cadreMap.get(dispatchCadre.cadreId).userId)}" var="user"/>
      <tr>
        <td nowrap>${DISPATCH_CADRE_TYPE_MAP.get(dispatchCadre.type)}</td>
        <td nowrap>${wayMap.get(dispatchCadre.wayId).name}</td>
        <td nowrap>${procedureMap.get(dispatchCadre.procedureId).name}</td>
        <td nowrap>${user.realname}</td>
        <td nowrap>${unitMap.get(dispatchCadre.unitId).name}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<script>
  <c:if test="${empty dispatch}">
  $("select, input, button, textarea", "#cadreForm").prop("disabled", true);
  </c:if>
  $('textarea.limited').inputlimiter();
  $("#cadreForm button[type=submit]").click(function(){$("#cadreForm").submit();return false;});
  $("#cadreForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success:function(ret){
          if(ret.success){
            $("#dispatch-cadres-view").load("${ctx}/dispatch_cadres_admin?dispatchId=${param.dispatchId}");
            SysMsg.success('操作成功。', '成功');
          }
        }
      });
    }
  });
  $('[data-rel="select2"]').select2();
  $('[data-rel="tooltip"]').tooltip();
  register_cadre_select($('#cadreForm select[name=cadreId]'), $('#cadreForm input[name=_name]'));
  register_unit_select($('#cadreForm select[name=_unitStatus]'), $('#cadreForm select[name=unitId]'), $('#cadreForm input[name=_unitType]'));
</script>