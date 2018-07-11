package quay.com.ipos.listeners;

public interface MyAdapterTags {
    void onRowClicked(int position);
    void onRowClicked(int position, int value, String tag);
    void onRowClicked(int position, int value, String tag,int parentPosition);

}