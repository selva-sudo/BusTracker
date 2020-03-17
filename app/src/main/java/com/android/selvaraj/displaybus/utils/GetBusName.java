package com.android.selvaraj.displaybus.utils;

public class GetBusName {
    static String getBusDetails(String email) {
        String getBus;
        switch (email) {
            case "maharathiperumal24@gmail.com":
                getBus = "Maharathi";
                break;
            case "selvarajmca18@gmail.com":
                getBus = "Selvaraj";
                break;
            case "kirthibalusamy@gmail.com":
                getBus = "Kirthika";
                break;
            case "vinocsv201@gmail.com":
                getBus = "Vinothini";
                break;
            case "oviyavinu15@gmail.com":
                getBus = "Oviya001";
                break;
            case "manistar050@gmail.com":
                getBus =  "Manikandan";
                break;
            default:
                getBus = "Selvaraj";
                break;
        }
        return getBus;
    }
}

