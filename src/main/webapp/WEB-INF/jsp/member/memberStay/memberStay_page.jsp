<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="JASPER_PRINT_TYPE_MEMBER_STAY_ABROAD" value="<%=SystemConstants.JASPER_PRINT_TYPE_MEMBER_STAY_ABROAD%>"/>
<c:set var="JASPER_PRINT_TYPE_MEMBER_STAY_INTERNAL" value="<%=SystemConstants.JASPER_PRINT_TYPE_MEMBER_STAY_INTERNAL%>"/>
<c:set var="MEMBER_STAY_STATUS_BACK" value="<%=MemberConstants.MEMBER_STAY_STATUS_BACK%>"/>
<c:set var="MEMBER_STAY_STATUS_OW_VERIFY" value="<%=MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY%>"/>
<c:set var="MEMBER_STAY_TYPE_ABROAD" value="<%=MemberConstants.MEMBER_STAY_TYPE_ABROAD%>"/>
<c:set var="MEMBER_STAY_TYPE_INTERNAL" value="<%=MemberConstants.MEMBER_STAY_TYPE_INTERNAL%>"/>

<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/memberStay_au"
                 data-url-page="${ctx}/memberStay?type=${param.type}"
                 data-url-export="${ctx}/memberStay_data?type=${param.type}"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.partyId 
                || not empty param.status ||not empty param.isBack
                || not empty param.country ||not empty param._abroadTime|| not empty param._returnTime
                 ||not empty param._payTime||not empty param.mobile
                ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">

                        <li class="${cls==1?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberStay?type=${param.type}&cls=1"}><i class="fa fa-circle-o"></i> 支部待审核</a>
                        </li>
                        <li class="${cls==12?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberStay?type=${param.type}&cls=12"}><i class="fa fa-check-circle-o"></i> 支部已审核</a>
                        </li>
                        <c:if test="${cm:isPermitted(PERMISSION_OWADMIN) || cm:hasRole(ROLE_PARTYADMIN)}">

                        <li class="${cls==2?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberStay?type=${param.type}&cls=2"}><i class="fa fa-circle-o"></i> ${_p_partyName}待审核</a>
                        </li>
                        <li class="${cls==22?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberStay?type=${param.type}&cls=22"}><i class="fa fa-check-circle-o"></i> ${_p_partyName}已审核</a>
                        </li>

                        </c:if>
                        <shiro:hasPermission name="${PERMISSION_OWADMIN}">
                            <li class="${cls==3?'active':''}">
                                <a href="javascript:;" class="hashchange" data-querystr="cls=3"><i
                                        class="fa fa-circle-o"></i> 组织部审核</a>
                            </li>
                        </shiro:hasPermission>

                        <li class="${cls==4?'active':''}">
                            <a href="javascript:;" class="hashchange" data-querystr="cls=4"><i
                                    class="fa fa-times"></i> 未通过/已撤销</a>
                        </li>

                        <li class="dropdown <c:if test="${cls==5||cls==6}">active</c:if>">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
                                <i class="fa fa-sign-in"></i> 已完成审批${cls==5?"(未转出)":(cls==6)?"(已转出)":""}
                                <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                <li>
                                    <a href="javascript:;" class="hashchange" data-querystr="cls=5"><i class="fa fa-hand-o-right"></i> 现有暂留的党员</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="hashchange" data-querystr="cls=6"><i class="fa fa-hand-o-right"></i> 已转出的暂留党员</a>
                                </li>
                            </ul>
                        </li>
                        <div class="buttons pull-left" style="margin-left: 25px">
                             <shiro:hasPermission name="memberStay:edit">
                                    <a href="javascript:;" class="popupBtn btn btn-info btn-sm"
                                       data-url="${ctx}/memberStay_au?type=${param.type}">
                                        <i class="fa fa-plus"></i> 添加</a>
                             </shiro:hasPermission>
                            <shiro:hasPermission name="${PERMISSION_OWADMIN}">
                                <a class="downloadBtn btn btn-success btn-sm"
                                   href="javascript:;" data-url="${ctx}/memberStay?export=2&type=${param.type}">
                                    <i class="fa fa-download"></i> 汇总导出</a>
                            </shiro:hasPermission>
                        </div>

                        <c:if test="${(cls==1||cls==2||cls==3) && (approvalCountNew+approvalCountBack)>0}">
                            <div class="pull-right"
                                 style="top: 3px; right:10px; position: relative; color: red;  font-weight: bolder">
                                有${approvalCountNew+approvalCountBack}条待审核记录（其中新申请：共${approvalCountNew}条，返回修改：共${approvalCountBack}条）
                            </div>
                        </c:if>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="memberStay:edit">
                                    <c:if test="${cls==1||cls>=4}">
                                        <button class="jqEditBtn btn btn-primary btn-sm"
                                                data-url="${ctx}/user/memberStay"
                                                data-querystr="&type=${param.type}&auth=admin"
                                                data-open-by="page">
                                            <i class="fa fa-edit"></i> 修改信息
                                        </button>
                                    </c:if>
                                </shiro:hasPermission>
                                <c:if test="${cm:isPermitted(PERMISSION_OWADMIN) || cm:hasRole(ROLE_PARTYADMIN)}">
                                    <c:if test="${cls==5||cls==6}">
                                        <button class="jqOpenViewBtn btn btn-danger btn-sm"
                                                data-url="${ctx}/memberStay_transfer_au">
                                            <i class="fa fa-edit"></i> 修改暂留党支部
                                        </button>
                                    </c:if>
                                </c:if>
                                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i
                                        class="fa fa-download"></i> 导出</a>

                                <c:if test="${cls==1}">
                                    <button id="branchApprovalBtn" ${approvalCount>0?'':'disabled'}
                                            class="jqOpenViewBtn btn btn-success btn-sm"
                                            data-url="${ctx}/memberStay_approval"
                                            data-open-by="page"
                                            data-querystr="&type=${param.type}&checkType=1&cls=${cls}"
                                            data-need-id="false"
                                            data-count="${approvalCount}">
                                        <i class="fa fa-sign-in"></i> 党支部审核（${approvalCount}）
                                    </button>
                                </c:if>

                                <c:if test="${cls==2}">
                                    <button id="partyApprovalBtn" ${approvalCount>0?'':'disabled'}
                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/memberStay_approval"
                                            data-open-by="page"
                                            data-querystr="&type=${param.type}&checkType=2&cls=${cls}"
                                            data-need-id="false"
                                            data-count="${approvalCount}">
                                        <i class="fa fa-sign-in"></i> ${_p_partyName}审核（${approvalCount}）
                                    </button>
                                </c:if>

                                <c:if test="${cls==3}">
                                    <button id="odApprovalBtn" ${approvalCount>0?'':'disabled'}
                                            class="jqOpenViewBtn btn btn-danger btn-sm"
                                            data-url="${ctx}/memberStay_approval"
                                            data-open-by="page"
                                            data-querystr="&type=${param.type}&checkType=3&cls=${cls}"
                                            data-need-id="false"
                                            data-count="${approvalCount}">
                                        <i class="fa fa-sign-in"></i> 组织部审核（${approvalCount}）
                                    </button>
                                </c:if>

                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/applyApprovalLog"
                                        data-querystr="&type=<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY%>"
                                        data-open-by="page">
                                    <i class="fa fa-search"></i> 审批记录
                                </button>
                                <c:if test="${cls==3}">
                                注：审批通过后，将转移至暂留党员库
                                </c:if>
                                <c:if test="${cls==5}">
                                    <button class="jqOpenViewBtn btn btn-danger btn-sm"
                                                    data-url="${ctx}/memberStay_abolish">
                                        <i class="fa fa-reply"></i> 撤销
                                    </button>
                                </c:if>
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
                                                <label>用户</label>

                                                <div class="input-group">
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/sysUser_selects"
                                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>${_p_partyName}</label>
                                                <select class="form-control" data-width="350" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/party_selects?auth=1"
                                                        name="partyId" data-placeholder="请选择">
                                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                </select>
                                            </div>

                                            <div class="form-group" style="${(empty branch)?'display: none':''}"
                                                 id="branchDiv">
                                                <label>党支部</label>
                                                <select class="form-control" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/branch_selects?auth=1"
                                                        name="branchId" data-placeholder="请选择党支部">
                                                    <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                                </select>
                                            </div>
                                            <script>
                                                $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                        '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                            </script>

                                            <div class="form-group">
                                                <label>去往国家</label>
                                                <input type="text" name="country" value="${param.country}">
                                            </div>
                                            <div class="form-group">
                                                <label>出国时间</label>

                                                <div class="input-group tooltip-success" data-rel="tooltip"
                                                     title="选择时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择时间范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker"
                                                           type="text" name="_abroadTime" value="${param._abroadTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>预计回国时间</label>

                                                <div class="input-group tooltip-success" data-rel="tooltip"
                                                     title="选择时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择时间范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker"
                                                           type="text" name="_returnTime" value="${param._returnTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>党费缴纳至年月</label>

                                                <div class="input-group tooltip-success" data-rel="tooltip"
                                                     title="选择时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择时间范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker"
                                                           type="text" name="_payTime" value="${param._payTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>手机号码</label>
                                                <input type="text" name="mobile" value="${param.mobile}">
                                            </div>

                                            <div class="form-group">
                                                <label>当前状态</label>

                                                <div class="input-group">
                                                    <select name="status" data-rel="select2" data-placeholder="请选择">
                                                        <option></option>
                                                        <c:forEach var="_status" items="<%=MemberConstants.MEMBER_STAY_STATUS_MAP%>">
                                                            <c:if test="${_status.key>MEMBER_STAY_STATUS_BACK && _status.key<MEMBER_STAY_STATUS_OW_VERIFY}">
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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    function page_reload(){
        $.hashchange();
    }
    function goto_next(goToNext) {
        if (goToNext) {
            if ($("#next").hasClass("disabled") && $("#last").hasClass("disabled"))
                $.hashchange();
            else if (!$("#next").hasClass("disabled"))
                $("#next").click();
            else
                $("#last").click();
        } else {
            page_reload();
        }
    }
    function apply_deny(id, type, goToNext) {

        $.loadModal("${ctx}/memberStay_deny?id=" + id + "&type=" + type + "&goToNext=" + ((goToNext != undefined && goToNext) ? "1" : "0"));
    }
    function apply_pass(id, type, goToNext) {
        if (type == 2) {
            $.loadModal("${ctx}/memberStay_transfer?ids={0}".format(id) + "&goToNext=" + ((goToNext != undefined && goToNext) ? "1" : "0"))
        } else {
            $.post("${ctx}/memberStay_check", {ids: [id], type: type}, function (ret) {
                if (ret.success) {
                    goto_next(goToNext);
                }
            });
        }
    }

    $("#jqGrid").jqGrid({
        /*multiboxonly: false,*/
        ondblClickRow: function () {
        },
        url: '${ctx}/memberStay_data?callback=?&type=${param.type}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '编号', name: 'code', frozen: true},
            {label: '学工号', name: 'user.code', width: 120, frozen: true},
            {
                label: '姓名', name: 'user.realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                return $.member(rowObject.userId, cellvalue);
            }, frozen: true
            },
            {
                label: '所在党组织', name: 'party', width: 450, align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                }, frozen: true
            },
            <c:if test="${cls==5||cls==6}">
            {
                label: '审批表', width: 130, formatter: function (cellvalue, options, rowObject) {

                var html = '<button class="openView btn btn-success btn-xs"'
                        + ' data-url="${ctx}/report/printPreview?type=${param.type==MEMBER_STAY_TYPE_ABROAD?JASPER_PRINT_TYPE_MEMBER_STAY_ABROAD:JASPER_PRINT_TYPE_MEMBER_STAY_INTERNAL}&ids={0}"><i class="fa fa-print"></i> 打印</button>'
                                .format(rowObject.id);

                        html += '&nbsp;<button class="downloadBtn btn btn-primary btn-xs"'
                        + ' data-url="${ctx}/report/member_stay?type=${param.type}&ids={0}&print=2"><i class="fa fa-file-pdf-o"></i> 下载</button>'
                                .format(rowObject.signId);
                return html;
            }
            },
            {label: '打印次数', name: 'printCount'},
            {label: '最近打印时间', width: 150, name: 'lastPrintTime'},
            {label: '最近打印人', name: 'lastPrintUser.realname'},
            </c:if>
            <c:if test="${cls==22||cls==3||cls==5||cls==6}">
            {
                label: '暂留后所在党支部', name: 'toBranchId', width: 250, formatter: function (cellvalue, options, rowObject) {
                return ($.trim(rowObject.toBranchId) == '') ? '-' : _cMap.branchMap[cellvalue].name;
            }
            },
            {
                label: '原党支部负责人',
                name: 'orgBranchAdmin.realname',
                width: 120,
                formatter: function (cellvalue, options, rowObject) {
                    return ($.trim(rowObject.branchId) == '') ? '-' : $.trim(cellvalue);
                }
            },
            {
                label: '原党支部负责人联系电话',
                name: 'orgBranchAdminPhone',
                width: 180,
                formatter: function (cellvalue, options, rowObject) {
                    return ($.trim(rowObject.branchId) == '') ? '-' : $.trim(cellvalue);
                }
            },
            </c:if>
            {label: '人员类别', name: 'userType', formatter: $.jgrid.formatter.MetaType},
            {label: '手机号码', name: 'mobile', width: 120},
            {label: '家庭电话', name: 'phone', width: 120},
            {label: '微信', name: 'weixin', width: 120},
            {label: '电子邮箱', name: 'email', width: 200},
            {label: 'QQ号', name: 'qq'},
            {
                label: '申请保留组织关系起止时间', name: '_time', width: 200, formatter: function (cellvalue, options, rowObject) {
                return $.date(rowObject.saveStartTime, "yyyy.MM") + " ~ " + $.date(rowObject.saveEndTime, "yyyy.MM");
            }
            },
            {label: '党费交纳截止时间', name: 'payTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},

            <c:if test="${param.type==MEMBER_STAY_TYPE_ABROAD}">
            {
                label: '接收函/邀请函',
                align: 'center',
                width: 120,
                formatter: function (cellvalue, options, rowObject) {
                    return $.imgPreview(rowObject.letter, rowObject.user.realname + "-申请组织关系暂留" + ".jpg", '查看');
                }
            },
            {
                label: '出国原因',
                name: 'stayReason',
                width: 250,
                algin: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return cellvalue.replace(/\+\+\+/g, ',');
                }
            },
            {label: '国内通讯地址', name: 'inAddress', width: 200},
            {label: '国外通讯地址', name: 'outAddress', width: 200},
            {label: '去往国家', name: 'country'},
            {label: '留学学校或工作单位', name: 'school', width: 200},
            {
                label: '出国起止时间', name: 'abroadTime', width: 160, formatter: function (cellvalue, options, rowObject) {
                return $.date(rowObject.startTime, "yyyy.MM")
                    + " ~ " + $.date(rowObject.endTime, "yyyy.MM");
            }
            },
            {label: '预计回国时间', name: 'overDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {
                label: '留学方式', name: 'type', width: 80, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return _cMap.MEMBER_STAY_ABROAD_TYPE_MAP_MAP[cellvalue];
            }
            },

            {label: '国内第一联系人', name: 'name1', width: 150},
            {label: '与本人关系', name: 'relate1'},
            {label: '单位', name: 'unit1', width: 200},
            {label: '职务', name: 'post1'},
            {label: '办公电话', name: 'phone1', width: 120},
            {label: '手机号', name: 'mobile1', width: 120},
            {label: '电子邮箱', name: 'email1', width: 200},
            {label: '国内第二联系人', name: 'name2', width: 150},
            {label: '与本人关系', name: 'relate2'},
            {label: '单位', name: 'unit2', width: 200},
            {label: '职务', name: 'post2'},
            {label: '办公电话', name: 'phone2', width: 120},
            {label: '手机号', name: 'mobile2', width: 120},
            {label: '电子邮箱', name: 'email2', width: 200},
            </c:if>
            <c:if test="${param.type==MEMBER_STAY_TYPE_INTERNAL}">
            {
                label: '户档暂留证明',
                align: 'center',
                width: 120,
                formatter: function (cellvalue, options, rowObject) {
                    return $.imgPreview(rowObject.letter, rowObject.user.realname + "-户档暂留证明" + ".jpg", '查看');
                }
            },
            {label: '暂留原因', name: 'stayReason', width: 200},
            {label: '预计转出时间', name: 'overDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '联系人', name: 'name1', width: 150},
            {label: '联系人手机号', name: 'mobile1', width: 120},
            </c:if>
            {
                label: '状态', name: 'statusName', width: 150, formatter: function (cellvalue, options, rowObject) {
                return _cMap.MEMBER_STAY_STATUS_MAP[rowObject.status];
            }
            }<c:if test="${cls==1}">
            , {
                label: '审核类别', name: 'isBackName', width: 150, formatter: function (cellvalue, options, rowObject) {
                    return rowObject.isBack ? "返回修改" : "新申请";
                }
            }</c:if>, {hidden: true, name: 'status'}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#branchApprovalBtn,#partyApprovalBtn,#odApprovalBtn").prop("disabled", true);
            } else if (ids.length == 1) {

                var rowData = $(this).getRowData(ids[0]);
                $("#branchApprovalBtn").prop("disabled", rowData.status != "<%=MemberConstants.MEMBER_STAY_STATUS_APPLY%>");
                $("#partyApprovalBtn").prop("disabled", rowData.status != "<%=MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY%>");
                $("#odApprovalBtn").prop("disabled", rowData.status != "<%=MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY%>");
            } else {
                $("*[data-count]").each(function () {
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#partyApprovalBtn,#odApprovalBtn").prop("disabled", true);
            } else {
                $("*[data-count]").each(function () {
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $.initNavGrid("jqGrid", "jqGridPager");
    <c:if test="${cls==1}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "支部批量审核",
        btnbase: "jqBatchBtn btn btn-success btn-xs",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/memberStay_check" data-querystr="&type=1" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </c:if>
    <c:if test="${cls==2}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "${_p_partyName}批量审核",
        btnbase: "jqBatchBtn btn btn-primary btn-xs",
        buttonicon: "fa fa-check-circle-o",
        onClickButton: function () {
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length == 0) {
                SysMsg.warning("请选择行", "提示");
                return;
            }

            $.loadModal("${ctx}/memberStay_transfer?ids={0}".format(ids))
        }
    });
    </c:if>
    <c:if test="${cls==3}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "组织部批量审核",
        btnbase: "jqBatchBtn btn btn-warning btn-xs",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/memberStay_check" data-querystr="&type=3" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </c:if>
    <c:if test="${cls==1||cls==2||cls==3}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "批量退回申请",
        btnbase: "btn btn-danger btn-xs",
        buttonicon: "fa fa-reply-all",
        onClickButton: function () {
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length == 0) {
                SysMsg.warning("请选择行", "提示");
                return;
            }
            var minStatus;
            for (var key in ids) {
                var rowData = $(this).getRowData(ids[key]);
                if (minStatus == undefined || minStatus > rowData.status) minStatus = rowData.status;
            }

            $.loadModal("${ctx}/memberStay_back?ids={0}&status={1}".format(ids, minStatus))
        }
    });
    </c:if>

    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=userId]'));
    $.register.fancybox();
</script>
