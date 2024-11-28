    package com.example.naplo;

    public class Diak {
        private int id;
        private String nev;
        private String osztaly;
        private boolean fiu;


        public Diak(int id, String nev, String osztaly, boolean fiu) {
            this.id = id;
            this.nev = nev;
            this.osztaly = osztaly;
            this.fiu = fiu;
        }

        // Getterek és Setterek
        public int getId() { return id; }
        public String getNev() { return nev; }
        public String getOsztaly() { return osztaly; }
        public boolean getFiu() { return fiu; }
    }
