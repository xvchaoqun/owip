<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/memberReg_au"
                 data-url-page="${ctx}/memberReg"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.username||not empty param.realname
                ||not empty param.partyId ||not empty param.idcard|| not empty param.importUserId || not empty param.importSeq}"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="${cls==1?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberReg?cls=1" }><i
                                    class="fa fa-circle-o"></i> ${_p_partyName}审核</a>
                        </li>
                        <li class="${cls==2?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberReg?cls=2" }><i
                                    class="fa fa-times"></i> 未通过</a>
                        </li>
                        <li class="${cls==3?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberReg?cls=3" }><i
                                    class="fa fa-check"></i> 审批通过</a>
                        </li>
                        <div class="buttons pull-left" style="left: 30px; position: relative">
                        <shiro:hasPermission name="memberReg:edit">
                             <a class="editBtn btn btn-info btn-sm" style="margin-right: 5px"><i class="fa fa-plus"></i> 添加</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="memberReg:import">
                            <button class="popupBtn btn btn-success btn-sm tooltip-info"
                                    data-url="${ctx}/memberReg_import"
                                    data-rel="tooltip" data-placement="top" title="批量生成系统注册账号"><i
                                    class="fa fa-upload"></i>
                                批量生成账号
                            </button>
                        </shiro:hasPermission>
                            </div>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">

                                <shiro:hasPermission name="memberReg:edit">
                                    <c:if test="${cls==1}">
                                        <button id="editBtn" class="jqEditBtn btn btn-primary btn-sm">
                                            <i class="fa fa-edit"></i> 修改信息
                                        </button>
                                    </c:if>
                                </shiro:hasPermission>
                                <c:if test="${cls==1}">
                                    <shiro:hasPermission name="memberReg:check">
                                        <button id="partyApprovalBtn" ${partyApprovalCount>0?'':'disabled'}
                                                class="jqOpenViewBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/memberReg_approval"
                                                data-open-by="page"
                                                data-need-id="false"
                                                data-count="${partyApprovalCount}">
                                            <i class="fa fa-check-square-o"></i> 审核（${partyApprovalCount}）
                                        </button>
                                    </shiro:hasPermission>
                                </c:if>

                                <c:if test="${cls==3}">
                                    <shiro:hasPermission name="memberReg:changepw">
                                        <button class="jqOpenViewBtn btn btn-danger btn-sm"
                                                data-url="${ctx}/memberReg_changepw">
                                            <i class="fa fa-edit"></i> 修改登录密码
                                        </button>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="memberReg:import">
                                        <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                                data-url="${ctx}/memberReg_data"
                                                data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）">
                                            <i class="fa fa-download"></i> 导出
                                        </button>
                                    </shiro:hasPermission>
                                </c:if>
                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/applyApprovalLog"
                                        data-querystr="&type=<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG%>"
                                        data-open-by="page">
                                    <i class="fa fa-history"></i> 操作记录
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

                                            <input type="hidden" name="cls" value="${cls}">
                                            <div class="form-group">
                                                <label>注册账号</label>
                                                <input type="text" name="username" value="${param.username}">
                                            </div>

                                            <div class="form-group">
                                                <label>真实姓名</label>
                                                <input type="text" name="realname" value="${param.realname}">
                                            </div>
                                            <div class="form-group">
                                                <label>身份证号码</label>
                                                <input type="text" name="idcard" value="${param.idcard}">
                                            </div>
                                            <div class="form-group">
                                                <label>所属${_p_partyName}</label>
                                                <select data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/party_selects?auth=1"
                                                        name="partyId" data-placeholder="请选择">
                                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                </select>
                                            </div>

                                            <div class="form-group">
                                                <label>关联系统账号</label>
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                            </div>
                                            <c:if test="${cls==3}">
                                                <shiro:hasPermission name="memberReg:import">
                                                    <div class="form-group">
                                                        <label>生成人</label>
                                                        <select data-rel="select2-ajax"
                                                                data-ajax-url="${ctx}/sysUser_selects"
                                                                name="importUserId" data-placeholder="请输入账号或姓名或学工号">
                                                            <option value="${importUser.id}">${importUser.realname}-${importUser.code}</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>生成批次</label>
                                                        <input type="text" class="num" style="width: 50px"
                                                               name="importSeq" value="${param.importSeq}">
                                                    </div>
                                                </shiro:hasPermission>
                                            </c:if>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i
                                                        class="fa fa-search"></i> 查找</a>

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
                            <table id="jqGrid" class="jqGrid table-striped"></table>
                            <div id="jqGridPager"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view">

        </div>
    </div>
</div>
<script>
    function goto_next(goToNext) {
        if (goToNext) {
            if ($("#next").hasClass("disabled") && $("#last").hasClass("disabled"))
                $.hashchange();
            else if (!$("#next").hasClass("disabled"))
                $("#next").click();
            else
                $("#last").click();
        }
    }

    function apply_deny(id, goToNext) {

        $.loadModal("${ctx}/memberReg_deny?id=" + id + "&goToNext=" + ((goToNext != undefined && goToNext) ? "1" : "0"));
    }

    function apply_pass(id, goToNext) {
        SysMsg.confirm("确定通过该申请？", "操作确认", function () {
            $.post("${ctx}/memberReg_check", {id: id}, function (ret) {
                if (ret.success) {
                    //SysMsg.success('操作成功。', '成功', function () {
                    //page_reload();
                    goto_next(goToNext);
                    // });
                }
            });
        });
    }

    $("#jqGrid").jqGrid({
        url: '${ctx}/memberReg_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '账号', name: 'username', width: 120, frozen: true},
            {label: '真实姓名', name: 'realname', frozen: true},
            {
                label: '类别', name: 'typeName', formatter: function (cellvalue, options, rowObject) {
                    return _cMap.USER_TYPE_MAP[rowObject.type];
                }, frozen: true
            },
            {label: '学工号', name: 'code', frozen: true},
            {label: '身份证号码', name: 'idcard', width: 200, frozen: true},
            {label: '手机号码', name: 'phone', width: 120},
            {
                label: '所在党组织',
                name: 'party',
                width: 450,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId);
                }
            },
            {label: '注册时间', name: 'createTime', width: 150},
            <c:if test="${cls==3}">
            <shiro:hasPermission name="memberReg:import">
            {label: '生成批次', name: 'importSeq', width: 80},
            {label: '生成人', name: 'importUser.realname'},
            </shiro:hasPermission>
            </c:if>
            {label: 'IP', name: 'ip', width: 150},
            {hidden: true, name: 'status'}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id);
            //console.log(id)

            var $this = $(this);
            var ids = $this.getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#partyApprovalBtn").prop("disabled", true);
            } else if (status) {
                var rowData = $this.getRowData(id);
                $("#partyApprovalBtn").prop("disabled", rowData.status != "<%=MemberConstants.MEMBER_STAY_STATUS_APPLY%>");
            } else {
                $("*[data-count]").each(function () {
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            $("*[data-count]").each(function () {
                $(this).prop("disabled", $(this).data("count") == 0);
            })
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=userId]'));
    $.register.user_select($('#searchForm select[name=importUserId]'));
    $.register.del_select($('#searchForm select[name=partyId]'));
</script>
