package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CatalogueModalRealmProxy extends quay.com.ipos.productCatalogue.productModal.CatalogueModal
    implements RealmObjectProxy, CatalogueModalRealmProxyInterface {

    static final class CatalogueModalColumnInfo extends ColumnInfo
        implements Cloneable {

        public long sProductNameIndex;
        public long sProductFeatureIndex;
        public long sProductPriceIndex;

        CatalogueModalColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(3);
            this.sProductNameIndex = getValidColumnIndex(path, table, "CatalogueModal", "sProductName");
            indicesMap.put("sProductName", this.sProductNameIndex);
            this.sProductFeatureIndex = getValidColumnIndex(path, table, "CatalogueModal", "sProductFeature");
            indicesMap.put("sProductFeature", this.sProductFeatureIndex);
            this.sProductPriceIndex = getValidColumnIndex(path, table, "CatalogueModal", "sProductPrice");
            indicesMap.put("sProductPrice", this.sProductPriceIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final CatalogueModalColumnInfo otherInfo = (CatalogueModalColumnInfo) other;
            this.sProductNameIndex = otherInfo.sProductNameIndex;
            this.sProductFeatureIndex = otherInfo.sProductFeatureIndex;
            this.sProductPriceIndex = otherInfo.sProductPriceIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final CatalogueModalColumnInfo clone() {
            return (CatalogueModalColumnInfo) super.clone();
        }

    }
    private CatalogueModalColumnInfo columnInfo;
    private ProxyState<quay.com.ipos.productCatalogue.productModal.CatalogueModal> proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("sProductName");
        fieldNames.add("sProductFeature");
        fieldNames.add("sProductPrice");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    CatalogueModalRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (CatalogueModalColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<quay.com.ipos.productCatalogue.productModal.CatalogueModal>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$sProductName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.sProductNameIndex);
    }

    @Override
    public void realmSet$sProductName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.sProductNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.sProductNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.sProductNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.sProductNameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$sProductFeature() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.sProductFeatureIndex);
    }

    @Override
    public void realmSet$sProductFeature(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.sProductFeatureIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.sProductFeatureIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.sProductFeatureIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.sProductFeatureIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$sProductPrice() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.sProductPriceIndex);
    }

    @Override
    public void realmSet$sProductPrice(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.sProductPriceIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.sProductPriceIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.sProductPriceIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.sProductPriceIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("CatalogueModal")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("CatalogueModal");
            realmObjectSchema.add("sProductName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("sProductFeature", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("sProductPrice", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            return realmObjectSchema;
        }
        return realmSchema.get("CatalogueModal");
    }

    public static CatalogueModalColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_CatalogueModal")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'CatalogueModal' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_CatalogueModal");
        final long columnCount = table.getColumnCount();
        if (columnCount != 3) {
            if (columnCount < 3) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 3 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 3 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 3 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final CatalogueModalColumnInfo columnInfo = new CatalogueModalColumnInfo(sharedRealm.getPath(), table);

        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }

        if (!columnTypes.containsKey("sProductName")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'sProductName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("sProductName") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'sProductName' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.sProductNameIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'sProductName' is required. Either set @Required to field 'sProductName' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("sProductFeature")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'sProductFeature' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("sProductFeature") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'sProductFeature' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.sProductFeatureIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'sProductFeature' is required. Either set @Required to field 'sProductFeature' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("sProductPrice")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'sProductPrice' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("sProductPrice") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'sProductPrice' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.sProductPriceIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'sProductPrice' is required. Either set @Required to field 'sProductPrice' or migrate using RealmObjectSchema.setNullable().");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_CatalogueModal";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static quay.com.ipos.productCatalogue.productModal.CatalogueModal createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        quay.com.ipos.productCatalogue.productModal.CatalogueModal obj = realm.createObjectInternal(quay.com.ipos.productCatalogue.productModal.CatalogueModal.class, true, excludeFields);
        if (json.has("sProductName")) {
            if (json.isNull("sProductName")) {
                ((CatalogueModalRealmProxyInterface) obj).realmSet$sProductName(null);
            } else {
                ((CatalogueModalRealmProxyInterface) obj).realmSet$sProductName((String) json.getString("sProductName"));
            }
        }
        if (json.has("sProductFeature")) {
            if (json.isNull("sProductFeature")) {
                ((CatalogueModalRealmProxyInterface) obj).realmSet$sProductFeature(null);
            } else {
                ((CatalogueModalRealmProxyInterface) obj).realmSet$sProductFeature((String) json.getString("sProductFeature"));
            }
        }
        if (json.has("sProductPrice")) {
            if (json.isNull("sProductPrice")) {
                ((CatalogueModalRealmProxyInterface) obj).realmSet$sProductPrice(null);
            } else {
                ((CatalogueModalRealmProxyInterface) obj).realmSet$sProductPrice((String) json.getString("sProductPrice"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static quay.com.ipos.productCatalogue.productModal.CatalogueModal createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        quay.com.ipos.productCatalogue.productModal.CatalogueModal obj = new quay.com.ipos.productCatalogue.productModal.CatalogueModal();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("sProductName")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CatalogueModalRealmProxyInterface) obj).realmSet$sProductName(null);
                } else {
                    ((CatalogueModalRealmProxyInterface) obj).realmSet$sProductName((String) reader.nextString());
                }
            } else if (name.equals("sProductFeature")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CatalogueModalRealmProxyInterface) obj).realmSet$sProductFeature(null);
                } else {
                    ((CatalogueModalRealmProxyInterface) obj).realmSet$sProductFeature((String) reader.nextString());
                }
            } else if (name.equals("sProductPrice")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CatalogueModalRealmProxyInterface) obj).realmSet$sProductPrice(null);
                } else {
                    ((CatalogueModalRealmProxyInterface) obj).realmSet$sProductPrice((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static quay.com.ipos.productCatalogue.productModal.CatalogueModal copyOrUpdate(Realm realm, quay.com.ipos.productCatalogue.productModal.CatalogueModal object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (quay.com.ipos.productCatalogue.productModal.CatalogueModal) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static quay.com.ipos.productCatalogue.productModal.CatalogueModal copy(Realm realm, quay.com.ipos.productCatalogue.productModal.CatalogueModal newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (quay.com.ipos.productCatalogue.productModal.CatalogueModal) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            quay.com.ipos.productCatalogue.productModal.CatalogueModal realmObject = realm.createObjectInternal(quay.com.ipos.productCatalogue.productModal.CatalogueModal.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((CatalogueModalRealmProxyInterface) realmObject).realmSet$sProductName(((CatalogueModalRealmProxyInterface) newObject).realmGet$sProductName());
            ((CatalogueModalRealmProxyInterface) realmObject).realmSet$sProductFeature(((CatalogueModalRealmProxyInterface) newObject).realmGet$sProductFeature());
            ((CatalogueModalRealmProxyInterface) realmObject).realmSet$sProductPrice(((CatalogueModalRealmProxyInterface) newObject).realmGet$sProductPrice());
            return realmObject;
        }
    }

    public static long insert(Realm realm, quay.com.ipos.productCatalogue.productModal.CatalogueModal object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(quay.com.ipos.productCatalogue.productModal.CatalogueModal.class);
        long tableNativePtr = table.getNativeTablePointer();
        CatalogueModalColumnInfo columnInfo = (CatalogueModalColumnInfo) realm.schema.getColumnInfo(quay.com.ipos.productCatalogue.productModal.CatalogueModal.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$sProductName = ((CatalogueModalRealmProxyInterface)object).realmGet$sProductName();
        if (realmGet$sProductName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sProductNameIndex, rowIndex, realmGet$sProductName, false);
        }
        String realmGet$sProductFeature = ((CatalogueModalRealmProxyInterface)object).realmGet$sProductFeature();
        if (realmGet$sProductFeature != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sProductFeatureIndex, rowIndex, realmGet$sProductFeature, false);
        }
        String realmGet$sProductPrice = ((CatalogueModalRealmProxyInterface)object).realmGet$sProductPrice();
        if (realmGet$sProductPrice != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sProductPriceIndex, rowIndex, realmGet$sProductPrice, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(quay.com.ipos.productCatalogue.productModal.CatalogueModal.class);
        long tableNativePtr = table.getNativeTablePointer();
        CatalogueModalColumnInfo columnInfo = (CatalogueModalColumnInfo) realm.schema.getColumnInfo(quay.com.ipos.productCatalogue.productModal.CatalogueModal.class);
        quay.com.ipos.productCatalogue.productModal.CatalogueModal object = null;
        while (objects.hasNext()) {
            object = (quay.com.ipos.productCatalogue.productModal.CatalogueModal) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$sProductName = ((CatalogueModalRealmProxyInterface)object).realmGet$sProductName();
                if (realmGet$sProductName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.sProductNameIndex, rowIndex, realmGet$sProductName, false);
                }
                String realmGet$sProductFeature = ((CatalogueModalRealmProxyInterface)object).realmGet$sProductFeature();
                if (realmGet$sProductFeature != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.sProductFeatureIndex, rowIndex, realmGet$sProductFeature, false);
                }
                String realmGet$sProductPrice = ((CatalogueModalRealmProxyInterface)object).realmGet$sProductPrice();
                if (realmGet$sProductPrice != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.sProductPriceIndex, rowIndex, realmGet$sProductPrice, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, quay.com.ipos.productCatalogue.productModal.CatalogueModal object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(quay.com.ipos.productCatalogue.productModal.CatalogueModal.class);
        long tableNativePtr = table.getNativeTablePointer();
        CatalogueModalColumnInfo columnInfo = (CatalogueModalColumnInfo) realm.schema.getColumnInfo(quay.com.ipos.productCatalogue.productModal.CatalogueModal.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$sProductName = ((CatalogueModalRealmProxyInterface)object).realmGet$sProductName();
        if (realmGet$sProductName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sProductNameIndex, rowIndex, realmGet$sProductName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.sProductNameIndex, rowIndex, false);
        }
        String realmGet$sProductFeature = ((CatalogueModalRealmProxyInterface)object).realmGet$sProductFeature();
        if (realmGet$sProductFeature != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sProductFeatureIndex, rowIndex, realmGet$sProductFeature, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.sProductFeatureIndex, rowIndex, false);
        }
        String realmGet$sProductPrice = ((CatalogueModalRealmProxyInterface)object).realmGet$sProductPrice();
        if (realmGet$sProductPrice != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sProductPriceIndex, rowIndex, realmGet$sProductPrice, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.sProductPriceIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(quay.com.ipos.productCatalogue.productModal.CatalogueModal.class);
        long tableNativePtr = table.getNativeTablePointer();
        CatalogueModalColumnInfo columnInfo = (CatalogueModalColumnInfo) realm.schema.getColumnInfo(quay.com.ipos.productCatalogue.productModal.CatalogueModal.class);
        quay.com.ipos.productCatalogue.productModal.CatalogueModal object = null;
        while (objects.hasNext()) {
            object = (quay.com.ipos.productCatalogue.productModal.CatalogueModal) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$sProductName = ((CatalogueModalRealmProxyInterface)object).realmGet$sProductName();
                if (realmGet$sProductName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.sProductNameIndex, rowIndex, realmGet$sProductName, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.sProductNameIndex, rowIndex, false);
                }
                String realmGet$sProductFeature = ((CatalogueModalRealmProxyInterface)object).realmGet$sProductFeature();
                if (realmGet$sProductFeature != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.sProductFeatureIndex, rowIndex, realmGet$sProductFeature, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.sProductFeatureIndex, rowIndex, false);
                }
                String realmGet$sProductPrice = ((CatalogueModalRealmProxyInterface)object).realmGet$sProductPrice();
                if (realmGet$sProductPrice != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.sProductPriceIndex, rowIndex, realmGet$sProductPrice, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.sProductPriceIndex, rowIndex, false);
                }
            }
        }
    }

    public static quay.com.ipos.productCatalogue.productModal.CatalogueModal createDetachedCopy(quay.com.ipos.productCatalogue.productModal.CatalogueModal realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        quay.com.ipos.productCatalogue.productModal.CatalogueModal unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (quay.com.ipos.productCatalogue.productModal.CatalogueModal)cachedObject.object;
            } else {
                unmanagedObject = (quay.com.ipos.productCatalogue.productModal.CatalogueModal)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new quay.com.ipos.productCatalogue.productModal.CatalogueModal();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((CatalogueModalRealmProxyInterface) unmanagedObject).realmSet$sProductName(((CatalogueModalRealmProxyInterface) realmObject).realmGet$sProductName());
        ((CatalogueModalRealmProxyInterface) unmanagedObject).realmSet$sProductFeature(((CatalogueModalRealmProxyInterface) realmObject).realmGet$sProductFeature());
        ((CatalogueModalRealmProxyInterface) unmanagedObject).realmSet$sProductPrice(((CatalogueModalRealmProxyInterface) realmObject).realmGet$sProductPrice());
        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("CatalogueModal = [");
        stringBuilder.append("{sProductName:");
        stringBuilder.append(realmGet$sProductName() != null ? realmGet$sProductName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{sProductFeature:");
        stringBuilder.append(realmGet$sProductFeature() != null ? realmGet$sProductFeature() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{sProductPrice:");
        stringBuilder.append(realmGet$sProductPrice() != null ? realmGet$sProductPrice() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogueModalRealmProxy aCatalogueModal = (CatalogueModalRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aCatalogueModal.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aCatalogueModal.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aCatalogueModal.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
