<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="cadreAgeDiv" style="height: 160px"></div>
<script>
        var cadreAgeChart= echarts.init($('#cadreAgeDiv').get(0));
        var legendData = [];
        var seriesData1= [];
        var seriesData2 = [];
        var cadreAgeMap = ${cm:toJSONObject(cadreAgeMap)};

        $.each(cadreAgeMap, function (key, value) {
            var item=key+'('+value+')';
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
                text: '处级干部年龄分布',
                left: 'left',
                textStyle: {
                    fontSize: 15
                }
            },
            legend: {
                show: true,
                type: 'scroll',
                orient: 'vertical',
                left: 25,
                top: 30,
                bottom: 20,
                itemWidth: 11,
                itemHeight: 7,
                textStyle: {
                    fontSize: 12
                },
                data: legendData,
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {d}%'
            },
            series: [
                {
                    name: '年龄',
                    type: 'pie',
                    radius: '90%',
                    center: ['70%', '50%'],
                    data: seriesData1,
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
                            }
                        }
                    }
                }
            ]
        };
        cadreAgeChart.setOption(option);

</script>