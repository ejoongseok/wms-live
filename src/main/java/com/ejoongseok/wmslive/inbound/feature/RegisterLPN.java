package com.ejoongseok.wmslive.inbound.feature;

import com.ejoongseok.wmslive.inbound.domain.Inbound;
import com.ejoongseok.wmslive.inbound.domain.InboundRepository;
import com.ejoongseok.wmslive.inbound.domain.LPNRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class RegisterLPN {
    private final InboundRepository inboundRepository;
    private final LPNRepository lPNRepository;

    @PostMapping("/inbounds/inbound-items/{inboundItemNo}/lpns")
    @Transactional
    public void request(
            @PathVariable final Long inboundItemNo,
            @RequestBody @Valid final Request request) {
        checkIfLPNBarcodeAlreadyExists(request.lpnBarcode);
        final Inbound inbound = inboundRepository.getByInboundItemNo(
                inboundItemNo);

        inbound.registerLPN(
                inboundItemNo,
                request.lpnBarcode,
                request.expirationAt);
    }

    private void checkIfLPNBarcodeAlreadyExists(final String lpnBarcode) {
        lPNRepository.findByLPNBarcode(lpnBarcode).ifPresent(
                lpn -> {
                    throw new LPNBarcodeAlreadyExistsException(lpnBarcode);
                }
        );
    }

    public record Request(
            @NotBlank(message = "LPN 바코드는 필수입니다.")
            String lpnBarcode,
            @NotNull(message = "유통기한은 필수입니다.")
            LocalDateTime expirationAt) {
    }

}
