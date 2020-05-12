<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i> 返回</a>
        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
            ${cetProject.name}（${cm:formatDate(cetProject.startDate, "yyyy-MM-dd")} ~ ${cm:formatDate(cetProject.endDate, "yyyy-MM-dd")}）
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" id="detail-ul">
                <li class="active">
                    <a href="javascript:;"><i class="green ace-icon fa fa-list bigger-120"></i> 培训方案</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="tab-content padding-4" id="detail-content">
                <div class="jqgrid-vertical-offset panel panel-default" style="margin-bottom: 0">
                    <div class="panel-heading">
                        <h3 class="panel-title"><span class="text-primary bolder"><i
                                class="fa fa-line-chart"></i>   参加学习情况</span>
                        </h3>
                    </div>
                    <div class="collapse in">
                        <div class="panel-body">
                            <label>应完成学时数：</label>
                            <c:if test="${cetProjectObj.shouldFinishPeriod==null}">
                                --
                            </c:if>
                            <c:if test="${cetProjectObj.shouldFinishPeriod!=null}">
                                <span class="result">${cm:stripTrailingZeros(cetProjectObj.shouldFinishPeriod)}</span>
                            </c:if>
                           </span>
                            <label>已完成学时数：</label><span class="result">${cm:stripTrailingZeros(cetProjectObj.finishPeriod)}</span>
                            <label>学习进度：</label>
                            <c:if test="${cetProjectObj.shouldFinishPeriod==null}">
                                --
                            </c:if>
                            <c:if test="${cetProjectObj.shouldFinishPeriod!=null}">
                            <c:set var="progress" value="0%"/>
                            <c:if test="${cetProjectObj.shouldFinishPeriod>0 && cetProjectObj.finishPeriod>0}">
                                <fmt:formatNumber var="progress"
                                                  value="${(cm:divide(cetProjectObj.finishPeriod>cetProjectObj.shouldFinishPeriod?cetProjectObj.shouldFinishPeriod:cetProjectObj.finishPeriod, cetProjectObj.shouldFinishPeriod, 3))}"
                                                  type="percent" pattern="#0.0%"/>
                            </c:if>
                            <div class="progress progress-striped pos-rel" data-percent="${progress}" style="width:150px;display: inline-block;top:2px;">
                                <div class="progress-bar progress-bar-success" style="width:${progress};"></div>
                            </div>
                            </c:if>
                            <label>结业：</label>
                            <c:if test="${cetProjectObj.shouldFinishPeriod==null}">
                                --
                            </c:if>
                            <c:if test="${cetProjectObj.shouldFinishPeriod!=null}">
                                ${cetProjectObj.isGraduate?'<span class="result graduate">已结业</span>'
                                                    :'<span class="result">未结业</span>'}
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<style>
    .panel-body label{
        font-size: 14pt;
        font-weight: bolder;
        margin-left: 20px;
    }
    .panel-body span.result{
        font-size: 14pt;
        color: #a94442;
        font-weight: bolder;
    }
    .panel-body span.result.graduate{
        font-size: 14pt;
        color: #449d44;
        font-weight: bolder;
    }
</style>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        multiselect:false,
        pager: "jqGridPager2",
        url: '${ctx}/user/cet/cetProjectPlan_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '详情',name: '_detail', width: 110, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.type=='<%=CetConstants.CET_PROJECT_PLAN_TYPE_WRITE%>'){
                    return ('<button class="popupBtn btn btn-primary btn-xs" '
                            + 'data-url="${ctx}/user/cet/cetProjectObj_uploadWrite?id={0}&planId={1}&projectId=${param.projectId}">'
                            + '<i class="fa fa-upload"></i> {2}</button>')
                            .format(${cetProjectObj.id}, rowObject.id, '${not empty cetProjectObj.writeFilePath?"更新":"上传"}')
                }
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/user/cet/cetProjectPlan_detail?planId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }},
            { label: '培训时间',name: 'startDate', width: 200, formatter: function (cellvalue, options, rowObject) {
                return '{0} ~ {1}'.format($.date(rowObject.startDate, "yyyy-MM-dd"), $.date(rowObject.endDate, "yyyy-MM-dd"))
            }, frozen: true},
            {label: '培训形式', name: 'type', width: 180, formatter: function (cellvalue, options, rowObject) {
                return _cMap.CET_PROJECT_PLAN_TYPE_MAP[cellvalue];
            }, frozen: true},
            /*{label: '培训内容', name: '_summary', width: 80, formatter: function (cellvalue, options, rowObject) {
                if (!rowObject.hasSummary) return '--';
                return ('<button class="popupBtn btn btn-primary btn-xs" data-width="750" ' +
                'data-url="${ctx}/cet/cetProjectPlan_summary?view=1&id={0}"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id);
            }},*/
            {label: '学时', name: 'period'},
            {label: '完成学时数', name: 'finishPeriod'},
            { label: '学习进度',name: '_finish', width: 120,formatter: function (cellvalue, options, rowObject) {
                if(Math.trimToZero(rowObject.period)==0) return '--'
                var progress = Math.formatFloat(Math.trimToZero(rowObject.finishPeriod)*100/rowObject.period, 1) + "%";
                return ('<div class="progress progress-striped pos-rel" data-percent="{0}">' +
                '<div class="progress-bar progress-bar-success" style="width:{0};"></div></div>').format(progress)
            }},
            { label: '备注',name: '_remark', width: 180,formatter: function (cellvalue, options, rowObject) {

                if(rowObject.type=='<%=CetConstants.CET_PROJECT_PLAN_TYPE_WRITE%>'){
                    <c:if test="${not empty cetProjectObj.writeFilePath}">
                    return '已上传' + ' <button class="downloadBtn btn btn-xs btn-success"' +
                    'data-url="${ctx}/attach_download?path=${cm:encodeURI(cetProjectObj.writeFilePath)}&filename=心得体会"> ' +
                    '<i class="fa fa-download"></i> 下载 </button>'
                    </c:if>
                }
                return '--'
            }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
</script>