<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CADRE_COURSE_TYPE_MAP" value="<%=SystemConstants.CADRE_COURSE_TYPE_MAP%>"/>
<c:set var="CADRE_REWARD_TYPE_TEACH" value="<%=SystemConstants.CADRE_REWARD_TYPE_TEACH%>"/>

<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa  fa-history"></i> 本、硕、博课程情况</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">

                <div class="buttons pull-right">
                    <shiro:hasPermission name="cadreCourse:edit">
                    <a class="btn btn-info btn-sm" onclick="cadreCourse_au()"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                </div>
                <h4>&nbsp;</h4>
            <div class="space-4"></div>
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
							<th>课程名称</th>
							<th>类型</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cadreCourses}" var="cadreCourse" varStatus="st">
                        <tr>
								<td>${cadreCourse.name}</td>
								<td>${CADRE_COURSE_TYPE_MAP.get(cadreCourse.type)}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="cadreCourse:edit">
                                    <button onclick="cadreCourse_au(${cadreCourse.id})" class="btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="cadreCourse:del">
                                    <button class="btn btn-danger btn-mini" onclick="cadreCourse_del(${cadreCourse.id})">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div></div></div>

<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa  fa-diamond"></i> 教学成果及获奖情况</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <div class="buttons pull-right">
                <shiro:hasPermission name="cadreReward:edit">
                    <a class="btn btn-info btn-sm" onclick="cadreReward_au()"><i class="fa fa-plus"></i> 添加</a>
                </shiro:hasPermission>
            </div>
            <h4>&nbsp;</h4>
            <div class="space-4"></div>
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th>日期</th>
                        <th>获得奖项</th>
                        <th>颁奖单位</th>
                        <th>排名</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cadreRewards}" var="cadreReward" varStatus="st">
                        <tr>

                            <td>${cm:formatDate(cadreReward.rewardTime,'yyyy-MM-dd')}</td>
                            <td>${cadreReward.name}</td>
                            <td>${cadreReward.unit}</td>
                            <td>${cadreReward.rank}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="cadreReward:edit">
                                        <button onclick="cadreReward_au(${cadreReward.id})" class="btn btn-mini">
                                            <i class="fa fa-edit"></i> 编辑
                                        </button>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="cadreReward:del">
                                        <button class="btn btn-danger btn-mini" onclick="cadreReward_del(${cadreReward.id})">
                                            <i class="fa fa-times"></i> 删除
                                        </button>
                                    </shiro:hasPermission>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div></div></div>
<script>

    function cadreCourse_au(id) {
        url = "${ctx}/cadreCourse_au?cadreId=${param.cadreId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function cadreCourse_del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreCourse_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function cadreReward_au(id) {
        url = "${ctx}/cadreReward_au?type=${CADRE_REWARD_TYPE_TEACH}&cadreId=${param.cadreId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function cadreReward_del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreReward_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/cadreCourse_page?${pageContext.request.queryString}");
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 300,
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
</div>