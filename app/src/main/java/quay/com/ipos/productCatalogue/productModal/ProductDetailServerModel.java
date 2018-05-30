package quay.com.ipos.productCatalogue.productModal;

import java.util.List;

/**
 * Created by niraj.kumar on 5/28/2018.
 */

public class ProductDetailServerModel {

    /**
     * media : [{"isImage":true,"isVideo":false,"imageUrl":"https://2016.hackerspace.govhack.org/sites/default/files/field/image/smartpath_banner.png","video":"https://2016.hackerspace.govhack.org/sites/default/files/field/image/smartpath_banner.png","videoPreviewImage":"https://2016.hackerspace.govhack.org/sites/default/files/field/image/smartpath_banner.png"},{"isImage":true,"isVideo":false,"imageUrl":"https://ivend.com/wp-content/uploads/2018/01/2018-Global-Path-to-Purchase-Survey-Banner.jpg","video":"https://ivend.com/wp-content/uploads/2018/01/2018-Global-Path-to-Purchase-Survey-Banner.jpg","videoPreviewImage":"https://ivend.com/wp-content/uploads/2018/01/2018-Global-Path-to-Purchase-Survey-Banner.jpg"},{"isImage":false,"isVideo":true,"imageUrl":"https://youtu.be/JYfNVYB_BEY","video":"https://youtu.be/JYfNVYB_BEY","videoPreviewImage":"https://youtu.be/JYfNVYB_BEY"}]
     * productDetail : {"data":[{"title":"ProductDetails","icon":"http://13.127.101.233/image/product_detail.png","dataDescription":[{"heading":"ProductDetails","description":"product details coming here..........","isCheckStock":"False"}]},{"title":"Offers & Discount","icon":"http://13.127.101.233/image/offers_and_discount.png","dataDescription":[{"heading":"Seasnal Scheme","description":"Season Discount 10%","isCheckStock":"False"},{"heading":"Special Offer","description":"Special Offer(Buy 2 get 1 free)","isCheckStock":"False"}]},{"title":"Packaging & Availability","icon":"http://13.127.101.233/image/packaging_availibility.png","dataDescription":[{"heading":"Packaging & Availability","description":"pack details coming here.........","isCheckStock":"True"}]},{"title":"How To Use","icon":"http://13.127.101.233/image/how_to_use.png","dataDescription":[{"heading":"How To Use","description":"use details coming here.........","isCheckStock":"False"}]}]}
     */

    private ProductDetailBean productDetail;
    private List<MediaBean> media;

    public ProductDetailBean getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetailBean productDetail) {
        this.productDetail = productDetail;
    }

    public List<MediaBean> getMedia() {
        return media;
    }

    public void setMedia(List<MediaBean> media) {
        this.media = media;
    }

    public static class ProductDetailBean {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * title : ProductDetails
             * icon : http://13.127.101.233/image/product_detail.png
             * dataDescription : [{"heading":"ProductDetails","description":"product details coming here..........","isCheckStock":"False"}]
             */

            private String title;
            private String icon;
            private List<DataDescriptionBean> dataDescription;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public List<DataDescriptionBean> getDataDescription() {
                return dataDescription;
            }

            public void setDataDescription(List<DataDescriptionBean> dataDescription) {
                this.dataDescription = dataDescription;
            }

            public static class DataDescriptionBean {
                /**
                 * heading : ProductDetails
                 * description : product details coming here..........
                 * isCheckStock : False
                 */

                private String heading;
                private String description;
                private String isCheckStock;

                public String getHeading() {
                    return heading;
                }

                public void setHeading(String heading) {
                    this.heading = heading;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getIsCheckStock() {
                    return isCheckStock;
                }

                public void setIsCheckStock(String isCheckStock) {
                    this.isCheckStock = isCheckStock;
                }
            }
        }
    }

    public static class MediaBean {
        /**
         * isImage : true
         * isVideo : false
         * imageUrl : https://2016.hackerspace.govhack.org/sites/default/files/field/image/smartpath_banner.png
         * video : https://2016.hackerspace.govhack.org/sites/default/files/field/image/smartpath_banner.png
         * videoPreviewImage : https://2016.hackerspace.govhack.org/sites/default/files/field/image/smartpath_banner.png
         */

        private boolean isImage;
        private boolean isVideo;
        private String imageUrl;
        private String video;
        private String videoPreviewImage;

        public boolean isIsImage() {
            return isImage;
        }

        public void setIsImage(boolean isImage) {
            this.isImage = isImage;
        }

        public boolean isIsVideo() {
            return isVideo;
        }

        public void setIsVideo(boolean isVideo) {
            this.isVideo = isVideo;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getVideoPreviewImage() {
            return videoPreviewImage;
        }

        public void setVideoPreviewImage(String videoPreviewImage) {
            this.videoPreviewImage = videoPreviewImage;
        }
    }
}
