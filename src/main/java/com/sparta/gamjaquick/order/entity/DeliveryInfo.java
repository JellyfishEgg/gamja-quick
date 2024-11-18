package com.sparta.gamjaquick.order.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.order.dto.request.OrderCreateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "p_delivery_info")
public class DeliveryInfo extends AuditingFields {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne(mappedBy = "deliveryInfo")
    private Order order;

    @Column(name = "address", nullable = false)
    private String address = "";

    @Column(name = "request")
    private String request;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Builder
    public DeliveryInfo(String address, String request) {
        this.address = address;
        this.request = request;
        this.isDeleted = false;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public static DeliveryInfo from(OrderCreateRequestDto.DeliveryInfoRequestDto dto) {
        String address = (dto.getAddress() == null || dto.getAddress().isEmpty()) ? "" : dto.getAddress();
        String request = (dto.getRequest() == null) ? "" : dto.getRequest(); // 요청 메시지도 일단 기본값

        return DeliveryInfo.builder()
                .address(address)
                .request(request)
                .build();
    }

    public void update(OrderCreateRequestDto.DeliveryInfoRequestDto dto) {
        this.address = dto.getAddress();
        this.request = dto.getRequest();
    }

    public void delete(String auditingUser) {
        this.isDeleted = true;
        super.delete(auditingUser);
    }

}
