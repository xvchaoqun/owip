<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="col-sm-4" id="stat_cadre_count" style="width:550px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="fa fa-pie-chart"></i>
                ${param.cadreCategory == 1?"处级":"科级"}干部${param.type == 1?"性别":"民族"}统计
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <c:if test="${param.type == 1 && param.cadreCategory == 1}">
                     <div id="cadreGender-placeholder_CJ" style="height: 250px"></div>
                </c:if>

                <c:if test="${param.type == 1 && param.cadreCategory == 2}">
                     <div id="cadreGender-placeholder_KJ" style="height: 250px"></div>
                </c:if>
                <c:if test="${param.type == 2 && param.cadreCategory == 1}">
                    <div id="cadreNation-placeholder_CJ" style="height: 250px"></div>
                </c:if>

                <c:if test="${param.type == 2 && param.cadreCategory == 2}">
                    <div id="cadreNation-placeholder_KJ" style="height: 250px"></div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script>

        var $div;
        <c:if test="${param.type == 1 && param.cadreCategory == 1}">
             $div=$('#cadreGender-placeholder_CJ');
        </c:if>
        <c:if test="${param.type == 1 && param.cadreCategory == 2}">
             $div=$('#cadreGender-placeholder_KJ');
        </c:if>
        <c:if test="${param.type == 2 && param.cadreCategory == 1}">
             $div= $('#cadreNation-placeholder_CJ');
        </c:if>
        <c:if test="${param.type == 2 && param.cadreCategory == 2}">
             $div= $('#cadreNation-placeholder_KJ');
        </c:if>

        (function($displayDiv, cadreCategory){
            var cadreOtherChart = echarts.init($displayDiv);
            cadreOtherChart.showLoading({text: '正在加载数据'});

            $.get("${ctx}/stat_cadreOther_count_data", {type:${param.type},cadreCategory:cadreCategory}, function (map) {
                var legendData = [];
                var seriesData1 = [];
                var seriesData2 = [];
                var otherMap = map.otherMap;
                $.each(otherMap, function (key, value) {
                    var item=key+'('+value+')';
                    legendData.push(item);
                    seriesData1.push({
                        name: item,
                        value: value,
                        _type: key
                    });
                    seriesData2.push({
                        name: key,
                        value: value,
                        _type: key
                    });
                });

             var option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {d}%'
                    },
                    legend: {
                        type: 'scroll',
                        orient: 'vertical',
                        left: 10,
                        top: 20,
                        bottom: 20,
                        data: legendData,
                    },
                    series: [
                        {
                            name: '${param.type == 1?"性别":"民族"}',
                            type: 'pie',
                            radius: '60%',
                            center: ['60%', '50%'],
                            data: seriesData2
                        },
                        {
                            name: '${param.type == 1?"性别":"民族"}',
                            type: 'pie',
                            radius: '60%',
                            center: ['60%', '50%'],
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

                cadreOtherChart.setOption(option, true);
                cadreOtherChart.hideLoading();

                <shiro:hasPermission name="cadre:list">
                cadreOtherChart.on('click', function (params) {
                    var status = ${param.cadreCategory == 1 ? 1 : 8};
                    if (${(param.type == 1 && (param.cadreCategory == 1 || param.cadreCategory == 2))}) {
                        var genderId = map[params.data._type];
                        var url = "";
                        if (genderId != undefined) {
                            url = "#${ctx}/cadre?isEngage=${param.isEngage}&isKeepSalary=${param.isKeepSalary}&gender={0}&status={1}".format($.trim(genderId), status);
                        } else {
                            var genderMap = _cMap.GENDER_MAP;
                            var data = [];
                            for (var i in genderMap) {
                                data.push(i);
                            }
                            url = "#${ctx}/cadre?isEngage=${param.isEngage}&isKeepSalary=${param.isKeepSalary}&genders={0}&_type={1}&status={2}".format($.trim(data), "其他", status);
                        }
                        window.open(url, "_blank");
                    }
                    if (${param.type == 2 && (param.cadreCategory == 1 || param.cadreCategory == 2)}) {
                        var nation = "";
                        var type = params.data._type;
                        var url = "";
                        if (type == '其他') {
                            nation = '其他';
                        } else if (type != '汉族') {
                            var ids = [];
                            var nations = ${cm:toJSONObject(cm:getMetaTypes("mc_nation"))};
                            var nationsName = map.nationsName;
                            ids.push("少数民族");
                            for (var j in nationsName) {
                                if (nationsName[j] == "汉族") {
                                    continue;
                                }
                                ids.push(nationsName[j]);
                            }
                            url = "#${ctx}/cadre?isEngage=${param.isEngage}&isKeepSalary=${param.isKeepSalary}&nation={0}_type={1}&status={2}".format(ids, "其他", status);
                            window.open(url, "_blank");
                            return;
                        } else {
                            nation = type;
                        }
                        url = "#${ctx}/cadre?isEngage=${param.isEngage}&isKeepSalary=${param.isKeepSalary}&nation={0}&status={1}".format($.trim(nation), status);
                        window.open(url, "_blank");
                    }
                });
                </shiro:hasPermission>

            })
        })($div[0], ${param.cadreCategory});
</script>