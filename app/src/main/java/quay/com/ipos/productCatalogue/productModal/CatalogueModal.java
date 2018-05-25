package quay.com.ipos.productCatalogue.productModal;

import io.realm.RealmObject;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class CatalogueModal extends RealmObject {

    public String ProductCode;
    public String sProductName;
    public String sProductFeature;
    public String sProductPrice;
    public String sDataSheet;
    public String sPoints;

    public CatalogueModal() {

    }

}
