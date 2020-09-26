package com.example.puppypals_foryourpooch;

public final class DistanceCalculator {

    Math math;

    public double distance(double lat1, double lat2,
                           double long1, double long2){
        long1 = math.toRadians(long1);
        long2 = math.toRadians(long2);
        lat1 = math.toRadians(lat1);
        lat2 = math.toRadians(lat2);

        double dLon = long2 - long1;
        double dLat = lat2 - lat1;
        double a = math.pow(Math.sin(dLat / 2), 2)
                + math.cos(lat1) * math.cos(lat2)
                * math.pow(Math.sin(dLon / 2),2);

        double c = 2 * math.asin(math.sqrt(a));

        double r = 6371;

        return (c * r);

    }

}
