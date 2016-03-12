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
                        <a href="?status=0"><i class="fa fa-circle-o"></i> 未完成审批</a>
                    </li>
                    <li  class="<c:if test="${status==1}">active</c:if>">
                        <a href="?status=1"><i class="fa fa-check"></i> 已完成审批</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
        <div class="myTableDiv"
             data-url-au="${ctx}/applySelf_au"
             data-url-page="${ctx}/applySelf_page"
             data-url-del="${ctx}/applySelf_del"
             data-url-bd="${ctx}/applySelf_batchDel"
             data-url-co="${ctx}/applySelf_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <input type="hidden" name="status" value="${status}">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${cadre.id}">${sysUser.realname}</option>
                </select>
                <div class="input-group tooltip-success" data-rel="tooltip" title="申请日期范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                    <input placeholder="请选择申请日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_applyDate" value="${param._applyDate}"/>
                </div>
                <select name="type" data-rel="select2" data-placeholder="请选择出行时间范围">
                    <option></option>
                    <c:forEach items="${APPLY_SELF_DATE_TYPE_MAP}" var="type">
                        <option value="${type.key}">${type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=type]").val('${param.type}');
                </script>

                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.cadreId ||not empty param._applyDate ||not empty param.type || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="applySelf:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="applySelf:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
            <div class="table-container">
                <table style="min-width: 1800px"  class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>申请日期</th>
                            <th>工作证号</th>
                            <th>姓名</th>
                            <th>所在单位及职务</th>
							<th>出行时间</th>
							<th>回国时间</th>
							<th>出行天数</th>
							<th>前往国家或地区</th>
							<th>因私出国（境）事由</th>
							<th>组织部初审</th>
                        <c:forEach items="${approverTypeMap}" var="type">
                            <th>${type.value.name}审批</th>
                        </c:forEach>
							<th>组织部终审</th>
                        <shiro:hasRole name="cadreAdmin">
							<th>短信提醒</th>
                        </shiro:hasRole>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${applySelfs}" var="applySelf" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(applySelf.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${applySelf.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</td>
                            <td>${sysUser.code}</td>
                            <td><a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id=${applySelf.cadreId}">
                                    ${sysUser.realname}
                            </a></td>
                            <td>${cadre.title}</td>
								<td>${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}</td>
                            <td>${cm:getDayCountBetweenDate(applySelf.startDate, applySelf.endDate)}</td>
								<td>${applySelf.toCountry}</td>
								<td>${fn:replace(applySelf.reason, '+++', ',')}</td>
                                <wo:approvalTd applySelfId="${applySelf.id}" view="false"/>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">

                                    <button data-url="${ctx}/applySelf_view?id=${applySelf.id}"
                                            class="openView btn btn-success btn-mini btn-xs">
                                        <i class="fa fa-info-circle"></i> 详情
                                    </button>
                                    <c:if test="${status==0}">
                                    <shiro:hasPermission name="applySelf:edit">
                                        <button class="editBtn btn btn-primary btn-mini btn-xs" data-id="${applySelf.id}">
                                            <i class="fa fa-edit"></i> 编辑
                                        </button>
                                    </shiro:hasPermission>
                                     <shiro:hasPermission name="applySelf:del">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${applySelf.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </div>
                <wo:page commonList="${commonList}" uri="${ctx}/applySelf_page" target="#page-content" pageNum="5"
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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $(".shortMsgBtn").click(function(){
        var msg = '<p style="padding:30px;font-size:20px;text-indent: 2em; ">';
        var id = $(this).data("id");
        var status = $(this).data("status");
        var name = $(this).data("name");
        if(status)
            msg += name+"同志，您好！您的因私出国（境）申请已通过审批，请登录系统继续申请领取因私出国（境）证件。谢谢！"
        else
            msg += name+"同志，您好！您的因私出国（境）申请未通过审批，请登录系统查看。谢谢！";
        bootbox.confirm({
            buttons: {
                confirm: {
                    label: '确定发送',
                    className: 'btn-success'
                },
                cancel: {
                    label: '取消',
                    className: 'btn-default'
                }
            },
            message: msg,
            callback: function(result) {
                if(result) {
                    SysMsg.success('通知成功', '提示', function(){
                        //page_reload();
                    });
                }
            },
            title: "短信通知"
        });
    });

    $(".approvalBtn").click(function(){

        loadModal("${ctx}/applySelf_approval?applySelfId="+ $(this).data("id") +"&approvalTypeId="+ $(this).data("approvaltypeid"));
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('[data-rel="select2-ajax"]'));
</script>