package com.cherkashyn.vitalii.bpmnui.core.repository;


import com.cherkashyn.vitalii.bpmnui.core.domain.DeployStatus;
import org.springframework.data.repository.CrudRepository;


public interface DeployStatusRepository extends CrudRepository<DeployStatus, Long> {
}
