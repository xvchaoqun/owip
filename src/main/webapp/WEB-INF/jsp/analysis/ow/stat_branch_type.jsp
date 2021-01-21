<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div class="col-sm-4" id="stat_ow_branch_type" style="width:750px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="ace-icon fa fa-pie-chart"></i>
                支部类型统计
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <div id="branchType-placeholder" style="height: 252px;"></div>
            </div>
        </div>
    </div>
</div>
<script>
        $("#stat_ow_branch_type ul li").click(function () {

            $.get("${ctx}/stat_ow_branch_type", {type: $(this).data('type')}, function (html) {
                $("#stat_ow_branch_type").replaceWith(html);
            });
        });

        var myChart = echarts.init($('#branchType-placeholder').get(0));

        var label= [
            <c:forEach items="${branchTypeMap}" var="type">
                '${metaTypes.get(type.key).name}(${type.value})',
            </c:forEach>
        ];
        <c:set var="totalCount" value="0"/>
        var data = [
            <c:forEach items="${branchTypeMap}" var="type">
                {name: "${metaTypes.get(type.key).name}(${type.value})", value: '${type.value}'},
                <c:set var="totalCount" value="${totalCount+type.value}"/>
            </c:forEach>
        ];

        var option = {
            title: {
                text: '（支部总数：${totalCount}）',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left:-5,
                top: -5,
                bottom: 5,
                data: label
            },
            series: [
                {
                    name: '支部类型',
                    type: 'pie',
                    radius: '60%',
                    center: ['60%', '60%'],
                    data:data,
                    emphasis: {
                        itemStyle: {
                            shadowBlur:10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                },
                {
                    name: '支部类型',
                    type: 'pie',
                    radius: '60%',
                    center: ['60%', '60%'],
                    data: data,
                    itemStyle:{            //饼图图形上的文本标签
                        normal: {
                            label: {
                                show: true,
                                position: 'inner',
                                fontSize: 12,
                                align: "left",
                                textStyle: {
                                    fontWeight: 300,
                                    fontSize: 12,   //文字的字体大小
                                    color:'white'
                                },
                                formatter: '{d}%'
                            },
                            labelLine:{
                                show : false //显示饼状图上的文本时，指示线不显示，在第一个data时显示指示线
                            }
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
</script>