<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
                 data-url-au="${ctx}/member_au"
                 data-url-page="${ctx}/memberStudent_page"
                 data-url-bd="${ctx}/member_batchDel"
                 data-url-export="${ctx}/memberStudent_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.unitId
                ||not empty param._growTime ||not empty param._positiveTime || not empty param.partyId }"/>
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/party/member/member_menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/member_au">
                                <i class="fa fa-plus"></i> 添加党员</a>
                            <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm"
                               data-open-by="page" data-id-name="userId">
                                <i class="fa fa-edit"></i> 修改信息</a>
                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）">
                                <i class="fa fa-download"></i> 导出</a>
                            <a class="jqDelBtn btn btn-danger btn-sm">
                                <i class="fa fa-trash"></i> 删除
                            </a>
                            </div>
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4>
                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <mytag:sort-form css="form-horizontal " id="searchForm">
                                        <input type="hidden" name="cls" value="${cls}">
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label class="col-xs-3 control-label">姓名</label>
                                                    <div class="col-xs-6">
                                                        <div class="input-group">
                                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?type=${MEMBER_TYPE_STUDENT}&status=${MEMBER_STATUS_NORMAL}"
                                                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                                <option value="${sysUser.id}">${sysUser.realname}</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-xs-3 control-label">所在单位</label>
                                                    <div class="col-xs-6">
                                                        <select name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
                                                            <option></option>
                                                            <c:forEach items="${unitMap}" var="unit">
                                                                <option value="${unit.key}">${unit.value.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=unitId]").val('${param.unitId}');
                                                        </script>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label class="col-xs-4 control-label">入党时间</label>
                                                    <div class="col-xs-6">
                                                        <div class="input-group tooltip-success" data-rel="tooltip" title="入党时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                            <input placeholder="请选择入党时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                                   type="text" name="_growTime" value="${param._growTime}"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-xs-4 control-label">转正时间</label>
                                                    <div class="col-xs-6">
                                                        <div class="input-group tooltip-success" data-rel="tooltip" title="转正时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                            <input placeholder="请选择转正时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                                   type="text" name="_positiveTime" value="${param._positiveTime}"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label class="col-xs-3 control-label">所在分党委</label>
                                                    <div class="col-xs-6">
                                                        <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                                                name="partyId" data-placeholder="请选择分党委">
                                                            <option value="${party.id}">${party.name}</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                                    <label class="col-xs-3 control-label">所在党支部</label>
                                                    <div class="col-xs-6">
                                                        <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                                                                name="branchId" data-placeholder="请选择党支部">
                                                            <option value="${branch.id}">${branch.name}</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <script>
                                                    register_party_branch_select($("#searchForm"), "branchDiv",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
                                                </script>

                                            </div>

                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="cls=${cls}">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </mytag:sort-form>
                                </div>
                            </div>
                        </div>
                        <%--<div class="space-4"></div>--%>
                        <table id="jqGrid" class="jqGrid table-striped"> </table>
                        <div id="jqGridPager"> </div>
                    </div></div></div>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('#searchForm select[name=userId]'));

    $("#jqGrid").jqGrid({
        url: '${ctx}/memberStudent_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '姓名', name: 'realname',resizable:false, width: 75, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId={0}">{1}</a>'
                        .format(rowObject.userId, cellvalue);
            } ,frozen:true },
            { label: '学生证号',  name: 'code', width: 100 ,frozen:true},
            { label: '性别',  name: 'gender', width: 55 },
            { label: '年龄',  name: 'age', width: 55 },
            { label: '学生类别',  name: 'type', width: 150 },
            { label: '年级',  name: 'grade', width: 55 },
            { label:'所属组织机构', name: 'party', width: 550, formatter:function(cellvalue, options, rowObject){
                var party = rowObject.party;
                var branch = rowObject.branch;
                return party + ((branch=='')?'':'-'+branch);
            } },
            { label:'入党时间',  name: 'growTime', width: 100 },
            { label:'转正时间',  name: 'positiveTime', width: 100 },
            {hidden:true, key:true, name:'userId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');


    jQuery("#jqGrid")
            .navGrid('#jqGridPager',{refresh: false, edit:false,add:false,del:false,search:false})
            .navButtonAdd('#jqGridPager',{
                caption:"批量转移",
                btnbase:"btn btn-info btn-xs",
                buttonicon:"fa fa-check",
                onClickButton: function(){
                    alert("Adding Row");
                },
                position:"last"
            })
            .navButtonAdd('#jqGridPager',{
                caption:"批量删除",
                btnbase:"btn btn-danger btn-xs",
                buttonicon:"fa fa-plus",
                onClickButton: function(){
                    alert("Deleting Row");
                },
                position:"first"
            });
</script>