package io.wispforest.owo.ui.event;

import io.wispforest.owo.ui.core.ParentComponent;
import io.wispforest.owo.util.EventStream;

public interface Mounted {
    void onMounted(ParentComponent parent, int x, int y);

    static EventStream<Mounted> newStream() {
        return new EventStream<>(subscribers -> (parent, x, y) -> {
            for (var subscriber : subscribers) {
                subscriber.onMounted(parent, x, y);
            }
        });
    }
}
