<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="cadreCountDiv" style="height: 160px"></div>
<script>
        var cadreCountChart= echarts.init($('#cadreCountDiv').get(0));
        var legendData = [];
        var seriesData1 = [];
        var seriesData2 = [];
        var totalCount = 0;
        var statCadreCountMap = ${cm:toJSONObject(statCadreCountMap)};
        $.each(statCadreCountMap, function (key, value) {
            totalCount += value;
            var item = key + '(' + value + ')';
            legendData.push(item);
            seriesData1.push({
                name: item,
                value: value
            });
            seriesData2.push({
                name: key,
                value: value
            });
        });
        var option = {
            title: {
                text: totalCount > 0 ? '干部总数：' + totalCount : '',
                left: 'center',
                textStyle: {
                    fontSize: 15
                }
            },
            legend: {
                show: true,
                type: 'scroll',
                orient: 'vertical',
                left: 25,
                top: 10,
                bottom: 20,
                itemWidth: 11,
                itemHeight: 7,
                textStyle: {
                    fontSize: 8
                    /*fontWeight: 'bolder'*/

                },
                data: legendData,
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {d}%'
            },
            series: [
               /* {
                    name: '行政级别',
                    type: 'pie',
                    radius: '60%',
                    center: ['50%', '60%'],
                    data: seriesData2
                },*/
                {
                    name: '行政级别',
                    type: 'pie',
                    radius: '75%',
                    center: ['65%', '60%'],
                    data: seriesData1,
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
                            }
                        }
                    }
                }
            ]
        };
        cadreCountChart.setOption(option);

</script>