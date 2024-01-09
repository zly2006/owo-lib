package io.wispforest.owo.ui.util;

import io.wispforest.owo.ui.core.Component;
import io.wispforest.owo.ui.core.Positioning;
import io.wispforest.owo.ui.core.Size;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MountingHelper {

    protected final ComponentSink sink;
    protected final List<Component> lateChildren;

    protected MountingHelper(ComponentSink sink, List<Component> children) {
        this.sink = sink;
        this.lateChildren = children;
    }

    public static void inflateWithExpand(List<Component> children, Size childSpace, boolean vertical) {
        var nonExpandChildren = new ArrayList<Component>();

        children.forEach(child -> {
            if (!child.verticalSizing().get().isExpand() && !child.horizontalSizing().get().isExpand()) {
                if(child.positioning().get().type == Positioning.Type.LAYOUT) {
                    nonExpandChildren.add(child);
                }

                child.inflate(childSpace);
            }
        });

        //noinspection ExtractMethodRecommender
        Size remainingSpace;
        if (vertical) {
            int height = childSpace.height();
            for (var nonExpandChild : nonExpandChildren) {
                height -= nonExpandChild.fullSize().height();
            }

            remainingSpace = Size.of(childSpace.width(), Math.max(0, height));
        } else {
            int width = childSpace.width();
            for (var nonExpandChild : nonExpandChildren) {
                width -= nonExpandChild.fullSize().width();
            }

            remainingSpace = Size.of(Math.max(0, width), childSpace.height());
        }


        children.forEach(child -> {
            if (child.verticalSizing().get().isExpand() || child.horizontalSizing().get().isExpand()) {
                child.inflate(remainingSpace);
            }
        });
    }

    public static MountingHelper mountEarly(ComponentSink sink, List<Component> children, Consumer<Component> layoutFunc) {
        var lateChildren = new ArrayList<Component>();

        for (var child : children) {
            if (!child.positioning().get().isRelative()) {
                sink.accept(child, layoutFunc);
            } else {
                lateChildren.add(child);
            }
        }

        return new MountingHelper(sink, lateChildren);
    }

    public void mountLate() {
        for (var child : this.lateChildren) {
            this.sink.accept(child, component -> {throw new IllegalStateException("A layout-positioned child was mounted late");});
        }
        this.lateChildren.clear();
    }

    public interface ComponentSink {
        void accept(@Nullable Component child, Consumer<Component> layoutFunc);
    }

}
