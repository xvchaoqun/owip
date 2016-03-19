<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li  class="<c:if test="${status==0}">active</c:if>">
                        <a href="?status=0"><i class="fa fa-circle-o"></i> 办理证件审批</a>
                    </li>
                    <li  class="<c:if test="${status==1}">active</c:if>">
                        <a href="?status=1"><i class="fa fa-check"></i> 批准办理新证件</a>
                    </li>
                    <li  class="<c:if test="${status==2}">active</c:if>">
                        <a href="?status=2"><i class="fa fa-times"></i> 未批准办理新证件</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
        <div class="myTableDiv"
             data-url-au="${ctx}/passportApply_au"
             data-url-page="${ctx}/passportApply_page"
             data-url-del="${ctx}/passportApply_del"
             data-url-bd="${ctx}/passportApply_batchDel"
             data-url-co="${ctx}/passportApply_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <input type="hidden" name="status" value="${status}">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${cadre.id}">${sysUser.realname}</option>
                </select>
                <select data-rel="select2" name="classId" data-placeholder="请选择证件名称">
                    <option></option>
                    <c:import url="/metaTypes?__code=mc_passport_type"/>
                </select>
                <script type="text/javascript">
                    $("#searchForm select[name=classId]").val(${param.classId});
                </script>
                <div class="input-group" style="width: 120px">
                    <input class="form-control date-picker" name="year" type="text"
                           data-date-format="yyyy" placeholder="年份" data-date-min-view-mode="2" value="${param.year}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.year ||not empty param.cadreId ||not empty param.classId ||not empty param.applyDate || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="status=${status}">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="passportApply:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="passportApply:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <div class="table-container">
                <table style="min-width: 1200px" class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
                            <th>
                            ${status==0?"申请日期":(status==1?"批准日期":"审批日期")}
                            </th>
							<th>工作证号</th>
							<th>姓名</th>
							<th>所在单位及职务</th>
							<th>申办证件名称</th>
                <c:if test="${status==0}">
							<th>审批状态</th>
                </c:if>
                <c:if test="${status!=0}">
							<th>审批人</th>
                </c:if>
                    <c:if test="${status==1}">
							<th>应交日期</th>
							<th>实交日期</th>
                    </c:if>
                        <c:if test="${status==2}">
                            <th nowrap>未批准原因</th>
                        </c:if>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${passportApplys}" var="passportApply" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(passportApply.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${passportApply.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <td>
                                    ${status==0?(cm:formatDate(passportApply.applyDate,'yyyy-MM-dd')):(cm:formatDate(passportApply.approveTime,'yyyy-MM-dd'))}
                           </td>
                            <td>${sysUser.code}</td>
								<td><a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id=${passportApply.cadreId}">
                                        ${sysUser.realname}
                                </a></td>
                            <td>${unitMap.get(cadre.unitId).name}-${cadre.title}</td>
								<td>${passportTypeMap.get(passportApply.classId).name}</td>
                            <c:if test="${status==0}">
								<td>${PASSPORT_APPLY_STATUS_MAP.get(passportApply.status)}</td></c:if>
                            <c:if test="${status!=0}">
                                <td>
                                    <c:if test="${not empty passportApply.userId}">
                                        <c:set var="_sysUser" value="${cm:getUserById(passportApply.userId)}"/>
                                        <a href="javascript:;" class="openView" data-url="${ctx}/sysUser_view?userId=${_sysUser.id}">
                                                ${_sysUser.realname}
                                        </a>
                                    </c:if>
                                </td>
                            </c:if>

                            <c:if test="${status==1}">
								<td>${cm:formatDate(passportApply.expectDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(passportApply.handleDate,'yyyy-MM-dd')}</td>
                            </c:if>
                            <c:if test="${status==2}">
                                <td>${passportApply.remark}</td>
                            </c:if>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <c:if test="${status==1  && passportApply.status==PASSPORT_APPLY_STATUS_PASS && empty passportApply.handleDate}">
                                        <button data-id="${passportApply.id}" class="returnMsgBtn btn btn-danger btn-mini btn-xs">
                                            <i class="fa fa-hand-paper-o"></i> 催交证件
                                        </button>
                                        <button data-id="${passportApply.id}" class="handleBtn btn btn-success btn-mini btn-xs">
                                            <i class="fa fa-hand-paper-o"></i> 交证件
                                        </button>
                                    </c:if>
                                    <button data-id="${passportApply.id}"
                                            class="openView btn ${passportApply.status==PASSPORT_APPLY_STATUS_INIT?"btn-success":"btn-warning"} btn-mini btn-xs"
                                            data-url="${ctx}/passportApply_check?id=${passportApply.id}">
                                        <c:if test="${passportApply.status==PASSPORT_APPLY_STATUS_INIT}">
                                            <i class="fa fa-check-square-o"></i> 审批
                                        </c:if>
                                        <c:if test="${passportApply.status!=PASSPORT_APPLY_STATUS_INIT}">
                                            <i class="fa fa-info-circle"></i> 申请表
                                        </c:if>
                                    </button>
                                    <%--<c:if test="${passportApply.status==PASSPORT_APPLY_STATUS_PASS}">
                                    <button data-id="${passportApply.id}" class="printBtn btn  btn-info btn-mini btn-xs">
                                        <i class="fa fa-print"></i> 打印审批表
                                    </button>
                                    </c:if>--%>
                                   <%-- <shiro:hasPermission name="passportApply:edit">
                                    <button data-id="${passportApply.id}" class="editBtn btn btn-primary btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>--%>
                                     <%--<shiro:hasPermission name="passportApply:del">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${passportApply.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                      </shiro:hasPermission>--%>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </div>
                <wo:page commonList="${commonList}" uri="${ctx}/passportApply_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
        </div>
                </div></div></div>
        <div id="item-content">
        </div>
    </div>
</div>
<script>

    $(".returnMsgBtn").click(function() {
        var id = $(this).data("id");
        loadModal("${ctx}/shortMsg_view?id={0}&type=passportApplyDraw".format(id));
    });
    // 交证件
    $(".handleBtn").click(function(){
        loadModal("${ctx}/passport_au?applyId="+ $(this).data("id"));
    });
    $(".printBtn").click(function(){
        printWindow("${ctx}/report/passportApply?id="+ $(this).data("id"));
    });
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })
    register_user_select($('[data-rel="select2-ajax"]'));
</script>