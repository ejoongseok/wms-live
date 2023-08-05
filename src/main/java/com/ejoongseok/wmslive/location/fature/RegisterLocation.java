package com.ejoongseok.wmslive.location.fature;

import com.ejoongseok.wmslive.location.domain.Location;
import com.ejoongseok.wmslive.location.domain.LocationRepository;
import com.ejoongseok.wmslive.location.domain.StorageType;
import com.ejoongseok.wmslive.location.domain.UsagePurpose;
import org.springframework.util.Assert;

class RegisterLocation {
    private final LocationRepository locationRepository;

    RegisterLocation(final LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void request(final Request request) {
        final Location location = request.toDomain();

        locationRepository.save(location);
    }

    public record Request(
            String locationBarcode,
            StorageType storageType,
            UsagePurpose usagePurpose) {

        public Request {
            Assert.hasText(locationBarcode, "로케이션 바코드는 필수입니다.");
            Assert.notNull(storageType, "보관 타입은 필수입니다.");
            Assert.notNull(usagePurpose, "보관 목적은 필수입니다.");
        }

        public Location toDomain() {
            return new Location(
                    locationBarcode,
                    storageType,
                    usagePurpose
            );
        }
    }
}
