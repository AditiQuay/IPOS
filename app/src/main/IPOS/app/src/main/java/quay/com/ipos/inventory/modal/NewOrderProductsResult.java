package quay.com.ipos.inventory.modal;

import java.util.List;

/**
 * Created by ankush.bansal on 04-06-2018.
 */

public class NewOrderProductsResult {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * productCode : Billo
         * iProductModalId : BLLO2KG
         * sProductName : BILLO 2KG
         * productImage : https://www.khedutstore.com/p3y9_inv/admin/img/product/orignal/276108.jpg
         * sProductPrice : 150
         * sProductStock : available
         * sProductWeight : 45
         * isDiscount : true
         * gstPerc : 18
         * cgst : 9
         * sgst : 9
         * salesPrice : 100
         * nrv : 150
         * gpl : 120
         * mrp : 160
         * barCodeNumber : 9421023610112
         * discount : [{"sDiscountName":"Season Discount","sDiscountDisplayName":"Seasnal Scheme","rule":[{"sDiscountType":"p","sDiscountValue":10,"sEligibilityBasedOn":"V","sDiscountBasedOn":"SP","slabFrom":0,"slabTO":100000,"packSize":0,"isCrossBundle":false,"opsCriteria":"L","ruleType":"I","ruleNotes":"Independent","ruleID":1,"ruleNumber":1,"ruleSequence":0,"ruleProdecessors":0,"opsType":"P"}]},{"sDiscountName":"Special Offer","sDiscountDisplayName":"Special Offer","rule":[{"sDiscountType":"P","sDiscountValue":5,"sEligibilityBasedOn":"V","sDiscountBasedOn":"MRP","slabFrom":0,"slabTO":2,"packSize":0,"isCrossBundle":false,"opsCriteria":"L","ruleType":"D","ruleNotes":"Dependent","ruleID":2,"ruleNumber":2,"ruleSequence":2,"ruleProdecessors":2,"opsType":"P"},{"sDiscountType":"P","sDiscountValue":100,"sEligibilityBasedOn":"V","sDiscountBasedOn":"MRP","slabFrom":2,"slabTO":2,"packSize":2,"isCrossBundle":false,"opsCriteria":"L","ruleType":"I","ruleNotes":"InDependent","ruleID":3,"ruleNumber":3,"ruleSequence":0,"ruleProdecessors":0,"opsType":"P"},{"sDiscountType":"P","sDiscountValue":20,"sEligibilityBasedOn":"V","sDiscountBasedOn":"MRP","slabFrom":100,"slabTO":300,"packSize":0,"isCrossBundle":false,"opsCriteria":"L","ruleType":"D","ruleNotes":"Dependent","ruleID":4,"ruleNumber":4,"ruleSequence":1,"ruleProdecessors":1,"opsType":"P"}]}]
         * points : 20
         * pointsBasedOn : P
         * valueFrom : 200
         * valueTo : 600
         * pointsPer : 2
         * isReserveStock : true
         * isCheckStock : true
         * isStockDisplay : true
         */

        private String productCode;
        private String iProductModalId;
        private String sProductName;
        private String productImage;
        private int sProductPrice;
        private String sProductStock;
        private int sProductWeight;
        private boolean isDiscount;
        private int gstPerc;
        private int cgst;
        private int sgst;
        private int salesPrice;
        private int nrv;
        private int gpl;
        private int mrp;
        private String barCodeNumber;
        private int points;
        private String pointsBasedOn;
        private int valueFrom;
        private int valueTo;
        private int pointsPer;
        private boolean isReserveStock;
        private boolean isCheckStock;
        private boolean isStockDisplay;
        private List<DiscountBean> discount;

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getIProductModalId() {
            return iProductModalId;
        }

        public void setIProductModalId(String iProductModalId) {
            this.iProductModalId = iProductModalId;
        }

        public String getSProductName() {
            return sProductName;
        }

        public void setSProductName(String sProductName) {
            this.sProductName = sProductName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public int getSProductPrice() {
            return sProductPrice;
        }

        public void setSProductPrice(int sProductPrice) {
            this.sProductPrice = sProductPrice;
        }

        public String getSProductStock() {
            return sProductStock;
        }

        public void setSProductStock(String sProductStock) {
            this.sProductStock = sProductStock;
        }

        public int getSProductWeight() {
            return sProductWeight;
        }

        public void setSProductWeight(int sProductWeight) {
            this.sProductWeight = sProductWeight;
        }

        public boolean isIsDiscount() {
            return isDiscount;
        }

        public void setIsDiscount(boolean isDiscount) {
            this.isDiscount = isDiscount;
        }

        public int getGstPerc() {
            return gstPerc;
        }

        public void setGstPerc(int gstPerc) {
            this.gstPerc = gstPerc;
        }

        public int getCgst() {
            return cgst;
        }

        public void setCgst(int cgst) {
            this.cgst = cgst;
        }

        public int getSgst() {
            return sgst;
        }

        public void setSgst(int sgst) {
            this.sgst = sgst;
        }

        public int getSalesPrice() {
            return salesPrice;
        }

        public void setSalesPrice(int salesPrice) {
            this.salesPrice = salesPrice;
        }

        public int getNrv() {
            return nrv;
        }

        public void setNrv(int nrv) {
            this.nrv = nrv;
        }

        public int getGpl() {
            return gpl;
        }

        public void setGpl(int gpl) {
            this.gpl = gpl;
        }

        public int getMrp() {
            return mrp;
        }

        public void setMrp(int mrp) {
            this.mrp = mrp;
        }

        public String getBarCodeNumber() {
            return barCodeNumber;
        }

        public void setBarCodeNumber(String barCodeNumber) {
            this.barCodeNumber = barCodeNumber;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public String getPointsBasedOn() {
            return pointsBasedOn;
        }

        public void setPointsBasedOn(String pointsBasedOn) {
            this.pointsBasedOn = pointsBasedOn;
        }

        public int getValueFrom() {
            return valueFrom;
        }

        public void setValueFrom(int valueFrom) {
            this.valueFrom = valueFrom;
        }

        public int getValueTo() {
            return valueTo;
        }

        public void setValueTo(int valueTo) {
            this.valueTo = valueTo;
        }

        public int getPointsPer() {
            return pointsPer;
        }

        public void setPointsPer(int pointsPer) {
            this.pointsPer = pointsPer;
        }

        public boolean isIsReserveStock() {
            return isReserveStock;
        }

        public void setIsReserveStock(boolean isReserveStock) {
            this.isReserveStock = isReserveStock;
        }

        public boolean isIsCheckStock() {
            return isCheckStock;
        }

        public void setIsCheckStock(boolean isCheckStock) {
            this.isCheckStock = isCheckStock;
        }

        public boolean isIsStockDisplay() {
            return isStockDisplay;
        }

        public void setIsStockDisplay(boolean isStockDisplay) {
            this.isStockDisplay = isStockDisplay;
        }

        public List<DiscountBean> getDiscount() {
            return discount;
        }

        public void setDiscount(List<DiscountBean> discount) {
            this.discount = discount;
        }

        public static class DiscountBean {
            /**
             * sDiscountName : Season Discount
             * sDiscountDisplayName : Seasnal Scheme
             * rule : [{"sDiscountType":"p","sDiscountValue":10,"sEligibilityBasedOn":"V","sDiscountBasedOn":"SP","slabFrom":0,"slabTO":100000,"packSize":0,"isCrossBundle":false,"opsCriteria":"L","ruleType":"I","ruleNotes":"Independent","ruleID":1,"ruleNumber":1,"ruleSequence":0,"ruleProdecessors":0,"opsType":"P"}]
             */

            private String sDiscountName;
            private String sDiscountDisplayName;
            private List<RuleBean> rule;

            public String getSDiscountName() {
                return sDiscountName;
            }

            public void setSDiscountName(String sDiscountName) {
                this.sDiscountName = sDiscountName;
            }

            public String getSDiscountDisplayName() {
                return sDiscountDisplayName;
            }

            public void setSDiscountDisplayName(String sDiscountDisplayName) {
                this.sDiscountDisplayName = sDiscountDisplayName;
            }

            public List<RuleBean> getRule() {
                return rule;
            }

            public void setRule(List<RuleBean> rule) {
                this.rule = rule;
            }

            public static class RuleBean {
                /**
                 * sDiscountType : p
                 * sDiscountValue : 10
                 * sEligibilityBasedOn : V
                 * sDiscountBasedOn : SP
                 * slabFrom : 0
                 * slabTO : 100000
                 * packSize : 0
                 * isCrossBundle : false
                 * opsCriteria : L
                 * ruleType : I
                 * ruleNotes : Independent
                 * ruleID : 1
                 * ruleNumber : 1
                 * ruleSequence : 0
                 * ruleProdecessors : 0
                 * opsType : P
                 */

                private String sDiscountType;
                private int sDiscountValue;
                private String sEligibilityBasedOn;
                private String sDiscountBasedOn;
                private int slabFrom;
                private int slabTO;
                private int packSize;
                private boolean isCrossBundle;
                private String opsCriteria;
                private String ruleType;
                private String ruleNotes;
                private int ruleID;
                private int ruleNumber;
                private int ruleSequence;
                private int ruleProdecessors;
                private String opsType;

                public String getSDiscountType() {
                    return sDiscountType;
                }

                public void setSDiscountType(String sDiscountType) {
                    this.sDiscountType = sDiscountType;
                }

                public int getSDiscountValue() {
                    return sDiscountValue;
                }

                public void setSDiscountValue(int sDiscountValue) {
                    this.sDiscountValue = sDiscountValue;
                }

                public String getSEligibilityBasedOn() {
                    return sEligibilityBasedOn;
                }

                public void setSEligibilityBasedOn(String sEligibilityBasedOn) {
                    this.sEligibilityBasedOn = sEligibilityBasedOn;
                }

                public String getSDiscountBasedOn() {
                    return sDiscountBasedOn;
                }

                public void setSDiscountBasedOn(String sDiscountBasedOn) {
                    this.sDiscountBasedOn = sDiscountBasedOn;
                }

                public int getSlabFrom() {
                    return slabFrom;
                }

                public void setSlabFrom(int slabFrom) {
                    this.slabFrom = slabFrom;
                }

                public int getSlabTO() {
                    return slabTO;
                }

                public void setSlabTO(int slabTO) {
                    this.slabTO = slabTO;
                }

                public int getPackSize() {
                    return packSize;
                }

                public void setPackSize(int packSize) {
                    this.packSize = packSize;
                }

                public boolean isIsCrossBundle() {
                    return isCrossBundle;
                }

                public void setIsCrossBundle(boolean isCrossBundle) {
                    this.isCrossBundle = isCrossBundle;
                }

                public String getOpsCriteria() {
                    return opsCriteria;
                }

                public void setOpsCriteria(String opsCriteria) {
                    this.opsCriteria = opsCriteria;
                }

                public String getRuleType() {
                    return ruleType;
                }

                public void setRuleType(String ruleType) {
                    this.ruleType = ruleType;
                }

                public String getRuleNotes() {
                    return ruleNotes;
                }

                public void setRuleNotes(String ruleNotes) {
                    this.ruleNotes = ruleNotes;
                }

                public int getRuleID() {
                    return ruleID;
                }

                public void setRuleID(int ruleID) {
                    this.ruleID = ruleID;
                }

                public int getRuleNumber() {
                    return ruleNumber;
                }

                public void setRuleNumber(int ruleNumber) {
                    this.ruleNumber = ruleNumber;
                }

                public int getRuleSequence() {
                    return ruleSequence;
                }

                public void setRuleSequence(int ruleSequence) {
                    this.ruleSequence = ruleSequence;
                }

                public int getRuleProdecessors() {
                    return ruleProdecessors;
                }

                public void setRuleProdecessors(int ruleProdecessors) {
                    this.ruleProdecessors = ruleProdecessors;
                }

                public String getOpsType() {
                    return opsType;
                }

                public void setOpsType(String opsType) {
                    this.opsType = opsType;
                }
            }
        }
    }
}
