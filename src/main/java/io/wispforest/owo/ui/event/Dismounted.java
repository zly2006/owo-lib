package io.wispforest.owo.ui.event;

import io.wispforest.owo.ui.core.Component;
import io.wispforest.owo.ui.core.ParentComponent;
import io.wispforest.owo.util.EventStream;

public interface Dismounted {
    void onDismounted(ParentComponent parent, Component.DismountReason reason);

    static EventStream<Dismounted> newStream() {
        return new EventStream<>(subscribers -> (parent, reason) -> {
            for (var subscriber : subscribers) {
                subscriber.onDismounted(parent, reason);
            }
        });
    }
}
