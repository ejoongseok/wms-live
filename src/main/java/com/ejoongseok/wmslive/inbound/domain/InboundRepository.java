package com.ejoongseok.wmslive.inbound.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InboundRepository {
    private final Map<Long, Inbound> inbounds = new HashMap<>();
    private Long sequence = 1L;


    public void save(final Inbound inbound) {
        inbound.assignId(sequence);
        sequence++;
        inbounds.put(inbound.getId(), inbound);
    }

    public List<Inbound> findAll() {
        return new ArrayList<>(inbounds.values());
    }
}
