package test3;

import java.util.Comparator;

public class Hits {

    private int hits;

    public int getHits(){
        return hits;
    }

    public void setHits(int hits){
        this.hits = hits;
    }

    public Hits(int hits){
        this.hits = hits;
    }

}

class hitscomp implements Comparator<Hits>{
    public int compare(Hits h1, Hits h2){
        return h1.getHits() - h2.getHits();
    }
}
