<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="myTableDiv"
             data-url-page="${ctx}/sysOnlineLog_page"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.code ||not empty param.username }"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasAnyRoles name="admin">
                <shiro:hasPermission name="sysOnlineLog:kickout">
                    <a class="jqBatchBtn btn btn-danger btn-sm"
                       data-url="${ctx}/sysOnlineLog_kickout"
                       data-title="踢用户下线"
                       data-msg="确定踢出这{0}个用户吗？（不能将自己踢下线）"><i class="fa fa-power-off"></i> 踢下线</a>
                </shiro:hasPermission>
                </shiro:hasAnyRoles>
                当前在线${_onlineCount}人；历史最高在线<span style="font-size: 20pt;font-weight: bolder">${_most.onlineCount}</span>人，发生在${cm:formatDate(_most.createTime, "yyyy-MM-dd HH:mm:ss")}。
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"> </table>
            <div id="jqGridPager"> </div>
        </div>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<script>

    $("#jqGrid").jqGrid({
        url: '${ctx}/sysOnlineLog_data?callback=?${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '会话ID', name: 'sid', width: 280,frozen:true },
            { label: '账号', name: 'shiroUser.username', width: 150,frozen:true },
            { label: '姓名', name: 'shiroUser.realname', width: 120, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/sysUser_view?userId={0}">{1}</a>'
                        .format(rowObject.shiroUser.id, rowObject.shiroUser.realname);
            },frozen:true },
            { label: '角色', name: 'shiroUser.roles', width: 300, formatter: function (cellvalue, options, rowObject) {
                //console.log(_cMap.ROLE_MAP)
                return $.map(cellvalue, function(item){
                    return _cMap.ROLE_MAP[item];
                });
            },frozen:true},
            { label: '最新操作时间', name: 'lastAccessTime', width: 200 },
            { label: '登录时间', name: 'startTimestamp', width: 200 },
            { label:'登录IP', name: 'ip', width: 150},
            { label: '超时（分钟）', name: 'timeOut', width: 100, formatter: function (cellvalue, options, rowObject) {
                return cellvalue/(1000*60)
            } },{hidden:true, key:true, name:'sid'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })
</script>