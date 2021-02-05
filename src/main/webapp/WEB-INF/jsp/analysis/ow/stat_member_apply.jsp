<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div class="col-sm-4" id="stat_member_apply" style="width:550px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="ace-icon fa fa-pie-chart"></i>
                发展党员情况统计
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <div id="memberApply-placeholder" style="height: 252px"></div>
            </div>
        </div>
    </div>
</div>
<script>
        $("#stat_member_apply ul li").click(function () {

            $.get("${ctx}/stat_member_apply", {type: $(this).data('type')}, function (html) {
                $("#stat_member_apply").replaceWith(html);
            });
        });

        var memberApplyChart = echarts.init($('#memberApply-placeholder').get(0));
        memberApplyChart.showLoading({text: '正在加载数据'});
        var url = "${ctx}/${empty partyId?'stat_member_apply_data':'stat_party_member_apply_data'}";

        $.get(url, {type:"${type}",partyId:"${partyId}",branchId:"${branchId}"}, function (statApplyMap) {

            var legendData = [];
            var seriesData = [];
            $.each(statApplyMap, function (key, value) {
                /*console.log(key);*/

                var item = _cMap.OW_APPLY_STAGE_MAP[key] + '(' + value + ')';
                legendData.push(item);
                seriesData.push({
                    name: item,
                    value: value
                });
            });

            var option = {
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
                        name: '党员发展情况',
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
                        name: '党员发展情况',
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
            memberApplyChart.setOption(option, true);
            memberApplyChart.hideLoading();
        })
</script>