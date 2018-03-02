<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
    {label: '月份', name: 'payMonth', formatter: 'date', formatoptions: {newformat: 'Y年m月'}, frozen: true},
    { label: '报送',name: '_report', formatter: function (cellvalue, options, rowObject) {

      if (rowObject.hasReport) return '<span class="text-success">已报送</span>'
      return ('<button class="popupBtn btn btn-success btn-xs" ' +
      'data-url="${ctx}/pmd/pmdBranch_report?id={0}&cls=${cls}" {1}><i class="fa fa-hand-paper-o"></i> 报送</button>')
              .format(rowObject.id, rowObject.canReport ? '' : 'disabled');

    }, frozen: true},
    /*{ label: '报表',name: '_table', frozen: true},*/

    { label: '详情',name: '_members', formatter: function (cellvalue, options, rowObject) {
      <c:if test="${cls==1}">
      return ('<button class="openView btn btn-primary btn-xs" ' +
      'data-url="${ctx}/pmd/pmdMember?branchId={0}&monthId={1}&partyId={2}"><i class="fa fa-search"></i> 详情</button>')
              .format(rowObject.branchId, rowObject.monthId, rowObject.partyId);
      </c:if>
      <c:if test="${cls==2}">
      return ('<button class="popupBtn btn btn-primary btn-xs" data-width="1200" ' +
      'data-url="${ctx}/pmd/pmdMember?cls=3&branchId={0}&monthId={1}&partyId={2}"><i class="fa fa-search"></i> 详情</button>')
              .format(rowObject.branchId, rowObject.monthId, rowObject.partyId);
      </c:if>
    }, frozen: true},

    { label: '党支部名称',name: 'branchName', width:200, align:'left'},
    { label: '所属党委',name: 'partyName', width:300, align:'left'},
    { label: '党员总数',name: 'memberCount', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.memberCount;
    }},
    { label: '本月应<br/>缴纳党费数',name: 'duePay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.duePay;
    }},
    { label: '本月按时缴纳<br/>党费党员数', width:120,name: 'finishMemberCount', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.finishMemberCount;
    }},
    { label: '本月实缴纳<br/>党费数',name: 'realPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.realPay;
    }},
   /* { label: '本月线上<br/>缴纳党费数',name: 'onlineRealPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.onlineRealPay;
    }},
    { label: '本月现金<br/>缴纳党费数',name: 'cashRealPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.cashRealPay;
    }},*/
    { label: '本月未缴纳<br/>党费数',name: 'delayPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.delayPay;
    }},
    { label: '本月未缴纳<br/>党费党员数',name: 'delayMemberCount', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.delayMemberCount;
    }},
    { label: '往月延迟<br/>缴费党员数',name: 'historyDelayMemberCount'},
    { label: '应补缴<br/>往月党费数',name: 'historyDelayPay'},
    { label: '补缴往月<br/>党费党员数',name: 'realDelayMemberCount', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.realDelayMemberCount;
    }},
    { label: '实补缴<br/>往月党费数',name: 'realDelayPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.realDelayPay;
    }}, {hidden: true, name: 'hasReport'}, {hidden: true, name: 'canReport'}/*,
    { label: '线上补缴<br/>往月党费数',name: 'onlineRealDelayPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.onlineRealDelayPay;
    }},
    { label: '现金补缴<br/>往月党费数',name: 'cashRealDelayPay', formatter: function (cellvalue, options, rowObject) {
      return (rowObject.hasReport)?cellvalue:rowObject.r.cashRealDelayPay;
    }}*/
  ]
</script>
