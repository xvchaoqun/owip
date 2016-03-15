<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/passport_au"
             data-url-page="${ctx}/passport_page"
             data-url-del="${ctx}/passport_del"
             data-url-bd="${ctx}/passport_batchDel"
             data-url-ba="${ctx}/passport_abolish"
             data-url-co="${ctx}/passport_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param.classId
                ||not empty param.safeBoxId ||not empty param.type || not empty param.code }"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <jsp:include page="menu.jsp"/>

                    <div class="buttons pull-right" style="top: -3px; right:10px; position: relative">
                        <c:if test="${status==PASSPORT_TYPE_KEEP}">
                            <div class="buttons pull-right">
                                <shiro:hasPermission name="passport:edit">
                                    <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加证件信息</a>
                                </shiro:hasPermission>
                                <a class="importBtn btn btn-success btn-sm tooltip-success"
                                   data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i> 批量导入</a>
                                <c:if test="${commonList.recNum>0}">
                                    <a class="batchAbolishBtn btn btn-warning btn-sm">
                                        <i class="fa fa-times"></i> 作废
                                    </a>
                                    <shiro:hasPermission name="passport:del">
                                        <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 删除</a>
                                    </shiro:hasPermission>
                                </c:if>
                            </div>
                        </c:if>
                        <c:if test="${status==3}">
                            <div class="buttons pull-right">
                                <shiro:hasPermission name="passport:edit">
                                    <a class="addLostBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加丢失证件</a>
                                </shiro:hasPermission>
                            </div>
                        </c:if>
                        <c:if test="${status==5}">
                            <div class="buttons pull-right">
                                <shiro:hasPermission name="safeBox:edit">
                                    <a class="btn btn-success btn-sm" onclick="openView_safeBox()"><i class="fa fa-plus"></i> 保险柜管理</a>
                                </shiro:hasPermission>
                            </div>
                        </c:if>
                    </div>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
            <div class="widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                            <div class="row">
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">姓名</label>
                                        <div class="col-xs-6">
                                            <div class="input-group">
                                                <input type="hidden" name="status" value="${status}">
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${cadre.id}">${sysUser.realname}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">证件名称</label>
                                        <div class="col-xs-6">
                                            <select data-rel="select2" name="classId" data-placeholder="请选择证件名称">
                                                <option></option>
                                                <c:import url="/metaTypes?__code=mc_passport_type"/>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=classId]").val(${param.classId});
                                            </script>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">保险柜</label>
                                        <div class="col-xs-6">
                                            <select data-rel="select2" name="safeBoxId" data-placeholder="请选择保险柜">
                                                <option></option>
                                                <c:forEach items="${safeBoxMap}" var="safeBox">
                                                    <option value="${safeBox.key}">${safeBox.value.code}</option>
                                                </c:forEach>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=safeBoxId]").val(${param.safeBoxId});
                                            </script>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label">证件号码</label>
                                        <div class="col-xs-6">
                                            <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                                   placeholder="请输入证件号码">
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="clearfix form-actions center">
                                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="status=${status}">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </mytag:sort-form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <div class="table-container">
                    <table style="min-width: 2000px" class="overflow-y table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>工作证号</th>
							<th>姓名</th>
							<th>所在单位及职务</th>
							<th>职位属性</th>
							<th>证件名称</th>
							<th>证件号码</th>
							<th>发证机关</th>
							<th>发证日期</th>
							<th>有效期</th>
                            <c:if test="${status!=PASSPORT_TYPE_LOST}">
							<th>集中保管日期</th>
							<th>存放保险柜编号</th>
							<th>是否借出</th>
                            <c:if test="${status==4 || status==5}">
							<th>类型</th>
                            </c:if>
                                </c:if>
                <c:if test="${passport.type==PASSPORT_TYPE_LOST}">
                    <th>丢失证明</th>
                    </c:if>
                            <c:if test="${status==PASSPORT_TYPE_CANCEL}">
							<th>取消集中保管原因</th>
							<th>状态</th>
                            </c:if>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${passports}" var="passport" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(passport.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${passport.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <td>${sysUser.code}</td>
                            <td><a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id=${passport.cadreId}">
                                    ${sysUser.realname}
                            </a></td>
                            <td style="text-align: left">${cadre.title}</td>
                            <td>${postMap.get(cadre.postId).name}</td>
								<td>${passportTypeMap.get(passport.classId).name}</td>
								<td>${passport.code}</td>
								<td>${passport.authority}</td>
								<td>${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}</td>
                                <c:if test="${passport.type!=PASSPORT_TYPE_LOST}">
								<td>${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(passport.keepDate,'yyyy-MM-dd')}</td>
								<td>${safeBoxMap.get(passport.safeBoxId).code}</td>
								<td>${passport.isLent?"借出":"-"}</td>
                                </c:if>
                            <c:if test="${status==PASSPORT_TYPE_LOST}">
                                <td>
                                    <a href="${ctx}/passport_lostProof_download?id=${passport.id}" target="_blank">
                                    丢失证明
                                    </a>
                                </td>
                            </c:if>
                                <c:if test="${status==4 || status==5}">
                                    <td>${PASSPORT_TYPE_MAP.get(passport.type)}</td>
                                </c:if>
                                <c:if test="${status==PASSPORT_TYPE_CANCEL}">
                                    <td>${PASSPORT_CANCEL_TYPE_MAP.get(passport.cancelType)}</td>
                                    <td>${passport.cancelConfirm?"已确认":"未确认"}</td>
                                </c:if>

                            <td>
                                    <c:if test="${status==PASSPORT_TYPE_CANCEL}">
                                        <button data-id="${passport.id}"
                                                data-type="${passport.cancelType}"
                                                data-userid="${sysUser.id}"
                                                data-name="${sysUser.realname}"
                                                data-cls="${passportTypeMap.get(passport.classId).name}"
                                                class="shortMsgBtn btn btn-primary btn-mini btn-xs">
                                            <i class="fa fa-info-circle"></i> 短信通知
                                        </button>
                                    </c:if>
                                    <c:if test="${status==PASSPORT_TYPE_CANCEL}">
                                        <button data-url="${ctx}/passport_cancel?id=${passport.id}" class="openView btn btn-success btn-mini btn-xs">
                                            <i class="fa fa-check-circle-o"></i> 确认单
                                        </button>
                                    </c:if>

                                    <shiro:hasPermission name="passport:edit">
                                    <button data-id="${passport.id}" class="editBtn btn btn-primary btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                    <%--<c:if test="${!passport.abolish}">
                                    <button class="abolishBtn btn btn-warning btn-mini btn-xs" data-id="${passport.id}">
                                        <i class="fa fa-times"></i> 作废
                                    </button>
                                    </c:if>
                                     <shiro:hasPermission name="passport:del">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${passport.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>--%>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </div>
                <wo:page commonList="${commonList}" uri="${ctx}/passport_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
    </div>
                </div></div></div>
    <div id="item-content">
    </div>
    </div>
</div>
<style>
    table thead tr th,table tbody tr td{
        text-align: center;!important;
    }
</style>
<script>
    stickheader();
    function openView_safeBox(pageNo){
        pageNo = pageNo||1;
        loadModal( "${ctx}/safeBox_page?pageNo="+pageNo, '400');
    }

    $(".importBtn").click(function(){
        loadModal("${ctx}/passport_import");
    });

    $(".shortMsgBtn").click(function(){
        var msg = '';
        var cancelType = $(this).data("type");
        var name = $(this).data("name");
        var cls = $(this).data("cls");
        var userid = $(this).data("userid");
        if(cancelType==1)
            msg += name+"同志，您好！因证件过期，您所持有的"+cls+"不再纳入集中管理范围。" +
            "请到组织部（主楼A306）取回证件，谢谢！联系电话：58808302、58806879。"
        if(cancelType==2)
            msg += name+"同志，您好！因您不再担任行政职务，您所持有的"+cls+"不再纳入集中管理范围。" +
            "请您到组织部（主楼A306）取回证件，谢谢！联系电话：58808302、58806879。";
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
            message: '<p style="padding:30px;font-size:20px;text-indent: 2em; ">'+ msg + '</p>',
            callback: function(result) {
                if(result) {
                    $.post("${ctx}/shortMsg", {type:'取消集中管理',content: msg, userId: userid}, function(ret){
                        if(ret.success) {
                            SysMsg.success('通知成功', '提示', function () {
                                //page_reload();
                            });
                        }
                    })
                }
            },
            title: "短信通知"
        });
    });

    $(".addLostBtn").click(function(){
        loadModal("${ctx}/passport_au?type=${PASSPORT_TYPE_LOST}");
    });
    $(".cancelConfirmBtn").click(function(){
        loadModal("${ctx}/passport_cancel_confirm?id="+$(this).data("id"));
    });

   /* $(".abolishBtn").click(function(){
        var id = $(this).data("id");
        bootbox.confirm("确定作废该证件吗？", function (result) {
            if (result) {
                $.post("${ctx}/passport_abolish", {id: id}, function (ret) {
                    if (ret.success) {
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    });*/
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('[data-rel="select2-ajax"]'));
</script>