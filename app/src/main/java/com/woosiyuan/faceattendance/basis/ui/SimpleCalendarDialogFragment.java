package com.woosiyuan.faceattendance.basis.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.woosiyuan.faceattendance.MyApplication;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.basis.entity.DateTellEvent;
import com.woosiyuan.faceattendance.basis.util.RxBus;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.basis.ui.SimpleCalendarDialogFragment.java
 * @author: so
 * @date: 2018-01-09 11:02
 */

public  class SimpleCalendarDialogFragment extends AppCompatDialogFragment
        implements OnDateSelectedListener {

    private TextView textView;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    public static String choose_date;
    private RxBus mRxBus;
    private DateTellEvent mDateTellEvent;
    ViewDataBinding dataBinding;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //inflate custom layout and get views
        //pass null as parent view because will be in dialog layout
        View view = inflater.inflate(R.layout.dialog_basic, null);

        textView = (TextView) view.findViewById(R.id.textView);
        mRxBus= RxBus.getInstance();

        MaterialCalendarView widget = (MaterialCalendarView) view.findViewById(R.id.calendarView);

        widget.setOnDateChangedListener(this);

        return new AlertDialog.Builder(getActivity())
                .setTitle("请选择日期")
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        //et_search.setText(data);
                        mDateTellEvent=new DateTellEvent();
                        mDateTellEvent.setDate(choose_date);
                        mRxBus.post(mDateTellEvent);
                        dismiss();
                    }
                })
                .create();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull
            CalendarDay date, boolean selected) {
        choose_date=FORMATTER.format(date.getDate());
        textView.setText(FORMATTER.format(date.getDate()));
    }
}
