package com.zak.boursesocieteservice.dao;

import com.zak.boursesocieteservice.entities.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SocieteRepository extends JpaRepository<Societe,Long> {
}
