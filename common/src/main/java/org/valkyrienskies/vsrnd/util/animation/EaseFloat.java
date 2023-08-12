package org.valkyrienskies.vsrnd.util.animation;

import java.lang.reflect.UndeclaredThrowableException;

public class EaseFloat {

    String type;
    float speed;
    float target;
    float current;
    float start;
    float eased;
    public EaseFloat(float speed, String type ) {
        this.type = type;
        this.speed = speed;
        this.target = 0;
        this.current = 0;
        this.start = 0;
        this.eased = 0;
    }
    public void tick() {

        float mult;
        if (start < target && current < target) {
            mult = (target-start);

        }
        else if (start > target && current > target) {
            mult = (start-target);

        }
        else {
            return;
        }

        float percent=current/mult;

        current+=speed;

        switch (type) {
            case "easeInSine": eased = EaseHelper.easeInSine(percent)*mult; break;
            case "easeOutSine": eased = EaseHelper.easeOutSine(percent)*mult; break;
            case "easeInOutSine": eased = EaseHelper.easeInOutSine(percent)*mult; break;
            case "easeInQuad": eased = EaseHelper.easeInQuad(percent)*mult; break;
            case "easeOutQuad": eased = EaseHelper.easeOutQuad(percent)*mult; break;
            case "easeInOutQuad": eased = EaseHelper.easeInOutQuad(percent)*mult; break;
            case "easeInExpo": eased = EaseHelper.easeInExpo(percent)*mult; break;
            case "easeOutExpo": eased = EaseHelper.easeOutExpo(percent)*mult; break;
            case "easeInOutExpo": eased = EaseHelper.easeInOutExpo(percent)*mult; break;
            case "easeInOvershoot": eased = EaseHelper.easeInOvershoot(percent)*mult; break;
            case "easeOutOvershoot": eased = EaseHelper.easeOutOvershoot(percent)*mult; break;
            case "easeInOutOvershoot": eased = EaseHelper.easeInOutOvershoot(percent)*mult; break;
            case "easeInElastic": eased = EaseHelper.easeInElastic(percent)*mult; break;
            case "easeOutElastic": eased = EaseHelper.easeOutElastic(percent)*mult; break;
            case "easeInOutElastic": eased = EaseHelper.easeInOutElastic(percent)*mult; break;
            case "easeInBounce": eased = EaseHelper.easeInBounce(percent)*mult; break;
            case "easeOutBounce": eased = EaseHelper.easeOutBounce(percent)*mult; break;
            case "easeInOutBounce": eased = EaseHelper.easeInOutBounce(percent)*mult; break;
            default: throw new RuntimeException();
        }

    }

    public void setTarget(float target) {
        this.target = target;
        this.start = this.current;
    }

    public float GetEased() {
        return eased;
    }
}
