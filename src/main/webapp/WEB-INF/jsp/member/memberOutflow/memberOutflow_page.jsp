<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_OUTFLOW_STATUS_BACK" value="<%=MemberConstants.MEMBER_OUTFLOW_STATUS_BACK%>"/>
<c:set var="MEMBER_OUTFLOW_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_OUTFLOW_STATUS_PARTY_VERIFY%>"/>

<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div  class="myTableDiv"
                  data-url-au="${ctx}/memberOutflow_au"
                  data-url-page="${ctx}/memberOutflow"
                  data-url-export="${ctx}/memberOutflow_data"
                  data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.type
                || not empty param.status ||not empty param.isBack||not empty param.originalJob
                ||not empty param.direction||not empty param._flowTime||not empty param.province
                ||not empty param.reason||not empty param.hasPapers||not empty param.orStatus||not empty param._createTime
                ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>

                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="dropdown <c:if test="${cls==1||cls==4||cls==5}">active</c:if>" >
                            <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
                                <i class="fa fa-circle-o"></i> 支部审核${cls==1?"(新申请)":(cls==4)?"(返回修改)":(cls==5)?"(已审核)":""}
                                <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOutflow?cls=1"><i class="fa fa-hand-o-right"></i> 新申请</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOutflow?cls=4"><i class="fa fa-hand-o-right"></i> 返回修改</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOutflow?cls=5"><i class="fa fa-hand-o-right"></i> 已审核</a>
                                </li>
                            </ul>
                        </li>
                        <li class="${cls==6?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOutflow?cls=6"}><i class="fa fa-circle-o"></i> ${_p_partyName}审核</a>
                        </li>
                        <li class="${cls==2?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOutflow?cls=2"}><i class="fa fa-times"></i> 未通过/已撤销</a>
                        </li>
                        <li class="${cls==3?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOutflow?cls=3"}><i class="fa fa-check"></i> 已完成审批</a>
                        </li>
                        <li class="${cls==31?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOutflow?cls=31"}><i class="fa fa-sign-out"></i> 已转出的流出党员</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="memberOutflow:edit">
                                    <c:if test="${cls==1}">
                                    <a class="editBtn btn btn-info btn-sm" data-width="800"><i class="fa fa-plus"></i> 添加流出党员</a>
                                    </c:if>
                                    <c:if test="${cls!=3&&cls!=31}">
                                <button  id="editBtn"  class="jqEditBtn btn btn-primary btn-sm" data-width="800">
                                    <i class="fa fa-edit"></i> 修改信息
                                </button>
                                    </c:if>
                                </shiro:hasPermission>
                                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>

                                    <c:if test="${cls==1||cls==4}">
                                    <button id="branchApprovalBtn" ${branchApprovalCount>0?'':'disabled'} class="jqOpenViewBtn btn btn-success btn-sm"
                                            data-url="${ctx}/memberOutflow_approval"
                                            data-open-by="page"
                                            data-querystr="&type=1&cls=${cls}"
                                            data-need-id="false"
                                            data-count="${branchApprovalCount}">
                                        <i class="fa fa-check-circle-o"></i> 党支部审核（${branchApprovalCount}）
                                    </button>
                                    </c:if>
                                    <c:if test="${cls==6}">
                                    <button id="partyApprovalBtn" ${partyApprovalCount>0?'':'disabled'} class="jqOpenViewBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/memberOutflow_approval"
                                            data-open-by="page"
                                            data-querystr="&type=2&cls=${cls}"
                                            data-need-id="false"
                                            data-count="${partyApprovalCount}">
                                        <i class="fa fa-check-circle-o"></i> ${_p_partyName}审核（${partyApprovalCount}）
                                    </button>
                                    </c:if>
                                    <button class="jqOpenViewBtn btn btn-info btn-sm"
                                                                                  data-url="${ctx}/applyApprovalLog"
                                                                                  data-querystr="&type=<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW%>"
                                                                                  data-open-by="page">
                                        <i class="fa fa-check-circle-o"></i> 查看审批记录
                                    </button>
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
                                                        <label>用户</label>
                                                            <div class="input-group">
                                                                <input type="hidden" name="cls" value="${cls}">
                                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                                </select>
                                                            </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>类别</label>
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
                                                    <div class="form-group">
                                                        <label>${_p_partyName}</label>
                                                            <select class="form-control" data-width="350"
                                                                    data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?auth=1"
                                                                    name="partyId" data-placeholder="请选择">
                                                                <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                            </select>
                                                    </div>
                                                    <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                                        <label>党支部</label>
                                                            <select class="form-control"  data-rel="select2-ajax"
                                                                    data-ajax-url="${ctx}/branch_selects?auth=1"
                                                                    name="branchId" data-placeholder="请选择党支部">
                                                                <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                                            </select>
                                                    </div>
                                                <script>
                                                    $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}",  "${party.classId}" );
                                                </script>
                                            <div class="form-group">
                                                <label>原职业</label>
                                                <select data-rel="select2" name="originalJob" data-placeholder="请选择">
                                                    <option></option>
                                                    <jsp:include page="/metaTypes?__code=mc_job"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=originalJob]").val(${param.originalJob});
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>外出流向</label>
                                                <select data-rel="select2" name="direction" data-placeholder="请选择">
                                                    <option></option>
                                                    <jsp:include page="/metaTypes?__code=mc_flow_direction"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=direction]").val(${param.direction});
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>流出时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="选择时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                           type="text" name="_flowTime" value="${param._flowTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>流出省份</label>
                                                <select class="loc_province" name="province" style="width:120px;"
                                                        data-placeholder="请选择">
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label>流出原因</label>
                                                <input type="text" name="reason" value="${param.reason}">
                                            </div>
                                            <div class="form-group">
                                                <label>是否持有《中国共产党流动党员活动证》</label>
                                                <select name="hasPapers" data-width="80" data-rel="select2" data-placeholder="请选择"> 
                                                    <option></option>
                                                    <option value="1">是</option>
                                                    <option value="0">否</option>
                                                </select> 
                                                <script>
                                                    $("#searchForm select[name=hasPapers]").val('${param.hasPapers}');
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>组织关系状态</label>
                                                <select data-rel="select2" name="orStatus" data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach items="<%=OwConstants.OW_OR_STATUS_MAP%>" var="orStatus">
                                                        <option value="${orStatus.key}">${orStatus.value}</option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#searchForm select[name=orStatus]").val(${param.orStatus});
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>申请时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="选择时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                           type="text" name="_createTime" value="${param._createTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>当前状态</label>
                                                <div class="input-group">
                                                    <select name="status" data-rel="select2" data-placeholder="请选择">
                                                        <option></option>
                                                        <c:forEach var="_status" items="<%=MemberConstants.MEMBER_OUTFLOW_STATUS_MAP%>">
                                                            <c:if test="${_status.key>MEMBER_OUTFLOW_STATUS_BACK && _status.key<MEMBER_OUTFLOW_STATUS_PARTY_VERIFY}">
                                                                <option value="${_status.key}">${_status.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=status]").val("${param.status}");
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>审核类别</label>
                                                <div class="input-group">
                                                    <select name="isBack" data-rel="select2" data-placeholder="请选择">
                                                        <option></option>
                                                        <option value="0">新申请</option>
                                                        <option value="1">返回修改</option>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=isBack]").val("${param.isBack}");
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-querystr="cls=${cls}">
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
        <div id="body-content-view">

        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $.showLocation("${param.province}");
    function goto_next(goToNext){
        if(goToNext){
            if($("#next").hasClass("disabled") && $("#last").hasClass("disabled") )
                $.hashchange();
            else if(!$("#next").hasClass("disabled"))
                $("#next").click();
            else
                $("#last").click();
        }
    }
    function apply_deny(id, type, goToNext) {

        $.loadModal("${ctx}/memberOutflow_deny?id=" + id + "&type="+type +"&goToNext="+((goToNext!=undefined&&goToNext)?"1":"0"));
    }
    function apply_pass(id, type, goToNext){
        SysMsg.confirm("确定通过该申请？", "操作确认", function () {
            $.post("${ctx}/memberOutflow_check",{ids:[id], type:type},function(ret){
                if(ret.success){
                    //SysMsg.success('操作成功。', '成功',function(){
                        //page_reload();
                        goto_next(goToNext);
                   // });
                }
            });
        });
    }

    $("#jqGrid").jqGrid({
        /*multiboxonly:false,*/
        ondblClickRow:function(){},
        url: '${ctx}/memberOutflow_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '学工号',  name: 'user.code', width: 120, frozen:true },
            { label: '姓名', name: 'user.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return $.member(rowObject.userId, cellvalue);
            }, frozen:true  },
            { label: '所在党组织', name: 'party', width: 450 ,
                formatter:function(cellvalue, options, rowObject){
                    return $.party(rowObject.partyId, rowObject.branchId);
                }, frozen:true  },
            { label: '状态', name: 'statusName', width: 150, formatter:function(cellvalue, options, rowObject){
                return _cMap.MEMBER_OUTFLOW_STATUS_MAP[rowObject.status];
            }, frozen:true }<c:if test="${cls==4}">
            ,{label: '返回修改原因', name: 'reason', width: 180}</c:if>,
            { label:'原职业',  name:'originalJob',width: 200 , formatter: $.jgrid.formatter.MetaType},
            { label: '外出流向',   name: 'direction', width: 150 , formatter: $.jgrid.formatter.MetaType},
            { label: '流出时间',   name: 'flowTime' , formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            { label: '流出省份',   name: 'province', width: 150 , formatter:function(cellvalue, options, rowObject){
                return _cMap.locationMap[cellvalue].name;
            }},
            { label: '流出原因',   name: 'reason', width: 350 },
            { label: '是否持有《中国共产党流动党员活动证》',   name: 'hasPapers', width: 300, formatter:$.jgrid.formatter.TRUEFALSE},
            { label: '组织关系状态',   name: 'orStatus', width: 150, formatter:function(cellvalue, options, rowObject){
                return _cMap.OW_OR_STATUS_MAP[cellvalue];
            }  },
            { label: '申请时间',   name: 'createTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'} },
            {hidden:true, name:'status'}
        ],
        onSelectRow: function(id,status){
            saveJqgridSelected("#"+this.id);
            //console.log(id)
            var ids  = $(this).getGridParam("selarrrow");
            if(ids.length>1){
                $("#branchApprovalBtn,#partyApprovalBtn").prop("disabled",true);
            } else if (ids.length==1) {

                var rowData = $(this).getRowData(ids[0]);
                $("#branchApprovalBtn").prop("disabled",rowData.status!="<%=MemberConstants.MEMBER_OUTFLOW_STATUS_APPLY%>");
                $("#partyApprovalBtn").prop("disabled",rowData.status!="<%=MemberConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY%>");
            }else{
                $("*[data-count]").each(function(){
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        },
        onSelectAll:function(aRowids, status){
            saveJqgridSelected("#" + this.id);
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#branchApprovalBtn,#partyApprovalBtn").prop("disabled",true);
            }else {
                $("*[data-count]").each(function () {
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $.initNavGrid("jqGrid", "jqGridPager");
    <c:if test="${cls==1||cls==4}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"支部批量审核",
        btnbase:"jqBatchBtn btn btn-success btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/memberOutflow_check" data-querystr="&type=1" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </c:if>
    <c:if test="${cls==6}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"${_p_partyName}批量审核",
        btnbase:"jqBatchBtn btn btn-primary btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/memberOutflow_check" data-querystr="&type=2" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </c:if>
    <c:if test="${cls==1||cls==4||cls==6}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"批量退回申请",
        btnbase:"btn btn-danger btn-xs",
        buttonicon:"fa fa-reply-all",
        onClickButton: function(){
            var ids  = $(this).getGridParam("selarrrow");
            if(ids.length==0){
                SysMsg.warning("请选择行", "提示");
                return ;
            }
            var minStatus;
            $.each(ids, function (i, id) {
                var rowData = $(this).getRowData(id);
                if(minStatus==undefined || minStatus>rowData.status) minStatus = rowData.status;
            })

            $.loadModal("${ctx}/memberOutflow_back?ids={0}&status={1}".format(ids, minStatus))
        }
    });
    </c:if>

    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=userId]'));
</script>
