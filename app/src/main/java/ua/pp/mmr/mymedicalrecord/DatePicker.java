package ua.pp.mmr.mymedicalrecord;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class DatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private EditText setInThisTextView;

    public void setTextView(EditText setInThisTextView) {
        this.setInThisTextView = setInThisTextView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int[] choosedDate = getChoosedDate();
        // создаем DatePickerDialog и возвращаем его
        Dialog picker = new DatePickerDialog(getActivity(), this,
                choosedDate[2], choosedDate[1], choosedDate[0]);
        picker.setTitle(getResources().getString(R.string.choose_date));

        return picker;
    }
    @Override
    public void onStart() {
        super.onStart();
        // добавляем кастомный текст для кнопки
        Button nButton =  ((AlertDialog) getDialog())
                .getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText(getResources().getString(R.string.ready));

    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year,
                          int month, int day) {
        if (setInThisTextView == null) return;
        StringBuilder sb = new StringBuilder();
        if (day < 10) {
            sb.append("0");
            sb.append(day);
        } else
            sb.append(day);
        sb.append("-");
        if (month < 10) {
            sb.append("0");
            sb.append(month);
        } else
            sb.append(month);
        sb.append("-");
        sb.append(year);
        setInThisTextView.setText(sb.toString());
    }

    private int[] getChoosedDate(){
        int[] result = new int[3];
        String[] fromTextView = setInThisTextView.getText().toString().split("-");
        if(fromTextView.length == 3) {
            try {
                result[0] = Integer.parseInt(fromTextView[0]);
                result[1] = Integer.parseInt(fromTextView[1]);
                result[2] = Integer.parseInt(fromTextView[2]);
            } catch (Exception ignored){}
            return result;
        }
        // определяем текущую дату
        final Calendar c = Calendar.getInstance();
        result[0] = c.get(Calendar.DAY_OF_MONTH);
        result[1] = c.get(Calendar.MONTH);
        result[2] = c.get(Calendar.YEAR);
        return result;
    }
}