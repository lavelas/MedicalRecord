package ua.pp.mmr.mymedicalrecord;

/**
 * Created by Uliana on 01.06.2015.
 */
public class Sequence {
    private boolean requestedId = false;
    synchronized public int getNextId() {
        requestedId = true;
        // some code
        requestedId = false;
        return 1;
    }

    public int getCurrentId() {
        while (requestedId){
            // need wait
        }
        return 2;
    }
}
