ALTER TABLE tenant

 ADD nome_empresa VARCHAR(50) NOT NULL,
 ADD email_empresa VARCHAR(50) NOT NULL,
  ADD nome_admin VARCHAR(50) NOT NULL,
 ADD senha VARCHAR(120) NOT NULL,
 ADD ativo BOOLEAN DEFAULT true;
   