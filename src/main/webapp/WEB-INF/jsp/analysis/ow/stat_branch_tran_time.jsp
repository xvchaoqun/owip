<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div class="col-sm-4" id="stat_member_apply" style="width: ${empty param.partyId?550:750}px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="ace-icon fa fa-pie-chart"></i>
                党支部换届时间统计
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <div id="branchTranTimeDiv" style="height: 252px"></div>
            </div>
        </div>
    </div>
</div>
<script>

        var branchTranTimeChart = echarts.init($('#branchTranTimeDiv').get(0));
        branchTranTimeChart.showLoading({text: '正在加载数据'});
        var url = "${ctx}/${empty param.partyId?'stat_branch_tran_data':'stat_pb_tran_data'}";
        $.get(url,{partyId: "${param.partyId}"},  function (statBranchTranMap) {

            var legendData = [];
            var seriesData = [];
            var totalCount = 0;
            $.each(statBranchTranMap, function (key, value) {
                /*console.log(key);*/
                totalCount += value;
                var item = key + '(' + value + ')';
                legendData.push(item);
                seriesData.push({
                    name: item,
                    value: value
                });
            });

            var option = {
                title: {
                    text: '（党支部总数：'+ totalCount +'）',
                    left: 'right'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: -5,
                    top: 5,
                    bottom: 5,
                    data: legendData
                },
                series: [
                    {
                        name: '党支部换届',
                        type: 'pie',
                        radius: '60%',
                        center: ['60%', '60%'],
                        data: seriesData,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    },
                    {
                        name: '党支部换届',
                        type: 'pie',
                        radius: '60%',
                        center: ['60%', '60%'],
                        data: seriesData,
                        itemStyle: {            //饼图图形上的文本标签
                            normal: {
                                label: {
                                    show: true,
                                    position: 'inner',
                                    fontSize: 12,
                                    align: "left",
                                    textStyle: {
                                        fontWeight: 300,
                                        fontSize: 12,   //文字的字体大小
                                        color: 'white'
                                    },
                                    formatter: '{d}%'
                                },
                                labelLine: {
                                    show: false //显示饼状图上的文本时，指示线不显示，在第一个data时显示指示线
                                }
                            }
                        }
                    }
                ]
            };
            branchTranTimeChart.setOption(option, true);
            branchTranTimeChart.hideLoading();
        })
</script>