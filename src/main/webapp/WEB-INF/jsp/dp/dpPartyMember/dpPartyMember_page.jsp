<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
                <shiro:hasPermission name="dpPartyMember:edit">
                    <c:if test="${cls==1}">
                    <a class="popupBtn btn btn-info btn-sm"
                       data-url="${ctx}/dp/dpPartyMember_au?gridId=jqGrid2&groupId=${dpPartyMemberGroup.id}">
                        <i class="fa fa-plus"></i> 添加</a>
                    </c:if>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dp/dpPartyMember_au?gridId=jqGrid2"
                       data-grid-id="#jqGrid2"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="dpPartyMember:del">
                    <c:if test="${cls==0}">
                        <button data-url="${ctx}/dp/dpPartyMember_recover"
                                data-title="恢复"
                                data-msg="确定恢复这{0}条数据？"
                                data-grid-id="#jqGrid2"
                                class="jqBatchBtn btn btn-success btn-sm">
                            <i class="fa fa-reply"></i> 恢复
                        </button>
                    </c:if>
                    <c:if test="${cls==1}">
                        <button data-url="${ctx}/dp/dpPartyMember_cancel"
                                data-title="撤销"
                                data-msg="确定撤销这{0}条数据？"
                                data-grid-id="#jqGrid2"
                                class="jqOpenViewBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-history"></i> 撤销
                        </button>
                    </c:if>
                    <button data-url="${ctx}/dp/dpPartyMember_del"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <c:if test="${dpPartyMemberGroup.isDeleted}">
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
        url: '${ctx}/dp/dpPartyMember_data?callback=?&groupId=${param.groupId}&cls=${cls}',
        pager: "jqGridPager2",
        colModel: [
            {label: '工作证号', name: 'user.code', width: 110, frozen: true},
            {
                label: '姓名', name: 'user.realname', align:'left', width: 120, formatter: function (cellvalue, options, rowObject) {

                var str = '<span class="label label-sm label-primary " style="display: inline!important;"> 管理员</span>&nbsp;';
                return (rowObject.isAdmin?str:'')+ cellvalue;
            }, frozen: true
            },
             <shiro:hasPermission name="dpPartyMember:edit">
            <c:if test="${cls!=1}">
            { label: '撤销时间',name: 'deleteTime',width:120,sortable:true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}},
            </c:if>
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
            {label: '部门', name: 'unit', width: 200},
            {label: '所属民主党派', name: 'dpParty.name', width: 200, formatter: function (cellvalue, options, rowObject) {
                    var _dpPartyView = null;
                    if ($.inArray("dpParty:list", _permissions) >= 0 || $.inArray("dpParty:*", _permissions) >= 0)
                        _dpPartyView = '<a href="javascript:;" class="openView" data-url="{2}/dp/dpParty_view?id={0}">{1}</a>'
                            .format(rowObject.groupPartyId, cellvalue, ctx);
                    if (cellvalue != undefined){
                        return '<span class="{0}">{1}</span>'.format(rowObject.isDeleted ? "delete" : "", _dpPartyView);
                    }
                    return "--";
                }},
            {label: '职务', name: 'postId', formatter:$.jgrid.formatter.MetaType},
            {
                label: '分工', name: 'typeIds', width: 270, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    var typeIdStrs = [];
                    var typeIds = cellvalue.split(",");
                    $.each(typeIds, function (i, typeId) {
                        //console.log(typeId)
                        typeIdStrs.push($.jgrid.formatter.MetaType(typeId));
                    })
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
            { label: '手机号', name: 'mobile' },
            { label: '备注', name: 'remark', width: 200 }
        ]
    }).jqGrid("setFrozenColumns");
     $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");

</script>