package com.payrecon.repository;
import com.payrecon.model.SettlementRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface SettlementRecordRepository extends JpaRepository<SettlementRecord, Long> {
    Optional<SettlementRecord> findByReference(String reference);
}
