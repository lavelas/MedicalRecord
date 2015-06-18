package ua.pp.mmr.mymedicalrecord;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FirstFragment extends Fragment {
    private Utils utils;
    private StepContainer stepsInfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        utils = Utils.getInstance(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepsInfo = new StepContainer((RelativeLayout)getActivity().findViewById(R.id.stepBackground), getActivity().getApplicationContext());
        initSteps();
        createListeners();
        initialize();
    }

    private void initSteps() {
        // Заполним контейнер шагами, с которыми будем работать
        RelativeLayout steps = (RelativeLayout) getActivity().findViewById(R.id.addDiseaseStep1);
        stepsInfo.addStep(1, steps);
        steps = (RelativeLayout) getActivity().findViewById(R.id.addDiseaseStep2);
        stepsInfo.addStep(2, steps);

    }

    private void createListeners(){
        // Инициализируем слушатели всех кнопок
        // Кнопки далее/назад и установка даты шага 1
        Button next = (Button) getActivity().findViewById(R.id.next);
        Button back = (Button) getActivity().findViewById(R.id.back);
        EditText setDate = (EditText) getActivity().findViewById(R.id.date);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepsInfo.getCurrentStep() == 1) {

                }
                DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
                EditText field = (EditText) getActivity().findViewById(R.id.name);
                String diseaseName = field.getText().toString();
                field = (EditText) getActivity().findViewById(R.id.annotation);
                String diseaseAnnotation = field.getText().toString();
                field = (EditText) getActivity().findViewById(R.id.date);
                Date diseaseDate = Utils.getInstance(null).getDateFromString(field.getText().toString());
                Disease d = new Disease(diseaseName,diseaseAnnotation);
                d.setStartDate(diseaseDate);
                //d.setStartDate(new Date());
                db.addDisease(d);
                db.getDisease(11);
                stepsInfo.activateStep(2);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepsInfo.getCurrentStep() == 1) {
                    stepsInfo.deactivateStep(1);
                }
            }
        });

        setDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    DatePicker dateDialog = new DatePicker();
                    dateDialog.setTextView((EditText) getActivity().findViewById(R.id.date));
                    dateDialog.show(getFragmentManager(), "datePicker");
                }
                return false;
            }
        });

        // Кнопки далее/назад шага 2
        Button next2 = (Button) getActivity().findViewById(R.id.next2);
        Button back2 = (Button) getActivity().findViewById(R.id.back2);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepsInfo.deactivateStep(stepsInfo.getCurrentStep());
            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepsInfo.activateStep(stepsInfo.getCurrentStep() - 1);
            }
        });

        setDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    DatePicker dateDialog = new DatePicker();
                    dateDialog.setTextView((EditText) getActivity().findViewById(R.id.date));
                    dateDialog.show(getFragmentManager(), "datePicker");
                }
                return false;
            }
        });

    }

    private void initialize() {
        // Отображаем текущий день в виде шкалы, используя веса от 1 до 24
        /*LinearLayout screenOffsetLeft = (LinearLayout) getActivity().findViewById(R.id.screenOffsetLeft);
        LinearLayout screenOffsetRight = (LinearLayout) getActivity().findViewById(R.id.screenOffsetRight);
        int partOfDay = utils.getPartOfDay();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) screenOffsetLeft.getLayoutParams();
        lp.weight = partOfDay;
        screenOffsetLeft.setLayoutParams(lp);
        lp = (LinearLayout.LayoutParams) screenOffsetRight.getLayoutParams();
        lp.weight = 24 - partOfDay;
        screenOffsetRight.setLayoutParams(lp);
        screenOffsetLeft.setBackgroundColor(utils.getCurrentColorOfDay());*/

        RelativeLayout dayCircleLayout = (RelativeLayout) getActivity().findViewById(R.id.dayCircle);
        dayCircleLayout.addView(new DayCircle(getActivity()));

        // Анимация и слушатель на кнопке "Добавить"
        final ImageView iv = (ImageView) getActivity().findViewById(R.id.add);
        TextView currentDate = (TextView) getActivity().findViewById(R.id.currentDate);
        currentDate.setText(utils.getDate());
        final Animation animationRotateCenter = AnimationUtils.loadAnimation(
                getActivity().getApplicationContext(), R.anim.rcenter);
        animationRotateCenter.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                stepsInfo.activateStep(1);
            }
        });
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // Анимация кнопки
                    iv.startAnimation(animationRotateCenter);
                }
                return true;
            }
        });
    }
}