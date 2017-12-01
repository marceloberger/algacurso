package com.algaworks.brewer.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {
	
	private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);
	
	private Path local;
	
	//private Path localUsuario;
	
	
	
	
	public FotoStorageLocal() {
		this(FileSystems.getDefault().getPath(System.getenv("HOME"), ".brewerfotos"));
		
		
		
		
	}
	
	public FotoStorageLocal(Path path) {
		this.local = path;
		criasPastas();
		
	}


	


	@Override
	public String salvar(MultipartFile[] files, String tenantId) {
		
		String novoNome = null;
		
		boolean exists = Files.exists(this.local.resolve(tenantId));
		
		Path localUsuario = FileSystems.getDefault().getPath(this.local.toString(), tenantId);
        
        Path imagemUsuario = FileSystems.getDefault().getPath(localUsuario.toString(), "imagem");
        
        Path videoUsuario = FileSystems.getDefault().getPath(localUsuario.toString(), "video");
		
		if(!exists) {
			
            
			
            try {
			Files.createDirectories(localUsuario);
			
			Files.createDirectories(imagemUsuario);
			
			Files.createDirectories(videoUsuario);
			
			Files.copy(this.local.resolve("cerveja-mock.png"), imagemUsuario.resolve("cerveja-mock.png"));
			
			Files.copy(this.local.resolve("cerveja-mock.png"), imagemUsuario.resolve("thumbnail.cerveja-mock.png"));
			
			
			
			
			
            } catch (IOException e) {
    			// TODO Auto-generated catch block
    			throw new RuntimeException("Erro criando pasta do usuario", e);
    		}


		}
		
		
		
		if(files != null && files.length > 0 ) {
			
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			
			try {
				arquivo.transferTo(new File(imagemUsuario.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + novoNome));
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("Erro salvando a foto", e);
			}
			
			
			
		}
		
		try {
			Thumbnails.of(imagemUsuario.resolve(novoNome).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
			
			
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando thumbnail", e);
		}
		
		
		return novoNome;
		
		
	}
	
	
	
	@Override
	public byte[] recuperar(String nomeFoto, String tenantId) {
			try {
			
			

			Path localUsuario = FileSystems.getDefault().getPath(this.local.toString(), tenantId);
		        
		    Path imagemUsuario = FileSystems.getDefault().getPath(localUsuario.toString(), "imagem");
			
			return Files.readAllBytes(imagemUsuario.resolve(nomeFoto));
			
			
			
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo uma foto ", e);
		}
	}
	
	
	
	private void criasPastas() {
		 try {
			Files.createDirectories(this.local);
			
			
			
			
			
			
			if (logger.isDebugEnabled()) {
				logger.debug("Pastas criadas para salvar fotos.");
				logger.debug("Pasta default: " + this.local.toAbsolutePath());
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}
		
	}
	
	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Nome original: %s, novo nome: %s", nomeOriginal, novoNome));
		}
		
		return novoNome;
		
	}

	@Override
	public void apagarFoto(String nome, String tenantId) {
		try {
			
			Path localUsuario = FileSystems.getDefault().getPath(this.local.toString(), tenantId);
	        
		    Path imagemUsuario = FileSystems.getDefault().getPath(localUsuario.toString(), "imagem");
			
			Files.deleteIfExists(imagemUsuario.resolve(nome));
			Files.deleteIfExists(imagemUsuario.resolve("thumbnail." + nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao apagar a foto", e);
		}
		
	}

	

	

	
	
	
	
	
}
