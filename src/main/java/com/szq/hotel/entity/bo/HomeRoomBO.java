package com.szq.hotel.entity.bo;

public class HomeRoomBO {
    private Integer roomId;//房间id
    private Integer childId;
    private String roomName;
    private String roomType;
    private String status;//空房/在住/超时/脏房/
    private Integer makeStatus;//预约状态
    private String outStatus;//预离状态
    private String balance;//余额
    private String checkType;//入住方式
    private String lockRoomState;//锁房状态
    private String maintain;//维修


    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMakeStatus() {
        return makeStatus;
    }

    public void setMakeStatus(Integer makeStatus) {
        this.makeStatus = makeStatus;
    }

    public String getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getLockRoomState() {
        return lockRoomState;
    }

    public void setLockRoomState(String lockRoomState) {
        this.lockRoomState = lockRoomState;
    }

    public String getMaintain() {
        return maintain;
    }

    public void setMaintain(String maintain) {
        this.maintain = maintain;
    }

    @Override
    public String toString() {
        return "HomeRoomBO{" +
                "roomId=" + roomId +
                ", childId=" + childId +
                ", roomName='" + roomName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", status='" + status + '\'' +
                ", makeStatus='" + makeStatus + '\'' +
                ", outStatus='" + outStatus + '\'' +
                ", balance='" + balance + '\'' +
                ", checkType='" + checkType + '\'' +
                ", lockRoomState='" + lockRoomState + '\'' +
                '}';
    }
}
