# 스프링부트로 MVP 백엔드 API 빠르게 개발하기(WMS 편)

## 스펙

- Java 17
- Spring boot 3.1

## 기획
- 구글 docs: https://docs.google.com/document/d/1Dy9G2pN-4M11OE3dQxyK_en9DE7rjsNTDO5RtSyTF80/edit?usp=sharing
- 구글 슬라이드: https://docs.google.com/presentation/d/14DmwOsJWyc5aY3ZDht3602IgHdcXZYJIKQJz44u13o4/edit?usp=sharing
- p6spy: https://docs.google.com/document/d/14WxDv-E5dwpiPXsD9QjLpiqRir_t-xxWXse8EtDTT2M/edit?usp=sharing

## 요구사항

### 입고

- [ ] 입고를 생성 할 수 있다.
- [ ] 입고를 확정 할 수 있다.
    - [ ] 발주 요청 상태에서만 입고를 확정 할 수 있다.
- [ ] 입고를 거부 할 수 있다.
    - [ ] 발주 요청 상태에서만 입고를 거부 할 수 있다.
- [ ] 입고상품의 LPN을 생성 할 수 있다.
    - [ ] 입고 확정 상태에서만 LPN을 생성 할 수 있다.

### 상품

- [ ] 상품을 생성 할 수 있다.

### 로케이션

- [ ] 로케이션을 생성 할 수 있다.
- [ ] 로케이션에 재고(LPN)를 추가 할 수 있다.
    - [ ] 로케이션의 재고를 하나씩 증가 시킬 수 있다.
    - [ ] 로케이션의 재고를 입력한 숫자만큼 증가 시킬 수 있다.
        - [ ] 입력한 재고 수량이 0보다 작을 수 없다.
- [ ] 로케이션을 다른 로케이션의 하위 로케이션으로 이동 시킬 수 있다.
    - [ ] 이동할 상위 로케이션보다 크기가 작아야 한다.
- [ ] 로케이션의 재고를 조정 할 수 있다.
    - [ ] 조정할 재고 수량이 0보다 작을 수 없다.
- [ ] 로케이션의 재고를 이동 할 수 있다.
    - [ ] 이동할 재고 수량은 현재 재고 수량 클 수 없다.

### 출고

- [ ] 출고를 생성 할 수 있다.
- [ ] 출고 목록을 분할 할 수 있다.
    - [ ] 출고 대기 상태에서만 출고를 분할 할 수 있다.
- [ ] 집품할 토트를 할당 할 수 있다.
    - [ ] 출고 대기 상태에서만 집품 토트를 할당 할 수 있다.
    - [ ] 추천 포장재가 있을 경우만 집품 토트를 할당 할 수 있다.
- [ ] 출고할 집품 목록을 할당 할 수 있다.
    - [ ] 집품 대기 상태에서만 집품 목록을 할당 할 수 있다.
- [ ] 출고목록을 집품 할 수 있다.
    - [ ] 집품 항목을 하나씩 스캔해서 집품 할 수 있다.
    - [ ] 집품 항목을 입력한 숫자만큼 집품 할 수 있다.
        - [ ] 입력한 집품 수량이 0보다 작을 수 없다.
- [ ] 출고 상품의 검수를 통과 시킬 수 있다.
    - [ ] 집품 완료 상태에서만 검수를 통과 시킬 수 있다.
- [ ] 출고 상품의 검수를 거부 할 수 있다.
    - [ ] 집품 완료 상태에서만 검수를 거부 할 수 있다.
- [ ] 포장 정보를 등록 할 수 있다.
    - [ ] 집품 완료 상태에서만 포장 정보를 등록 할 수 있다.
- [ ] 송장을 발행 할 수 있다.
- [ ] 포장을 완료 할 수있다.
    - [ ] 포장 진행 상태에서만 포장을 완료 할 수 있다.
    - [ ] 송장이 발행된 상태에서만 포장을 완료 할 수 있다.

## 용어 사전

| 한글명       | 영문명                               | 설명                                                                                                  |
|-----------|-----------------------------------|-----------------------------------------------------------------------------------------------------|
| 창고 관리 시스템 | Warehouse Management System (WMS) | 상품이나 원자재가 창고에 들어온 시점부터 나가는 시점까지 창고 작업을 관리하고 조정하는 소프트웨어와 절차의 체계입니다.                                  |
| 입고        | Inbound                           | 다양한 상품을 운송업체로부터 받아들이는 과정. 일반적으로 제품이 제대로 배송되었는지를 확인하는 것을 포함합니다.                                      |
| 출고        | Outbound                          | 주문 이행 과정의 일환으로 목적지로 상품을 보내는 행위.                                                                     |
| 재고        | Inventory                         | 창고에 보유하고 있는 상품 및 재료.                                                                                |
| 집품        | Picking                           | 주문을 이행하기 위해 창고에서 품목을 수집하는 과정.                                                                       |
| 포장        | Packing                           | 발송 또는 운송을 위한 품목 포장 과정.                                                                              |
| LPN       | License Plate Number (LPN)        | 재고 항목을 추적하고 관리하는데 사용되는 고유 식별자. 각 LPN은 고유한 번호를 가지고 있으며, 이를 통해 개별 항목, 팔레트, 콘테이너 또는 상품 묶음을 식별할 수 있습니다. |
| 로케이션      | Location                          | 창고 내에서 특정 재고 항목(LPN)이나 자산을 찾을 수 있는 실제 장소.                                                           |
