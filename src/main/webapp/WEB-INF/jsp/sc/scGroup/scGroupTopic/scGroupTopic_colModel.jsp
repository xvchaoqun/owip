<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
      {label: '年份', name: 'year', width: 60, frozen: true},
      {
          label: '干部小组会', name: '_num', width: 200, formatter: function (cellvalue, options, rowObject) {
              var _num = "干部小组会〔{0}〕号".format($.date(rowObject.holdDate, "yyyyMMdd"))
              //console.log(_num + " rowObject.groupFilePath="+rowObject.groupFilePath)
              if($.isBlank(rowObject.groupFilePath)) return _num;
              return $.pdfPreview(rowObject.groupFilePath, _num);
          }, frozen: true},
      {label: '干部小组会<br/>日期', name: 'holdDate', width: 95, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
      { label: '议题名称',name: 'name', width: 350, align:'left'},
      {
          label: '议题内容<br/>和讨论备忘',name: '_content', width: 95, formatter: function (cellvalue, options, rowObject) {
              return ('<button class="openView btn btn-primary btn-xs" ' +
                  'data-url="${ctx}/sc/scGroupTopic_content?topicId={0}"><i class="fa fa-search"></i> 查看</button>')
                  .format(rowObject.id);
          }
      },
      {label: '议题类型', name: 'type', width:180, align:'left', formatter: $.jgrid.formatter.MetaType},
      {
          label: '涉及单位', name: 'unitIds', width:180, align:'left', formatter: function (cellvalue, options, rowObject) {

              if($.isBlank(cellvalue)) return '--'

              var unitIds = cellvalue.split(",");
              var unitname = "-"
              for(i in unitIds){
                  var unit = _cMap.unitMap[unitIds[i]];
                  if(unit!=undefined && unit.name!=undefined) {
                      unitname = unit.name;
                      break;
                  }
              }
              if(unitIds.length>1){
                  unitname += "，..."
              }

              return unitIds.length>1?('<a href="javascript:;" class="popupBtn" ' +
                  'data-url="${ctx}/sc/scGroupTopicUnit?topicId={0}">{1}</a>')
                  .format(rowObject.id, unitname):unitname;
          }
      },
      {
          label: '参会人', name: 'users', width:280, align:'left', formatter: function (cellvalue, options, rowObject) {
              if($.isBlank(cellvalue)) return '--'
              return $.map(cellvalue, function(u){
                  return u.realname;
              })
          }
      },
      { label: '列席人',name: 'attendUsers'},
      {label: '会议记录', name: 'logFile', formatter: function (cellvalue, options, rowObject) {
              if(rowObject.logFile==undefined) return '--';
              return $.pdfPreview(rowObject.logFile, '会议记录', '查看');
          }},
      { label: '备注',name: 'remark', width:300}
  ]
</script>
