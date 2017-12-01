package com.algaworks.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	
	
	public String salvar(MultipartFile[] files, String tenantId);
	
	public byte[] recuperar(String foto, String tenantId);
	
	public void apagarFoto(String nome, String tenantId);

}
