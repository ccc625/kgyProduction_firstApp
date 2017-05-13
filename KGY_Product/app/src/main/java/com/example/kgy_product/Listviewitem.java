package com.example.kgy_product;

/**
    * Created by lee on 2017-05-13.
            */

    public class Listviewitem {
        private int icon;
        private String name;

        public int getIcon(){return icon;}
        public String getName() {return name;}

        public Listviewitem(int icon, String name){
            this.icon=icon;
            this.name=name;
        }
}
