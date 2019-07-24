//预约入住房间修改价格
clearModifyPrice();
function modifyPrice() {
    var checktype = $("input[name='checkType']:checked").val();
    if(sRooms.length==0){
        layer.msg('请先选房', {icon: 2, shift: 6})
        return;
    }
    if(sRooms[0]['roomId']==0){
        layer.msg('请先选房', {icon: 2, shift: 6})
        return;
    }

    if($("input[name='checkType']:checked").val()=="free"){
        return ;
    }

    var typeids = [];
    for(var i=0;i<sRooms.length;i++){
        if(sRooms[i]['roomTypeId']){
            if(typeids.indexOf(sRooms[i]['roomTypeId']+"")==-1){
                typeids.push(sRooms[i]['roomTypeId']+"")
            }
        }else {
            if(typeids.indexOf(sRooms[i]['roomType']+"")==-1){
                typeids.push(sRooms[i]['roomType']+"")
            }
        }
    }
    // console.log(typeids)
    var index = layer.load(1,{time:10*1000});
    $.ajax({
        data: {
            phone:document.getElementById("phone").value.trim(),
            startTime:document.getElementById("startTime").value,//入住时间,
            dayNum:checktype=='hour'?1:document.getElementById("days1").value,
            typeIds:typeids.join(","),
            orderId:yuyueData.id,
            checkType:checktype,
        },
        type: "POST",
        dataType: "JSON",
        url: 'updatePrice/updatePrice',
        complete: function () {
            layer.close(index);
        },
        success: function (rs) {

            layer.close(index);
            if (!rs.success) {
                layer.alert(rs.errMsg)
            }else{
                for(var i=0;i<sRooms.length;i++){
                    for(var j=0;j<rs.data.length;j++){
                        if(sRooms[i]['roomTypeId']){
                            if(sRooms[i]['roomTypeId']== rs.data[j]['roomTypeId']){
                                sRooms[i]['basicPrice'] = sRooms[i]['hourRoomPrice'] = rs.data[j]['price'];
                            }
                        }else{
                            if(sRooms[i]['roomType']== rs.data[j]['roomTypeId']){
                                sRooms[i]['basicPrice'] = sRooms[i]['hourRoomPrice'] = rs.data[j]['price'];
                            }
                        }

                    }
                }

                sRooms1 = JSON.parse(JSON.stringify(sRooms));
                localStorage.modifyPrice = JSON.stringify(rs.data);

                var dda = checktype=='hour'?1:document.getElementById("days1").value;
                dda == 0 ? dda = 1 : dda = dda;
                layer.open({
                    area: ['1000px', '420px'],
                    type: 2,
                    content: "iframe_modifyPriceYYRZ.html?v=" + Date.now()+"&dayNumber=" + dda +"&orderId=" + yuyueData.id,
                    title: "修改价格"
                })
            }
        }
    });





}
function getTypeIds() {
    var ids = [];
    sRooms.map(function(current,index){
        if(ids.indexOf(current['houseTypeId'])==-1){
            ids.push(current['houseTypeId'])
        }
    })
    return ids.join(",");
}

function parModifyPrice() {
    if(localStorage.modifyPrice){

        var _mp = JSON.parse(localStorage.modifyPrice);
        var _total = 0;
        _mp.map(function (curr,index) {
            for(var i in curr){
                if(i.split('/').length>2&&i.indexOf('y')==-1){

                    _total+=(getRoomQuantity(curr['roomTypeId'])*Number(curr[i]));
                }
            }
            if(window.location.href.indexOf("appoint")>-1){
                //表示预约入住页面
                for(var i=0;i<sRooms.length;i++){
                    if(sRooms[i].roomType == curr['roomTypeId']||sRooms[i].roomType == curr['roomTypeName']){
                        sRooms[i]['hourRoomPrice'] = sRooms[i]['basicPrice'] = getOneDayPrice(_mp,curr['id']);
                    }
                }
            }else{
                //表示预约页面
                for(var i=0;i<sRooms.length;i++){
                    if(sRooms[i].id == curr['id']){
                        sRooms[i]['hourRoomPrice'] = sRooms[i]['basicPrice'] = getOneDayPrice(_mp,curr['id']);
                    }
                }
            }
        })

        function getOneDayPrice(a,id) {
            for(var j=0;j<a.length;j++){
                if (a[j]['id'] != id) {
                    continue;
                }
                for (var i in a[j]) {
                    if (i.split('/').length > 2 && i.indexOf('y') == -1) {
                        return a[j][i];
                        break;
                    }
                }
                break;
            }
        }
        var _v = $("#totalPrice").text();

        if(_v.indexOf("/")==-1){
            $("#totalPrice").text(_total+"/"+_v);
        }else{
            $("#totalPrice").text(_total+"/"+_v.split('/')[1]);
        }

        tableObj.reload({data:JSON.parse(JSON.stringify(sRooms))})
        state.selRow=[];
    }
}
function getRoomQuantity (v){
    var _num=0;
    if(window.location.href.indexOf("appoint")>-1){
        //表示预约入住页面

        sRooms.map(function (curr) {
            if(curr['roomType']==v||curr['roomTypeId']==v)_num+=1
        })
    }else{
        //表示预约页面

        sRooms.map(function (curr) {
            if(curr['id']==v)_num+=1
        })
    }
    return _num;
}
