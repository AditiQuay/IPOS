package com.quayintech.tasklib.databinding;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
public abstract class FragmentRepeatUntilBinding extends ViewDataBinding {
    @NonNull
    public final android.widget.ImageView btnClose;
    @NonNull
    public final android.widget.ImageView btnDone;
    @NonNull
    public final android.widget.RadioButton checkBoxDefault;
    @NonNull
    public final android.widget.RadioButton checkBoxSpecific;
    @NonNull
    public final android.widget.CalendarView mCalendarView;
    @NonNull
    public final android.widget.LinearLayout mainLayout;
    @NonNull
    public final android.widget.RadioGroup radioGroup;
    @NonNull
    public final android.widget.TextView subTitle;
    @NonNull
    public final android.widget.TextView title;
    // variables
    protected com.quayintech.tasklib.interfaces.RepeatUntilPageHandler mHandler;
    protected com.quayintech.tasklib.viewmodel.RepeatUntilPageViewModel mViewModel;
    protected FragmentRepeatUntilBinding(@Nullable android.databinding.DataBindingComponent bindingComponent, @Nullable android.view.View root_, int localFieldCount
        , android.widget.ImageView btnClose1
        , android.widget.ImageView btnDone1
        , android.widget.RadioButton checkBoxDefault1
        , android.widget.RadioButton checkBoxSpecific1
        , android.widget.CalendarView mCalendarView1
        , android.widget.LinearLayout mainLayout1
        , android.widget.RadioGroup radioGroup1
        , android.widget.TextView subTitle1
        , android.widget.TextView title1
    ) {
        super(bindingComponent, root_, localFieldCount);
        this.btnClose = btnClose1;
        this.btnDone = btnDone1;
        this.checkBoxDefault = checkBoxDefault1;
        this.checkBoxSpecific = checkBoxSpecific1;
        this.mCalendarView = mCalendarView1;
        this.mainLayout = mainLayout1;
        this.radioGroup = radioGroup1;
        this.subTitle = subTitle1;
        this.title = title1;
    }
    //getters and abstract setters
    public abstract void setHandler(@Nullable com.quayintech.tasklib.interfaces.RepeatUntilPageHandler Handler);
    @Nullable
    public com.quayintech.tasklib.interfaces.RepeatUntilPageHandler getHandler() {
        return mHandler;
    }
    public abstract void setViewModel(@Nullable com.quayintech.tasklib.viewmodel.RepeatUntilPageViewModel ViewModel);
    @Nullable
    public com.quayintech.tasklib.viewmodel.RepeatUntilPageViewModel getViewModel() {
        return mViewModel;
    }
    @NonNull
    public static FragmentRepeatUntilBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static FragmentRepeatUntilBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static FragmentRepeatUntilBinding bind(@NonNull android.view.View view) {
        return null;
    }
    @NonNull
    public static FragmentRepeatUntilBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    @NonNull
    public static FragmentRepeatUntilBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    @NonNull
    public static FragmentRepeatUntilBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
}