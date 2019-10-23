<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_PARTY_REPU_PARTY" value="<%=OwConstants.OW_PARTY_REPU_PARTY%>"/>
<c:set var="OW_PARTY_REPU_BRANCH" value="<%=OwConstants.OW_PARTY_REPU_BRANCH%>"/>
<c:set var="OW_PARTY_REPU_MEMBER" value="<%=OwConstants.OW_PARTY_REPU_MEMBER%>"/>
<script>
    var colModels = function () {
    };
    colModels.partyReward = [
        <c:if test="${param.list==1}">
        <c:if test="${cls==OW_PARTY_REPU_PARTY}">
        { label: '${_p_partyName}',name: 'partyId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称

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
        </c:if>
        <c:if test="${cls==OW_PARTY_REPU_BRANCH}">
        { label: '党支部',name: 'branchId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称
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
        { label: '所属${_p_partyName}',name: 'branchPartyId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称

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
        </c:if>
        <c:if test="${cls==OW_PARTY_REPU_MEMBER}">
        { label: '学工号',name: 'user.code',frozen:true,width:120},
        { label: '党员',name: 'user.realname',formatter:function (cellvalue, options, rowObject) {
                return $.trim(cellvalue);
            },frozen:true},
        { label: '所属${_p_partyName}',name: 'userPartyId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称

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
        { label: '所属党支部',name: 'userBranchId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称
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
        </c:if>
        </c:if>
        { label: '获奖日期',name: 'rewardTime',formatter: $.jgrid.formatter.date,formatoptions:{newformat: 'Y.m.d'}},
        <c:if test="${cls==OW_PARTY_REPU_PARTY||cls==OW_PARTY_REPU_BRANCH}">
        { label: '获奖类型',name: 'rewardType',width: 150,formatter:$.jgrid.formatter.MetaType},
        </c:if>
        { label: '获得奖项',name: 'name',width: 150},
        { label: '颁奖单位',name: 'unit',width: 180, width: 280, align:'left', cellattr: function (rowId, val, rowObject, cm, rdata) {
                if($.trim(val)=='')
                    return "class='danger'";
            }},
        { label: '获奖证书',name: 'proof',width: 250,
            formatter: function (cellvalue, options, rowObject) {
                return $.imgPreview(rowObject.proof, rowObject.proofFilename);
            }},
        { label: '获奖证书文件名',name: 'proofFilename',width: 280},
        { label: '备注',name: 'remark',width: 200}, {hidden: true, key: true, name: 'id'}, {hidden: true, name: 'userId'}
    ];
    colModels.partyPunish = [
        <c:if test="${param.list==1}">
        <c:if test="${cls==OW_PARTY_REPU_PARTY}">
        { label: '${_p_partyName}',name: 'partyId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称
                var party = _cMap.partyMap[cellvalue];
                var _partyView = null;
                if (party != undefined) {
                    _partyView = party.name;
                    if ($.inArray("party:list", _permissions) >= 0 || $.inArray("party:*", _permissions) >= 0)
                        _partyView = $.trim('{0}'.format(party.name));
                }
                if (_partyView != null) {
                    return '<span class="{0}">{1}</span>'.format(party.isDeleted ? "delete" : "", _partyView);
                }
                return '--';
            },frozen:true},
        </c:if>
        <c:if test="${cls==OW_PARTY_REPU_BRANCH}">
        { label: '党支部',name: 'branchId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称
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
        { label: '所属${_p_partyName}',name: 'branchPartyId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称

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
        </c:if>
        <c:if test="${cls==OW_PARTY_REPU_MEMBER}">
        { label: '学工号',name: 'user.code',frozen:true,width:120},
        { label: '党员',name: 'user.realname',formatter:function (cellvalue, options, rowObject) {
                return $.trim(cellvalue);
            },frozen:true},
        { label: '所属${_p_partyName}',name: 'userPartyId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称

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
        { label: '所属党支部',name: 'userBranchId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称
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
        </c:if>
        </c:if>
        { label: '处分日期',name: 'punishTime',formatter:$.jgrid.formatter.date,formatoptions:{newformat:'Y.m.d'}},
        { label: '处分截止日期',name: 'endTime',formatter:$.jgrid.formatter.date,formatoptions:{newformat:'Y.m.d'}},
        { label: '受何种处分',name: 'name',width:180},
        { label: '处分单位',name: 'unit',width: 180, width: 280, align:'left', cellattr: function (rowId, val, rowObject, cm, rdata) {
                if($.trim(val)=='')
                    return "class='danger'";
            }},
        { label: '备注',name: 'remark',width:200}, {hidden: true, key: true, name: 'id'}, {hidden: true, name: 'userId'}
    ];
</script>