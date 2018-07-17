package quay.com.ipos.ddrsales.model.response;

import java.util.List;

public class DDRBatchMaster {

    /* {
         "materialCode": "ACEGPP",
             "batchData": [
         {
             "qty": 0,
                 "number": "BGHY678"
         }
       ]
     },*/
    public String materialCode;
    public List<DDRBatch> batchData;
}
