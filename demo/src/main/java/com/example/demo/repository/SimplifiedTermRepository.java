package com.example.demo.repository;


import com.example.demo.model.SimplifiedTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimplifiedTermRepository extends JpaRepository<SimplifiedTerm, Long> {
}