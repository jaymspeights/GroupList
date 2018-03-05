package com.jaymspeights.jay.grouplist;

/**
 * Created by Jay on 3/4/2018.
 */

public interface WebsocketHandler {
    public void all_lists(String[] lists);
    public void error(String message);
    public void list_update(String[] changes);
}
