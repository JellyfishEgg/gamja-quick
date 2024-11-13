package com.sparta.gamjaquick.order.entity;

import com.sparta.gamjaquick.common.AuditingFields;
import com.sparta.gamjaquick.order.dto.request.OrderCreateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private UUID id;

    @Column(nullable = false)
    private String address;

    private String request;

    private boolean isDeleted;

    @Builder
    private DeliveryInfo(String address, String request) {
        this.address = address;
        this.request = request;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public static DeliveryInfo from(OrderCreateRequestDto.DeliveryInfoRequestDto dto) {
        return DeliveryInfo.builder()
                .address(dto.getAddress())
                .request(dto.getRequest())
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
