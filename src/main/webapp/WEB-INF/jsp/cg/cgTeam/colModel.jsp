<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="CG_TEAM_TYPE_MAP" value="<%=CgConstants.CG_TEAM_TYPE_MAP%>" />
<script>
    var jgridName = ${cm:toJSONObject(empty param.fid?"#jqGrid":"#jqGrid2")};
    var team_type = ${cm:toJSONObject(CG_TEAM_TYPE_MAP)};
  var colModel = [
      { label: '名称',name: 'name', formatter:function(cellvalue, options, rowObject){
              var fid = rowObject.fid;
              return ('<a href="javascript:;" class="openView" ' +
                  'data-hide-el="{0}" data-load-el="{1}" ' +
                  'data-url="${ctx}/cg/cgTeam_view?teamId={2}&fid={4}">{3}</a>')
                  .format(
                      fid==undefined?"#body-content":"#body-content-view",
                      fid==undefined?"#body-content-view":"#body-content-view2",
                      rowObject.id, cellvalue,fid==undefined?'':fid)},width:300,align:'left'
      },
      <c:if test="${!_query}">
      { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
          formatoptions:{grid:jgridName, url:'${ctx}/cg/cgTeam_changeOrder?fid=${param.fid}'}},
      </c:if>
      { label: '类型',name: 'type', formatter: function (cellvalue, options, rowObject) {
              return team_type[cellvalue];
          }},
      { label: '类别',name: 'category',width: 200,formatter: $.jgrid.formatter.MetaType},
      <c:if test="${empty param.fid}">
      { label: '概况',name: 'cgTeamBase',width: 80,formatter:function(cellvalue, options, rowObject){

              return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/cg/cgTeam_base?id={0}"><i class="fa fa-search"></i> {1}</button>'
                  .format(rowObject.id, '查看');
          }},
      </c:if>

      { label: '是否需要调整',name: 'countNeedAdjust', formatter: function (cellvalue, options, rowObject) {

              return cellvalue != 0?"<span class='badge badge-danger'>"+cellvalue+"</span>":"--";
          }},
      { label: '挂靠单位',name: 'unit.name',width:250,align:'left'},
      { label: '办公室主任',name: 'user.realname'},
      { label: '联系方式',name: 'phone',width: 130},
      { label: '备注',name: 'remark',width: 200}
  ]
</script>
