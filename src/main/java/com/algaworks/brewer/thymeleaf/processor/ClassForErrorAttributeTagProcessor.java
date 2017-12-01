package com.algaworks.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring4.util.FieldUtils;
import org.thymeleaf.templatemode.TemplateMode;

public class ClassForErrorAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final String NOME_ATRIBUTO = "classforerror";
	private static final int PRECEDENCIA = 1000;
	
	public ClassForErrorAttributeTagProcessor(String dialectPrefix) {
		//dialectPrefix significa o nome do dialeto no nosso caso brewer
		//terceirpo parametro seria no casao se fossemos construir um novo elemento html
		//quinto elemnto é quando queremos um atributo classforerror
		//precedencia dissemos qual atributo tem prioridade em relação ao outro
		//ultimo true quando nao queremos que o elemento aparece no html caso fosse falso o elemento iria aparecer
		super(TemplateMode.HTML, dialectPrefix, null, false, NOME_ATRIBUTO, true, PRECEDENCIA, true);
	}
	
	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		
		//attributeValue é o valor do atributo passado no elemento
		
		boolean temErro = FieldUtils.hasErrors(context, attributeValue);
		
		//FieldUtils spring para verificar os erros dos campos
		
		if (temErro) {
			String classesExistentes = tag.getAttributeValue("class");
			//tag onde se encobntra o atributo
			structureHandler.setAttribute("class", classesExistentes + " has-error");
		}
	}

}