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
              'data-url="${ctx}/user/pmd/payConfirm_campuscard?id={0}"><i class="fa fa-rmb"></i> {1}</button>')
              .format(rowObject.id, rowObject.payStatus==1?'缴费':'补缴');
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
    { label: '工作证号',name: 'user.code', width: 120/*, formatter:function(cellvalue, options, rowObject){
      return (rowObject.configMemberTypeId==undefined)?'<span class="text-danger">'+cellvalue+'</span>':cellvalue;
    }*/,frozen: true},
    { label: '姓名',name: 'user.realname', frozen: true},
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
    { label: '党费计算标准',name: 'duePayReason', width: 150, cellattr: function (rowId, val, rowObject, cm, rdata) {
      if(rowObject.configMemberTypeId==undefined)
        return "class='danger'";
    }},
    { label: '应交金额',name: 'duePay', formatter: function (cellvalue, options, rowObject) {
        if(rowObject.pmdConfigMember==undefined || rowObject.pmdConfigMember.hasReset==undefined) return "-";
        return $.trim(rowObject.pmdConfigMember.hasReset?rowObject.duePay:rowObject.configMemberDuePay);
    }},
    { label: '确认额度',name: '_confirmDuePay', formatter: function (cellvalue, options, rowObject) {

        if(rowObject.hasPay) return '-'
        if(rowObject.pmdConfigMember==undefined
                ||rowObject.pmdConfigMember.pmdConfigMemberType==undefined
                ||rowObject.pmdConfigMember.pmdConfigMemberType.pmdNorm==undefined) return "-";

        if(rowObject.pmdConfigMember.hasReset) return '-'

        if(rowObject.pmdConfigMember.pmdConfigMemberType.pmdNorm.setType == ${PMD_NORM_SET_TYPE_SET}){
            return ('<button class="popupBtn btn btn-success btn-xs" ' +
            'data-url="${ctx}/pmd/pmdMember_selectMemberType?ids[]={0}&configMemberType={1}&confirm=1"><i class="fa fa-rmb"></i> 确认额度</button>')
                    .format(rowObject.id, rowObject.type)
        }

        return '-'
    }},
    </c:if>
    <c:if test="${param.type!='admin'}">
    { label: '应交金额',name: 'duePay'},
    </c:if>
    { label: '实交金额',name: 'realPay'},
    { label: '缴费方式',name: 'isSelfPay', formatter: function (cellvalue, options, rowObject) {
      if(!rowObject.hasPay) return '-'
      return cellvalue?"线上缴费":"代缴党费";
    }},
    <c:if test="${param.type=='admin'}">
    { label: '缴费订单号',name: '_orderNo', width: 160, formatter: function (cellvalue, options, rowObject) {
      if(rowObject.pmdMemberPayView==undefined) return '-'
      return $.trim(rowObject.pmdMemberPayView.orderNo);
    }},
    { label: '订单号生成人',name: '_orderUser', width: 120, formatter: function (cellvalue, options, rowObject) {
      if(rowObject.pmdMemberPayView==undefined ||
              rowObject.pmdMemberPayView.orderUser==undefined) return '-';
      var str = rowObject.pmdMemberPayView.orderUser.realname;

      if(!rowObject.hasPay){
        str += ('&nbsp;<button class="confirm btn btn-danger btn-xs" ' +
                'data-url="${ctx}/pmd/pmdMember_clearOrderUser?id={1}" ' +
                'data-callback="_reload2"'+
                'data-title="清除订单号生成人" ' +
                'data-msg="确认清除该订单号生成人{0}？<div style=\'color: red\'>（注：订单号生成人清除之后，如果需要更换账号进行缴费，请务必找校园卡支付系统负责人进行订单删除操作，否则无法缴费成功。）</div>"><i class="fa fa-eraser"></i> 清除</button>')
                .format(str, rowObject.id)
      }

      return str;
    }},
    </c:if>
    { label: '缴费日期',name: 'payTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
    { label: '代缴人',name: 'chargeUser.realname', formatter: function (cellvalue, options, rowObject) {
      if(!rowObject.hasPay) return ''
      if(rowObject.isSelfPay) return '-'
      return $.trim(cellvalue);
    }},
    { label: '备注',name: 'delayReason', width: 400, formatter: function (cellvalue, options, rowObject) {
      if(!rowObject.isDelay) return '-'
      return "延迟缴费：" + $.trim(cellvalue);
    }},{hidden: true, name: 'normType'}, {hidden: true, name: 'hasPay'},
    {hidden: true, name: 'isDelay'}, {hidden: true, name: 'monthId'},
    {hidden: true, name: 'type'}, {hidden: true, name: 'configMemberTypeId'},
    {hidden: true, name: 'isSelfSetSalary', formatter: function (cellvalue, options, rowObject) {
      if(rowObject.pmdConfigMember==undefined || rowObject.pmdConfigMember.isSelfSetSalary==undefined) return "0";
      return rowObject.pmdConfigMember.isSelfSetSalary?"1":"0";
    }},
    {hidden: true, name: 'formulaType', formatter: function (cellvalue, options, rowObject) {
      if(rowObject.pmdConfigMember==undefined
              ||rowObject.pmdConfigMember.pmdConfigMemberType==undefined
              ||rowObject.pmdConfigMember.pmdConfigMemberType.pmdNorm==undefined) return -1;

      return rowObject.pmdConfigMember.pmdConfigMemberType.pmdNorm.formulaType;
    }}/*,
    {hidden: true, name: 'hasReset', formatter: function (cellvalue, options, rowObject) {
      if(rowObject.pmdConfigMember==undefined || rowObject.pmdConfigMember.hasReset==undefined) return "0";
      return rowObject.pmdConfigMember.hasReset?"1":"0";
    }},
    {hidden: true, name: 'setType', formatter: function (cellvalue, options, rowObject) {
      if(rowObject.pmdConfigMember==undefined
              ||rowObject.pmdConfigMember.pmdConfigMemberType==undefined
              ||rowObject.pmdConfigMember.pmdConfigMemberType.pmdNorm==undefined) return -1;

      return rowObject.pmdConfigMember.pmdConfigMemberType.pmdNorm.setType;
    }}*/
  ]
</script>
