package quay.com.ipos.inventory.modal;

import java.util.List;

/**
 * Created by ankush.bansal on 04-06-2018.
 */

public class NoGetEntityResultModal {
    private List<BuisnessPlacesBean> buisnessPlaces;

    public List<BuisnessPlacesBean> getBuisnessPlaces() {
        return buisnessPlaces;
    }

    public void setBuisnessPlaces(List<BuisnessPlacesBean> buisnessPlaces) {
        this.buisnessPlaces = buisnessPlaces;
    }

    public static class BuisnessPlacesBean {
        /**
         * buisnessPlaceId : 1
         * buisnessPlaceName : Store 1 ,Sector -21 ,Gurugram ,Haryana ,122022
         * buisnessLocationStateCode : 06
         */

        private int buisnessPlaceId;
        private String buisnessPlaceName;
        private String buisnessLocationStateCode;

        public int getBuisnessPlaceId() {
            return buisnessPlaceId;
        }

        public void setBuisnessPlaceId(int buisnessPlaceId) {
            this.buisnessPlaceId = buisnessPlaceId;
        }

        public String getBuisnessPlaceName() {
            return buisnessPlaceName;
        }

        public void setBuisnessPlaceName(String buisnessPlaceName) {
            this.buisnessPlaceName = buisnessPlaceName;
        }

        public String getBuisnessLocationStateCode() {
            return buisnessLocationStateCode;
        }

        public void setBuisnessLocationStateCode(String buisnessLocationStateCode) {
            this.buisnessLocationStateCode = buisnessLocationStateCode;
        }
    }
}
