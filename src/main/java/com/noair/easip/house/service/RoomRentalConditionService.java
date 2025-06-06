package com.noair.easip.house.service;

import com.noair.easip.house.controller.dto.RoomInfoResponse;
import com.noair.easip.house.domain.RentalType;
import com.noair.easip.house.domain.RoomRentalCondition;
import com.noair.easip.house.repository.RoomRentalConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomRentalConditionService {
    private final RoomRentalConditionRepository roomRentalConditionRepository;

    public List<RoomRentalCondition> getRoomRentalConditionsByHouseIdAndRentalType(String houseId, RentalType rentalType) {
        return roomRentalConditionRepository.findAllByHouseIdAndRentalTypeOrderByRoomTypeAscExclusiveAreaAscSupplyTypeAscDepositAsc(
                houseId,
                rentalType
        );
    }

    public List<RoomInfoResponse> getRoomInfoResponses(String houseId, RentalType rentalType) {
        List<RoomRentalCondition> conditions = getRoomRentalConditionsByHouseIdAndRentalType(houseId, rentalType);
        return conditions.stream()
                .map(condition -> RoomInfoResponse.of(
                        condition.getRoomType(),
                        condition.getExclusiveArea(),
                        condition.getSupplyType().getKorName(),
                        condition.getTotalRoomCount(),
                        condition.getDeposit(),
                        condition.getMonthlyRent(),
                        condition.getMaintenanceFee()
                ))
                .toList();
    }
}
