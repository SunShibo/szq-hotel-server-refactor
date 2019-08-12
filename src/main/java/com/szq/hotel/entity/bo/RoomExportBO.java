package com.szq.hotel.entity.bo;

import java.io.Serializable;

/**
 * @Author: Wang bin
 * @date: Created in 15:05 2019/8/12
 */
public class RoomExportBO implements Serializable {
    private String roomName; //房号
    private String hotelName;//楼栋
    private String floorName;//楼层
    private String roomTypeName;//房型名称
    private String roomMajorState;//房态
    private String roomState;//是否维修
    private String lockRoomState;//是否锁房
    private String lockRoomStartTime;//锁房开始时间
    private String lockRoomEndTime;//锁房结束时间
    private String remark;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getRoomMajorState() {
        if("vacant".equals(roomMajorState)){
            return  "空房";
        }
        if("inthe".equals(roomMajorState)){
            return "在住";
        }
        if("timeout".equals(roomMajorState)){
            return"超时";
        }
        if("dirty".equals(roomMajorState)){
            return  "脏房";
        }
        return "空房";
    }

    public void setRoomMajorState(String roomMajorState) {
            this.roomMajorState = roomMajorState;

    }

    public String getRoomState() {
        if("no".equals(roomState)){
            return "未维修";
        }
        if("yes".equals(roomState)){
            return "维修";
        }
        return "未维修";
    }

    public void setRoomState(String roomState) {
        this.roomState = roomState;

    }

    public String getLockRoomState() {
        if("no".equals(lockRoomState)){
            return "未锁房";
        }
        if("yes".equals(lockRoomState)){
            return "锁房";
        }
        return "未锁房";
    }

    public void setLockRoomState(String lockRoomState) {
            this.lockRoomState = lockRoomState;
    }

    public String getLockRoomStartTime() {
        return lockRoomStartTime;
    }

    public void setLockRoomStartTime(String lockRoomStartTime) {
        this.lockRoomStartTime = lockRoomStartTime;
    }

    public String getLockRoomEndTime() {
        return lockRoomEndTime;
    }

    public void setLockRoomEndTime(String lockRoomEndTime) {
        this.lockRoomEndTime = lockRoomEndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "RoomExportBO{" +
                "roomName='" + roomName + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", floorName='" + floorName + '\'' +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", roomMajorState='" + roomMajorState + '\'' +
                ", roomState='" + roomState + '\'' +
                ", lockRoomState='" + lockRoomState + '\'' +
                ", lockRoomStartTime='" + lockRoomStartTime + '\'' +
                ", lockRoomEndTime='" + lockRoomEndTime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
