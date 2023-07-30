package com.ejoongseok.wmslive.common;

import com.ejoongseok.wmslive.product.feature.api.RegisterProductApi;

public class Scenario {
    public static RegisterProductApi registerProduct() {
        return new RegisterProductApi();
    }
}
