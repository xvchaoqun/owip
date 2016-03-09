<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li  class="<c:if test="${type==1}">active</c:if>">
                            <a href="?type=1"><i class="fa fa-credit-card"></i> 因私出国（境）</a>
                        </li>
                        <li  class="<c:if test="${type==2}">active</c:if>">
                            <a href="?type=2"><i class="fa fa-credit-card"></i> 因公出访台湾</a>
                        </li>
                        <li  class="<c:if test="${type==3}">active</c:if>">
                            <a href="?type=3"><i class="fa fa-credit-card"></i> 处理其他事务</a>
                        </li>
                    </ul>

                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active">
        <div class="myTableDiv"
             data-url-au="${ctx}/passportDraw_au"
             data-url-page="${ctx}/passportDraw_page"
             data-url-del="${ctx}/passportDraw_del"
             data-url-bd="${ctx}/passportDraw_batchDel"
             data-url-co="${ctx}/passportDraw_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <input type="hidden" name="type" value="${type}">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${cadre.id}">${sysUser.realname}</option>
                </select>
                <%--<select data-rel="select2" name="classId" data-placeholder="请选择证件名称">
                    <option></option>
                    <c:import url="/metaTypes?__code=mc_passport_type"/>
                </select>--%>
                <div class="input-group tooltip-success" data-rel="tooltip" title="申请日期范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                    <input placeholder="请选择申请日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_applyDate" value="${param._applyDate}"/>
                </div>

                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.cadreId ||not empty param._applyDate || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="type=${type}">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
               <%-- <div class="buttons pull-right">
                    <shiro:hasPermission name="passportDraw:edit">
                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="passportDraw:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>--%>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-actived table-striped table-bordered table-hover table-condensed">
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
                        <th>所在单位和职务</th>
							<th>申请领取证件名称</th>
                        <c:if test="${type==1}">
							<th>因私出国（境）行程</th>
                        </c:if>
                            <c:if test="${type!=3}">
							<th>是否签注</th>
							<th>签注申请表</th>
                            <th>打印签注申请表</th>
                            </c:if>
							<th>组织部审批</th>
							<th>短信通知</th>
							<th>领取证件</th>
							<th>应交组织部日期</th>
							<th>催交证件</th>
							<th>归还证件</th>
							<th>实交组织部日期</th>
							<th>详情</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${passportDraws}" var="passportDraw" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(passportDraw.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <c:set var="passport" value="${cm:getPassport(passportDraw.passportId)}"/>
                        <c:set var="passportType" value="${cm:getMetaType('mc_passport_type', passport.classId)}"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${passportDraw.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <td>${cm:formatDate(passportDraw.applyDate,'yyyy-MM-dd')}</td>
                            <td>${sysUser.code}</td>
                            <td><a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id=${passportDraw.cadreId}">
                                    ${sysUser.realname}
                            </a></td>
                            <td>${unitMap.get(cadre.unitId).name}-${cadre.title}</td>
                            <td>${passportType.name}</td>
                            <c:if test="${type==1}">
                            <td>
                                <a class="openView" href="javascript:;"
                                   data-url="${ctx}/applySelf_view?id=${passportDraw.applyId}"> S${passportDraw.applyId}</a>
                            </td>
                            </c:if>
                            <c:if test="${type!=3}">
                                <c:if test="${passportType.code=='mt_passport_normal'}">
                                    <td>-</td>
                                    <td>-</td>
                                    <td>-</td>
                                </c:if>
                            <c:if test="${passportType.code!='mt_passport_normal'}">
                                <td>${passportDraw.needSign?"是":"否"}</td>
                                <c:if test="${passportDraw.needSign}">
                            <td>
                                <a href="${ctx}/report/passportSign?id=${passportDraw.id}" target="_blank">
                                    签注申请表
                                </a>
                            </td>
                                <td>
                                    <button data-id="${passportDraw.id}" class="printBtn btn btn-info btn-mini">
                                        <i class="fa fa-print"></i> 打印签注申请表
                                    </button>
                                </td>
                                </c:if>
                                <c:if test="${!passportDraw.needSign}">
                                    <td>-</td>
                                    <td>-</td>
                                    </c:if>
                                </c:if>
                            </c:if>
                            <td>
                                <c:if test="${passportDraw.status==PASSPORT_DRAW_STATUS_INIT}">
                                <button data-url="${ctx}/passportDraw_check?id=${passportDraw.id}"  class="openView btn btn-success btn-mini">
                                    <i class="fa fa-check"></i> 组织部审批
                                </button>
                                </c:if>
                                <c:if test="${passportDraw.status!=PASSPORT_DRAW_STATUS_INIT}">
                                    ${PASSPORT_DRAW_STATUS_MAP.get(passportDraw.status)}
                                        </c:if>
                            </td>

                            <td>
                                <c:if test="${passportDraw.status==PASSPORT_DRAW_STATUS_INIT}">-</c:if>
                                <c:if test="${passportDraw.status!=PASSPORT_DRAW_STATUS_INIT}">
                                <button data-id="${passportDraw.id}" data-name="${sysUser.realname}"
                                        data-cls="${passportType.name}"
                                        data-status="${passportDraw.status}" class="shortMsgBtn btn btn-warning btn-mini">
                                    <i class="fa fa-info"></i> 短信通知
                                </button>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${passportDraw.status==PASSPORT_DRAW_STATUS_PASS}">
                                    <c:if test="${passportDraw.drawStatus==PASSPORT_DRAW_DRAW_STATUS_UNDRAW}">
                                    <button data-url="${ctx}/passportDraw_draw?id=${passportDraw.id}" class="openView btn btn-info btn-mini">
                                        <i class="fa fa-hand-lizard-o"></i> 领取证件
                                    </button>
                                    </c:if>
                                    <c:if test="${passportDraw.drawStatus!=PASSPORT_DRAW_DRAW_STATUS_UNDRAW}">
                                        ${PASSPORT_DRAW_DRAW_STATUS_MAP.get(passportDraw.drawStatus)}
                                    </c:if>
                                </c:if>
                                <c:if test="${passportDraw.status!=PASSPORT_DRAW_STATUS_PASS}">-</c:if>
                            </td>
								<td>${cm:formatDate(passportDraw.returnDate,'yyyy-MM-dd')}</td>
                            <c:set value="<%=new Date()%>" var="now"/>
                            <c:if test="${passportDraw.drawStatus==PASSPORT_DRAW_DRAW_STATUS_DRAW}">
                                <c:if test="${cm:compareDate(passportDraw.returnDate, now)==false}">
                            <td>
                                <button data-id="${passportDraw.id}" data-name="${sysUser.realname}"
                                        data-cls="${passportType.name}"
                                        data-drawtime="${cm:formatDate(passportDraw.drawTime,'yyyy年MM月dd日')}"
                                        data-returndate="${cm:formatDate(passportDraw.returnDate,'yyyy年MM月dd日')}" class="returnMsgBtn btn btn-danger btn-mini">
                                    <i class="fa fa-hand-paper-o"></i> 催交证件
                                </button>
                            </td>
                                </c:if>
                                <c:if test="${cm:compareDate(passportDraw.returnDate, now)}">
                                    <td>-</td>
                                </c:if>
                            <td>
                                <button data-url="${ctx}/passportDraw_return?id=${passportDraw.id}" class="openView btn btn-mini">
                                    <i class="fa fa-reply"></i> 归还证件
                                </button>
                            </td>
                            </c:if>
                            <c:if test="${passportDraw.drawStatus!=PASSPORT_DRAW_DRAW_STATUS_DRAW}">
                                <td>-</td>
                                <td>-</td>
                             </c:if>
								<td>${cm:formatDate(passportDraw.realReturnDate,'yyyy-MM-dd')}</td>
                            <td>
                                    <button data-url="${ctx}/passportDraw_view?id=${passportDraw.id}"
                                            class="openView btn btn-success btn-mini">
                                        <i class="fa fa-info-circle"></i> 详情
                                    </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <wo:page commonList="${commonList}" uri="${ctx}/passportDraw_page" target="#page-content" pageNum="5"
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
    $(".returnMsgBtn").click(function(){
        var msg = '<p style="padding:30px;font-size:20px;text-indent: 2em; ">';
        var drawtime = $(this).data("drawtime");
        var returndate = $(this).data("returndate");
        var id = $(this).data("id");
        var name = $(this).data("name");
        var cls = $(this).data("cls");
         msg += name+"同志，您好！您于"+drawtime+"领取因私出国（境）证件，" +
         "应于"+returndate+"交回组织部。目前还未交回，请于二日内将证件交到组织部（主楼A306）。谢谢！"

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
            title: "催交证件"
        });
    });

    $(".shortMsgBtn").click(function(){
        var msg = '<p style="padding:30px;font-size:20px;text-indent: 2em; ">';
        var status = $(this).data("status");
        var id = $(this).data("id");
        var name = $(this).data("name");
        var cls = $(this).data("cls");
        if(status=="${PASSPORT_DRAW_STATUS_PASS}")
            msg += name+"同志，您好！您提交的领取使用"+cls
            +"的申请（编码为：D"+id+"）已通过审批，请派人携带有效证件（教工卡、学生卡或身份证）并凭此短信到组织部（主楼A306）领取证件。谢谢！"
        if(status=="${PASSPORT_DRAW_STATUS_NOT_PASS}")
            msg += name+"同志，您好！您提交的领取使用"+cls+
            "的申请（编码为：D"+id+"）未通过审批，请登录系统查看具体原因。谢谢！";
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

    $(".printBtn").click(function(){
        printWindow("${ctx}/report/passportSign?id="+ $(this).data("id"));
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 200,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>