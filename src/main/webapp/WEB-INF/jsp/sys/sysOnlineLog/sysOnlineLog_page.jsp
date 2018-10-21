<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
        <div class="myTableDiv"
             data-url-page="${ctx}/sysOnlineLog"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.code ||not empty param.username }"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="sysOnlineLog:kickout">
                    <a class="jqBatchBtn btn btn-danger btn-sm"
                       data-url="${ctx}/sysOnlineLog_kickout"
                       data-title="踢用户下线"
                       data-msg="确定踢出这{0}个用户吗？（不能将自己踢下线）"><i class="fa fa-power-off"></i> 踢下线</a>
                </shiro:hasPermission>
                当前在线${_onlineCount}人；历史最高在线<span style="font-size: 20pt;font-weight: bolder">${_most.onlineCount}</span>人，发生在${cm:formatDate(_most.createTime, "yyyy-MM-dd HH:mm")}。
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"> </table>
            <div id="jqGridPager"> </div>
        </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>

    $("#jqGrid").jqGrid({
        url: '${ctx}/sysOnlineLog_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            /*{ label: '会话ID', name: 'sid', width: 280,frozen:true },*/
            { label: '账号', name: 'shiroUser.username', width: 120,frozen:true, formatter:function(cellvalue, options, rowObject){
                    return ('<span class="text {0}">{1}</span>')
                            .format(($.trim(rowObject.switchUser)=='')?'':'text-danger bolder', cellvalue);
            }, cellattr: function (rowId, val, rawObject, cm, rdata) {
                if($.trim(rawObject.switchUser)!='')
                    return ('data-tooltip="tooltip" data-container="#body-content" ' +
                            'data-html="true" data-original-title="切换登录主账号：{0}"').format(rawObject.switchUser);
            }},
            { label: '姓名', name: 'shiroUser.realname', formatter:function(cellvalue, options, rowObject){

                return $.user(rowObject.shiroUser.id, rowObject.shiroUser.realname)
            },frozen:true },
            <shiro:hasAnyRoles name="${ROLE_ADMIN}">
            { label: '角色', name: 'shiroUser.roles', align:'left', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '-'
                return $.map(cellvalue, function(item){
                    var role = _cMap.roleCodeMap[item];
                    return role?role.description:null;
                });
            }, width: 300},
            </shiro:hasAnyRoles>
            { label: '最新操作时间', name: 'lastAccessTime',sortable:true, width: 160 },
            { label: '登录时间', name: 'startTimestamp',sortable:true, width: 160 },
            { label:'登录IP', name: 'ip', width: 150},
            { label: '登录地点', name: 'country', width: 120},
            { label: '地区', name: 'area', align:'left', width: 250,formatter: function (cellvalue, options, rowObject) {
                if(rowObject.country=='局域网') return '校内';
                return $.trim(cellvalue);
            }},
            { label: '客户端', name: 'userAgent', align:'left', width: 250},
            { label: '超时（分钟）', name: 'timeOut', width: 100, formatter: function (cellvalue, options, rowObject) {
                return cellvalue/(1000*60)
            } },{hidden:true, key:true, name:'sid'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-tooltip="tooltip"]').tooltip({html:true});
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
</script>