<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/cadre_au?status=${status}"
             data-url-page="${ctx}/cadre_page"
             data-url-del="${ctx}/cadre_del"
             data-url-bd="${ctx}/cadre_batchDel"
             data-url-co="${ctx}/cadre_changeOrder?status=${status}"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="widget-box hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
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
                                        <label class="col-xs-3 control-label">行政级别</label>
                                        <div class="col-xs-6">
                                            <select data-rel="select2" name="typeId" data-placeholder="请选择行政级别">
                                                <option></option>
                                                <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=typeId]").val(${param.typeId});
                                            </script>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">职务属性</label>
                                        <div class="col-xs-6">
                                            <select data-rel="select2" name="postId" data-placeholder="请选择职务属性">
                                                <option></option>
                                                <jsp:include page="/metaTypes?__code=mc_post"/>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=postId]").val(${param.postId});
                                            </script>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-4 control-label">单位及职务</label>
                                        <div class="col-xs-6">
                                            <input class="form-control search-query" name="title" type="text" value="${param.title}"
                                                   placeholder="请输入单位及职务">
                                        </div>
                                    </div>
                                    </div>

                            </div>
                            <div class="clearfix form-actions center">
                                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                <c:set var="_query" value="${not empty param.cadreId ||not empty param.typeId ||not empty param.postId ||not empty param.title || not empty param.code || not empty param.sort}"/>
                                <c:if test="${_query || not empty param.sort}">&nbsp; &nbsp; &nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </mytag:sort-form>
                    </div>
                </div>
            </div>
        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <c:forEach var="cadreStatus" items="${CADRE_STATUS_MAP}">
                <li class="<c:if test="${status==cadreStatus.key}">active</c:if>">
                    <a href="?status=${cadreStatus.key}"><i class="fa fa-flag"></i> ${cadreStatus.value}</a>
                </li>
                </c:forEach>
                <div class="buttons pull-right" style="top: -3px; right:10px; position: relative">
                    <shiro:hasPermission name="cadre:edit">
                        <a class="editBtn btn btn-info btn-sm btn-success"><i class="fa fa-plus"></i>
                            <c:if test="${status==CADRE_STATUS_TEMP}">提任干部</c:if>
                            <c:if test="${status==CADRE_STATUS_NOW}">添加现任干部</c:if>
                            <c:if test="${status==CADRE_STATUS_LEAVE}">添加离任处级干部</c:if>
                            <c:if test="${status==CADRE_STATUS_LEADER_LEAVE}">添加离任校领导干部</c:if>
                        </a>
                    </shiro:hasPermission>
                    <a class="importBtn btn btn-primary btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i> 导入</a>
                    <c:if test="${commonList.recNum>0}">
                        <a class="exportBtn btn btn-success btn-sm tooltip-success"
                           data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                        <shiro:hasPermission name="cadre:del">
                            <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 删除</a>
                        </shiro:hasPermission>
                    </c:if>
                </div>
                <div class="space-4"></div>
            </ul>

            <div class="tab-content">
                <div id="home4" class="tab-pane in active">

        <div >

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
                            <th>姓名</th>
							<th>工号</th>
							<th nowrap>行政级别</th>
							<th>职务属性</th>
							<th nowrap>所属单位</th>
							<th nowrap>职务</th>
							<th nowrap>所在单位及职务</th>
							<th>备注</th>
                        <shiro:hasPermission name="cadre:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cadres}" var="cadre" varStatus="st">
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${cadre.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <td nowrap> <a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id=${cadre.id}">${sysUser.realname}</a></td>
								<td nowrap>${sysUser.code}</td>

								<td nowrap>${adminLevelMap.get(cadre.typeId).name}</td>
								<td nowrap>${postMap.get(cadre.postId).name}</td>
								<td nowrap>${unitMap.get(cadre.unitId).name}</td>
								<td nowrap>${cadre.post}</td>
								<td nowrap>${cadre.title}</td>
								<td>${cadre.remark}</td>
                            <shiro:hasPermission name="cadre:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadre.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadre.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <c:if test="${cadre.status==CADRE_STATUS_TEMP}">
                                        <button onclick="_pass(${cadre.id}, '${sysUser.realname}', '${sysUser.code}')" class="btn btn-xs btn-success">
                                            <i class="fa fa-edit"></i> 通过常委会任命
                                        </button>
                                    </c:if>

                                    <c:if test="${cadre.status==CADRE_STATUS_NOW}">
                                        <button onclick="_leave(${cadre.id})" class="btn btn-xs btn-success">
                                            <i class="fa fa-edit"></i> 离任
                                        </button>
                                    </c:if>
                                    <shiro:hasPermission name="cadre:edit">
                                    <button data-id="${cadre.id}" class="editBtn btn btn-default btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <%--<shiro:hasPermission name="cadre:del">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${cadre.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>--%>
                                </div>
                                <div class="hidden-md hidden-lg">
                                    <div class="inline pos-rel">
                                        <button class="btn btn-mini btn-xser btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                            <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                        </button>

                                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                            <%--<li>
                                            <a href="#" class="tooltip-info" data-rel="tooltip" title="查看">
                                                        <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                            </a>
                                        </li>--%>
                                            <shiro:hasPermission name="cadre:edit">
                                            <li>
                                                <a href="#" data-id="${cadre.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="cadre:del">
                                            <li>
                                                <a href="#" data-id="${cadre.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
                                                    <span class="red">
                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                        </ul>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </div>
                <wo:page commonList="${commonList}" uri="${ctx}/cadre_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
                </div></div></div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>
<script>
    $(".importBtn").click(function(){
        loadModal("${ctx}/cadre_import?status=${status}");
    });
    function _pass(id, realname, code){

        bootbox.confirm("姓名：{0}，工号：{1}，确定通过常委会任命吗？".format(realname, code), function (result) {
            if (result) {
                $.post("${ctx}/cadre_pass", {id: id}, function (ret) {
                    if (ret.success) {
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function _leave(id){

        loadModal("${ctx}/cadre_leave?id="+id+"&status=${CADRE_STATUS_LEAVE}&");
    }

    function openView(id){
        $("#body-content").hide();
        $("#item-content").load("${ctx}/cadre_view?id="+id).show();
    }
    function closeView(){
        $("#body-content").show();
        $("#item-content").hide();
    }

    /*$(".tabbable li a").click(function(){
        $this = $(this);
        $(".tabbable li").removeClass("active");
        $this.closest("li").addClass("active");
        $(".myTableDiv #searchForm input[name=status]").val($this.data("status"));
        $(".myTableDiv .searchBtn").click();
    });*/

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

        register_user_select($('#searchForm select[name=cadreId]'));
</script>
</div>