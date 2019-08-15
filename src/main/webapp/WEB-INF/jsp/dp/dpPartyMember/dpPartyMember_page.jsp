<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
                <shiro:hasPermission name="dpPartyMember:edit">
                    <a class="popupBtn btn btn-info btn-sm"
                       data-url="${ctx}/dp/dpPartyMember_au?gridId=jqGrid2&groupId=${dpPartyMemberGroup.id}">
                        <i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dp/dpPartyMember_au?gridId=jqGrid2"
                       data-grid-id="#jqGrid2"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="dpPartyMember:del">
                    <c:if test="${cls==1}">
                    <button data-url="${ctx}/dp/dpPartyMember_batchDel"
                            data-title="删除"
                            data-msg="确定撤销这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 撤销
                    </button>
                    </c:if>
                    <c:if test="${cls==0}">
                    <button data-url="${ctx}/dp/dpPartyMember_del"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                    </c:if>
                </shiro:hasPermission>
                <c:if test="${!dpPartyMemberGroup.isPresent}">
                    <span style="color: red;font-size: 12px">【注：当前委员会不是当届委员会，只有成为当届委员会后，设置的管理员才生效】</span>
                </c:if>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
<script>
    function _adminCallback(){
        $("#modal").modal("hide")
        $("#jqGrid2").trigger("reloadGrid");
    }
    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager: "jqGridPager2",
        url: '${ctx}/dp/dpPartyMember_data?callback=?&groupId=${param.groupId}&cls=${cls}',
        colModel: [
            {label: '工作证号', name: 'user.code', width: 110, frozen: true},
            {
                label: '姓名', name: 'user.realname', align:'left', width: 120, formatter: function (cellvalue, options, rowObject) {

                var str = '<span class="label label-sm label-primary " style="display: inline!important;"> 管理员</span>&nbsp;';
                return (rowObject.isAdmin?str:'')+ cellvalue;
            }, frozen: true
            },
             <shiro:hasPermission name="dpPartyMember:edit">
            {
                label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2', url: "${ctx}/dp/dpPartyMember_changeOrder"}, frozen: true
            },
             </shiro:hasPermission>
            <shiro:hasPermission name="dpPartyMember:edit">
            {label: '管理员', name: 'isAdmin',align:'left',formatter: function (cellvalue, options, rowObject) {
                if (cellvalue)
                 return '<button data-url="${ctx}/dp/dpPartyMember_admin?id={0}" data-msg="确定删除该管理员？" data-loading="#body-content-view" data-callback="_adminCallback" class="confirm btn btn-danger btn-xs">删除管理员</button>'.format(rowObject.id);
                else
                    return '<button data-url="${ctx}/dp/dpPartyMember_admin?id={0}" data-msg="确定设置该委员为管理员？" data-loading="#body-content-view" data-callback="_adminCallback" class="confirm btn btn-success btn-xs">设为管理员</button>'.format(rowObject.id);
            }},
            </shiro:hasPermission>
            {label: '所在单位', name: 'user.unit', width: 350,align:'left'},
            {label: '所属民主党派', name: 'groupPartyId', width: 400, align:'left',formatter: function (cellvalue, options, rowObject) {
                return $.party(rowObject.groupPartyId);
            }},
            {label: '职务', name: 'postId', formatter:$.jgrid.formatter.MetaType},
            {
                label: '分工', name: 'typeIds', width: 300, formatter: function (cellvalue, options, rowObject) {
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
            {label: '任职时间', name: 'assignDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {
                label: '性别', name: 'user.gender', width: 50, formatter:$.jgrid.formatter.GENDER
            },
            {label: '民族', name: 'user.nation', width: 60},
            { label: '办公电话', name: 'officePhone' },
            { label: '手机号', name: 'mobile' }
        ]
    }).jqGrid("setFrozenColumns");
     $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");

</script>