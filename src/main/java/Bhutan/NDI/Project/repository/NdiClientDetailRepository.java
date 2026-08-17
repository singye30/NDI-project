package Bhutan.NDI.Project.repository;

import Bhutan.NDI.Project.entity.NdiClientDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NdiClientDetailRepository extends JpaRepository<NdiClientDetail, Long> {

    /**
     * Find client by clientId
     */
    Optional<NdiClientDetail> findByClientId(String clientId);

    /**
     * Find client by clientId and active status
     */
    Optional<NdiClientDetail> findByClientIdAndClientStatus(String clientId, String status);
}
