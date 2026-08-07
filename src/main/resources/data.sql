INSERT INTO categoria (id, nome, descricao)
VALUES (1, 'Marmitas', 'Pratos do dia'),
    (2, 'Bebidas', 'Refrigerantes e sucos');
INSERT INTO usuario (id, nome, senha, email, cargo, data_criacao)
VALUES (
        1,
        'Admin',
        '$2b$10$f48VGE3.4Ype5qM2UxE9VuQaFR6mgl5bI9JQCw6G/Uq4SwYXc6kpK',
        'admin@marmitech.com',
        'ADMIN',
        '2026-08-07'
    ),
    (
        2,
        'Funcionario',
        '$2b$10$f48VGE3.4Ype5qM2UxE9VuQaFR6mgl5bI9JQCw6G/Uq4SwYXc6kpK',
        'func@marmitech.com',
        'FUNCIONARIO',
        '2026-08-07'
    ),
    (
        3,
        'Eliana Martins',
        '$2b$10$f48VGE3.4Ype5qM2UxE9VuQaFR6mgl5bI9JQCw6G/Uq4SwYXc6kpK',
        'eliana.martins@gmail.com',
        'CLIENTE',
        '2026-08-07'
    );
INSERT INTO cliente (
        id,
        nome,
        email,
        telefone,
        cpf_cnpj,
        endereco,
        data_cadastro
    )
VALUES (
        1,
        'Eliana Martins',
        'eliana.martins@gmail.com',
        '45999120034',
        '52418796031',
        'Rua das Palmeiras, 128',
        '2026-08-07'
    ),
    (
        2,
        'Camila Souza',
        'camila.souza@gmail.com',
        '45998451276',
        '31875642008',
        'Av. Brasil, 2450',
        '2026-08-07'
    );
INSERT INTO produto (
        id,
        sku,
        nome,
        descricao,
        preco_unitario,
        estoque,
        data_cadastro,
        categoria_id
    )
VALUES (
        1,
        'MRM-P-001',
        'Marmita P',
        'Arroz, feijao e bife',
        18.90,
        60,
        '2026-08-07',
        1
    ),
    (
        2,
        'BEB-R-009',
        'Pepsi Lata',
        'Refrigerante 350ml',
        6.00,
        120,
        '2026-08-07',
        2
    );
INSERT INTO pedido (
        id,
        data_pedido,
        valor_total,
        status,
        endereco_entrega,
        usuario_id,
        cliente_id
    )
VALUES (
        1,
        '2026-08-07',
        43.80,
        'FILA',
        'Rua das Palmeiras, 128',
        2,
        1
    ),
    (
        2,
        '2026-08-06',
        24.90,
        'ENTREGUE',
        'Rua das Palmeiras, 128',
        2,
        1
    ),
    (
        3,
        '2026-08-07',
        18.90,
        'PREPARANDO',
        'Av. Brasil, 2450',
        2,
        2
    );
INSERT INTO pedido_item (
        id,
        quantidade,
        preco_unitario_pedido,
        subtotal,
        pedido_id,
        produto_id
    )
VALUES (1, 2, 18.90, 37.80, 1, 1),
    (2, 1, 6.00, 6.00, 1, 2),
    (3, 1, 18.90, 18.90, 2, 1),
    (4, 1, 6.00, 6.00, 2, 2),
    (5, 1, 18.90, 18.90, 3, 1);
INSERT INTO historico_compra (
        id,
        data_evento,
        tipo_evento,
        descricao,
        pedido_id
    )
VALUES (
        1,
        '2026-08-07',
        'CRIACAO',
        'Pedido registrado',
        1
    ),
    (
        2,
        '2026-08-06',
        'ENTREGA',
        'Pedido entregue',
        2
    );