package ed.mobileassignment.com;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;


public class GlobalVar extends Application {
    private List ListPOI;

    public List getListPOI() {
        if (ListPOI == null) {
            ListPOI = new ArrayList();
        }
        return ListPOI;
    }
    public void setListPOI(List listPOI) {
        this.ListPOI = listPOI;
    }


}
