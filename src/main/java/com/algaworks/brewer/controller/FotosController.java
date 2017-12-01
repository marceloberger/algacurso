package com.algaworks.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.dto.FotoDTO;
import com.algaworks.brewer.storage.FotoStorage;
import com.algaworks.brewer.storage.FotoStorageRunnable;
import com.algaworks.brewer.tenant.TenancyInterceptor;

@RestController
public class FotosController {
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@PostMapping("/{tenantid}/fotos")
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<FotoDTO> resultado = new DeferredResult<>();
		
		String tenantId = TenancyInterceptor.getTenantId();

		Thread thread = new Thread(new FotoStorageRunnable(files, resultado, fotoStorage, tenantId));
		thread.start();
		
		return resultado;
	}
	
	
	
	
	@GetMapping("/{tenantid}/fotos/{nome:.*}")
	public byte[] recuperar(@PathVariable String nome) {
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		return fotoStorage.recuperar(nome, tenantId);
		
	}
	
	@RequestMapping(path= "/{tenantid}/fotos/{nome:.*}", method={RequestMethod.DELETE})
	public void apagarFoto(@PathVariable String nome) {
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		fotoStorage.apagarFoto(nome, tenantId);
	}
	
	

}
