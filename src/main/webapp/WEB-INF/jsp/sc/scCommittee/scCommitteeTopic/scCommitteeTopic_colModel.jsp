<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
      {label: '年份', name: 'year', width: 60, frozen: true},
      {
          label: '党委常委会', name: '_num', width: 210, formatter: function (cellvalue, options, rowObject) {
              //console.log(rowObject.holdDate)
              var _num = "党委常委会〔{0}〕号".format($.date(rowObject.holdDate, "yyyyMMdd"))
              if($.trim(rowObject.filePath)=='') return _num;
              return $.pdfPreview(rowObject.filePath, _num);
          }, frozen: true},
      {label: '党委常委会<br/>日期', name: 'holdDate', width: 95, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
      { label: '议题名称',name: 'name', width: 400, align:'left'},
      /*{
          label: '议题内容', name: '_content', width: 80, formatter: function (cellvalue, options, rowObject) {
          return ('<button class="popupBtn btn btn-primary btn-xs" ' +
          'data-url="${ctx}/sc/scCommitteeTopic_content?topicId={0}"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id);
            }
            },*/
      {
          label: '议题内容和<br/>讨论备忘',name: '_content', width: 90, formatter: function (cellvalue, options, rowObject) {
              return ('<button class="openView btn btn-primary btn-xs" ' +
                  'data-url="${ctx}/sc/scCommitteeTopic_content?topicId={0}"><i class="fa fa-search"></i> 查看</button>')
                  .format(rowObject.id);
          }
      },
      {
          label: '涉及单位', name: 'unitIds', width:180, align:'left', formatter: function (cellvalue, options, rowObject) {

              if($.isBlank(cellvalue)) return '--'

              var unitIds = cellvalue.split(",");
              var unitname = "-"
              var unitnames = []
              for(i in unitIds){
                  var unit = _cMap.unitMap[unitIds[i]];
                  if(unit!=undefined && unit.name!=undefined) {
                      unitnames.push(unit.name);
                  }
              }
              if(unitnames.length>0){
                  unitname = unitnames.join(",")
              }

              return unitname;
          }
      },
      { label: '干部选拔任用<br/>表决',name: 'hasVote', width:130, formatter: function (cellvalue, options, rowObject) {
              if(cellvalue)
                  return ('<button class="openView btn btn-xs btn-success" ' +
                      'data-url="${ctx}/sc/scCommitteeVote_au?topicId={0}"><i class="fa fa-edit"></i> 编辑</button>')
                          .format(rowObject.id)
                      + ('&nbsp;<button class="loadPage btn btn-info btn-xs" ' +
                          'data-url="${ctx}/sc/scCommittee?cls=3&topicId={0}"><i class="fa fa-search"></i> 查看</button>')
                          .format(rowObject.id);
              return '--'
          }},
      { label: '其他事项<br/>表决',name: 'hasOtherVote', width:120, formatter: function (cellvalue, options, rowObject) {
              if(cellvalue)
                  return ('<button class="popupBtn btn btn-xs btn-primary" ' +
                      'data-url="${ctx}/sc/scCommitteeOtherVote_au?topicId={0}"><i class="fa fa-edit"></i> 编辑</button>')
                          .format(rowObject.id)
                      + ('&nbsp;<button class="loadPage btn btn-success btn-xs" ' +
                          'data-url="${ctx}/sc/scCommittee?cls=4&topicId={0}"><i class="fa fa-search"></i> 查看</button>')
                          .format(rowObject.id);
              return '--'
          }},
      { label: '常委总数',name: 'committeeMemberCount', width: 80},
      { label: '应参会<br/>常委数',name: '_total', width: 70, formatter: function (cellvalue, options, rowObject) {
              return rowObject.count + rowObject.absentCount;
          }},
      {
          label: '实际参会<br/>常委数', name: 'count', width: 80, formatter: function (cellvalue, options, rowObject) {
              if(cellvalue==0) return '--'
              return ('<a href="javascript:;" class="popupBtn bolder" ' +
                  'data-url="${ctx}/sc/scCommitteeMember?committeeId={0}&isAbsent=0"><u>{1}</u></a>')
                  .format(rowObject.committeeId, cellvalue);
          }},
      {
          label: '请假<br/>常委数', name: 'absentCount', width: 70, formatter: function (cellvalue, options, rowObject) {
              if(cellvalue==0) return '--'
              return ('<a href="javascript:;" class="popupBtn bolder" ' +
                  'data-url="${ctx}/sc/scCommitteeMember?committeeId={0}&isAbsent=1"><u>{1}</u></a>')
                  .format(rowObject.committeeId, cellvalue);
          }},
      { label: '列席人',name: 'attendUsers', width: 140, formatter: function (cellvalue, options, rowObject) {
              if(rowObject.attendUsers==undefined) return '--';
              if(rowObject.attendUsers.length<=8) return cellvalue;
              return rowObject.attendUsers.substring(0,8) + "...";
          },cellattr:function(rowId, val, rowObject, cm, rdata) {
              if(rowObject.attendUsers!=undefined)
                  return "title='"+rowObject.attendUsers+"'";
          }},
      {label: '会议记录', name: 'logFile', width: 70, formatter: function (cellvalue, options, rowObject) {
              if(rowObject.logFile==undefined) return '--';
              return $.pdfPreview(rowObject.logFile, '会议记录', '查看');
          }},
      {label: '表决票', name: 'voteFilePath', width: 70, formatter: function (cellvalue, options, rowObject) {
              if(rowObject.voteFilePath==undefined) return '--';
              return $.pdfPreview(rowObject.voteFilePath, '表决票', '查看');
          }},
      { label: '备注',name: 'remark', width:300}
  ]
</script>
