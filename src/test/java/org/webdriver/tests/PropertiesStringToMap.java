package org.webdriver.tests;

import java.util.*;

public class PropertiesStringToMap {

    /*
    Input string:
    =============
    "Optional[Qty: 1
    Delivery time: Esinduses kohal

    Housing color: Red]"
     */
    public static Map<String, String> stringToMap(String propertiesString){
        List<String> lines = new ArrayList<>();
        propertiesString.lines().forEach(lines::add);
        Map<String, String> propertiesMap = new HashMap<>();
        for (String l : lines) {
            if (l.length() > 0) {
                String[] keyValuePair = l.split(": ", 2);
                if (keyValuePair[0].startsWith("Optional[")) keyValuePair[0] = keyValuePair[0].substring(9);
                if (keyValuePair[1].endsWith("]")) keyValuePair[1] = keyValuePair[1].substring(0, keyValuePair[1].length()-1);
                propertiesMap.put(keyValuePair[0].trim(), keyValuePair[1].trim());
            }
        }
        return propertiesMap;
    }

    public static void main(String[] args) {
        String colourString = "Optional[Qty: 1\nDelivery time: Esinduses kohal\n\nHousing color: Red]";
        System.out.println(stringToMap(colourString)); // result: {Delivery time=Esinduses kohal, Qty=1, Housing color=Red}
    }
}
