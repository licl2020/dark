$(function () {
    //页面尺寸变化 刷新页面
    $(window).resize(function () {
        window.location.reload();
    });

    setInterval(function () {
        if ($('.JenterManage').hasClass('animated')) {
            $('.JenterManage').removeClass('animated bounce');//bounceIn
        } else {
            $('.JenterManage').addClass('animated bounce');
        }
    }, 1500);

    //region 基础数据统计
    bdtask();
    setInterval(function () {
        bdtask();
    }, 30*1000);

    function doul(ul,value) {
        ul.empty();
        for (var i=value.length-1;i >= 0;i--) {
            ul.prepend("<li>"+value[i]+"</li>");
        }

        if(value.length < 7){
            for(var j = 0;j< 7-value.length;j++){
                ul.prepend("<li>"+0+"</li>");
            }
        }
    }


    function bdtask() {
        $.ajax({
            url: "/zerb-web/static/baseData.action",
            type: 'POST',
            dataType: 'json',
            async: false,
            success: function (map) {
                for(var key in map){
                    // console.log("属性：" + key + ",值：" + map[key]);
                    switch (key) {
                        case "user_count":
                            if(map[key] != "0") {
                                var ul = $("#ul1");
                                doul(ul,map[key]);
                            }
                            break;
                        case "download_count":
                            if(map[key] != "0") {
                                var ul = $("#ul2");
                                doul(ul,map[key]);
                            }
                            break;
                        case "entrust_count":
                            if(map[key] != "0") {
                                var ul = $("#ul3");
                                doul(ul,map[key]);
                            }
                            break;

                            //----
                        case "user_last":
                             $("#ul11").html(map[key]+"%");
                            break;
                        case "download_last":
                            $("#ul22").html(map[key]+"%");
                            break;
                        case "entrust_last":
                            $("#ul33").html(map[key]+"%");
                            break;
                    }
                }
            },
            error: function (map) {
                console.log(map);
            }
        });
    }
    //endregion


    //region 实名用户比率
    var JauthNum = echarts.init(document.getElementById('JauthNum'));
    var JhomeIndustryOption_real = {
        legend: {
            data: ['已实名用户', '未实名用户'],
            itemWidth: 14,
            itemHeight: 14,
            borderWidth: 1.8,
            borderColor: '#3160C2',
            show: true,
            padding: [6, 8],
            shadowColor: '#2C68DC',
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            textStyle: {
                color: '#ADC0EE'
            },
            borderRadius: 3,
            bottom: '5%',
            right: '1%',
            orient: 'vertical'
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: ['35%', '55%'],
                color: ['#0194e1', ' #ffc637'],
                center: ['50%', '50%'],
                label: {
                    normal: {
                        formatter: '{d}%\n{b}'
                        // formatter: '{d}%'
                    }
                },
                data: [{value: 0, name: '已实名用户'},
                    {value: 0, name: '未实名用户'}]
            }
        ]
    };


    rnametask();

    setInterval(function () {
        rnametask();
    }, 34*1000);

    function rnametask() {
        $.ajax({
            url: "/zerb-web/static/realNameRate.action",
            type: 'POST',
            dataType: 'json',
            async: false,
            success: function (map) {
                 var totalCount = map["totalCount"];
                 var unRName = map["unRName"];
                JhomeIndustryOption_real.series[0].data[0].value=totalCount-unRName;
                JhomeIndustryOption_real.series[0].data[1].value=unRName;
            },
            error: function (map) {
                console.log(map);
            }
        });
    }

    JauthNum.setOption(JhomeIndustryOption_real);
    //endregion


    //region 在线活跃度统计
    var legend_month=[];
    var JcustomerApply = echarts.init(document.getElementById('JcustomerApply'));
    var JcustomerApplyOption = {
        tooltip: {
            trigger: 'axis'
        },

        legend: {
            // data: ['一月', '二月', '三月'],
            data:legend_month,
            itemHeight: 10,
            borderWidth: 1.8,
            borderColor: '#3160C2',
            show: true,
            padding: [10, 25, 10, 10],
            shadowColor: '#2C68DC',
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            textStyle: {
                color: '#ADC0EE'
            },
            borderRadius: 3,
            top: '2%',
            right: '4%'
            // orient: 'horizontal'
            // orient: 'vertical'
        },
        grid: {
            top: '12%',
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['1','2','3','4', '5','6','7','8','9', '10',
                '11','12','13','14','15', '16','17','18','19','20',
                '21','22','23','24','25','26','27','28','29','30','31'],
            splitLine: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: ['#0F53A3'],
                    width: 2
                }
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                fontWeight: 'bold',
                color: '#cad7fa',
                textStyle: {
                    color: '#cad7fa',//坐标值得具体的颜色

                }
            }
        },
        yAxis: {
            type: 'value',
            axisLine: {
                lineStyle: {
                    color: ['#0F53A3'],
                    width: 2
                }
            },
            splitLine: {
                lineStyle: {
                    color: ['#0F53A3'],
                    type: 'dashed'
                }
            },
            axisTick: {
                length: 25,
                lineStyle: {
                    color: ['#0E50AC'],
                    width: 2
                }
            },
            axisLabel: {
                margin: 35,
                fontWeight: 'bold',
                color: '#cad7fa',
                textStyle: {
                    color: '#cad7fa',//坐标值得具体的颜色

                }
            }
        },

        //9FCA56 D49809 0194E1 A401E0 E10170 E17501
        series: [
            {
                name: '',
                type: 'line',

                symbolSize: 1,
                // data: ['2', '4', '7', '5', '10', '2', '2'],
                data: [],
                lineStyle: {
                    width: 4,

                },
                itemStyle: {
                    normal: {
                        color: '#a9c05c',
                        lineStyle: {
                            color: '#a9c05c'
                        }
                    }
                },

                hoverAnimation: true
            },
            {
                name: '',
                type: 'line',

                data: [],
                symbolSize: 1,
                lineStyle: {
                    width: 4,

                },
                itemStyle: {
                    normal: {
                        color: '#d7960a',
                        lineStyle: {
                            color: '#d7960a'
                        }
                    }
                },
                hoverAnimation: true
            }, {
                name: '',
                type: 'line',

                data: [],
                symbolSize: 1,
                lineStyle: {
                    width: 4,

                },
                itemStyle: {
                    normal: {
                        color: '#1289dd',
                        lineStyle: {
                            color: '#1289dd'
                        }
                    }
                },
                hoverAnimation: true
            }
        ]
    };
    onlinetask();

    setInterval(function () {
        onlinetask();
    }, 31*1000);

    function onlinetask() {
        $.ajax({
            url: "/zerb-web/static/onlineStatic.action",
            type: 'POST',
            dataType: 'json',
            async: false,
            success: function (map) {
                var i = 0;
                for (var key in map) {
                    // console.log("属性：" + key + ",值：" + map[key]);
                    legend_month.push(key + "月");
                    JcustomerApplyOption.series[i].name = key + "月"

                    for (var a = 0; a < map[key].length; a++) {
                        JcustomerApplyOption.series[i].data.push(map[key][a]);
                    }

                    i++;
                }

            },
            error: function (map) {
                console.log(map);
            }
        });
    }

    JcustomerApply.setOption(JcustomerApplyOption);
    //endregion


    //region top10
    var JhomeIndustry = echarts.init(document.getElementById('JhomeIndustry'));
    var JhomeIndustryOption = {
        title: {},
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {},
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
            , axisLabel: {
                textStyle: {
                    color: '#cad7fa',//坐标值得具体的颜色

                }
            }
            , splitLine: {show: false},
        },
        yAxis: {
            type: 'category',
            // data: ['应用名称1', '应用名称2', '应用名称3', '应用名称4', '应用名称5', '应用名称6', '应用名称7', '应用名称8', '应用名称9', '应用名称10']
            data: []
            , axisLabel: {
                textStyle: {
                    color: '#cad7fa',//坐标值得具体的颜色

                }
            }
            , splitLine: {show: false},
        },
        series: [
            {
                name: '次',
                type: 'bar',
                // data: [501.88, 543.34, 234.54, 600, 877, 123, 895, 466, 591.25, 433],
                data: [],
                itemStyle: {
                    //通常情况下：
                    normal: {
                        //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                        color: function (params) {
                            var colorList = ['#01a3ec','#ef1a1f', '#fd7f25', '#fef003' , '#9932CC'
                                , '#9AFF9A', '#8B0A50', '#76EE00', '#1E90FF', '#CD3278'];
                            return colorList[params.dataIndex];

                            // var a=params.dataIndex;
                            // if(a<=2){
                            //     var colorList = ['#ef1a1f', '#fd7f25', '#fef003' ];
                            //     return colorList[params.dataIndex];
                            // }else if((a>2)&&(a<=3)){
                            //     var colorList = ['#01a3ec','#ef1a1f', '#fd7f25', '#fef003' ];
                            //     return colorList[params.dataIndex];
                            // }else if((a>3)&&(a<=4)){
                            //     var colorList = ['#01a3ec','#01a3ec','#ef1a1f', '#fd7f25', '#fef003' ];
                            //     return colorList[params.dataIndex];
                            // }else if((a>4)&&(a<=5)){
                            //     var colorList = ['#01a3ec','#01a3ec','#01a3ec','#ef1a1f', '#fd7f25', '#fef003' ];
                            //     return colorList[params.dataIndex];
                            // }else if((a>5)&&(a<=6)){
                            //     var colorList = ['#01a3ec','#01a3ec','#01a3ec','#01a3ec','#ef1a1f', '#fd7f25', '#fef003' ];
                            //     return colorList[params.dataIndex];
                            // }else if((a>6)&&(a<=7)){
                            //     var colorList = ['#01a3ec','#01a3ec','#01a3ec','#01a3ec','#01a3ec','#ef1a1f', '#fd7f25', '#fef003' ];
                            //     return colorList[params.dataIndex];
                            // }else if((a>7)&&(a<=8)){
                            //     var colorList = ['#01a3ec','#01a3ec','#01a3ec','#01a3ec','#01a3ec','#01a3ec','#ef1a1f', '#fd7f25', '#fef003' ];
                            //     return colorList[params.dataIndex];
                            // }else if((a>8)&&(a<=9)){
                            //     var colorList = ['#01a3ec','#01a3ec','#01a3ec','#01a3ec','#01a3ec','#01a3ec','#01a3ec','#ef1a1f', '#fd7f25', '#fef003' ];
                            //     return colorList[params.dataIndex];
                            // }





                        }
                    }
                },
            }
        ]
    }

    top10task();

    setInterval(function () {
        top10task();
    }, 32*1000);

    function top10task() {
        $.ajax({
            url: "/zerb-web/static/top10.action",
            type: 'POST',
            dataType: 'json',
            async: false,
            success: function (map) {
                for(var key in map){
                    JhomeIndustryOption.yAxis.data.push(key);
                    JhomeIndustryOption.series[0].data.push(map[key]);

                }
            },
            error: function (map) {
                console.log(map);
            }
        });
    }


    JhomeIndustry.setOption(JhomeIndustryOption);
    //endregion


    //region 年龄分布统计
    var JauthorizeExpire = echarts.init(document.getElementById('JauthorizeExpire'));
    var JauthorizeExpireOption = {
        title: {},
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'right',
            data: ['18-30', '30-40', '40-65', '65以上']
            , textStyle: {
                color: '#99b2f4',
            },
            itemHeight: 10,
            borderWidth: 1.8,
            borderColor: '#3160C2',
            show: true,
            padding: [10, 25, 10, 10],
            shadowColor: '#2C68DC',
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        series: [
            {
                name: '年龄',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: 0, name: '18-30'},
                    {value: 0, name: '30-40'},
                    {value: 0, name: '40-65'},
                    {value: 0, name: '65以上'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }

                }
            }
        ]
    };


    ageRatetask();

    setInterval(function () {
        ageRatetask();
    }, 35*1000);

    function ageRatetask() {
        $.ajax({
            url: "/zerb-web/static/ageRate.action",
            type: 'POST',
            dataType: 'json',
            async: false,
            success: function (map) {
                var i = 0;
                for(var key in map){
                    JauthorizeExpireOption.series[0].data[i].value = map[key];
                    i++;
                }
            },
            error: function (map) {
                console.log(map);
            }
        });
    }

    JauthorizeExpire.setOption(JauthorizeExpireOption);
    //endregion


    //region realTime
    realTimetask();

    setInterval(function () {
        realTimetask();
    }, 33*1000);

    function realTimetask() {
        $.ajax({
            url: "/zerb-web/static/realTime.action",
            type: 'POST',
            dataType: 'json',
            async: false,
            success: function (list) {
                for(var i=0;i < list.length;i++){
                     var li = [];

                      li.push("<li><span>"+list[i].time+"</span>" +
                          "<span>"+list[i].affair+"</span>" +
                          "<span>"+list[i].phone+"</span>" +
                          "<span class='namepeople'>"+list[i].name+"</span>" +
                          "<span>"+list[i].idNumber+"</span></li>");

                     li.join("");

                     $("#realUL").append(li);
                }
            },
            error: function (map) {
                console.log(map);
            }
        });
    }
    //endregion

});