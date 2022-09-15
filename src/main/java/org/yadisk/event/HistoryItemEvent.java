package org.yadisk.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.yadisk.entity.SystemItem;

@Getter
public class HistoryItemEvent extends ApplicationEvent {
    private final SystemItem item;

    public HistoryItemEvent(Object source, SystemItem item) {
        super(source);
        this.item = item;
    }
}
