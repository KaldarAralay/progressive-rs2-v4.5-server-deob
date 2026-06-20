/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.path;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.path.PathResult;
import com.rs2.model.path.PathStep;
import com.rs2.model.path.PathStrategy;
import com.rs2.util.GameUtil;

public final class DirectPathStrategy
implements PathStrategy {
    @Override
    public final PathResult buildPath(Entity object, Position position, boolean bl) {
        PathResult pathResult = new PathResult();
        object = ((Entity)object).getPosition().copy();
        Position position2 = ((Position)object).copy();
        int n = position.getX() - ((Position)object).getX();
        int n2 = position.getY() - ((Position)object).getY();
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        if (n < 0) {
            n3 = -1;
        } else if (n > 0) {
            n3 = 1;
        }
        if (n2 < 0) {
            n4 = -1;
        } else if (n2 > 0) {
            n4 = 1;
        }
        if (n < 0) {
            n5 = -1;
        } else if (n > 0) {
            n5 = 1;
        }
        int n7 = Math.abs(n);
        int n8 = Math.abs(n2);
        if (n7 <= n8) {
            n7 = Math.abs(n2);
            n8 = Math.abs(n);
            if (n2 < 0) {
                n6 = -1;
            } else if (n2 > 0) {
                n6 = 1;
            }
            n5 = 0;
        }
        n2 = n7 >> 1;
        n = 0;
        while (n <= n7) {
            n2 += n8;
            if (!position2.equals(object) && bl && !GameUtil.a((Position)object, position2, true)) {
                return pathResult;
            }
            position2 = ((Position)object).copy();
            pathResult.getSteps().add(new PathStep(((Position)object).getX(), ((Position)object).getY()));
            if (n2 >= n7) {
                n2 -= n7;
                ((Position)object).setX(((Position)object).getX() + n3);
                ((Position)object).setY(((Position)object).getY() + n4);
            } else {
                ((Position)object).setX(((Position)object).getX() + n5);
                ((Position)object).setY(((Position)object).getY() + n6);
            }
            ++n;
        }
        return pathResult;
    }
}

