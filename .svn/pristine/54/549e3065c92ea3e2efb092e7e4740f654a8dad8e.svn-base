package quay.com.ipos.productCatalogue.productModal;

import java.util.List;

/**
 * Created by niraj.kumar on 5/25/2018.
 */

public class ProductCatalogueServerModal {

    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * section : GroupName1-PCname
         * sectionProduct : PCDesc
         * sectionItems : [{"productId":"1","productName":"PSubCatName","productUrl":"https://vignette.wikia.nocookie.net/googology/images/f/f3/Test.jpeg/revision/latest?cb=20180121032443","count":12}]
         */

        private String section;
        private String sectionProduct;
        private List<SectionItemsBean> sectionItems;

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getSectionProduct() {
            return sectionProduct;
        }

        public void setSectionProduct(String sectionProduct) {
            this.sectionProduct = sectionProduct;
        }

        public List<SectionItemsBean> getSectionItems() {
            return sectionItems;
        }

        public void setSectionItems(List<SectionItemsBean> sectionItems) {
            this.sectionItems = sectionItems;
        }

        public static class SectionItemsBean {
            /**
             * productId : 1
             * productName : PSubCatName
             * productUrl : https://vignette.wikia.nocookie.net/googology/images/f/f3/Test.jpeg/revision/latest?cb=20180121032443
             * count : 12
             */

            private String productId;
            private String productName;
            private String productUrl;
            private int count;

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductUrl() {
                return productUrl;
            }

            public void setProductUrl(String productUrl) {
                this.productUrl = productUrl;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }
    }
}
