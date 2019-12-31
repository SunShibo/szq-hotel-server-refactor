//直接入住房间修改价格
clearModifyPrice();
function modifyPrice() {

    var price = '';
    if(sRooms.length==0){
        return;
    }
    if($("input[name='checkType']:checked").val() =="free"){
        return ;
    }
    if($("input[name='checkType']:checked").val() =="day"){
        price = 'basicPrice';
    }
    if($("input[name='checkType']:checked").val() =="hour"){
        price = 'hourRoomPrice';
    }
    var aaa1 = [];//处理过改价格用的房间数据
    var dayss = $("#days").val();//几天
    if($("input[name='checkType']:checked").val()=="hour"){
        dayss = 1;//几天
    }

    if(localStorage.modifyPrice==''){
        var temp = [];
        var tempRoomType = [];
        var days = getModifyPriceData($("#days").val());

        for(var i=0;i<sRooms.length;i++){
            if(!sRooms[i]['yhourRoomPrice']){
                sRooms[i]['yhourRoomPrice'] = sRooms[i]['hourRoomPrice'] +'';
            }
            if(!sRooms[i]['ybasicPrice']){
                sRooms[i]['ybasicPrice'] = sRooms[i]['basicPrice'] +'';
            }
            var yuanjia = 0;//原价
            if($("input[name='checkType']:checked").val() =="day"){
                yuanjia = sRooms[i]['ybasicPrice'] + '';
            }else if($("input[name='checkType']:checked").val() =="hour"){
                yuanjia = sRooms[i]['yhourRoomPrice'] + '';
            }

            if(temp.indexOf(sRooms[i].id) == -1 && tempRoomType.indexOf(sRooms[i].roomType) == -1 ){
                temp.push(sRooms[i].id);
                tempRoomType.push(sRooms[i].roomType);
                var newdata = JSON.parse(JSON.stringify(sRooms[i]));
                for(var j=0;j<days.length;j++){
                    newdata[days[j]['time']] = newdata[price];
                    if(newdata['roomTypeId']){
                    }else {
                        newdata['roomTypeId'] = newdata['id'];
                    }

                    if(!newdata['roomTypeName'])newdata['roomTypeName'] = newdata['name'];


                    if( !newdata['y'+days[j]['time']]){
                        newdata['y'+days[j]['time']] = yuanjia;
                    }
                }
                aaa1.push(newdata)
            }
        }

        localStorage.modifyPrice = JSON.stringify(aaa1);
    }


    layer.open({
        area: ['1000px', '420px'],
        type: 2,
        content: "iframe_modifyPriceZJRZ.html?v=" + Date.now()+"&dayNumber="+dayss + "&checkType=" + $("input[name='checkType']:checked").val(),
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

            //表示预约页面
            for(var i=0;i<sRooms.length;i++){
                if(sRooms[i].id == curr['id']||sRooms[i].roomTypeId == curr['roomTypeId']){
                    sRooms[i]['ybasicPrice'] = sRooms[i]['basicPrice'] + "";
                    sRooms[i]['yhourRoomPrice'] = sRooms[i]['hourRoomPrice'] + "";
                    sRooms[i]['hourRoomPrice'] = sRooms[i]['basicPrice'] = getOneDayPrice(_mp,curr['id']);

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
            $("#totalPrice").text((Math.round(_total * 100) / 100)+"/"+_v);
        }else{
            $("#totalPrice").text((Math.round(_total * 100) / 100)+"/"+_v.split('/')[1]);
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

            if(curr['roomType']==v||curr['roomTypeId']==v||curr['id']==v)_num+=1
        })
    }
    return _num;
}
