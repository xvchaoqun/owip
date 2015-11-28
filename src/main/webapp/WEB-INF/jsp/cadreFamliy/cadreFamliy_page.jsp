<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CADRE_FAMLIY_TITLE_MAP" value="<%=SystemConstants.CADRE_FAMLIY_TITLE_MAP%>"/>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-home"></i> 家庭成员信息</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
                <div class="buttons pull-right">
                    <shiro:hasPermission name="cadreFamliy:edit">
                    <a class="btn btn-info btn-sm" onclick="cadreFamliy_au()"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                </div>
            <h4>&nbsp;</h4>
            <div class="space-4"></div>
            <c:if test="${fn:length(cadreFamliys)>0}">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
							<th>称谓</th>
							<th>姓名</th>
							<th>出生年月</th>
							<th>年龄</th>
							<th>政治面貌</th>
							<th>工作单位及职务</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cadreFamliys}" var="cadreFamliy" varStatus="st">
                        <tr>
								<td>${CADRE_FAMLIY_TITLE_MAP.get(cadreFamliy.title)}</td>
								<td>${cadreFamliy.realname}</td>
								<td>${cm:formatDate(cadreFamliy.birthday,'yyyy-MM-dd')}</td>
								<td>${cm:intervalYearsUntilNow(cadreFamliy.birthday)}</td>
								<td>${politicalStatusMap.get(cadreFamliy.politicalStatus).name}</td>
								<td>${cadreFamliy.unit}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="cadreFamliy:edit">
                                    <button onclick="cadreFamliy_au(${cadreFamliy.id})" class="btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="cadreFamliy:del">
                                    <button class="btn btn-danger btn-mini" onclick="cadreFamliy_del(${cadreFamliy.id})">
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
            <c:if test="${fn:length(cadreFamliys)==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
    </div>
</div>

<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-fighter-jet"></i> 家庭成员移居国（境）外的情况</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <div class="buttons pull-right">
                <shiro:hasPermission name="cadreFamliyAbroad:edit">
                    <a class="btn btn-info btn-sm" onclick="cadreFamliyAbroad_au()"><i class="fa fa-plus"></i> 添加</a>
                </shiro:hasPermission>
            </div>
            <h4>&nbsp;</h4>
            <div class="space-4"></div>
            <c:if test="${fn:length(cadreFamliyAbroads)>0}">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th>称谓</th>
                        <th>姓名</th>
                        <th>移居国家</th>
                        <th>移居类别</th>
                        <th>移居时间</th>
                        <th>现居住城市</th>
                        <shiro:hasPermission name="cadreFamliyAbroad:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cadreFamliyAbroads}" var="cadreFamliyAbroad" varStatus="st">
                        <c:set value="${cadreFamliyMap.get(cadreFamliyAbroad.famliyId)}" var="cf"/>
                        <tr>
                            <td>${CADRE_FAMLIY_TITLE_MAP.get(cf.title)}</td>
                            <td>${cf.realname}</td>
                            <td>${cadreFamliyAbroad.country}</td>
                            <td>${abroadTypeMap.get(cadreFamliyAbroad.type).name}</td>
                            <td>${cm:formatDate(cadreFamliyAbroad.abroadTime,'yyyy-MM-dd')}</td>
                            <td>${cadreFamliyAbroad.city}</td>
                            <shiro:hasPermission name="cadreFamliyAbroad:changeOrder">
                                <c:if test="${!_query && commonList.recNum>1}">
                                    <td class="hidden-480">
                                        <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreFamliyAbroad.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                        <input type="text" value="1"
                                               class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                        <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreFamliyAbroad.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                    </td>
                                </c:if>
                            </shiro:hasPermission>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="cadreFamliyAbroad:edit">
                                        <button onclick="cadreFamliyAbroad_au(${cadreFamliyAbroad.id})" class="btn btn-mini">
                                            <i class="fa fa-edit"></i> 编辑
                                        </button>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="cadreFamliyAbroad:del">
                                        <button class="btn btn-danger btn-mini" onclick="cadreFamliyAbroad_del(${cadreFamliyAbroad.id})">
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
            <c:if test="${fn:length(cadreFamliyAbroads)==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
            </div></div></div>
<script>

    function cadreFamliy_au(id) {
        url = "${ctx}/cadreFamliy_au?cadreId=${param.cadreId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function cadreFamliy_del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreFamliy_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function cadreFamliyAbroad_au(id) {
        url = "${ctx}/cadreFamliyAbroad_au?cadreId=${param.cadreId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function cadreFamliyAbroad_del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreFamliyAbroad_del", {id: id}, function (ret) {
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
        $("#view-box .tab-content").load("${ctx}/cadreFamliy_page?${pageContext.request.queryString}");
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