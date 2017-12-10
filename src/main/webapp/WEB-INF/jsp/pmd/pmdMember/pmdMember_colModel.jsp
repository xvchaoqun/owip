<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
    {label: '月份', name: 'payMonth', formatter: 'date', formatoptions: {newformat: 'Y年m月'}, frozen: true},
    <c:if test="${param.type!='admin'}">
    {label: '缴费', name: '_pay', formatter: function (cellvalue, options, rowObject) {
      // 能缴费的情况：已开启缴费 且 党支部未报送  且 还未缴费  且 当月的没有设置为延迟缴费
      if(rowObject.payStatus==0) return '-';
      return ('<button class="popupBtn btn btn-success btn-xs" ' +
              'data-url="${ctx}/user/pmd/payConfirm_campuscard?monthId={0}"><i class="fa fa-rmb"></i> {1}</button>')
              .format(rowObject.monthId, rowObject.payStatus==1?'缴费':'补缴');
    }, frozen: true},
    </c:if>
    { label: '缴费状态',name: '_hasPay', formatter: function (cellvalue, options, rowObject) {
      return rowObject.hasPay?('<span class="text-success">'+ (rowObject.isDelay?'补缴已确认':'缴费已确认') + '</span>')
              :'<span class="text-danger">未缴费</span>';
    }, frozen: true},
    { label: '按时/延迟缴费',name: '_isDelay', width: 120, formatter: function (cellvalue, options, rowObject) {

      if(rowObject.isDelay) return '延迟缴费'
      return (rowObject.hasPay)?"按时缴费":"-";
    }, frozen: true},
    { label: '工作证号',name: 'user.code', width: 120, frozen: true},
    { label: '姓名',name: 'user.realname', frozen: true, /*cellattr: function (rowId, val, rowObject, cm, rdata) {
      if(rowObject.configMemberTypeId==undefined)
      return "class='danger'";
    },*/ formatter:function(cellvalue, options, rowObject){
      return (rowObject.configMemberTypeId==undefined)?'<span class="text-danger">'+cellvalue+'</span>':cellvalue;
    }},
    <c:if test="${cls!=3}">
    { label: '所在党委',name: 'partyId', width: 350, align:'left', formatter:function(cellvalue, options, rowObject){
      return cellvalue==undefined?"":_cMap.partyMap[cellvalue].name;
    }},
    <c:if test="${cls!=2}">
    { label: '所在党支部',name: 'branchId', width: 200, align:'left', formatter: function (cellvalue, options, rowObject) {
      return cellvalue==undefined?'-' : _cMap.branchMap[cellvalue].name;
    }},
    </c:if>
    </c:if>
    { label: '党员类别',name: '_type', width: 150, formatter: function (cellvalue, options, rowObject) {
      return _cMap.PMD_MEMBER_TYPE_MAP[rowObject.type];
    }},
    <c:if test="${param.type=='admin'}">
    { label: '党费计算标准',name: 'duePayReason', width: 150},
    </c:if>
    { label: '应交金额',name: 'duePay'},
    { label: '实交金额',name: 'realPay'},
    { label: '缴费方式',name: 'isOnlinePay', formatter: function (cellvalue, options, rowObject) {
      if(!rowObject.hasPay) return ''
      return cellvalue?"线上缴费":"现金缴费";
    }},
    { label: '缴费日期',name: 'payTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
    { label: '收款人',name: 'chargeUser.realname', formatter: function (cellvalue, options, rowObject) {
      if(!rowObject.hasPay) return ''
      if(rowObject.isOnlinePay) return '-'
      return $.trim(cellvalue);
    }},
    { label: '备注',name: 'delayReason', width: 400, formatter: function (cellvalue, options, rowObject) {
      if(!rowObject.isDelay) return '-'
      return "延迟缴费：" + $.trim(cellvalue);
    }},{hidden: true, name: 'normType'}, {hidden: true, name: 'hasPay'},
    {hidden: true, name: 'isDelay'}, {hidden: true, name: 'monthId'},
    {hidden: true, name: 'type'}, {hidden: true, name: 'configMemberTypeId'}
  ]
</script>
