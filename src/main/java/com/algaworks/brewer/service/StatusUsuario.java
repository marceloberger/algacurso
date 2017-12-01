package com.algaworks.brewer.service;

import com.algaworks.brewer.repository.Usuarios;

public enum StatusUsuario {
	
	ATIVAR {
		@Override
		public void executar(Long[] codigos, Usuarios usuarios, String tenantUsuario) {
			usuarios.findByTenantIdAndCodigoIn(tenantUsuario, codigos).forEach(u -> u.setAtivo(true));
			
		}
	},
	DESATIVAR {
		@Override
		public void executar(Long[] codigos, Usuarios usuarios, String tenantUsuario) {
			usuarios.findByTenantIdAndCodigoIn(tenantUsuario, codigos).forEach(u -> u.setAtivo(false));
			
		}
	};
	
	public abstract void executar(Long[] codigos, Usuarios usuarios, String tenantUsuario);

}
