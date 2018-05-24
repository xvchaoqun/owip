<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:if test="${empty dispatch.scDispatchId}">
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
        <input type="hidden" name="id" value="${dispatchCadre.id}">
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
              <c:import url="/metaTypes?__code=mc_dispatch_cadre_type"/>
            </select>
            <script type="text/javascript">
              $("#cadreForm select[name=cadreTypeId]").val('${dispatchCadre.cadreTypeId}');
            </script>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">任免方式</label>
          <div class="col-xs-6">
            <select data-rel="select2" name="wayId" data-placeholder="请选择任免方式">
              <option></option>
              <c:import url="/metaTypes?__code=mc_dispatch_cadre_way"/>
            </select>
            <script type="text/javascript">
              $("#cadreForm select[name=wayId]").val('${dispatchCadre.wayId}');
            </script>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">任免程序</label>
          <div class="col-xs-6">
            <select class="form-control" data-rel="select2" name="procedureId" data-placeholder="请选择任免程序">
              <option></option>
              <c:import url="/metaTypes?__code=mc_dispatch_cadre_procedure"/>
            </select>
            <script type="text/javascript">
              $("#cadreForm select[name=procedureId]").val('${dispatchCadre.procedureId}');
            </script>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">工作证号</label>
          <div class="col-xs-8">
            <select required data-ajax-url="${ctx}/cadre_selects?type=0"
                    name="cadreId" data-placeholder="请选择干部">
              <option value="${dispatchCadre.cadre.id}">${dispatchCadre.user.code}</option>
            </select>
          </div>
        </div>

        <div class="form-group">
          <label class="col-xs-3 control-label">姓名</label>
          <div class="col-xs-8">
            <input disabled class="form-control" type="text" name="_name" value="${dispatchCadre.user.realname}">
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
            <select required name="postId" data-rel="select2" data-placeholder="请选择职务属性">
              <option></option>
              <c:import url="/metaTypes?__code=mc_post"/>
            </select>
            <script>
              $("#cadreForm select[name=postId]").val('${dispatchCadre.postId}');
            </script>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">行政级别</label>
          <div class="col-xs-6">
            <select required class="form-control" data-rel="select2" name="adminLevelId" data-placeholder="请选择行政级别">
              <option></option>
              <c:import url="/metaTypes?__code=mc_admin_level"/>
            </select>
            <script type="text/javascript">
              $("#cadreForm select[name=adminLevelId]").val('${dispatchCadre.adminLevelId}');
            </script>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">单位类别</label>
          <div class="col-xs-6">
            <select required class="form-control" name="_unitStatus" data-rel="select2" data-placeholder="请选择单位类别">
              <option></option>
              <option value="1" ${dispatchCadre.unit.status==1?"selected":""}>正在运转单位</option>
              <option value="2" ${dispatchCadre.unit.status==2?"selected":""}>历史单位</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">所属单位</label>
          <div class="col-xs-6">
            <select required data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                    name="unitId" data-placeholder="请选择单位">
              <option value="${dispatchCadre.unit.id}">${dispatchCadre.unit.name}</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">单位类型</label>
          <div class="col-xs-8">
            <input class="form-control" name="_unitType" type="text" disabled value="${dispatchCadre.unit.unitType.name}">
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
    <div class="clearfix form-actions center">
        <button class="btn ${empty dispatchCadre?'btn-success':'btn-info'} btn-sm" type="submit">
          <i class="ace-icon fa ${empty dispatchCadre?"fa-plus":"fa-edit"} "></i>
          ${empty dispatchCadre?"添加":"修改"}
        </button>
        <c:if test="${not empty dispatchCadre}">
        &nbsp; &nbsp; &nbsp;
        <button class="btn btn-default btn-sm" onclick="_update()">
          <i class="ace-icon fa fa-undo"></i>
          返回添加
        </button>
        </c:if>
    </div>
  </form>
    </div>
  </div>
</div>
</c:if>
<div style="padding-top: 20px">
  <table class="table table-actived table-striped table-bordered table-hover">
    <thead>
    <tr>
      <th style="width: 50px">类别</th>
      <th style="width: 80px">任免方式</th>
      <th style="width: 80px">任免程序</th>
      <th style="width: 80px">姓名</th>
      <th>所属单位</th>
      <th>
<c:if test="${not empty dispatch.scDispatchId}">
        <button style="margin-right: 10px;top: -5px;"
                class="confirm btn btn-primary btn-xs" type="button"
                data-msg="确定同步?"
                data-callback="_reload"
                data-url="${ctx}/sc/scDispatch_snyc?dispatchId=${dispatch.id}">
          <i class="fa fa-refresh"></i> 同步“文件起草签发”
        </button>
  </c:if>
      </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${dispatchCadres}" var="dispatchCadre" varStatus="st">
      <c:set value="${cm:getUserById(cm:getCadreById(dispatchCadre.cadreId).userId)}" var="user"/>
      <tr>
        <td nowrap>${DISPATCH_CADRE_TYPE_MAP.get(dispatchCadre.type)}</td>
        <td nowrap>${cm:getMetaType(dispatchCadre.wayId).name}</td>
        <td nowrap>${cm:getMetaType(dispatchCadre.procedureId).name}</td>
        <td nowrap>${user.realname}</td>
        <td nowrap>${unitMap.get(dispatchCadre.unitId).name}</td>
        <td>
          <a href="javascript:void(0)" onclick="_update(${dispatchCadre.id})">修改</a>
        </td>
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
            //SysMsg.success('操作成功。', '成功');
          }
        }
      });
    }
  });
  function _reload(){
    SysMsg.info("同步成功。");
    $("#dispatch-cadres-view").load("${ctx}/dispatch_cadres_admin?dispatchId=${param.dispatchId}");
  }
  function _update(id){
    $("#dispatch-cadres-view").load("${ctx}/dispatch_cadres_admin?dispatchId=${param.dispatchId}&id="+ $.trim(id));
  }

  $('[data-rel="select2"]').select2();
  $('[data-rel="tooltip"]').tooltip();

  var $selectCadre = $.register.user_select($('#cadreForm select[name=cadreId]'),function(state){ var $state = state.text;
    if(state.code!=undefined && state.code.length>0)
      $state = state.code;
    return $state;
  });
  $selectCadre.on("change",function(){
    //console.log($(this).select2("data")[0])
    var name = $(this).select2("data")[0]['text']||'';
    $('#cadreForm input[name=_name]').val(name);
  });
  $.register.unit_select($('#cadreForm select[name=_unitStatus]'), $('#cadreForm select[name=unitId]'), $('#cadreForm input[name=_unitType]'));
</script>