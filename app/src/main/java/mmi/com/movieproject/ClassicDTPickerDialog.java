package mmi.com.movieproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * Created by Anant Rana
 * <p/>
 * Layout File : fragment_classic_dtpicker_dialog
 * <p/>
 * Android Old style (Spinner) Date and Time Picker
 **/
public class ClassicDTPickerDialog extends DialogFragment {

    private OnDateTimeSelectedListener mListener;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;


    public static ClassicDTPickerDialog newInstance(OnDateTimeSelectedListener listener) {
        ClassicDTPickerDialog dialog = new ClassicDTPickerDialog();
        dialog.setListener(listener);
        return dialog;
    }

    public ClassicDTPickerDialog() {
        // Required empty public constructor
    }


    public interface OnDateTimeSelectedListener {
        public void onDateTimeSelected(int year, int month, int dayOfMonth, int hour, int minute);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_classic_dtpicker_dialog, null);

        mDatePicker = (DatePicker) view.findViewById(R.id.classic_picker_date);
        mTimePicker = (TimePicker) view.findViewById(R.id.classic_picker_time);
        mDatePicker.setMinDate(System.currentTimeMillis()-1000);
        mDatePicker.setMaxDate(System.currentTimeMillis() + 1000*60*60*24*2);

        builder.setView(view);
        builder.setPositiveButton("SET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClickDialogButton(which);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClickDialogButton(which);
            }
        });

        return builder.create();

    }


    private void onClickDialogButton(int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (mListener != null) {
                    mListener.onDateTimeSelected(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth(), mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }

    private void setListener(OnDateTimeSelectedListener listener) {
        mListener = listener;
    }

}
