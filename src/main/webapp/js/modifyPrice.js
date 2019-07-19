clearModifyPrice();
function modifyPrice() {

    //console.log(sRooms)
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
    if($("input[name='checkType']:checked").val()!="day"){
        return ;
    }
    productRoomType();

    layer.open({
        area: ['1000px', '420px'],
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
                    if (i.split('-').length > 2 && i.indexOf('y') == -1) {
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
