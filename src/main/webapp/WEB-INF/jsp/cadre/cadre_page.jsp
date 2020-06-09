<%@ page import="sys.utils.JSONUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${empty _pMap['label_staffType']?'个人身份':_pMap['label_staffType']}" var="_p_label_staffType"/>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-url-page="${ctx}/cadre"
                 data-url-co="${ctx}/cadre_changeOrder"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.cadreId ||not empty param.gender ||not empty param.nation
                ||not empty param.startAge||not empty param.endAge||not empty param.startDpAge||not empty param.endDpAge
                ||not empty param.startNowPostAge||not empty param.endNowPostAge||not empty param.startNowLevelAge||not empty param.endNowLevelAge
                ||not empty param._birth||not empty param._cadreGrowTime
                ||not empty param.dpTypes||not empty param.unitIds||not empty param.unitTypes||not empty param.adminLevels
                ||not empty param.maxEdus||not empty param.major ||not empty param.staffTypes ||not empty param.degreeType
                ||not empty param.proPosts ||not empty param.postTypes ||not empty param.proPostLevels
                ||not empty param.isPrincipal ||not empty param.isDouble ||not empty param.hasCrp || not empty param.code
                ||not empty param.leaderTypes  ||not empty param.type  ||not empty param.isDep
                 ||not empty param.state  ||not empty param.title ||not empty param.labels ||not empty param.workTypes
                 ||not empty param.hasAbroadEdu }"/>

                <div class="tabbable">
                    <shiro:lacksPermission name="hide:cadreMenu">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="<c:if test="${status==CADRE_STATUS_CJ}">active</c:if>">
                            <a href="javascript:;" data-url="/cadre?status=${CADRE_STATUS_CJ}" class="loadPage"><i
                                    class="fa fa-flag"></i> ${CADRE_STATUS_MAP.get(CADRE_STATUS_CJ)}</a>
                        </li>

                        <li class="<c:if test="${status==CADRE_STATUS_CJ_LEAVE}">active</c:if>">
                            <a href="javascript:;" data-url="/cadre?status=${CADRE_STATUS_CJ_LEAVE}"
                               class="loadPage"><i
                                    class="fa fa-history"></i> ${CADRE_STATUS_MAP.get(CADRE_STATUS_CJ_LEAVE)}</a>
                        </li>
                        <c:if test="${_p_hasKjCadre}">
                        <li class="<c:if test="${status==CADRE_STATUS_KJ}">active</c:if>">
                            <a href="javascript:;" data-url="/cadre?status=${CADRE_STATUS_KJ}" class="loadPage"><i
                                    class="fa fa-flag"></i> ${CADRE_STATUS_MAP.get(CADRE_STATUS_KJ)}</a>
                        </li>
                        <li class="<c:if test="${status==CADRE_STATUS_KJ_LEAVE}">active</c:if>">
                            <a href="javascript:;" data-url="/cadre?status=${CADRE_STATUS_KJ_LEAVE}"
                               class="loadPage"><i
                                    class="fa fa-history"></i> ${CADRE_STATUS_MAP.get(CADRE_STATUS_KJ_LEAVE)}</a>
                        </li>
                        </c:if>
                        <shiro:hasPermission name="cadre:list">
                            <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative">
                                <shiro:hasPermission name="leader:menu">
                                <button type="button" class="popupBtn btn btn-danger btn-sm"
                                        data-url="${ctx}/cadre_search"><i class="fa fa-search"></i> 查询账号所属干部库
                                </button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="cadre:edit">
                                    <button type="button" class="popupBtn btn btn-info btn-sm"
                                            data-url="${ctx}/cadre_transfer"><i class="fa fa-recycle"></i> 干部库转移
                                    </button>
                                </shiro:hasPermission>
                            </div>
                        </shiro:hasPermission>
                    </ul>
                    </shiro:lacksPermission>
                    <div class="tab-content multi-row-head-table">
                        <div class="tab-pane in active rownumbers">
                            <div class="jqgrid-vertical-offset buttons">

                                <shiro:hasPermission name="cadre:changeCode">
                                    <a href="javascript:;" class="jqEditBtn btn btn-warning btn-sm"
                                       data-url="${ctx}/cadre_changeCode"
                                       data-id-name="cadreId">
                                        <i class="fa fa-refresh"></i> 更换工号</a>
                                </shiro:hasPermission>

                                <c:if test="${cm:roleIsPermitted(ROLE_CADREADMIN, 'cadreInspect:list')}">
                                    <c:if test="${status==CADRE_STATUS_CJ_LEAVE}">
                                        <shiro:hasPermission name="cadre:edit">
                                            <button class="jqBatchBtn btn btn-warning btn-sm"
                                                    data-title="重新任用"
                                                    data-msg="确定重新任用这{0}个干部吗？（添加到考察对象中）"
                                                    data-url="${ctx}/cadre_re_assign" data-callback="_reAssignCallback">
                                                <i class="fa fa-reply"></i> 重新任用
                                            </button>
                                        </shiro:hasPermission>
                                    </c:if>
                                </c:if>

                                <shiro:hasPermission name="cadre:edit">
                                    <a class="popupBtn btn btn-info btn-sm btn-success"
                                       data-url="${ctx}/cadre_au?status=${status}"><i class="fa fa-plus"></i> 添加</a>
                                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                            data-url="${ctx}/cadre_au"
                                            data-querystr="&status=${status}">
                                        <i class="fa fa-edit"></i> 修改信息
                                    </button>
                                </shiro:hasPermission>
                                <c:if test="${status==CADRE_STATUS_CJ||status==CADRE_STATUS_KJ}">
                                    <shiro:hasPermission name="cadre:edit">
                                        <button class="jqOpenViewBtn btn btn-success btn-sm"
                                                data-url="${ctx}/cadre_promote">
                                            <i class="fa fa-level-up"></i> 提任${status==CADRE_STATUS_CJ?'校领导':'处级干部'}
                                        </button>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="cadre:leave">
                                        <button class="jqOpenViewBtn btn btn-danger btn-sm"
                                                data-width="700"
                                                data-url="${ctx}/cadre_leave">
                                            <i class="fa fa-sign-out"></i> 离任
                                        </button>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="cadre:import">
                                        <div class="btn-group">
                                            <button data-toggle="dropdown"
                                                    data-rel="tooltip" data-placement="top" data-html="true"
                                                    title="<div style='width:180px'>批量导入干部信息入口</div>"
                                                    class="btn btn-info btn-sm dropdown-toggle tooltip-success">
                                                <i class="fa fa-download"></i> 批量导入 <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu dropdown-success" role="menu"
                                                style="z-index: 1031">
                                                <li>
                                                    <a href="javascript:;" class="popupBtn"
                                                       data-url="${ctx}/cadre_import?status=${status}">
                                                        <i class="fa fa-file-excel-o"></i> 导入现任${status==CADRE_STATUS_CJ?'处级':'科级'}干部信息</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="popupBtn"
                                                       data-url="${ctx}/cadre_import?status=${status==CADRE_STATUS_CJ?CADRE_STATUS_CJ_LEAVE:CADRE_STATUS_KJ_LEAVE}">
                                                        <i class="fa fa-file-excel-o"></i> 导入离任${status==CADRE_STATUS_CJ?'处级':'科级'}干部信息</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="popupBtn"
                                                       data-url="${ctx}/cadrePost_import?isMainPost=1">
                                                        <i class="fa fa-file-excel-o"></i> 干部任职情况（第一主职）</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="popupBtn"
                                                       data-url="${ctx}/cadrePost_importWorkTime">
                                                        <i class="fa fa-file-excel-o"></i> 导入任职时间（第一主职）</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="popupBtn"
                                                       data-url="${ctx}/cadreAdminLevel_import">
                                                        <i class="fa fa-file-excel-o"></i> 导入任职级情况</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="popupBtn"
                                                       data-url="${ctx}/cadrePost_import?isMainPost=0">
                                                        <i class="fa fa-file-excel-o"></i> 导入干部任职情况（兼职）</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="popupBtn"
                                                       data-url="${ctx}/cadreParttime_import?isMainPost=0">
                                                        <i class="fa fa-file-excel-o"></i> 导入社会或学术兼职情况</a>
                                                </li><li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="popupBtn"
                                                       data-url="${ctx}/cadreTrain_import?isMainPost=0">
                                                        <i class="fa fa-file-excel-o"></i> 导入培训情况</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="popupBtn"
                                                       data-url="${ctx}/cadreCompany_import?isMainPost=0">
                                                        <i class="fa fa-file-excel-o"></i> 导入企业、社团兼职情况</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="popupBtn"
                                                       data-url="${ctx}/cadreEva_import?isMainPost=0">
                                                        <i class="fa fa-file-excel-o"></i> 导入年度考核结果</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="popupBtn"
                                                       data-url="${ctx}/cadreAdform_import">
                                                        <i class="fa fa-file-excel-o"></i> 导入中组部干部任免审批表</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </shiro:hasPermission>
                                </c:if>
                                <shiro:hasPermission name="cadre:edit">
                                    <div class="btn-group">
                                        <button data-toggle="dropdown"
                                                data-rel="tooltip" data-placement="top" data-html="true"
                                                title="<div style='width:180px'>批量干部信息入口</div>"
                                                class="btn btn-warning btn-sm dropdown-toggle tooltip-success">
                                            <i class="fa fa-download"></i> 批量操作 <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu dropdown-success" role="menu" style="z-index: 1031">
                                            <li>
                                                <a href="javascript:;" class="popupBtn"
                                                   data-url="${ctx}/cadre_batchSort?status=${status}">
                                                    <i class="fa fa-file-excel-o"></i> 批量排序</a>
                                            </li>
                                            <li role="separator" class="divider"></li>
                                            <li>
                                                <a href="javascript:;" class="jqRunBtn" data-grid-id="#jqGrid"
                                                   data-title="更新"
                                                   data-msg="确定更新这{0}条数据（<span class='text-danger'>更新所有的“无此类情况”为“是”</span>）？"
                                                   data-url="${ctx}/cadreInfoCheck_batchUpdate?status=${status}">
                                                    <i class="fa fa-check-circle-o"></i> 更新“无此类情况”为“是”</a>
                                            </li>
                                        </ul>
                                    </div>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="cadre:export">
                                    <div class="btn-group">
                                        <button data-toggle="dropdown"
                                                data-rel="tooltip" data-placement="top" data-html="true"
                                                title="<div style='width:180px'>导出选中记录或所有搜索结果</div>"
                                                class="btn btn-success btn-sm dropdown-toggle tooltip-success">
                                            <i class="fa fa-download"></i> 导出 <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu dropdown-success" role="menu" style="z-index: 1031">
                                            <shiro:hasPermission name="cadre:list">
                                                <li class="dropdown-hover" data-stopPropagation="true">
                                                    <a href="javascript:;" data-need-id="false">
                                                        <i class="fa fa-file-excel-o"></i> 导出干部一览表（全部字段）
                                                        <i class="ace-icon fa fa-caret-right pull-right"></i>
                                                    </a>
                                                    <div class="dropdown-menu" style="width: 675px;top:-220px;">
                                                        <form class="form-horizontal" id="exportForm">
                                                            <div style="padding: 7px 0 10px 10px">
                                                                <c:forEach items="${titles}" var="title" varStatus="vs">
                                                                    <div style="padding-left:5px;float: left;width:220px">
                                                                        <input class="big" type="checkbox"
                                                                               value="${vs.index}">
                                                                            ${fn:split(title, "|")[0]}</div>
                                                                </c:forEach>
                                                                <div style="clear: both"/>
                                                            </div>
                                                            <div class="form-actions center">
                                                                <div style="position: absolute; float:left; left:10px;padding-top: 3px">
                                                                    <input type="button" id="btnSelectAll"
                                                                           class="btn btn-success btn-xs" value="全选"/>
                                                                    <input type="button" id="btnDeselectAll"
                                                                           class="btn btn-danger btn-xs" value="全不选"/>
                                                                </div>
                                                                <button type="button"
                                                                        class="jqExportBtn btn btn-success"
                                                                        data-need-id="false"
                                                                        data-url="${ctx}/cadre_data"
                                                                        data-querystr="format=1">
                                                                    <i class="fa fa-file-excel-o"></i> 导出
                                                                </button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="cadre:list2">
                                                <li>
                                                    <a href="javascript:;" class="jqExportBtn"
                                                       data-need-id="false" data-url="${ctx}/cadre_data"
                                                       data-querystr="format=1">
                                                        <i class="fa fa-file-excel-o"></i> 导出干部一览表（全部字段）</a>
                                                </li>
                                            </shiro:hasPermission>
                                            <c:if test="${status==CADRE_STATUS_CJ||status==CADRE_STATUS_KJ}">
                                                <shiro:hasPermission name="cadre:list">
                                                    <li role="separator" class="divider"></li>
                                                    <li>
                                                        <a href="javascript:;" class="jqExportBtn"
                                                           data-need-id="false" data-url="${ctx}/cadre_data"
                                                           data-querystr="format=2">
                                                            <i class="fa fa-file-excel-o"></i> 导出干部名单（部分字段，可单页打印）</a>
                                                    </li>
                                                    <li role="separator" class="divider"></li>
                                                    <li>
                                                        <a href="javascript:;" class="jqExportBtn"
                                                           data-need-id="false" data-url="${ctx}/cadreEdu_data">
                                                            <i class="fa fa-file-excel-o"></i> 导出学习经历（批量）</a>
                                                    </li>
                                                    <li role="separator" class="divider"></li>
                                                    <li>
                                                        <a href="javascript:;" class="jqExportBtn"
                                                           data-need-id="false" data-url="${ctx}/cadreWork_data">
                                                            <i class="fa fa-file-excel-o"></i> 导出工作经历（批量）</a>
                                                    </li>
                                                    <li role="separator" class="divider"></li>
                                                    <li>
                                                        <a href="javascript:;" class="jqExportBtn"
                                                           data-need-id="false" data-url="${ctx}/cadreEva_data">
                                                            <i class="fa fa-file-excel-o"></i> 导出近五年考核结果（批量）</a>
                                                    </li>
                                                </shiro:hasPermission>
                                                <shiro:hasPermission name="cadre:exportFamily">
                                                    <li role="separator" class="divider"></li>
                                                    <li>
                                                        <a href="javascript:;" class="jqExportBtn"
                                                           data-need-id="false" data-url="${ctx}/cadreFamily_data">
                                                            <i class="fa fa-file-excel-o"></i> 导出家庭成员（批量）</a>
                                                    </li>
                                                </shiro:hasPermission>
                                            </c:if>
                                            <li role="separator" class="divider"></li>
                                            <li>
                                                <a href="javascript:;" class="jqExportBtn"
                                                   data-need-id="false" data-url="${ctx}/cadreParttime_data">
                                                    <i class="fa fa-file-excel-o"></i> 导出社会或学术兼职（批量）</a>
                                            </li>
                                            <li role="separator" class="divider"></li>
                                            <li>
                                                <a href="javascript:;" class="jqExportBtn"
                                                   data-need-id="false" data-url="${ctx}/cadreTrain_data">
                                                    <i class="fa fa-file-excel-o"></i> 导出培训情况（批量）</a>
                                            </li>
                                            <li role="separator" class="divider"></li>
                                            <li>
                                                <a href="javascript:;" class="jqExportBtn"
                                                   data-need-id="false" data-url="${ctx}/cadreCompany_data"
                                                   data-querystr="cadreStatus=${status}">
                                                    <i class="fa fa-file-excel-o"></i> 导出企业、社团兼职（批量）</a>
                                            </li>
                                            <shiro:hasPermission name="cadre:list">
                                            <li role="separator" class="divider"></li>
                                            <li>
                                                <a href="javascript:;" class="jqExportBtn"
                                                   data-need-id="false"
                                                   data-export="2"
                                                   data-url="${ctx}/cadre_data?format=1">
                                                    <i class="fa fa-file-excel-o"></i> 导出Word版任免审批表（批量）</a>
                                            </li>
                                            <li role="separator" class="divider"></li>
                                            <li>
                                                <a href="javascript:;" class="jqExportBtn"
                                                   data-need-id="false"
                                                   data-export="2"
                                                   data-url="${ctx}/cadre_data?format=2">
                                                    <i class="fa fa-file-excel-o"></i> 导出中组部版任免审批表（批量）</a>
                                            </li>
                                            <li role="separator" class="divider"></li>
                                            <li>
                                                <a href="javascript:;" class="jqExportBtn"
                                                   data-need-id="false"
                                                   data-export="3"
                                                   data-url="${ctx}/cadre_data">
                                                    <i class="fa fa-file-excel-o"></i> 导出信息采集表（批量）</a>
                                            </li>
                                            <li role="separator" class="divider"></li>
                                            <li>
                                                <a href="javascript:;" class="jqExportBtn"
                                                   data-need-id="false"
                                                   data-export="6"
                                                   data-url="${ctx}/cadre_data">
                                                <i class="fa fa-file-excel-o"></i> 导出简版干部信息表（批量）</a>
                                            </li>
                                            <li role="separator" class="divider"></li>
                                            <li>
                                                <a href="javascript:;" class="jqExportBtn"
                                                   data-need-id="false"
                                                   data-export="5"
                                                   data-url="${ctx}/cadre_data">
                                                    <i class="fa fa-info-circle"></i> 干部信息完整性校验结果（批量）</a>
                                            </li>

                                            </shiro:hasPermission>
                                        </ul>
                                    </div>

                                </shiro:hasPermission>

                                <shiro:hasPermission name="cadre:archive">
                                    <button class="jqOpenViewBatchBtn btn btn-primary btn-sm"
                                            data-need-id="false"
                                            data-ids-name="cadreIds"
                                            data-open-by="page"
                                            data-url="${ctx}/cadre_search_brief">
                                        <i class="fa fa-hand-pointer-o"></i> 提取简介
                                    </button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="cadre:del">
                                    <button data-url="${ctx}/cadre_batchDel"
                                            data-title="删除"
                                            data-msg="<span style='font-size:larger'>确定删除这{0}条数据？</span>
                                                        <br/><br/><div class='text-danger' style='text-indent:0;color:red'>注：<br/>
                                                        1、该操作将删除所有干部档案及干部任免审批表的相关数据，包含学习经历、工作经历、家庭情况等；<br/>
                                                        2、删除后数据不可恢复，请谨慎操作。
                                                        </div>"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-danger btn-sm">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                                <div class="pull-right hidden-sm hidden-xs">
                                    <select id="sortBy" data-placeholder="请选择排序方式" data-width="250">
                                        <option></option>
                                        <option value="birth_asc">按年龄排序(降序)</option>
                                        <option value="birth_desc">按年龄排序(升序)</option>
                                        <option value="npWorkTime_asc">按现职务始任年限排序(降序)</option>
                                        <option value="npWorkTime_desc">按现职务始任年限排序(升序)</option>
                                        <option value="sWorkTime_asc">按现职级年限排序(降序)</option>
                                        <option value="sWorkTime_desc">按现职级年限排序(升序)</option>
                                        <option value="growTime">按党派加入时间排序</option>
                                        <option value="arriveTime">按到校时间排序</option>
                                        <option value="finishTime">按毕业时间排序</option>
                                    </select>
                                    <script>
                                        $("#sortBy").val('${param.sortBy}');
                                        $("#searchForm input[name=sortBy]").val('${param.sortBy}');
                                        $("#sortBy").select2({
                                          theme: "default"
                                        }).change(function () {
                                            $("#searchForm input[name=sortBy]").val($(this).val());
                                            $("#searchForm .jqSearchBtn").click();
                                            if($(this).val()==''){
                                                throw new Error();
                                            }
                                        })
                                    </script>
                                </div>
                            </div>

                            <div class="jqgrid-vertical-offset widget-box collapsed hidden-sm hidden-xs">
                                <div class="widget-header">
                                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                                    <div class="widget-toolbar">
                                        <a href="javascript:;" data-action="collapse">
                                            <i class="ace-icon fa fa-chevron-down"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="widget-body" <%--style="position: fixed;z-index: 102"--%>>
                                    <div class="widget-main no-padding">
                                        <form class="form-inline search-form" id="searchForm">
                                            <input type="hidden" name="cols">
                                            <input type="hidden" name="sortBy">
                                            <input type="hidden" name="status" value="${status}">
                                            <div class="columns">
                                                <div class="column">
                                                    <label>姓名</label>
                                                    <div class="input">
                                                        <select data-rel="select2-ajax"
                                                                data-ajax-url="${ctx}/cadre_selects?status=${status}"
                                                                name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                            <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>性别</label>
                                                    <div class="input">
                                                        <select name="gender" data-width="100" data-rel="select2"
                                                                data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="<%=SystemConstants.GENDER_MALE%>">男</option>
                                                            <option value="<%=SystemConstants.GENDER_FEMALE%>">女
                                                            </option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=gender]").val('${param.gender}');
                                                        </script>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>政治面貌</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="dpTypes"
                                                                style="width: 250px;">
                                                            <option value="-1">非中共党员</option>
                                                            <option value="0">中共党员</option>
                                                            <c:import url="/metaTypes?__code=mc_democratic_party"/>
                                                           <shiro:hasRole name="${ROLE_SUPER}">
                                                            <option value="-2">空</option>
                                                           </shiro:hasRole>
                                                        </select>
                                                    </div>
                                                </div>
                                                <c:if test="${fn:length(staffTypes)>0}">
                                                <div class="column">
                                                    <label>${_p_label_staffType}</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="staffTypes">
                                                            <c:forEach items="${staffTypes}" var="staffType">
                                                                <option value="${staffType}">${staffType}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                </c:if>
                                                <div class="column">
                                                    <label>民族</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="nation">
                                                            <option value="-1">少数民族</option>
                                                            <c:forEach var="nation" items="${cm:getMetaTypes('mc_nation').values()}">
                                                                <option value="${nation.name}">${nation.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>所在单位及职务</label>
                                                    <div class="input">
                                                        <input type="text" name="title"
                                                               value="${param.title}">
                                                    </div>
                                                </div>
                                                 <c:if test="${cm:getMetaTypes('mc_cadre_label').size()>0}">
                                                <div class="column">
                                                    <label>干部标签</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="labels"
                                                                data-placeholder="请选择">
                                                            <c:import url="/metaTypes?__code=mc_cadre_label"/>
                                                        </select>
                                                    </div>
                                                </div>
                                                     </c:if>
                                                    <c:if test="${_p_useCadreState}">
                                                <div class="column">
                                                    <label>${_pMap['cadreStateName']}</label>
                                                    <div class="input">
                                                        <select data-rel="select2" data-width="100" name="state"
                                                                    data-placeholder="请选择">
                                                                <option></option>
                                                                <c:import url="/metaTypes?__code=mc_cadre_state"/>
                                                            </select>
                                                            <script type="text/javascript">
                                                                $("#searchForm select[name=state]").val(${param.state});
                                                            </script>
                                                    </div>
                                                </div>
                                                        </c:if>
                                                <c:if test="${fn:length(authorizedTypes)>0}">
                                                <div class="column">
                                                    <label>编制类别</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="authorizedTypes">
                                                            <c:forEach items="${authorizedTypes}" var="authorizedType">
                                                                <option value="${authorizedType}">${authorizedType}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                    </c:if>
                                                <div class="column">
                                                    <label>部门属性</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="unitTypes">
                                                            <c:import url="/metaTypes?__code=mc_unit_type"/>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>出生日期</label>
                                                    <div class="input">
                                                            <input placeholder="请选择出生日期范围" data-rel="date-range-picker"
                                                                   class="form-control date-range-picker"
                                                                   type="text" name="_birth" value="${param._birth}"/>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>党派加入时间</label>
                                                    <div class="input">
                                                            <input placeholder="请选择党派加入时间范围"
                                                                   data-rel="date-range-picker"
                                                                   class="form-control date-range-picker"
                                                                   type="text" name="_cadreGrowTime"
                                                                   value="${param._cadreGrowTime}"/>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>所在单位</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="unitIds">
                                                            <c:forEach var="unitType"
                                                                       items="${cm:getMetaTypes('mc_unit_type')}">
                                                                <optgroup label="${unitType.value.name}">
                                                                    <c:forEach
                                                                            items="${unitListMap.get(unitType.value.id)}"
                                                                            var="unitId">
                                                                        <c:set var="unit"
                                                                               value="${unitMap.get(unitId)}"></c:set>
                                                                        <option value="${unit.id}">${unit.name}</option>
                                                                    </c:forEach>
                                                                </optgroup>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <c:if test="${status==CADRE_STATUS_CJ_LEAVE || status==CADRE_STATUS_KJ_LEAVE}">
                                                <div class="column">
                                                    <label>历史单位</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="unitIds">
                                                            <c:forEach var="unitType"
                                                                       items="${cm:getMetaTypes('mc_unit_type')}">
                                                                <optgroup label="${unitType.value.name}">
                                                                    <c:forEach
                                                                            items="${historyUnitListMap.get(unitType.value.id)}"
                                                                            var="unitId">
                                                                        <c:set var="unit"
                                                                               value="${unitMap.get(unitId)}"></c:set>
                                                                        <option value="${unit.id}">${unit.name}</option>
                                                                    </c:forEach>
                                                                </optgroup>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                    </c:if>
                                                <div class="column">
                                                    <label>年龄</label>
                                                    <div class="input">
                                                        <input class="num" type="text" name="startAge"
                                                               value="${param.startAge}"> 至 <input class="num"
                                                                                                   type="text"
                                                                                                   name="endAge"
                                                                                                   value="${param.endAge}">
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>党龄</label>
                                                    <div class="input">
                                                        <input class="num" type="text" name="startDpAge"
                                                               value="${param.startDpAge}"> 至 <input class="num"
                                                                                                     type="text"
                                                                                                     name="endDpAge"
                                                                                                     value="${param.endDpAge}">
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>行政级别</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="adminLevels">
                                                            <c:import url="/metaTypes?__code=mc_admin_level"/>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>最高学历</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="maxEdus">
                                                            <c:import url="/metaTypes?__code=mc_edu"/>
                                                            <option value="-1">无(仅查询无最高学历的干部)</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>最高学位</label>
                                                    <div class="input">
                                                        <select data-rel="select2" data-placeholder="请选择"
                                                                name="degreeType">
                                                            <option></option>
                                                            <c:forEach items="<%=SystemConstants.DEGREE_TYPE_MAP%>"
                                                                       var="_type">
                                                                <option value="${_type.key}">${_type.value}</option>
                                                            </c:forEach>
                                                            <option value="-1">无</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=degreeType]").val('${param.degreeType}');
                                                        </script>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>所学专业</label>
                                                    <div class="input">
                                                        <input type="text" name="major"
                                                               value="${param.major}">
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>专业技术职务</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="proPosts">
                                                            <c:forEach items="${proPosts}" var="proPost">
                                                                <option value="${proPost}">${proPost}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>职务属性</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="postTypes">
                                                            <c:import url="/metaTypes?__code=mc_post"/>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>现职务始任年限</label>
                                                    <div class="input">
                                                        <input class="num" type="text" name="startNowPostAge"
                                                               value="${param.startNowPostAge}"> 至 <input class="num"
                                                                                                          type="text"
                                                                                                          name="endNowPostAge"
                                                                                                          value="${param.endNowPostAge}">
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>职称级别</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="proPostLevels">
                                                            <c:forEach items="${proPostLevels}" var="proPostLevel">
                                                                <option value="${proPostLevel}">${proPostLevel}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <shiro:hasRole name="${ROLE_ADMIN}">
                                                <div class="column">
                                                    <label class="rows">第一主职是否已关联岗位</label>
                                                    <div class="input">
                                                        <select name="firstUnitPost" data-width="100"
                                                                data-rel="select2" data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">是</option>
                                                            <option value="0">否</option>
                                                            <shiro:hasRole name="${ROLE_SUPER}">
                                                            <option value="-1">缺第一主职</option>
                                                            </shiro:hasRole>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=firstUnitPost]").val('${param.firstUnitPost}');
                                                        </script>
                                                    </div>
                                                </div>
                                                    </shiro:hasRole>
                                                <div class="column">
                                                    <label>是否正职</label>
                                                    <div class="input">
                                                        <select name="isPrincipal" data-width="100"
                                                                data-rel="select2" data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">是</option>
                                                            <option value="0">否</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=isPrincipal]").val('${param.isPrincipal}');
                                                        </script>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>现职级始任年限</label>
                                                    <div class="input">
                                                        <input class="num" type="text" name="startNowLevelAge"
                                                               value="${param.startNowLevelAge}"> 至 <input class="num"
                                                                                                           type="text"
                                                                                                           name="endNowLevelAge"
                                                                                                           value="${param.endNowLevelAge}">
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>是否双肩挑</label>
                                                    <div class="input">
                                                        <select name="isDouble" data-width="100" data-rel="select2"
                                                                data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">是</option>
                                                            <option value="0">否</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=isDouble]").val('${param.isDouble}');
                                                        </script>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label class="rows">工作经历<br/>
                                                        (<input ${param.andWorkTypes==1?'checked':''} style="vertical-align: -2px" type="checkbox" name="andWorkTypes" value="1">交集
                                                        <input ${param.andWorkTypes!=1?'checked':''} style="vertical-align: -2px" type="checkbox" name="andWorkTypes" value="0">并集)</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="workTypes" data-placeholder="请选择">
                                                            <c:import url="/metaTypes?__code=mc_cadre_work_type"/>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>是否班子负责人</label>
                                                    <div class="input">
                                                        <select class="multiselect" multiple="" name="leaderTypes">
                                                            <c:forEach
                                                                    items="<%=SystemConstants.UNIT_POST_LEADER_TYPE_MAP%>"
                                                                    var="leaderType">
                                                                <option value="${leaderType.key}">${leaderType.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>干部类别</label>
                                                    <div class="input">
                                                        <select name="isDep" data-width="150" data-rel="select2"
                                                                data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">院系干部</option>
                                                            <option value="0">机关干部</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=isDep]").val('${param.isDep}');
                                                        </script>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>是否有挂职经历</label>
                                                    <div class="input">
                                                        <select name="hasCrp" data-width="100" data-rel="select2"
                                                                data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">是</option>
                                                            <option value="0">否</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=hasCrp]").val('${param.hasCrp}');
                                                        </script>
                                                    </div>
                                                </div>
                                                <div class="column">
                                                    <label>国外学习经历</label>
                                                    <div class="input">
                                                        <select name="hasAbroadEdu" data-width="100" data-rel="select2"
                                                                data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">有</option>
                                                            <option value="0">无</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=hasAbroadEdu]").val('${param.hasAbroadEdu}');
                                                        </script>
                                                    </div>
                                                </div>
                                                <%--<div class="column">
                                                    <label></label>
                                                    <div class="input">

                                                    </div>
                                                </div>--%>
                                            </div>
                                            <div class="clearfix"></div>

                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i
                                                        class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-querystr="status=${status}">
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
    $.each(${cm:toJSONObject(param)}, function(name, val){
        if($.trim(val)!=''){
            $("[name='"+name+"']", ".search-form .columns .column .input").closest(".column").find("label").addClass("bolder red");
        }
    })
    $("ul.dropdown-menu").on("click", "[data-stopPropagation]", function (e) {
        //console.log($(e.target).hasClass("jqExportBtn"))
        if (!$(e.target).hasClass("jqExportBtn")) {
            e.stopPropagation();
        }
    });
    $("input[name=andWorkTypes]").click(function () {
        if($(this).is(":checked")){
            $("input[name=andWorkTypes]").not(this).prop("checked", false);
        }else{
            $(this).prop("checked", true);
        }
    });
    $("#btnSelectAll").click(function () {
        $("#exportForm :checkbox").prop("checked", true);
        _updateCols()
    });
    $("#btnDeselectAll").click(function () {
        $("#exportForm :checkbox").prop("checked", false);
        _updateCols()
    });
    $("#exportForm :checkbox").click(function () {
        _updateCols()
    });

    function _updateCols() {
        var cols = $.map($("#exportForm :checkbox:checked"), function (chk) {
            return $(chk).val();
        });
        $("#searchForm input[name=cols]").val(cols.join(','));
    }

    $.register.multiselect($('#searchForm select[name=nation]'), ${cm:toJSONArray(selectNations)});
    $.register.multiselect($('#searchForm select[name=dpTypes]'), ${cm:toJSONArray(selectDpTypes)});
    $.register.multiselect($('#searchForm select[name=unitIds]'), ${cm:toJSONArray(selectUnitIds)}, {
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false
    });
    $.register.multiselect($('#searchForm select[name=unitTypes]'), ${cm:toJSONArray(selectUnitTypes)});
    $.register.multiselect($('#searchForm select[name=adminLevels]'), ${cm:toJSONArray(selectAdminLevels)});
    $.register.multiselect($('#searchForm select[name=maxEdus]'), ${cm:toJSONArray(selectMaxEdus)});
    $.register.multiselect($('#searchForm select[name=postTypes]'), ${cm:toJSONArray(selectPostTypes)});
    $.register.multiselect($('#searchForm select[name=proPosts]'), ${cm:toJSONArray(selectProPosts)});
    $.register.multiselect($('#searchForm select[name=proPostLevels]'), ${cm:toJSONArray(selectProPostLevels)});
    $.register.multiselect($('#searchForm select[name=leaderTypes]'), ${cm:toJSONArray(selectLeaderTypes)});
    $.register.multiselect($('#searchForm select[name=labels]'), ${cm:toJSONArray(selectLabels)});
    $.register.multiselect($('#searchForm select[name=staffTypes]'), ${cm:toJSONArray(selectStaffTypes)});
    $.register.multiselect($('#searchForm select[name=authorizedTypes]'), ${cm:toJSONArray(selectAuthorizedTypes)});
    $.register.multiselect($('#searchForm select[name=workTypes]'), ${cm:toJSONArray(selectWorkTypes)});
    function _reAssignCallback() {
        $.hashchange('', '${ctx}/cadreInspect');
    }

    $("#jqGrid").jqGrid({
        //forceFit:true,
        rownumbers: true,
        url: '${ctx}/cadre_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: ${(status==CADRE_STATUS_CJ||status==CADRE_STATUS_KJ)
        ?(cm:isPermitted("cadre:list")?"colModels.cadre":"colModels.cadre2")
        :(cm:isPermitted("cadre:list")?"colModels.cadreLeave":"colModels.cadreLeave2")}
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });

    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=cadreId]'));
</script>