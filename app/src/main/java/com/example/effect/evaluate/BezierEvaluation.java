package com.example.effect.evaluate;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class BezierEvaluation implements TypeEvaluator<PointF> {
    private PointF point1;
    private PointF point2;

    public BezierEvaluation(PointF control1, PointF control2) {
        this.point1 = control1;
        this.point2 = control2;
    }

    @Override
    public PointF evaluate(float t, PointF point0, PointF point3) {
        // t百分比， 0~1
        PointF point = new PointF();
        point.x = point0.x * (1 - t) * (1 - t) * (1 - t) //
                        + 3 * point1.x * t * (1 - t) * (1 - t)//
                        + 3 * point2.x * t * t * (1 - t)//
                        + point3.x * t * t * t;

        point.y = point0.y * (1 - t) * (1 - t) * (1 - t) //
                        + 3 * point1.y * t * (1 - t) * (1 - t)//
                        + 3 * point2.y * t * t * (1 - t)//
                        + point3.y * t * t * t;
        // 套用上面的公式把点返回
        return point;
    }
}
