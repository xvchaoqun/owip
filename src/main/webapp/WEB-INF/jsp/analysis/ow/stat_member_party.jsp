<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div id="container" style="height:500px; margin: 0 auto;"></div>
<script>

    var myChart = echarts.init($('#container').get(0));

    var label= [
        <c:forEach items="${categories}" var="category">
            '${category}',
        </c:forEach>
    ];

    var data = [
        <c:forEach items="${statAgeMap}" var="age">
            <c:if test="${not empty age.value}">
                {name: "${MEMBER_AGE_MAP.get(age.key)}(${age.value})", value: '${age.value}'},
            </c:if>
        </c:forEach>
    ];

    var sumData = [];
    var teachers = ${cm:toJSONArray(teachers)};
    var students = ${cm:toJSONArray(students)};

    for (var i = 0; i<teachers.length; i++){
        var sumCount = 0;
        sumCount = teachers[i]+students[i];
        sumData.push(sumCount);
    }


    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'none'        // 默认为直线，可选为：'line'：直线 | 'shadow'：阴影|' ‘cross’：十字准星指示器'|‘none’：无指示器
            }
        },
        legend: {
            data: label
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        yAxis: {
            type: 'value'
        },
        xAxis: {
            type: 'category',
            data: label,
            axisLabel: {rotate: 40, interval: 0}
        },
        series: [
            {
                name: '党员总数',
                type: 'bar',
                barWidth: 20,
                barGap: '-100%',         // 左移100%，stack不再与上面两个在一列
                label: {
                    normal: {
                        show: true,
                        position: 'top',       //  位置设为top
                        formatter: '{c}',
                        textStyle: { color: '#000' }
                    }
                },
                itemStyle: {
                    normal: {
                        color: 'rgba(128, 128, 128, 0.3)'    // 仍为透明
                    }
                },
                data: sumData,
            },
            {
                name: '学生',
                type: 'bar',
                barWidth:20,
                stack: '总量',
                color:'rgb(200, 103, 175)',
                label: {
                    show: true,
                    position: 'insideRight'
                },
                data: ${cm:toJSONArray(students)}
            },
            {
                name: '教工',
                type: 'bar',
                barWidth:20,
                stack: '总量',
                color:'rgb(124, 181, 236)',
                label: {
                    show: true,
                    position: 'insideRight'
                },
                data: ${cm:toJSONArray(teachers)}
            }
        ]
    };
    myChart.setOption(option);
</script>