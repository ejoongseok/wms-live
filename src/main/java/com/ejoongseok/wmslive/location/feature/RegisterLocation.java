package com.ejoongseok.wmslive.location.feature;

import com.ejoongseok.wmslive.location.domain.Location;
import com.ejoongseok.wmslive.location.domain.LocationRepository;
import com.ejoongseok.wmslive.location.domain.StorageType;
import com.ejoongseok.wmslive.location.domain.UsagePurpose;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterLocation {
    private final LocationRepository locationRepository;

    @PostMapping("/locations")
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@RequestBody @Valid final Request request) {
        final Location location = request.toDomain();

        locationRepository.save(location);
    }

    public record Request(
            @NotBlank(message = "로케이션 바코드는 필수입니다.")
            String locationBarcode,
            @NotNull(message = "보관 타입은 필수입니다.")
            StorageType storageType,
            @NotNull(message = "보관 목적은 필수입니다.")
            UsagePurpose usagePurpose) {

        public Location toDomain() {
            return new Location(
                    locationBarcode,
                    storageType,
                    usagePurpose
            );
        }
    }
}
