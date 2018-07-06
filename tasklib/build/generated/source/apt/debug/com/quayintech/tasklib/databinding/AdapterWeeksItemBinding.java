package com.quayintech.tasklib.databinding;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
public abstract class AdapterWeeksItemBinding extends ViewDataBinding {
    @NonNull
    public final android.widget.CheckBox checkUser;
    // variables
    protected AdapterWeeksItemBinding(@Nullable android.databinding.DataBindingComponent bindingComponent, @Nullable android.view.View root_, int localFieldCount
        , android.widget.CheckBox checkUser1
    ) {
        super(bindingComponent, root_, localFieldCount);
        this.checkUser = checkUser1;
    }
    //getters and abstract setters
    @NonNull
    public static AdapterWeeksItemBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static AdapterWeeksItemBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static AdapterWeeksItemBinding bind(@NonNull android.view.View view) {
        return null;
    }
    @NonNull
    public static AdapterWeeksItemBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    @NonNull
    public static AdapterWeeksItemBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    @NonNull
    public static AdapterWeeksItemBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
}