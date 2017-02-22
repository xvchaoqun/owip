<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="closeView btn btn-xs btn-success">
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
                       data-querystr="&"><i class="fa fa-edit"></i>
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
<script type="text/template" id="sort_tpl">
<a href="#" class="jqOrderBtn" data-grid-id="#jqGrid2" data-url="{{=url}}" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
       title="修改操作步长">
<a href="#" class="jqOrderBtn" data-grid-id="#jqGrid2" data-url="{{=url}}" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>
    function _adminCallback(){
        $("#modal").modal("hide")
        $("#jqGrid2").trigger("reloadGrid");
    }

    register_date($('.date-picker'));
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
                label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
                return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.id, url:"${ctx}/partyMember_changeOrder"})
            }, frozen: true
            },
            {label: '管理员', name: 'isAdmin', width: 100,align:'left',formatter: function (cellvalue, options, rowObject) {
                if (cellvalue)
                 return '<button data-url="${ctx}/partyMember_admin?id={0}" data-msg="确定删除该管理员？" data-loading="#item-content" data-callback="_adminCallback" class="confirm btn btn-danger btn-xs">删除管理员</button>'.format(rowObject.id);
                else
                    return '<button data-url="${ctx}/partyMember_admin?id={0}" data-msg="确定设置该委员为管理员？" data-loading="#item-content" data-callback="_adminCallback" class="confirm btn btn-success btn-xs">设为管理员</button>'.format(rowObject.id);
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
                label: '分工', name: 'typeId', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.partyMemberTypeMap[cellvalue].name;
            }
            },
            {label: '任职时间', name: 'assginDate', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
            {
                label: '性别', name: 'gender', width: 50, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.GENDER_MAP[cellvalue];
            }
            },
            {label: '民族', name: 'nation', width: 60},
            {label: '身份证号', name: 'idcard', width: 170},

            {
                label: '出生日期', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '党派', name: 'isDp', width: 80, formatter: function (cellvalue, options, rowObject) {

                if (!rowObject.isDp && rowObject.growTime != undefined) return "中共党员";
                if (rowObject.isDp) return _cMap.metaTypeMap[rowObject.dpTypeId].name;
                return "-";
            }
            },
            {
                label: '党派加入时间', name: 'growTime', width: 120, formatter: function (cellvalue, options, rowObject) {

                if (rowObject.isDp && rowObject.dpAddTime != undefined) return rowObject.dpAddTime.substr(0, 10);
                if (rowObject.growTime != undefined) return rowObject.growTime.substr(0, 10);
                return "-"
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
                    if (rowObject.partyId == undefined) return '';
                    var party = _cMap.partyMap[rowObject.partyId].name;
                    if (rowObject.branchId != undefined)
                        var branch = _cMap.branchMap[rowObject.branchId].name;
                    return party + (($.trim(branch) == '') ? '' : '-' + branch);
                }
            }
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid2');
    });
    _initNavGrid("jqGrid2", "jqGridPager2");

</script>