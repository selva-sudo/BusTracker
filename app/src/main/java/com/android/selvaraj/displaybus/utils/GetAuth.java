package com.android.selvaraj.displaybus.utils;

public class GetAuth {
    public static String[] getUserDetails(String busName) {
        String[] getBus=new String[2];
        switch (busName) {
            case "Bus 1":
                getBus[0] = "maharathiperumal24@gmail.com";
                getBus[1] = "Maharathi";
                break;
            case "Bus 2":
                getBus[0] = "selvarajmca18@gmail.com";
                getBus[1] = "Selvaraj";
                break;
            case "Bus 3":
                getBus[0] = "kirthibalusamy@gmail.com";
                getBus[1] = "Kirthika";
                break;
            case "Bus 4":
                getBus = new String[]{"vinocsv201@gmail.com", "Vinothini"};
                break;
            case "Bus 5":
                getBus[0] = "oviyavinu15@gmail.com";
                getBus[1] = "Oviya001";
                break;
            case "Bus 6":
                getBus = new String[]{"manistar050@gmail.com", "Manikandan"};
                break;
            default:
                getBus = new String[]{"srbca97@gmail.com","Selvaraj"};
                break;
        }
        return getBus;
    }
}
