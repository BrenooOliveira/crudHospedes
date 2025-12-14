package com.fatec.crudHospedes.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fatec.crudHospedes.domain.model.HospedeModel;

public interface HospedeRepository extends JpaRepository<HospedeModel, Long> {

    boolean existsByCpf(String cpf);

    @Query("""
        SELECT h FROM HospedeModel h
        WHERE (:nome IS NULL OR LOWER(h.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
          AND (:cpf IS NULL OR h.cpf LIKE CONCAT('%', :cpf, '%'))
          AND (:cidade IS NULL OR LOWER(h.endereco.cidade) LIKE LOWER(CONCAT('%', :cidade, '%')))
          AND (:ativo IS NULL OR h.ativo = :ativo)
    """)
    List<HospedeModel> filtrar(
            @Param("nome") String nome,
            @Param("cpf") String cpf,
            @Param("cidade") String cidade,
            @Param("ativo") Boolean ativo
    );
}
