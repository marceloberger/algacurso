package com.algaworks.brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.dto.FotoDTO;

public class FotoStorageRunnable implements Runnable {
	
	
	private MultipartFile[] files;
	private DeferredResult<FotoDTO> resultado;
	private FotoStorage fotoStorage;
	private String tenantId;
	
	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> resultado, FotoStorage fotoStorage, String tenantId) {
		this.files = files;
		this.resultado = resultado;
		this.fotoStorage = fotoStorage;
		this.tenantId = tenantId;
	}

	@Override
	public void run() {
		
		
		String novoNome = this.fotoStorage.salvar(files, tenantId);
		
		String nomeFoto = novoNome;
		
		String contentType = files[0].getContentType();
		// TODO: Salvar a foto no sistema de arquivos...
		resultado.setResult(new FotoDTO(nomeFoto, contentType, tenantId));
	}

}
