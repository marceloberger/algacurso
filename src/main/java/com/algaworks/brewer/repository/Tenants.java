package com.algaworks.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.Tenant;

@Repository
public interface Tenants extends JpaRepository<Tenant, Long> {
	
	
	public Optional<Tenant> findByTenantIdIgnoreCase(String tenantId);

}
