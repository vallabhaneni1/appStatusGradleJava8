package com.wf.appstatus.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wf.appstatus.springboot.model.SoftwareUpdateReport;

@Repository
public interface ReportsRepository extends JpaRepository<SoftwareUpdateReport, Long> {

}
