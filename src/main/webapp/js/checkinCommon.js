//入住预约等等页面公共方法

//清除房间信息------用户修改身份证或者电话号码之后执行方法
function clearRoomInfo() {
    sRooms = [];
    sRooms1 = []
    customs = [];
    calcPrice();
    localStorage.modifyPrice = '';
}

//清空表单
function clearForm() {
    document.getElementById("myform").reset();
    layui.form.render();
}

//备注修改
function remarks(){
    if(state.selRow.length==0){
        layer.msg("您还没有勾选房间");
        return;
    }
    if(!state.selRow[0]['roomId']){
        layer.msg("您还没有选择房间");
        return;
    }
    layer.prompt({
        formType: 2,
        value: '',
        title: '备注修改',
    }, function(value, index, elem){
        eachReamrk(state.selRow,value)
        layer.close(index);
    });
}

//取消操作
function cancel(){
    sessionStorage.clear();
    location.href =HOME;
}

//同来人页面60行调用了
function parToget() {
    calcPrice();
}

//《清除资料》按钮
function clearData() {
    sessionStorage.clear();
    location.reload();
}

//识别渲染会员信息---预约页面使用
function renderUserInfo(r) {

    userInfo = r;
    try{
        document.getElementById('orderPlacer').value = r['name'];
        document.getElementById('certificateId').value = r['certificateType'];
        document.getElementById('IDNumber').value = r['certificateNumber'];
        document.getElementById('phone').value = r['phone'];
        document.getElementById("userLevel").innerText=r['memberLevelName'];
        if($("#certificateId").val() == null){
            document.getElementById('certificateId').value = 1;
        }
        customer_source_before = document.getElementById("channel").value;
        document.getElementById("channel").value = "会员";
        form.render();
    }catch (err){}

}

//生产改价数据
function getModifyPriceData(days) {

    if($("input[name='checkType']:checked").val()=="hour"){
        days = 1;
    }

    var time = $("#startTime").val();
    if(!time){
        time = DateToLStr(new Date())
    }

    if(new Date(time).getHours()<6){
        //如果6点之前，时间取前一天
        time = addDate(new Date(time),-1)
    }

    var arr = [
        {
            time:time.split(" ").length>1?time.split(" ")[0]:time
        }
    ];

    for(var i=1;i<Number(days);i++){
        arr.push({
            time:addDate(new Date(time),i)
        })
    }
    return arr;
}

setTimeout(function () {

    //获取侧边栏统计数据方法
    renderReport($)
    $(function () {
        setInterval(function () {
            getReport($)
        }, 60000)
    })

    //获取合作结构下拉框数据
    renderSelect("channelSelect", 'id', 'value', api.allChannel, form,function (str,rs) {allChannel = rs;});

    // 证件类型获取
    renderSelect("certificateId",'id','value',api.certificate,form);//证件类型获取000

    //设置接单人
    try{
        $("#clerkOrderingName").val(JSON.parse( localStorage.User ).name)
    }catch (e){
        console.info(e)
    }

    //开始时间选择
    laydate.render({
        elem: '#startTime',
        type:"datetime",
        min:0,
        format:'yyyy/MM/dd HH:mm:ss',
        done: function(value, date, endDate){

            if(value.indexOf("00:00:00")>0){
                var a = value.split(" ")[0] + " 14:00:00";
                setTimeout(function () {
                    $('#startTime').val(a);
                },100)
            }
            clearRoomInfo();
            clearTimeInfo();
        }
    });

    //离店时间选择
    laydate.render({
        elem: '#leaveDate1',//全天房和免费房离店时间
        min:0,
        format:'yyyy/MM/dd HH:mm:ss',
        done: function(value, date, endDate){

            if($("#startTime").val()==''){
                clearTimeInfo();
                layer.msg('请先选择入住时间', {icon: 2,shift:6});
                return;
            }
            if(new Date($("#startTime").val()).getTime()>(new Date(value.split(" ")[0]).getTime()+21600000)){
                clearTimeInfo();
                layer.msg('入住和离店时间选择有误', {icon: 2,shift:6});
                return;
            }
            var vv =  GetDateDiff($("#startTime").val(),value.split(" ")[0] + LEAVETIME);
            if(vv==0){
                clearTimeInfo();
                layer.msg('离店时间有误', {icon: 2,shift:6});
                return;
            }
            updateTimeInfo(value);
        }
    });

    //点击房间房型表格复选框获取该行数据
    table.on('checkbox(fjxx)', function(obj){
        state.selRow = table.checkStatus('test').data;
    });

    //更换合作机构后改变客源数据
    form.on('select(floor)', function (data) {
        document.getElementById("channel").value = $("#channelSelect").find("option:selected").text();
    });

    //合作机构选择按钮状态变化
    form.on('checkbox(hzjg)', function(data) {
        if(data.elem.checked){
            state.hzjg = true;
            customer_source_before = document.getElementById("channel").value;
            document.getElementById("channel").value = $("#channelSelect").find("option:selected").text();
            $(".channelSelect").removeClass("layui-hide")
        }else{
            state.hzjg = false;
            document.getElementById("channel").value = customer_source_before;
            $(".channelSelect").addClass("layui-hide")
        }
    });

        //小时房全天房免费房互相切换
        form.on('radio', function(data){
            if(data.value=="hour"){
                $(".typea").addClass("layui-hide")
                $(".typeb").removeClass("layui-hide")
            }else {
                $(".typeb").addClass("layui-hide")
                $(".typea").removeClass("layui-hide")
            }
            if(currSel.checkType!=data.value){
                currSel.checkType=data.value;
                state.roomType=data.value;
                clearRoomInfo();
            }
        });
},500)