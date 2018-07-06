package com.quayintech.tasklib.databinding;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
public abstract class FragmentRepeatBinding extends ViewDataBinding {
    @NonNull
    public final android.widget.ImageView btnClose;
    @NonNull
    public final android.widget.ImageView btnDone;
    @NonNull
    public final android.widget.LinearLayout mainLayout;
    @NonNull
    public final android.widget.TextView textFrequency;
    @NonNull
    public final android.widget.TextView textInterval;
    @NonNull
    public final android.widget.TextView textOnDays;
    @NonNull
    public final android.widget.TextView title;
    // variables
    protected com.quayintech.tasklib.interfaces.RepeatPageHandler mHandler;
    protected com.quayintech.tasklib.viewmodel.RepeatPageViewModel mViewModel;
    protected FragmentRepeatBinding(@Nullable android.databinding.DataBindingComponent bindingComponent, @Nullable android.view.View root_, int localFieldCount
        , android.widget.ImageView btnClose1
        , android.widget.ImageView btnDone1
        , android.widget.LinearLayout mainLayout1
        , android.widget.TextView textFrequency1
        , android.widget.TextView textInterval1
        , android.widget.TextView textOnDays1
        , android.widget.TextView title1
    ) {
        super(bindingComponent, root_, localFieldCount);
        this.btnClose = btnClose1;
        this.btnDone = btnDone1;
        this.mainLayout = mainLayout1;
        this.textFrequency = textFrequency1;
        this.textInterval = textInterval1;
        this.textOnDays = textOnDays1;
        this.title = title1;
    }
    //getters and abstract setters
    public abstract void setHandler(@Nullable com.quayintech.tasklib.interfaces.RepeatPageHandler Handler);
    @Nullable
    public com.quayintech.tasklib.interfaces.RepeatPageHandler getHandler() {
        return mHandler;
    }
    public abstract void setViewModel(@Nullable com.quayintech.tasklib.viewmodel.RepeatPageViewModel ViewModel);
    @Nullable
    public com.quayintech.tasklib.viewmodel.RepeatPageViewModel getViewModel() {
        return mViewModel;
    }
    @NonNull
    public static FragmentRepeatBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static FragmentRepeatBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static FragmentRepeatBinding bind(@NonNull android.view.View view) {
        return null;
    }
    @NonNull
    public static FragmentRepeatBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    @NonNull
    public static FragmentRepeatBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    @NonNull
    public static FragmentRepeatBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
}