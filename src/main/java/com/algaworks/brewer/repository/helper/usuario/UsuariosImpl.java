package com.algaworks.brewer.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Grupo;
import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.model.UsuarioGrupo;
import com.algaworks.brewer.repository.filter.UsuarioFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class UsuariosImpl implements UsuariosQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@Override
	public Optional<Usuario> porEmailEAtivo(String email) {
		
		return manager
				.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
						.setParameter("email", email)
						
						.getResultList().stream().findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager.createQuery(
				"select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable, String tenantId) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria, tenantId);
		
		List<Usuario> list = criteria.list();
		
		list.forEach(u -> Hibernate.initialize(u.getGrupos()));
		
		return new PageImpl<>(list, pageable, total(filtro, tenantId));
	}
	
	private void adicionarFiltro(UsuarioFilter filtro, Criteria criteria, String tenantId) {
		if (filtro != null) {
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			
			if(!StringUtils.isEmpty(tenantId)) {
				
				
				
				criteria.add(Restrictions.ilike("tenantId", tenantId, MatchMode.EXACT));
				
				
			}
			
			if (!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.START));
			}
			
			//criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
			
			if(filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
				
				List<Criterion> subqueries = new ArrayList<>();
				for (long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("id.grupo.codigo", codigoGrupo));
					
					dc.setProjection(Projections.property("id.usuario"));
					
					
					
					subqueries.add(Subqueries.propertyIn("codigo", dc));
					
				}
				
				Criterion[] criterions = new Criterion[subqueries.size()];
				
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
				
			}

			
		}
	}
	
	
	private Long total(UsuarioFilter filtro, String tenantId) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		adicionarFiltro(filtro, criteria, tenantId);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	
	
	
	
	
	
	

}
