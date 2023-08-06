package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.outbound.domain.MaterialType;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterial;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterialDimension;
import com.ejoongseok.wmslive.outbound.domain.PackagingMaterialRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
class RegisterPackagingMaterial {
    private final PackagingMaterialRepository packagingMaterialRepository;


    @PostMapping("/packaging-materials")
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@RequestBody @Valid final Request request) {
        final PackagingMaterial packagingMaterial = request.toDomain();

        packagingMaterialRepository.save(packagingMaterial);
    }

    public record Request(
            @NotBlank(message = "포장재 이름은 필수입니다.")
            String name,
            @NotBlank(message = "포장재 코드는 필수입니다.")
            String code,
            @NotNull(message = "내부 폭은 필수입니다.")
            @Min(value = 1, message = "내부 폭은 1mm 이상이어야 합니다.")
            Long innerWidthInMillimeters,
            @NotNull(message = "내부 높이는 필수입니다.")
            @Min(value = 1, message = "내부 높이는 1mm 이상이어야 합니다.")
            Long innerHeightInMillimeters,
            @NotNull(message = "내부 길이는 필수입니다.")
            @Min(value = 1, message = "내부 길이는 1mm 이상이어야 합니다.")
            Long innerLengthInMillimeters,
            @NotNull(message = "외부 폭은 필수입니다.")
            @Min(value = 1, message = "외부 폭은 1mm 이상이어야 합니다.")
            Long outerWidthInMillimeters,
            @NotNull(message = "외부 높이는 필수입니다.")
            @Min(value = 1, message = "외부 높이는 1mm 이상이어야 합니다.")
            Long outerHeightInMillimeters,
            @NotNull(message = "외부 길이는 필수입니다.")
            @Min(value = 1, message = "외부 길이는 1mm 이상이어야 합니다.")
            Long outerLengthInMillimeters,
            @NotNull(message = "무게는 필수입니다.")
            @Min(value = 1, message = "무게는 1g 이상이어야 합니다.")
            Long weightInGrams,
            @NotNull(message = "최대 무게는 필수입니다.")
            @Min(value = 1, message = "최대 무게는 1g 이상이어야 합니다.")
            Long maxWeightInGrams,
            @NotNull(message = "포장재 타입은 필수입니다.")
            MaterialType materialType) {

        public PackagingMaterial toDomain() {
            return new PackagingMaterial(
                    name,
                    code,
                    new PackagingMaterialDimension(
                            innerWidthInMillimeters,
                            innerHeightInMillimeters,
                            innerLengthInMillimeters,
                            outerWidthInMillimeters,
                            outerHeightInMillimeters,
                            outerLengthInMillimeters
                    ),
                    weightInGrams,
                    maxWeightInGrams,
                    materialType
            );
        }

    }
}
