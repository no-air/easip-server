package com.noair.easip.house.repository;

import com.noair.easip.house.domain.RentalType;
import com.noair.easip.house.domain.RoomRentalCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRentalConditionRepository extends JpaRepository<RoomRentalCondition, String> {
    List<RoomRentalCondition> findAllByHouseIdAndRentalTypeOrderByRoomTypeAscExclusiveAreaAscSupplyTypeAscDepositAsc(
            String houseId,
            RentalType rentalType
    );
}
