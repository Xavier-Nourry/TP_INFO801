package com.INFO801.TP_INFO801.control_room.view;

import com.INFO801.TP_INFO801.database_server.Building;

public class BuildingInfo {
    private final Building data;

    public BuildingInfo(Building data) {
        this.data = data;
    }

    public Building getBuilding() {
        return data;
    }

    @Override
    public String toString() {
        return "["+(data.isOnAlarm?"x":" ")+"] "+data.id;
    }
}