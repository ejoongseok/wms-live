package com.ejoongseok.wmslive.outbound.feature;

import com.ejoongseok.wmslive.location.domain.Location;
import com.ejoongseok.wmslive.location.domain.LocationRepository;
import com.ejoongseok.wmslive.outbound.domain.Outbound;
import com.ejoongseok.wmslive.outbound.domain.OutboundRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AllocatePickingTote {
    private final OutboundRepository outboundRepository;
    private final LocationRepository locationRepository;

    @Transactional
    @PostMapping("/outbounds/allocate-picking-tote")
    public void request(@RequestBody @Valid final Request request) {
        final Outbound outbound = outboundRepository.getBy(request.outboundNo);
        final Location tote = locationRepository.getByLocationBarcode(request.toteBarcode);
        outbound.allocatePickingTote(tote);
    }

    public record Request(
            @NotNull(message = "출고번호는 필수입니다.")
            Long outboundNo,
            @NotBlank(message = "토트 바코드는 필수입니다.")
            String toteBarcode) {
    }
}
