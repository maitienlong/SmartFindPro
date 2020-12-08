package com.poly.smartfindpro.ui.searchProduct.model;

public class ItemMarker {


    class ItemMarker {
        Double lat;
        Double log;
        String name;
        String address;

        public ItemMarker(Double lat, Double log, String name, String address) {
            this.lat = lat;
            this.log = log;
            this.name = name;
            this.address = address;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLog() {
            return log;
        }

        public void setLog(Double log) {
            this.log = log;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

}
