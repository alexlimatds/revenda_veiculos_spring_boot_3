package net.revenda.dominio;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ModeloRepositoryCustomImpl implements ModeloRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Modelo> findModeloByFields(String descricao, Integer idFabricante, Integer idTipoVeiculo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Modelo> query = cb.createQuery(Modelo.class);
        Root<Modelo> modeloRoot = query.from(Modelo.class);
        
        List<Predicate> predicates = new ArrayList<>(); //filtros
        if(idFabricante != null){
            Join<Modelo, Fabricante> fabricanteJoin = modeloRoot.join("fabricante", JoinType.INNER);
            predicates.add(
                cb.equal(
                    fabricanteJoin.get("id"),
                    idFabricante
                )
            );
        }
        if(idTipoVeiculo != null){
            Join<Modelo, TipoVeiculo> tipoJoin = modeloRoot.join("tipo", JoinType.INNER);
            predicates.add(
                cb.equal(
                    tipoJoin.get("id"),
                    idTipoVeiculo
                )
            );
        }
        if(descricao != null){
            predicates.add(
                cb.like(
                    cb.lower(modeloRoot.get("descricao")),
                    "%" + descricao.toLowerCase() + "%"
                )
            );
        }

        if(predicates.isEmpty())
            throw new IllegalArgumentException("Informe pelo menos um campo de consulta");
        
        query
            .select(modeloRoot)
            .where(
                cb.and(
                    predicates.toArray(new Predicate[predicates.size()])
                )
            );

        return entityManager.createQuery(query).getResultList();
    }
    
}