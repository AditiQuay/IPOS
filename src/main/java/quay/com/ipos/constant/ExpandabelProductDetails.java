package quay.com.ipos.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by niraj.kumar on 4/19/2018.
 */

public class ExpandabelProductDetails {
    public static HashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> ProductDetail = new ArrayList<String>();
        ProductDetail.add("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.");

        List<String> OAndD = new ArrayList<String>();
        OAndD.add("<b>\u2022 Season Discounts (10%)</b><br/>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.");

        List<String> Packaging = new ArrayList<String>();
        Packaging.add("\u2022 Aerosal can 750ml");

        List<String> HTU = new ArrayList<String>();
        HTU.add("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.");

        expandableListDetail.put("Product Details", ProductDetail);
        expandableListDetail.put("Offers & Discount", OAndD);
        expandableListDetail.put("Packaging & Availability", Packaging);
        expandableListDetail.put("How to Use", HTU);

        return expandableListDetail;
    }
}