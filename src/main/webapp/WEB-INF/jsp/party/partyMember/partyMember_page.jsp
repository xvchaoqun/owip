<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">${partyMemberGroup.name}-委员管理</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <shiro:hasPermission name="partyMember:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/partyMember_au?groupId=${partyMemberGroup.id}">
                        <i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/partyMember_au"
                       data-grid-id="#jqGrid2"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="partyMember:del">
                    <button data-url="${ctx}/partyMember_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>

                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
        <!-- /.widget-main -->
    </div>
    <!-- /.widget-body -->
</div>
<!-- /.widget-box -->
<script src="${ctx}/assets/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-multiselect.css" />
<script>
    function _adminCallback(){
        $("#modal").modal("hide")
        $("#jqGrid2").trigger("reloadGrid");
    }

    $.register.date($('.date-picker'));
    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager: "jqGridPager2",
        url: '${ctx}/partyMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'user.code', width: 100, frozen: true},
            {
                label: '姓名', name: 'user.realname', align:'left', width: 120, formatter: function (cellvalue, options, rowObject) {

                var str = '<span class="label label-sm label-primary " style="display: inline!important;"> 管理员</span>&nbsp;';
                return (rowObject.isAdmin?str:'')+ cellvalue;
            }, frozen: true
            },
            {
                label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2', url: "${ctx}/partyMember_changeOrder"}, frozen: true
            },
            {label: '管理员', name: 'isAdmin', width: 100,align:'left',formatter: function (cellvalue, options, rowObject) {
                if (cellvalue)
                 return '<button data-url="${ctx}/partyMember_admin?id={0}" data-msg="确定删除该管理员？" data-loading="#body-content-view" data-callback="_adminCallback" class="confirm btn btn-danger btn-xs">删除管理员</button>'.format(rowObject.id);
                else
                    return '<button data-url="${ctx}/partyMember_admin?id={0}" data-msg="确定设置该委员为管理员？" data-loading="#body-content-view" data-callback="_adminCallback" class="confirm btn btn-success btn-xs">设为管理员</button>'.format(rowObject.id);
            }},
            {label: '所在单位', name: 'unitId', width: 350,align:'left',formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.unitMap[cellvalue].name;
            }},
            {label: '所属分党委', name: 'groupPartyId', width: 400, align:'left',formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.partyMap[cellvalue].name;
            }},
            {
                label: '职务', name: 'postId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.partyMemberPostMap[cellvalue].name;
            }
            },
            {
                label: '分工', name: 'typeIds', width: 300, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                var typeIdStrs = [];
                var typeIds = cellvalue.split(",");
                for(i in typeIds){
                    var typeId = typeIds[i];
                    //console.log(typeId)
                    if(typeId instanceof Function == false)
                        typeIdStrs.push(_cMap.partyMemberTypeMap[typeId].name);
                }
                //console.log(typeIdStrs)
                return typeIdStrs.join(",");
            }
            },
            {label: '任职时间', name: 'assignDate', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
            {
                label: '性别', name: 'gender', width: 50, formatter:$.jgrid.formatter.GENDER
            },
            {label: '民族', name: 'nation', width: 60},
            {label: '身份证号', name: 'idcard', width: 170},

            {
                label: '出生日期', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '党派', name: 'cadreDpType', width: 80, formatter: function (cellvalue, options, rowObject) {

                if (cellvalue == 0) return "中共党员"
                else if (cellvalue > 0) return _cMap.metaTypeMap[rowObject.dpTypeId].name
                return "-";
            }
            },
            {
                label: '党派加入时间', name: 'cadreGrowTime', width: 120, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return cellvalue.substr(0, 10);
            }
            },
            {
                label: '党龄', name: '_growBirth', width: 50,
                formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.cadreGrowTime == undefined) return '-';
                    return $.yearOffNow(rowObject.cadreGrowTime);
                }
            },
            {label: '到校时间', name: 'arriveTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '岗位类别', name: 'postClass'},
            {label: '主岗等级', name: 'mainPostLevel', width: 150},
            {label: '专业技术职务', name: 'proPost', width: 120},
            {label: '专技岗位等级', name: 'proPostLevel', width: 150},
            {label: '管理岗位等级', name: 'manageLevel', width: 150},
            { label: '办公电话', name: 'officePhone' },
            { label: '手机号', name: 'mobile' },
            {
                label: '所属党组织',
                name: 'partyId',
                align: 'left',
                width: 550,
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                }
            }
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid2');
    });
    $.initNavGrid("jqGrid2", "jqGridPager2");

</script>