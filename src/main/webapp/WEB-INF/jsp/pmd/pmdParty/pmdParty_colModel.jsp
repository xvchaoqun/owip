<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
    {label: '月份', name: 'payMonth', formatter: 'date', formatoptions: {newformat: 'Y年m月'}, frozen: true},
    { label: '报送',name: '_report', width:80, formatter: function (cellvalue, options, rowObject) {
      <%--<c:if test="${cls==1}">
      if (rowObject.hasReport) return '<span class="text-success">已报送</span>'
      return ('<button class="popupBtn btn btn-success btn-xs" ' +
      'data-url="${ctx}/pmd/pmdParty_report?id={0}" {1}><i class="fa fa-hand-paper-o"></i> 报送</button>')
              .format(rowObject.id, rowObject.canReport ? '' : 'disabled');
      </c:if>
      <c:if test="${cls==2}">
      return (rowObject.hasReport) ? '<span class="text-success">已报送</span>' : '<span class="text-danger">未报送</span>'
      </c:if>--%>
      if (rowObject.hasReport) return '<span class="text-success">已报送</span>'
      return ('<button class="popupBtn btn btn-success btn-xs" ' +
      'data-url="${ctx}/pmd/pmdParty_report?id={0}&cls={2}" {1}><i class="fa fa-hand-paper-o"></i> 报送</button>')
              .format(rowObject.id, rowObject.canReport ? '' : 'disabled', '${cls}');

    }, frozen: true},
    { label: '报表',name: '_table', width:80, frozen: true, formatter: function (cellvalue, options, rowObject) {
      if(rowObject.hasReport){
        return ('<button class="downloadBtn btn btn-success btn-xs" ' +
        'data-url="${ctx}/pmd/pmdParty_export?id={0}"><i class="fa fa-file-excel-o"></i> 报表</button>')
                .format(rowObject.id);
      }
      return '-'
    }},
    { label: '党委名称',name: 'partyName', width:400, align:'left', frozen: true},
    <c:if test="${cls==1||cls==2}">
    { label: '各支部<br/>详情',name: '_branchs', width:80, formatter: function (cellvalue, options, rowObject) {
      if(rowObject.isDirectBranch){
        return ('<button class="openView btn btn-primary btn-xs" ' +
        'data-url="${ctx}/pmd/pmdMember?cls=2&partyId={0}&monthId={1}&backToPartyList=${cls==2?1:0}"><i class="fa fa-search"></i> 详情</button>')
                .format(rowObject.partyId, rowObject.monthId);
      }else{
        return ('<button class="openView btn btn-primary btn-xs" ' +
        'data-url="${ctx}/pmd/pmdBranch?cls=2&monthId={0}&partyId={1}&backToPartyList=${cls==2?1:0}"><i class="fa fa-search"></i> 详情</button>')
                .format(rowObject.monthId, rowObject.partyId);
      }
    }, frozen: true},
    </c:if>
    { label: '党支部数',name: 'branchCount', width:80, formatter: function (cellvalue, options, rowObject) {
      if(rowObject.isDirectBranch) return '1'
      return (rowObject.hasReport)?cellvalue:rowObject.r.branchCount;
    }},
    { label: '已报送<br/>党支部数',name: 'hasReportCount', width:80, formatter: function (cellvalue, options, rowObject) {
      if(rowObject.isDirectBranch) return '-'
      return (rowObject.hasReport)?cellvalue:rowObject.r.hasReportCount;
    }},
    /*{ label: '未报送<br/>党支部数',name: '_notReportCount', formatter: function (cellvalue, options, rowObject) {
      if(rowObject.isDirectBranch) return '-'
      return ((rowObject.hasReport)?rowObject.branchCount:rowObject.r.branchCount) - ((rowObject.hasReport)?rowObject.hasReportCount:rowObject.r.hasReportCount);
    }},*/
    { label: '已报送支部<br/>党员总数',name: 'memberCount', formatter: function (cellvalue, options, rowObject) {
      if(cellvalue==undefined) cellvalue=0;
      if(rowObject.isDirectBranch) return (rowObject.hasReport)?cellvalue:0
      return (rowObject.hasReport)?cellvalue:rowObject.r.memberCount;
    }},
    { label: '线上缴纳<br/>党费总数',name: '_onlinePay', width:80, formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?(rowObject.onlineRealPay + rowObject.onlineRealDelayPay).toFixed(2)
              :(rowObject.r.onlineRealPay + rowObject.r.onlineRealDelayPay).toFixed(2);
    },cellattr:function(rowId, val, rowObject, cm, rdata) {
      return "class='success bolder'";
    }},
    { label: '现金缴纳<br/>党费总数',name: '_cashPay', width:80, formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?(rowObject.cashRealPay + rowObject.cashRealDelayPay).toFixed(2)
              :(rowObject.r.cashRealPay + rowObject.r.cashRealDelayPay).toFixed(2);
    },cellattr:function(rowId, val, rowObject, cm, rdata) {
      return "class='success bolder'";
    }},
    { label: '本月应缴纳<br/>党费数',name: 'duePay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.duePay;
    }},
    { label: '本月按时缴纳<br/>党费党员数',name: 'finishMemberCount', width:120, formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.finishMemberCount;
    }},
    /*{ label: '本月实缴纳<br/>党费数',name: 'realPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.realPay;
    }},*/
    { label: '本月线上<br/>缴纳党费数',name: 'onlineRealPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.onlineRealPay;
    }},
    { label: '本月现金<br/>缴纳党费数',name: 'cashRealPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.cashRealPay;
    }},
    { label: '本月延迟缴纳<br/>党费党员数',name: 'delayMemberCount', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.delayMemberCount;
    }},
    { label: '本月延迟缴纳<br/>党费数',name: 'delayPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.delayPay;
    }},
    /*{ label: '往月延迟<br/>缴费党员数',name: 'historyDelayMemberCount'},*/
    { label: '往月应补缴<br/>党费数',name: 'historyDelayPay'},
    /*{ label: '补缴往月<br/>党费党员数',name: 'realDelayMemberCount', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.realDelayMemberCount;
    }},
    { label: '实补缴<br/>往月党费数',name: 'realDelayPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.realDelayPay;
    }},*/
    { label: '往月线上<br/>补缴党费数',name: 'onlineRealDelayPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.onlineRealDelayPay;
    }},
    { label: '往月现金<br/>补缴党费数',name: 'cashRealDelayPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.cashRealDelayPay;
    }},{hidden: true, name: 'hasReport'}
  ]
</script>
