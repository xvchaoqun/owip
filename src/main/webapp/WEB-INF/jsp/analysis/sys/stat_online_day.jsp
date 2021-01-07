<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="container" style="height:500px; margin: 0 auto;"></div>
<script>
	var data =  generateData();

	function generateData(){
        var beans = ${cm:toJSONArray(beans)};
        var categoryData=[];
        var valueData=[];
        beans.forEach(function(bean, i){
            categoryData.push(echarts.format.formatTime('yyyy-MM-dd', new Date(bean.year, bean.month-1, bean.day)));
            valueData.push(bean.onlineCount);
        })
        return {
            categoryData: categoryData,
            valueData: valueData
        };
    }
    //console.log(data)
    var myChart = echarts.init($('#container').get(0));
    var option = {
        toolbox: {
            show: true,
            feature: {
                saveAsImage: {
                    name:'每日最高在线人数统计'
                }
            }
            },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'line'        // 默认为直线，可选为：'line'：直线 | 'shadow'：阴影|' ‘cross’：十字准星指示器'|‘none’：无指示器
            }
        },
        grid:{
            x:45,           //左
            y:10,           //上
            x2:20,          //右
            //y2:25,          //下
            borderWidth:1
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: data.categoryData
        },
        yAxis: {
            type: 'value',
        },
        dataZoom: [{
            type: 'inside'
        }, {
            type: 'slider'
        }],
        series: [
            {
                name: '每日最高在线人数统计',
                type: 'line',
                smooth: true,
                symbol: 'none',
                sampling: 'average',
                itemStyle: {
                    color: 'rgb(255, 70, 131)'
                },
                areaStyle: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: 'rgb(255, 158, 68)'
                    }, {
                        offset: 1,
                        color: 'rgb(255, 70, 131)'
                    }])
                },
                data: data.valueData
            }
        ]
    };
    myChart.setOption(option);

</script>