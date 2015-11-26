<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CADRE_REWARD_TYPE_RESEARCH" value="<%=SystemConstants.CADRE_REWARD_TYPE_RESEARCH%>"/>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa  fa-history"></i> 科研情况</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">

                <div class="buttons pull-right">
                    <shiro:hasPermission name="cadreResearch:edit">
                    <a class="btn btn-info btn-sm" onclick="_au()"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    </div>
                <h4>&nbsp;</h4>
            <div class="space-4"></div>
            <c:if test="${fn:length(cadreResearchs)>0}">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>主持科研项目情况</th>
							<th>参与科研项目情况</th>
							<th>出版著作及发表论文等情况</th>
                        <shiro:hasPermission name="cadreResearch:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cadreResearchs}" var="cadreResearch" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${cadreResearch.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>

								<td>
                                    <c:if test="${not empty cadreResearch.chairFile}">
                                        <a href="/cadreResearch_download?id=${cadreResearch.id}&type=chair" target="_blank">下载</a>
                                        <a href="javascript:void(0)" onclick="cadreResearch_swf_preview(${cadreResearch.id}, 'chair')">预览</a>
                                    </c:if></td>
								<td>
                                    <c:if test="${not empty cadreResearch.joinFile}">
                                        <a href="/cadreResearch_download?id=${cadreResearch.id}&type=join" target="_blank">下载</a>
                                        <a href="javascript:void(0)" onclick="cadreResearch_swf_preview(${cadreResearch.id}, 'join')">预览</a>
                                    </c:if></td>
								<td>
                                    <c:if test="${not empty cadreResearch.publishFile}">
                                        <a href="/cadreResearch_download?id=${cadreResearch.id}&type=publish" target="_blank">下载</a>
                                        <a href="javascript:void(0)" onclick="cadreResearch_swf_preview(${cadreResearch.id}, 'publish')">预览</a>
                                    </c:if></td>
                            <shiro:hasPermission name="cadreResearch:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td class="hidden-480">
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreResearch.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreResearch.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="cadreResearch:edit">
                                    <button onclick="_au(${cadreResearch.id})" class="btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="cadreResearch:del">
                                    <button class="btn btn-danger btn-mini" onclick="_del(${cadreResearch.id})">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${fn:length(cadreResearchs)==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
</div></div></div>

<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa  fa-diamond"></i> 科研成果及获奖情况</h4>

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

    function cadreResearch_swf_preview(id, type){

        loadModal("${ctx}/cadreResearch_swf_preview?id="+id + "&type=" + type);
    }

    function _au(id) {
        url = "${ctx}/cadreResearch_au?cadreId=${param.cadreId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function _del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreResearch_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function cadreReward_au(id) {
        url = "${ctx}/cadreReward_au?type=${CADRE_REWARD_TYPE_RESEARCH}&cadreId=${param.cadreId}";
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
        $("#cadre-box .tab-content").load("${ctx}/cadreResearch_page?${pageContext.request.queryString}");
    }

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
</div>