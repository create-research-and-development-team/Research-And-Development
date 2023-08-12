package org.valkyrienskies.vsrnd.util.animation;

import java.lang.reflect.UndeclaredThrowableException;


public class EaseFloat {

    EaseType type;
    float speed;
    float target;
    float current;
    float start;
    float eased;
    boolean done;
    public EaseFloat(float speed, EaseType type ) {
        this.type = type;
        this.speed = speed;
        this.target = 0;
        this.current = 0;
        this.start = 0;
        this.eased = 0;
        this.done = false;
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
            done = true;
            return;
        }
        done = false;
        float percent=current/mult;

        current+=speed;

        switch (type) {
            case EASE_IN_SINE: eased = EaseHelper.easeInSine(percent)*mult; break;
            case EASE_OUT_SINE: eased = EaseHelper.easeOutSine(percent)*mult; break;
            case EASE_IN_OUT_SINE: eased = EaseHelper.easeInOutSine(percent)*mult; break;
            case EASE_IN_QUAD: eased = EaseHelper.easeInQuad(percent)*mult; break;
            case EASE_OUT_QUAD: eased = EaseHelper.easeOutQuad(percent)*mult; break;
            case EASE_IN_OUT_QUAD: eased = EaseHelper.easeInOutQuad(percent)*mult; break;
            case EASE_IN_EXPO: eased = EaseHelper.easeInExpo(percent)*mult; break;
            case EASE_OUT_EXPO: eased = EaseHelper.easeOutExpo(percent)*mult; break;
            case EASE_IN_OUT_EXPO: eased = EaseHelper.easeInOutExpo(percent)*mult; break;
            case EASE_IN_OVERSHOOT: eased = EaseHelper.easeInOvershoot(percent)*mult; break;
            case EASE_OUT_OVERSHOOT: eased = EaseHelper.easeOutOvershoot(percent)*mult; break;
            case EASE_IN_OUT_OVERSHOOT: eased = EaseHelper.easeInOutOvershoot(percent)*mult; break;
            case EASE_IN_ELASTIC: eased = EaseHelper.easeInElastic(percent)*mult; break;
            case EASE_OUT_ELASTIC: eased = EaseHelper.easeOutElastic(percent)*mult; break;
            case EASE_IN_OUT_ELASTIC: eased = EaseHelper.easeInOutElastic(percent)*mult; break;
            case EASE_IN_BOUNCE: eased = EaseHelper.easeInBounce(percent)*mult; break;
            case EASE_OUT_BOUNCE: eased = EaseHelper.easeOutBounce(percent)*mult; break;
            case EASE_IN_OUT_BOUNCE: eased = EaseHelper.easeInOutBounce(percent)*mult; break;
            default: throw new RuntimeException();
        }

    }

    public void setTarget(float target) {
        done = false;

        this.target = target;
        this.start = this.current;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    public boolean isDone() {
        return done;
    }
    public float getEased() {
        return eased;
    }

    public enum EaseType {
        EASE_IN_SINE,EASE_OUT_SINE,EASE_IN_OUT_SINE,EASE_IN_QUAD,EASE_OUT_QUAD,
        EASE_IN_OUT_QUAD,EASE_IN_EXPO,EASE_OUT_EXPO,EASE_IN_OUT_EXPO,EASE_IN_OVERSHOOT,
        EASE_OUT_OVERSHOOT,EASE_IN_OUT_OVERSHOOT,EASE_IN_ELASTIC,EASE_OUT_ELASTIC,EASE_IN_OUT_ELASTIC,
        EASE_IN_BOUNCE,EASE_OUT_BOUNCE,EASE_IN_OUT_BOUNCE
    }
}
