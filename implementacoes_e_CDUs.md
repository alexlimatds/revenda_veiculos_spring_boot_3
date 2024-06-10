# Implementações e casos de uso

## CRUD Fabricantes
- Renderização de HTML realizada no lado servidor (sem Ajax).

## CRUD Tipo de Veículo
- Renderização de HTML realizada no lado servidor (sem Ajax).

## CRUD Modelos
- Pesquisa de modelos usa JPA Criteria (consulta dinâmica);
- Pesquisa de modelos usa Ajax sem JSON. O servidor retorna o fragamento de página a ser atualizada como HTML.
- Uso de Ajax na exclusão de um veículo.

## CRUD Veículos
- Pesquisa de veículos usa JPA Criteria (consulta dinâmica);
- Uso de Ajax na atualização dos campos de seleção na página de pesquisa e de cadastro/edição;
- Uso de Ajax na atualização da tabela após uma consulta. Servidor retorna dados em JSON;
- Uso de Ajax na exclusão de um veículo.
