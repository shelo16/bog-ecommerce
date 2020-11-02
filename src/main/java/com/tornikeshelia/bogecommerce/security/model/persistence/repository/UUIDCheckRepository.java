package com.tornikeshelia.bogecommerce.security.model.persistence.repository;

import com.tornikeshelia.bogecommerce.security.model.enums.UuidTypeEnum;
import com.tornikeshelia.bogecommerce.security.model.persistence.entity.UUIDCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UUIDCheckRepository extends JpaRepository<UUIDCheck, Long> {

    @Query("FROM UUIDCheck uc WHERE uc.isValid = 1 and uc.uuid =:uuid and uc.uuidType=:uuidType")
    UUIDCheck getUUIDCheckByUUIDAndByType(String uuid, UuidTypeEnum uuidType);

}
