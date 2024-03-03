package io.wispforest.owo.ui.event;

import io.wispforest.owo.ui.core.Component;
import io.wispforest.owo.ui.core.ParentComponent;
import io.wispforest.owo.util.EventStream;

public interface Dismounted {
    boolean onDismounted(ParentComponent parent, Component.DismountReason reason);

    static EventStream<Dismounted> newStream() {
        return new EventStream<>(subscribers -> (parent, reason) -> {
            var anyTriggered = false;
            for (var subscriber : subscribers) {
                anyTriggered |= subscriber.onDismounted(parent, reason);
            }
            return anyTriggered;
        });
    }
}
