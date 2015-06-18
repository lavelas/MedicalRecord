package ua.pp.mmr.mymedicalrecord;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Uliana on 31.05.2015.
 */
public class StepContainer {
    private Context context;
    private RelativeLayout background;
    private HashMap<Integer, RelativeLayout> map = new HashMap<Integer, RelativeLayout>();
    private Integer currentStep;

    StepContainer(RelativeLayout background, Context context) {
        this.background = background;
        this.context = context;
    }

    // Проверка шага на наличие с возможностью скрыть остальные
    private boolean existStep(Integer step, boolean needSetVisible, boolean needDeactivated) {
        final Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha_02_1);
        //anim.setFillAfter(true);
        if (map == null || map.size() == 0) return false;
        boolean result = false;
        if (needDeactivated || (!needDeactivated && !needSetVisible)) background.setVisibility(View.GONE);
        else background.setVisibility(View.VISIBLE);
        for (Map.Entry<Integer, RelativeLayout> entry : map.entrySet()) {
            Integer key = entry.getKey();
            // закроем все шаги, кроме текущего
            if (needSetVisible) {
                entry.getValue().setVisibility(View.GONE);
            }
            if (key.equals(step)) {
                if (needSetVisible){
                    // покажем или скроем текущий
                    if (needDeactivated) {
                        entry.getValue().setVisibility(View.GONE);
                    } else {
                        entry.getValue().setVisibility(View.VISIBLE);
                        entry.getValue().startAnimation(anim);
                    }
                }
                result = true;
            }
        }
        return result;
    }

    // Просто проверка шага
    private boolean existStep(Integer step) {
        return existStep(step, false, false);
    }

    // Добавление шага
    public void addStep(Integer step, RelativeLayout layout) {
        if (!existStep(step)) {
            map.put(step, layout);
        }
    }

    // Получение шага
    public RelativeLayout getStep(Integer step) {
        if (map == null || map.size() == 0) return null;
        return map.get(step);
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    // Установка шага, как текущего
    public void activateStep(Integer step) {
        currentStep = step;
        existStep(step, true, false);
    }

    // закроем все шаги
    public void deactivateStep(Integer step) {
        existStep(step, true, true);
    }
}
