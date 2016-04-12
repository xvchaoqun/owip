<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div  class="myTableDiv"
                  data-url-au="${ctx}/memberInflow_au?cls=${cls}"
                  data-url-page="${ctx}/memberInflow_page"
                  data-url-del="${ctx}/memberInflow_del"
                  data-url-bd="${ctx}/memberInflow_batchDel"
                  data-url-export="${ctx}/memberInflow_data"
                  data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.type ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>

                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>

                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">

                                <shiro:hasPermission name="memberInflow:edit">
                                    <a class="editBtn btn btn-info btn-sm" data-width="800"><i class="fa fa-plus"></i> 添加流入党员</a>
                                <button id="editBtn" class="jqEditBtn btn btn-primary btn-sm" data-width="800">
                                    <i class="fa fa-edit"></i> 修改信息
                                </button>
                                </shiro:hasPermission>
                                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>

                                <%--<button id="passBtn" disabled class="jqItemBtn btn btn-danger btn-sm"
                                        data-url="${ctx}/memberInflow_deny" data-title="拒绝申请"
                                        data-msg="确定拒绝该申请吗？">
                                    <i class="fa fa-trash"></i> 不通过
                                </button>--%>
                                <c:if test="${cls==4}">
                                    <button id="branchApprovalBtn" ${branchApprovalCount>0?'':'disabled'} class="jqOpenViewBtn btn btn-warning btn-sm"
                                                                                   data-url="${ctx}/memberInflow_approval"
                                                                                   data-open-by="page"
                                                                                   data-querystr="&type=1"
                                                                                   data-need-id="false"
                                                                                   data-count="${branchApprovalCount}">
                                        <i class="fa fa-check-circle-o"></i> 支部审核（${branchApprovalCount}）
                                    </button>
                                    <button id="partyApprovalBtn" ${partyApprovalCount>0?'':'disabled'} class="jqOpenViewBtn btn btn-warning btn-sm"
                                                                                  data-url="${ctx}/memberInflow_approval"
                                                                                  data-open-by="page"
                                                                                  data-querystr="&type=2"
                                                                                  data-need-id="false"
                                                                                  data-count="${partyApprovalCount}">
                                        <i class="fa fa-check-circle-o"></i> 分党委审核（${partyApprovalCount}）
                                    </button>
                                </c:if>
                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/applyApprovalLog_page"
                                        data-querystr="&type=${APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW}"
                                        data-open-by="page">
                                    <i class="fa fa-check-circle-o"></i> 查看审批记录
                                </button>
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
                                        <form class="form-horizontal " id="searchForm">
                                            <div class="row">
                                                <div class="col-xs-4">
                                                    <div class="form-group">
                                                        <label class="col-xs-3 control-label">姓名</label>
                                                        <div class="col-xs-6">
                                                            <div class="input-group">
                                                                <input type="hidden" name="cls" value="${cls}">
                                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                                    <option value="${sysUser.id}">${sysUser.realname}</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-xs-3 control-label">类别</label>
                                                        <div class="col-xs-6">
                                                            <select data-rel="select2" name="type" data-placeholder="请选择类别">
                                                                <option></option>
                                                                <c:forEach items="${MEMBER_TYPE_MAP}" var="_type">
                                                                    <option value="${_type.key}">${_type.value}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <script>
                                                                $("#searchForm select[name=type]").val(${param.type});
                                                            </script>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xs-4">
                                                    <div class="form-group">
                                                        <label class="col-xs-3 control-label">分党委</label>
                                                        <div class="col-xs-6">
                                                            <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                                                    name="partyId" data-placeholder="请选择分党委">
                                                                <option value="${party.id}">${party.name}</option>
                                                            </select>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="col-xs-4" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                                    <div class="form-group">
                                                        <label class="col-xs-4 control-label">党支部</label>
                                                        <div class="col-xs-6">
                                                            <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                                                                    name="branchId" data-placeholder="请选择党支部">
                                                                <option value="${branch.id}">${branch.name}</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <script>
                                                    register_party_branch_select($("#searchForm"), "branchDiv",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
                                                </script>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="cls=${cls}">
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

                        </div></div></div>
            </div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>
<script>
    function goto_next(goToNext){
        if(goToNext){
            if($("#next").hasClass("disabled") && $("#last").hasClass("disabled") )
                $(".closeView").click();
            else if(!$("#next").hasClass("disabled"))
                $("#next").click();
            else
                $("#last").click();
        }
    }
    function apply_deny(id, type, goToNext){
        bootbox.confirm("确定拒绝该申请？", function (result) {
            if(result){
                $.post("${ctx}/memberInflow_deny",{id:id, type:type},function(ret){
                    if(ret.success){
                        SysMsg.success('操作成功。', '成功',function(){
                            //page_reload();
                            goto_next(goToNext);
                        });
                    }
                });
            }
        });
    }
    function apply_pass(id, type, goToNext){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/memberInflow_check",{id:id, type:type},function(ret){
                    if(ret.success){
                        SysMsg.success('操作成功。', '成功',function(){
                            //page_reload();
                            goto_next(goToNext);
                        });
                    }
                });
            }
        });
    }

    $("#jqGrid").jqGrid({
        url: '${ctx}/memberInflow_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '姓名',  name: 'user.realname', width: 100 ,frozen:true},
            { label: '所属组织机构', name: 'party',resizable:false, width: 450 ,
                formatter:function(cellvalue, options, rowObject){
                var party = rowObject.party;
                var branch = rowObject.branch;
                return party + (($.trim(branch)=='')?'':'-'+branch);
            } ,frozen:true },
            { label:'原职业',  name:'originalJob', width: 200 ,formatter:function(cellvalue, options, rowObject){
                return _metaMap[cellvalue];
            }, frozen:true },
            { label: '流入前所在省份',   name: 'province', width: 150 , formatter:function(cellvalue, options, rowObject){
                return _cMap.locationMap[cellvalue].name;
            }},
            { label: '是否持有《中国共产党流动党员活动证》',   name: 'hasPapers', width: 300, formatter:function(cellvalue, options, rowObject){
                return cellvalue?"是":"否";
            } },
            { label: '流入时间',   name: 'flowTime', width: 350 },
            { label: '流入原因',   name: 'reason', width: 350 },
            { label: '入党时间',   name: 'growTime', width: 150 },
            { label: '组织关系所在地',   name: 'orLocation', width: 150 },
            { label: '状态',   name: 'inflowStatusName', width: 150, formatter:function(cellvalue, options, rowObject){
                return _cMap.MEMBER_INFLOW_STATUS_MAP[rowObject.inflowStatus];
            }},{hidden:true, name:'inflowStatus'}
        ],
        onSelectRow: function(id,status){
            jgrid_sid=id;
            //console.log(id)
            var ids  = $(this).getGridParam("selarrrow");
            if(ids.length>1){
                $("#branchApprovalBtn,#partyApprovalBtn").prop("disabled",true);
            }else if(status){
                var rowData = $(this).getRowData(id);
                $("#branchApprovalBtn").prop("disabled",rowData.inflowStatus!="${MEMBER_INFLOW_STATUS_APPLY}");
                $("#partyApprovalBtn").prop("disabled",rowData.inflowStatus!="${MEMBER_INFLOW_STATUS_BRANCH_VERIFY}");
            }else{
                $("#branchApprovalBtn").prop("disabled",$("#branchApprovalBtn").data("count")==0);
                $("#partyApprovalBtn").prop("disabled",$("#partyApprovalBtn").data("count")==0);
            }
        },
        onSelectAll:function(aRowids, status){
            $("#branchApprovalBtn").prop("disabled",status || $("#branchApprovalBtn").data("count")==0);
            $("#partyApprovalBtn").prop("disabled",status || $("#partyApprovalBtn").data("count")==0);
        }}).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $('[data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=userId]'));
</script>
