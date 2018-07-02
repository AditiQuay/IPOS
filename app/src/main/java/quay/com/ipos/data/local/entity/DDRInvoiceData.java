package quay.com.ipos.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ddr_invoice_data")
public class DDRInvoiceData {
    @NonNull
    @PrimaryKey
    public String ddrCode;
    public String invoiceData;
}
