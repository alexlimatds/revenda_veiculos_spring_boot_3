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

public class VeiculoRepositoryCustomImpl implements VeiculoRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Veiculo> findVeiculoByFields(String placa, Integer idFabricante, Integer idModelo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Veiculo> query = cb.createQuery(Veiculo.class);
        Root<Veiculo> veiculoRoot = query.from(Veiculo.class);
        
        List<Predicate> predicates = new ArrayList<>(); //filtros
        if(idFabricante != null){
            Join<Veiculo, Modelo> modeloJoin = veiculoRoot.join("modelo", JoinType.INNER);
            Join<Modelo, Fabricante> fabricanteJoin = modeloJoin.join("fabricante", JoinType.INNER);
            predicates.add(
                cb.equal(
                    fabricanteJoin.get("id"),
                    idFabricante
                )
            );
        }
        if(idModelo != null){
            Join<Veiculo, Modelo> modeloJoin = veiculoRoot.join("modelo", JoinType.INNER);
            predicates.add(
                cb.equal(
                    modeloJoin.get("id"),
                    idModelo
                )
            );
        }
        if(placa != null){
            predicates.add(
                cb.like(
                    cb.lower(veiculoRoot.get("placa")),
                    "%" + placa.toLowerCase() + "%"
                )
            );
        }
        
        if(predicates.isEmpty())
            throw new IllegalArgumentException("Informe pelo menos um campo de consulta");
        
        query
            .select(veiculoRoot)
            .where(
                cb.and(
                    predicates.toArray(new Predicate[predicates.size()])
                )
            );
        
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public <S extends Veiculo> S save(S veiculo) {
        if(veiculo.getId() != null && veiculo.getFoto() == null){ // preserva foto já cadastrada
            Veiculo veiculoEmBanco = entityManager.find(Veiculo.class, veiculo.getId());
            veiculo.setFoto(veiculoEmBanco.getFoto());
        }
        entityManager.merge(veiculo);
        return veiculo;
    }
    
}