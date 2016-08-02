<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="tabbable">
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
        <a href="javascript:;" onclick="_innerPage(1)"><i class="fa fa-flag"></i> 其他奖励情况</a>
    </li>
    <li class="${type==2?"active":""}">
        <a href="javascript:;" onclick="_innerPage(2)"><i class="fa fa-flag"></i> 预览</a>
    </li>
</ul>
<c:if test="${type==1}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <shiro:hasPermission name="cadreReward:edit">
            <a class="popupBtn btn btn-success btn-sm"
               data-url="${ctx}/cadreReward_au?rewardType=${param.rewardType}&cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                添加</a>
            <a class="jqOpenViewBtn btn btn-primary btn-sm"
               data-url="${ctx}/cadreReward_au"
               data-grid-id="#jqGrid_cadreReward"
               data-querystr="&rewardType=${param.rewardType}&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                修改</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="cadreReward:del">
            <button data-url="${ctx}/cadreReward_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid_cadreReward"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-times"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid_cadreReward" class="jqGrid2"></table>
    <div id="jqGridPager_cadreReward"></div>
    </div>
</c:if>
<c:if test="${type==2}">
    <div class="row two-frames">
        <div class="left">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        初始数据
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="min-height: 647px" id="orginal">
                        <c:forEach items="${cadreRewards}" var="cadreReward">
                            <p>${cm:formatDate(cadreReward.rewardTime, "yyyy.MM")}
                                &nbsp;${cadreReward.name}&nbsp;${cadreReward.unit}</p>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div class="right">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        最终数据（<span
                            style="font-weight: bolder; color: red;">最近保存时间：${empty cadreInfo.lastSaveDate?"未保存":cm:formatDate(cadreInfo.lastSaveDate, "yyyy-MM-dd HH:mm")}</span>）
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="margin-bottom: 10px">
                        <textarea id="content">
                            <c:if test="${not empty cadreInfo.content}">${cadreInfo.content}</c:if>
                            <c:if test="${empty cadreInfo.content}">
                                <c:forEach items="${cadreRewards}" var="cadreReward">
                                    <p>${cm:formatDate(cadreReward.rewardTime, "yyyy.MM")}
                                        &nbsp;${cadreReward.name}&nbsp;${cadreReward.unit}</p>
                                </c:forEach>
                            </c:if>
                        </textarea>
                        <input type="hidden" name="content">
                    </div>
                    <div class="modal-footer center">
                        <a href="javascript:;" onclick="copyOrginal()" class="btn btn-sm btn-success">
                            <i class="ace-icon fa fa-copy"></i>
                            复制初始数据
                        </a>
                        <input type="button" onclick="updateCadreInfo()" class="btn btn-primary" value="保存"/>

                    </div>
                </div>
            </div>

        </div>
    </div>
</c:if>

<c:if test="${type==2}">
    <style>
        .two-frames {
            padding: 10px 20px;
            max-width: 1330px;
        }

        .two-frames .left, .two-frames .right {
            float: left;
        }

        .two-frames .left {
            width: 630px;
            margin-right: 25px;
        }

        .two-frames .right {
            width: 630px;
        }
    </style>
    <script type="text/javascript" src="${ctx}/kindeditor/kindeditor.js"></script>
    <script>
        KE.init({
            id: 'content',
            height: '550px',
            resizeMode: 1,
            width: '600px',
            //scriptPath:"${ctx}/js/kindeditor/",
            //skinsPath : KE.scriptPath + 'skins/',
            items: [
                'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'image', 'link', 'unlink', 'fullscreen']
        });
        KE.create('content');
        function updateCadreInfo() {
            $.post("${ctx}/cadreInfo_updateContent", {
                cadreId: '${param.cadreId}',
                content: KE.util.getData('content'),
                type:"${CADRE_INFO_TYPE_REWARD_OTHER}"
            }, function (ret) {
                if (ret.success) {
                    SysMsg.info("保存成功", "", function () {
                        _innerPage(2)
                    });
                }
            });
        }
        function copyOrginal() {
            //console.log($("#orginal").html())
            KE.util.setFullHtml('content', $("#orginal").html())
            SysMsg.info("复制成功，请务必点击\"保存\"按钮进行保存")
        }
    </script>
</c:if>
<c:if test="${type==1}">
    <script>
        function _innerPage(type) {
            $("#view-box .tab-content").load("${ctx}/cadreReward_page?rewardType=${param.rewardType}&cadreId=${param.cadreId}&type=" + type)
        }
        $("#jqGrid_cadreReward").jqGrid({
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_cadreReward",
            url: '${ctx}/cadreReward_data?${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: [
                {label: '日期', name: 'rewardTime', formatter: 'date', formatoptions: {newformat: 'Y.m'},frozen:true },
                {label: '获得奖项', name: 'name', width: 350},
                {label: '颁奖单位', name: 'unit', width: 280},
                {label: '获奖证书', name: 'proof', width: 250,
                    formatter: function (cellvalue, options, rowObject) {
                        if(rowObject.proof==undefined) return '-';
                        return '<a href="${ctx}/attach/download?path={0}&filename={1}">{1}</a>'
                                .format(rowObject.proof,rowObject.proofFilename);
                    }},
                {label: '排名', name: 'rank', formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==0) return '-'
                    return '第{0}'.format(cellvalue);
                }},
                {label: '备注', name: 'remark', width: 350}
            ]
        }).on("initGrid", function () {
            $(window).triggerHandler('resize.jqGrid2');
        });

        function _delCallback(target) {
            $("#jqGrid_cadreReward").trigger("reloadGrid");
        }

        $('#searchForm [data-rel="select2"]').select2();
        $('[data-rel="tooltip"]').tooltip();
    </script>
</c:if>