package com.thoughtworks.ddd.sample.jingxi.domain.common.publish;

import com.thoughtworks.ddd.sample.jingxi.domain.common.event.BaseEvent;

public interface EventPublisher {

    void publish(BaseEvent event);
}
