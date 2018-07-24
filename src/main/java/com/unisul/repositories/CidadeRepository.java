package com.unisul.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unisul.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{
	
	@Transactional(readOnly = true)
	@Query("select distinct obj from Cidade obj where obj.estado.id = :estado_id order by obj.nome")
	public List<Cidade> findCidades(@Param("estado_id") Integer estado_id);

}
