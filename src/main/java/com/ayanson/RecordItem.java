package com.ayanson;

public class RecordItem {

    private int itemId;
    private int groupId;

    public int getItemId() {
        return  itemId;
    }

    public int getGroupId() {
        return  groupId;
    }

    public RecordItem(int itemId, int groupId){
        this.itemId = itemId;
        this.groupId = groupId;
    }

}
