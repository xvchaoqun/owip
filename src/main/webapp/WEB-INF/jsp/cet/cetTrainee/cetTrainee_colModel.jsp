<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
    var cetTraineeTypeMap = ${cm:toJSONObject(cetTraineeTypeMap)};
  var colModel = [
      <c:if test="${param.type=='sign'}">
      {name:'isFinished', hidden:true},
      { label: '上课情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
          return rowObject.isFinished?'<span class="text-success">已上课</span>':'<span class="text-danger">未上课</span>'
      }},
      {label: '签到时间', name: 'signTime', width: 160},
      {label: '签退时间', name: 'signOutTime', width: 160},
      {label: '签到方式', name: 'signType', width: 80, formatter: function (cellvalue, options, rowObject){
          if(cellvalue==undefined) return '--'
          return _cMap.CET_TRAINEE_SIGN_TYPE_MAP[cellvalue];
      } },
      {label: '参训人类别', name: 'traineeTypeId', width: 120, formatter: function (cellvalue, options, rowObject) {
          return cetTraineeTypeMap[cellvalue].name;
      }},
      {label: '工作证号', name: 'user.code', width: 110, frozen: true},
      {label: '姓名', name: 'user.realname', width: 120, frozen: true},
      </c:if>
      <c:if test="${param.type=='admin'}">
      { label: '学习详情',name: '_detail', width: 80, formatter: function (cellvalue, options, rowObject) {
          return ('<button class="popupBtn btn btn-success btn-xs" data-width="1100" ' +
          'data-url="${ctx}/cet/cetTrainee_detail?userId={0}&trainId={1}"><i class="fa fa-search"></i> 查看</button>')
                  .format(rowObject.userId, rowObject.trainId);
      }, frozen: true},
      { label: '选课情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
          if(courseCount==0) return '--'
          return '{0}/{1}'.format(rowObject.courseCount, courseCount);
      }, frozen: true},
      { label: '签到情况',name: '_sign', width: 80, formatter: function (cellvalue, options, rowObject) {
          if(rowObject.courseCount==undefined) return '--'
          return '{0}/{1}'.format(rowObject.finishCount, rowObject.courseCount);
      }, frozen: true},
      { label: '完成学时数',name: 'finishPeriod', frozen: true},
      {label: '工作证号', name: 'obj.user.code', width: 110, frozen: true},
      {label: '姓名', name: 'obj.user.realname', width: 120, frozen: true},

      <c:if test="${cetTraineeType.code=='t_leader'||cetTraineeType.code=='t_cadre'
            ||cetTraineeType.code=='t_cadre_kj'||cetTraineeType.code=='t_reserve'}">
        {label: '所在单位及职务', name: 'obj.title', align: 'left', width: 350},
        {label: '行政级别', name: 'obj.adminLevel', formatter:$.jgrid.formatter.MetaType},
        {label: '职务属性', name: 'obj.postType', width: 150, formatter:$.jgrid.formatter.MetaType},
        {label: '政治面貌', name: '_cadreParty', width: 80, formatter: function (cellvalue, options, rowObject) {
            rowObject.isOw = rowObject.obj.isOw;
            rowObject.dpTypeId = rowObject.obj.dpTypeId;
            return $.jgrid.formatter.cadreParty(cellvalue, options, rowObject);
        }},
        {label: '专业技术职务', name: 'obj.proPost', width: 120},
        {
            label: '任现职时间',
            name: 'obj.lpWorkTime',
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y.m.d'}
        },
        </c:if>
        <c:if test="${cetTraineeType.code=='t_party_member'}">
         {
            label: '所在党组织',
            name: 'obj.partyId',
            align: 'left',
            width: 550,
            formatter: function (cellvalue, options, rowObject) {
                return $.party(rowObject.obj.partyId, rowObject.obj.branchId);
            }
        },
        {label: '职务', name: 'obj.postId', formatter:$.jgrid.formatter.MetaType},
        {
            label: '分工', name: 'obj.partyTypeIds', width: 300, formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '--';
            var typeIdStrs = [];
            var typeIds = cellvalue.split(",");
            for(i in typeIds){
                var typeId = typeIds[i];
                //console.log(typeId)
                if(typeId instanceof Function == false)
                    typeIdStrs.push($.jgrid.formatter.MetaType(typeId));
            }
            //console.log(typeIdStrs)
            return typeIdStrs.join(",");
        }
        },
        {label: '任职时间', name: 'obj.assignDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        </c:if>
        <c:if test="${cetTraineeType.code=='t_branch_member'}">
         {
            label: '所在党组织',
            name: 'obj.partyId',
            align: 'left',
            width: 550,
            formatter: function (cellvalue, options, rowObject) {
                return $.party(rowObject.obj.partyId, rowObject.obj.branchId);
            }
        },
        {label: '类别', name: 'obj.branchTypeId', formatter:$.jgrid.formatter.MetaType},
        {label: '任职时间', name: 'obj.assignDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        </c:if>
        <c:if test="${cetTraineeType.code=='t_organizer'}">
        { label: '组织员类别', name: 'obj.organizerType', width: 150, formatter:function(cellvalue, options, rowObject){
            return _cMap.OW_ORGANIZER_TYPE_MAP[cellvalue];
        }},
        { label: '联系单位',name: 'obj.organizerUnits', align:'left', formatter: function (cellvalue, options, rowObject) {
                if($.trim(cellvalue)=='') return'--'
                    return ($.map(cellvalue.split(","), function(u){
                        return u.split("|")[0];
                    })).join("、")
                }, width:700},
        { label: '联系${_p_partyName}', name: 'obj.organizerPartyId',align:'left', width: 350, formatter:function(cellvalue, options, rowObject){
            return $.party(rowObject.organizerPartyId);
        }},
        {label: '任职时间', name: 'obj.assignDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        </c:if>
        <c:if test="${cetTraineeType.code=='t_candidate'||cetTraineeType.code=='t_activist'||cetTraineeType.code=='t_grow'}">
         {
            label: '联系党组织',
            name: 'obj.partyId',
            align: 'left',
            width: 550,
            formatter: function (cellvalue, options, rowObject) {
                return $.party(rowObject.obj.partyId, rowObject.obj.branchId);
            }
        },
        {label: '成为积极分子时间', name: 'obj.activeTime', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
        <c:if test="${cetTraineeType.code=='t_candidate'}">
        {label: '成为发展对象时间', name: 'candidateTime', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
        </c:if>
        <c:if test="${cetTraineeType.code=='t_grow'}">
        {label: '入党时间', name: 'owGrowTime', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
        </c:if>
        </c:if>
      /*{label: '联系方式', name: 'cadre.mobile', width: 120},
      {label: '电子邮箱', name: 'cadre.email', width: 250, algin:"left"},*/

        { label: '本年度参加培训情况',name: 'courseCount', width: 150, formatter: function (cellvalue, options, rowObject) {

          var yearPeriod = yearPeriodMap[rowObject.userId];
          return yearPeriod==undefined?'-':yearPeriod;
      }},
      </c:if>
      { label: '备注',name: 'remark', width: 200, formatter: function (cellvalue, options, rowObject) {
          var str = "";
          if(rowObject.isQuit) str+="退班";
          str += " " + $.trim(cellvalue);

          return str;
      }}
  ]
</script>
