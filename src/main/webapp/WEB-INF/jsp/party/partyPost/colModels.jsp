<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_PARTY_REPU_PARTY" value="<%=OwConstants.OW_PARTY_REPU_PARTY%>"/>
<c:set var="OW_PARTY_REPU_BRANCH" value="<%=OwConstants.OW_PARTY_REPU_BRANCH%>"/>
<c:set var="OW_PARTY_REPU_MEMBER" value="<%=OwConstants.OW_PARTY_REPU_MEMBER%>"/>
<script>
    var colModels = function () {
    };
    colModels.partyPost = [
        <c:if test="${list==1}">
        { label: '学工号',name: 'user.code',frozen:true,width:120},
        { label: '党员',name: 'user.realname',formatter:function (cellvalue, options, rowObject) {
                return $.trim(cellvalue);
            },frozen:true},
        </c:if>
        { label: '所属${_p_partyName}',name: 'partyId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称

                var party = _cMap.partyMap[cellvalue];
                var _partyView = null;
                if (party != undefined) {
                    _partyView = party.name;
                    if ($.inArray("party:list", _permissions) >= 0 || $.inArray("party:*", _permissions) >= 0)
                        _partyView = $.trim('{0}'.format(party.name));
                }else {

                }
                if (_partyView != null) {
                    return '<span class="{0}">{1}</span>'.format(party.isDeleted ? "delete" : "", _partyView);
                }
            },frozen:true},
        { label: '所属党支部',name: 'branchId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称
                var branch = (cellvalue == undefined) ? undefined : _cMap.branchMap[cellvalue];
                var _branchView = null;
                if (branch != undefined) {
                    var _branchView = branch.name;
                    if ($.inArray("branch:list", _permissions) >= 0 || $.inArray("branch:*", _permissions) >= 0)
                        _branchView = $.trim('{0}'.format(branch.name))
                        ;
                }
                if (_branchView != null) { // 仅显示党支部
                    return '<span class="{0}">{1}</span>'
                        .format(branch.isDeleted ? "delete" : "", _branchView);
                }
                return '--';
            },frozen:true},
        { label: '任职开始时间',name:'startDate',formatter:$.jgrid.formatter.date,formatoptions:{newformat:'Y.m.d'}},
        { label: '任职结束时间',name:'endDate',formatter:$.jgrid.formatter.date,formatoptions:{newformat:'Y.m.d'}},
        { label: '工作单位及担任职务',name: 'detail',width:280},
        { label: '备注',name: 'remark',width:200},{ hidden: true, key: true,name: 'id'}
    ];
</script>