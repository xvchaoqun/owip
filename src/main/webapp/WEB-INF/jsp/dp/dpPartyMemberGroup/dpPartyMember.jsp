<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/dp/dpPartyMemberGroup?status=${status}"
             data-url-export="${ctx}/dp/dpPartyMember_data?isDeleted=0&isPresent=1"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.unitId ||not empty param.partyId
                ||not empty param.postId || not empty param.typeIds ||not empty param.deleteTime}"/>
                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>

                    <div class="tab-content">
                        <div class="tab-pane in active">
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="dpPartyMember:edit">
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dp/dpPartyMember_au?gridId=jqGrid2"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="dpPartyMember:del">
                    <button data-url="${ctx}/dp/dpPartyMember_batchDel"
                            data-title="撤销"
                            data-msg="确定撤销这{0}条数据？"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-history"></i> 撤销
                    </button>
                </shiro:hasPermission>
                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                        <i class="fa fa-download"></i> 导出</a>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
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
                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/dp/dpPartyMember_selects"
                                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                                            <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>所属单位</label>
                                    <select name="unitId" data-rel="select2" data-placeholder="请选择"> 
                                        <option></option>
                                          <c:forEach items="${unitMap}" var="unit"> 
                                            <option value="${unit.key}">${unit.value.name}</option>
                                              </c:forEach>  </select> 
                                    <script>         $("#searchForm select[name=unitId]").val('${param.unitId}');     </script>
                                </div>
                                    <div class="form-group">
                                        <label>所属民主党派</label>
                                        <select name="groupPartyId" data-rel="select2-ajax" data-ajax-url="${ctx}/dp/dpParty_selects"
                                                data-placeholder="请选择所属民主党派">
                                            <option value="${dpParty.id}" delete="${dpParty.isDeleted}">${dpParty.name}</option>
                                        </select>
                                        <script>
                                            $("#searchForm select[name=groupPartyId]").val('${param.groupPartyId}');
                                        </script>
                                    </div>
                                <div class="form-group">
                                    <label>职务</label>
                                    <select name="postId" data-rel="select2" data-placeholder="请选择"> 
                                        <option></option>
                                         <c:import url="/metaTypes?__code=mc_dp_party_member_post"/>
                                    </select> 
                                    <script>         $("#searchForm select[name=postId]").val('${param.postId}');     </script>
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
        $("#modal").modal("hide");
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}dp/dpPartyMember_data?callback=?&isDeleted=0&isPresent=1&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:[
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
            {label: '所在单位', name: 'unitId', width: 350,formatter: $.jgrid.formatter.unit},
            {label: '所属民主党派', name: 'dpParty.name', width: 300, formatter: function (cellvalue, options, rowObject) {
                    var _dpPartyView = null;
                    if ($.inArray("dpParty:list", _permissions) >= 0 || $.inArray("dpParty:*", _permissions) >= 0)
                        _dpPartyView = '<a href="javascript:;" class="openView" data-url="{2}/dp/dpParty_view?id={0}">{1}</a>'
                            .format(rowObject.groupPartyId, cellvalue, ctx);
                    if (cellvalue != ''){
                        return '<span class="{0}">{1}</span>'.format(rowObject.isDeleted ? "delete" : "", _dpPartyView);
                    }
                    return "--";
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
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>