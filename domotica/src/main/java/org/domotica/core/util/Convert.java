package org.domotica.core.util;

import java.util.List;

public class Convert {

    public String listToString(String listName, List list){
        if (list == null)return "[]";
        if (list.size() == 0)return "[]";

        StringBuffer buffer = new StringBuffer();
        boolean notFirstElement = false;
        buffer.append("\"").append(listName).append("\": [ ");
        for (Object obj : list) {
            if (notFirstElement) buffer.append(", ");
            buffer.append(obj.toString());
            notFirstElement = true;
        }
        buffer.append(" ]");

        return buffer.toString();
    }


}
