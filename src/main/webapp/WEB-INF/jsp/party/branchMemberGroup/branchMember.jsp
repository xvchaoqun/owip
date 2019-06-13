<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/branchMemberGroup?status=${status}"
             data-url-export="${ctx}/branchMember_data?isDeleted=0&isPresent=1"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId||not empty param.partyId
                || not empty param.typeId}"/>
                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>

                    <div class="tab-content">
                        <div class="tab-pane in active">
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="branchMember:edit">
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/branchMember_au"><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="branchMember:del">
                    <button data-url="${ctx}/branchMember_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                </shiro:hasPermission>

                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4>
                        <div class="widget-toolbar">
                            <a href="javascript:;" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main no-padding">
                            <form class="form-inline search-form" id="searchForm">

                                <div class="form-group">
                                    <label>姓名</label>
                                    <div class="input-group">
                                        <input type="hidden" name="status" value="${status}">
                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                                            <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                        </select>
                                    </div>
                                </div>

                                    <div class="form-group">
                                        <label>所属${_p_partyName}</label>
                                        <select name="partyId" data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                                data-placeholder="请选择">
                                            <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                        </select>
                                        <script>
                                            $.register.del_select($("#searchForm select[name=partyId]"), 350)
                                        </script>
                                    </div>
                                <div class="form-group">
                                    <label>类别</label>
                                    <select name="typeId" data-rel="select2" data-placeholder="请选择"> 
                                        <option></option>
                                         <c:import url="/metaTypes?__code=mc_branch_member_type"/>
                                    </select> 
                                    <script>         $("#searchForm select[name=typeId]").val('${param.typeId}');     </script>
                                     
                                </div>

                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                    <c:if test="${_query || not empty param.sort}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm" data-querystr="status=${status}">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid" class="jqGrid table-striped"> </table>
                <div id="jqGridPager"> </div>
            </div>
        </div>
                </div></div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $.register.multiselect($('#searchForm select[name=typeIds]'), ${cm:toJSONArray(selectedTypeIds)});
    $.register.user_select($('#searchForm select[name=userId]'));
    function _adminCallback(){
        $("#modal").modal("hide")
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}/branchMember_data?callback=?&isDeleted=0&isPresent=1&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:[
            {label: '工作证号', name: 'user.code', width: 110, frozen: true},
            {
                label: '姓名', name: 'user.realname', align:'left', width: 120, formatter: function (cellvalue, options, rowObject) {

                var str = '<span class="label label-sm label-primary " style="display: inline!important;"> 管理员</span>&nbsp;';
                return (rowObject.isAdmin?str:'')+ cellvalue;
            }, frozen: true
            },
            <shiro:hasPermission name="branchMember:edit">
            {label: '管理员', name: 'isAdmin',align:'left',formatter: function (cellvalue, options, rowObject) {
                if (cellvalue)
                    return '<button data-url="${ctx}/branchMember_admin?id={0}" data-msg="确定删除该管理员？" data-loading="#body-content-view" data-callback="_adminCallback" class="confirm btn btn-danger btn-xs">删除管理员</button>'.format(rowObject.id);
                else
                    return '<button data-url="${ctx}/branchMember_admin?id={0}" data-msg="确定设置该委员为管理员？" data-loading="#body-content-view" data-callback="_adminCallback" class="confirm btn btn-success btn-xs">设为管理员</button>'.format(rowObject.id);
            }},
            </shiro:hasPermission>
            {label: '所在单位', name: 'unitId', width: 180,align:'left', formatter: $.jgrid.formatter.unit},
            {
                label: '所属组织机构', name: 'party', align: 'left', width: 650,
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.groupPartyId, rowObject.groupBranchId);
                }, frozen: true
            },
            {label: '类别', name: 'typeId', formatter:$.jgrid.formatter.MetaType},
            {label: '任职时间', name: 'assignDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {
                label: '性别', name: 'gender', width: 50, formatter:$.jgrid.formatter.GENDER
            },
            {label: '民族', name: 'nation', width: 60},
            {label: '身份证号', name: 'idcard', width: 170},

            {
                label: '出生日期', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}
            },
            {label: '党派', name: '_cadreParty', width: 80, formatter: $.jgrid.formatter.cadreParty},
            {label: '党派加入时间', name: '_growTime', width: 120, formatter: $.jgrid.formatter.growTime},
            {label: '到校时间', name: 'arriveTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
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
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>