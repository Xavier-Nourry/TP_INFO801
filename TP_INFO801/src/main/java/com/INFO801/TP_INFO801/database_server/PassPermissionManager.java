package com.INFO801.TP_INFO801.database_server;

import java.util.ArrayList;
import java.util.HashMap;

public class PassPermissionManager {
    public ArrayList<Building> buildings;
    public ArrayList<Pass> passes;
    private HashMap<Pass, ArrayList<Building>> permissions;

    public PassPermissionManager(){
        buildings = new ArrayList<>();
        passes = new ArrayList<>();
        permissions = new HashMap<>();
    }
}
