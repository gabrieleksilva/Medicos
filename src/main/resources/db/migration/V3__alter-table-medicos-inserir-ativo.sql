alter table medicos add ativo tinyint;
-- O problema é: como fica este campo nos registros já existentes?
-- Vai ficar como null.
-- Isso é um erro, deveria ser ativo ou não ativo.
-- Vamos então fazer um comando SQL para trocar o campo para 1
-- em todos os registros. Isto é, no momento da migration
-- (criação do compo), todos os registros estarão "ativos":
update medicos set ativo = 1;
-- Poderíamos colocar aqui embaixo mais um comando para tornar
-- essa coluna "not null".