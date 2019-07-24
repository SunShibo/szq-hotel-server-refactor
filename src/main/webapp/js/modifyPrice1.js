clearModifyPrice();
function modifyPrice() {
    var inWay = state.roomType;
    var phoneNumber = $("#phoneNumber").val();
    var cnId = '';
    var dayNumber = $("#days").val();
    var roomTypeIds = getTypeIds();
    var certificateNumnber = $("#credentialNo").val();
    if(sRooms.length==0){
        return;
    }
    if(phoneNumber==''||dayNumber==''){
        return;
    }
    if (state.hzjg) {
        cnId = $("#channelSelect").val();
    }
    if(dayNumber==null){
        dayNumber = $("#days1").val();
    }
    var peo = checkPeopleNum();
    var stime = '';
    if($("#startTime").val()){
        stime = $("#startTime").val().replace(/-/g, "/")
    }
    if(state.roomType!=1){
        return ;
    }
    layer.open({
        area: ['1000px', '420px'],
        // area : ['100%', '100%'],
        type: 2,
        content: "iframe_mondifyPrice.html?v=" + Date.now()
        +"&inWay="+inWay
        +"&cnId="+cnId
        +"&phoneNumber="+phoneNumber
        +"&dayNumber="+dayNumber
        +"&roomTypeIds="+roomTypeIds
        +"&certificateNumnber="+certificateNumnber
        +"&startTime="+stime,
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
                if(i.split('-').length>2&&i.indexOf('y')==-1){

                    _total+=(getRoomQuantity(curr['roomTypeId'])*Number(curr[i]));
                }
            }
        })
        var _v = $("#totalPrice").text();

        if(_v.indexOf("/")==-1){
            $("#totalPrice").text(_total.toFixed(2)+"/"+_v);
        }else{
            $("#totalPrice").text(_total.toFixed(2)+"/"+_v.split('/')[1]);
        }
    }
}
function getRoomQuantity (v){
    var _num=0;
    sRooms.map(function (curr) {
        if(curr['houseTypeId']==v)_num+=1
    })
    return _num;
}
