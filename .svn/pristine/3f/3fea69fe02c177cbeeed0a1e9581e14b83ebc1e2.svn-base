package quay.com.ipos.compliance.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by deepak.kumar1 on 23-03-2018.
 */

public class AnnotationComplianceType {
    public static final int ALL = 0;
    public static final int BUSINESS = 1;
    public static final int STATUTORY = 2;

    public AnnotationComplianceType(@ComplianceType int complianceType) {
        System.out.print("ComplianceType:"+complianceType);
    }

    @IntDef({ALL, BUSINESS, STATUTORY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ComplianceType {

    }

    public static void main(String[] args) {
        AnnotationComplianceType annotationComplianceType = new AnnotationComplianceType(STATUTORY);
    }

}
