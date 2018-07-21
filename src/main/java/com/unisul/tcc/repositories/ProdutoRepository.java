package com.unisul.tcc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unisul.tcc.domain.Categoria;
import com.unisul.tcc.domain.Produto;

@Repository
@Transactional(readOnly=true)
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
	/**
	 * Using JPQL to construct the query or to construct the query by the name's rules.
	 * @param nome
	 * @param categorias
	 * @param pageRequest
	 * @return Page<Produto>
	 */
	@Query("select distinct obj from Produto obj inner join obj.categorias cat where obj.nome like %:nome% and cat in :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome,@Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
}
