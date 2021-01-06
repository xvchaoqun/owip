<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div id="inout_container" style="height:500px; margin: 0 auto;"></div>
<script>

    var inoutChart = echarts.init($('#inout_container').get(0));

    var label= [
        <c:forEach items="${months}" var="month">
            '${month}',
        </c:forEach>
    ];
    //console.log(label)

    var inData = [
        <c:forEach items="${countMemberIn}" var="in">
            {name: "${in.key}", value: '${in.value}'},
        </c:forEach>
    ];
    var outData = [
        <c:forEach items="${countMemberOut}" var="out">
        {name: "${out.key}", value: '${out.value}'},
        </c:forEach>
    ];
    var inoutOption = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['党员转入数量', '党员转出数量']
        },
        toolbox: {
            show: true,
            feature: {
                dataView: {
                    show: true,
                    title: '数据视图',
                    optionToContent: function (opt) {
                        //console.log(opt)
                        var axisData = opt.xAxis[0].data;//时间
                        var series = opt.series;//内容
                        var tdHeads = '<td  style="padding:0 10px">名称</td>';
                        axisData.forEach(function (item) {
                            tdHeads += '<td style="padding: 0 10px">'+item+'</td>';
                        });
                        var table = '<table border="1" style="margin-left:20px;border-collapse:collapse;font-size:14px;text-align:center"><tbody><tr>'+tdHeads+'</tr>';
                        var tdBodys = '';
                        for (var j = 0; j < series.length; j++) {
                            tdBodys +='<td>'+series[j].name+'</td>';
                            for (var i = 0, l = axisData.length; i < l; i++) {
                                if(typeof(series[j].data[i]) == 'object'){
                                    tdBodys += '<td>'+series[j].data[i].value+'</td>';
                                }else{
                                    tdBodys += '<td>'+ series[j].data[i]+'</td>';
                                }
                            }
                            table += '<tr>'+ tdBodys +'</tr>';
                            tdBodys = '';
                        }
                        table += '</tbody></table>';
                        return table;
                    }
                },
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {
                    name:'党员每月转入转出统计（近两年）'
                }
            }
        },
        grid:{
            x:80,           //左
            y:70,           //上
            x2:70,          //右
            //y2:25,          //下
            borderWidth:1
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: label
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value} 人'
            }
        },
        series: [
            {
                name: '党员转入数量',
                type: 'line',
                data: inData,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                }
            },
            {
                name: '党员转出数量',
                type: 'line',
                data: outData,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
            }
        ]
    };
    inoutChart.setOption(inoutOption);
</script>