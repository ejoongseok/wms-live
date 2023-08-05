package com.ejoongseok.wmslive.location.fature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LocationRepository {
    private final Map<Long, Location> locations = new HashMap<>();
    private Long sequence = 1L;

    public void save(final Location location) {
        location.assignNo(sequence);
        sequence++;
        locations.put(location.getLocationNo(), location);
    }

    public List<Location> finaAll() {
        return new ArrayList<>(locations.values());
    }
}
