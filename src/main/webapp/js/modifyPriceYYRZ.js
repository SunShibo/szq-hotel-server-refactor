//预约入住房间修改价格
clearModifyPrice();
function modifyPrice() {

    if(sRooms.length==0){
        return;
    }
    if($("input[name='checkType']:checked").val()!="day"){
        return ;
    }



        aaa1 = [];
        var temp = [];
        var days = getModifyPriceData($("#days1").val());
        for(var i=0;i<sRooms.length;i++){

            if(temp.indexOf(sRooms[i].roomType) == -1 ){
                //房間預約的id是房型id，這裡是預約入住，用這個 roomType
                temp.push(sRooms[i].roomType);
                var newdata = JSON.parse(JSON.stringify(sRooms[i]));
                for(var j=0;j<days.length;j++) {
                    var yudingPrice = yuyueData.orderChildBOS;
                    var flag = true;
                    for (var k = 0; k < yudingPrice.length; k++) {

                        if (yudingPrice[k]['roomTypeId'] == sRooms[i].roomType
                            || yudingPrice[k]['roomTypeName'] == sRooms[i].roomType) {
                            var ppp = yudingPrice[k]['everydayRoomPriceBOS'];
                            for (var l = 0; l < ppp.length; l++) {
                                // debugger;
                                if (DateToLStr(new Date(ppp[l]['time']['time'])).indexOf(days[j]['time']) > -1) {
                                    newdata[days[j]['time']] = ppp[l]['money'];
                                    newdata['y' + days[j]['time']] = ppp[l]['money'];
                                    break;
                                }
                            }
                            flag = false;
                        }
                    }

                    if(flag){
                        //这种情况下，预定的房型里面没有该房型
                        if($("input[name='checkType']:checked").val()=="hour"){
                            //小时房
                            newdata[days[j]['time']] = newdata['hourRoomPrice'];
                            newdata['y' + days[j]['time']] = newdata['hourRoomPrice'];
                        }else{
                            //全天房
                            newdata[days[j]['time']] = newdata['basicPrice'];
                            newdata['y' + days[j]['time']] = newdata['basicPrice'];
                        }
                    }
                }




                if(!newdata['roomTypeId']&&!isNaN(newdata['roomType'])){
                    newdata['roomTypeId'] =newdata['roomType'];
                }
                if(newdata['name']){
                    newdata['roomTypeName'] = newdata['name']+"";
                }
                if(newdata['roomTypeName']){
                    newdata['name'] = newdata['roomTypeName'] + "";
                }

                aaa1.push(newdata)
            }
        }

        localStorage.modifyPrice = JSON.stringify(aaa1);



    layer.open({
        area: ['1000px', '420px'],
        type: 2,
        content: "iframe_mondifyPrice.html?v=" + Date.now()+"&dayNumber="+$("#days1").val(),
        title: "修改价格"
    })
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
